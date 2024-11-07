package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class InMemoryQueueService implements PQService {
  private final Map<String, PriorityBlockingQueue<PQMessage>> queues;

  private long visibilityTimeout;

  InMemoryQueueService() {
    this.queues = new ConcurrentHashMap<>();
    String propFileName = "config.properties";
    Properties confInfo = new Properties();

    try (InputStream inStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
      confInfo.load(inStream);
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.visibilityTimeout = Integer.parseInt(confInfo.getProperty("visibilityTimeout", "30"));
  }

  @Override
  public void push(String queueUrl, String msgBody,int rank) {
    PriorityBlockingQueue<PQMessage> queue = queues.get(queueUrl);
    if (queue == null) {
      queue = new PriorityBlockingQueue<>();
      queues.put(queueUrl, queue);
    }
    Long now = now();
    PQMessage pqMessage = new PQMessage(rank,now, new Message(msgBody));
    queue.add(pqMessage);
  }

  @Override
  public Message pull(String queueUrl) {
    PriorityBlockingQueue<PQMessage> queue = queues.get(queueUrl);
    if (queue == null) {
      return null;
    }
    long nowTime = now();
    while (!queue.isEmpty()) {
      PQMessage priorityMessage = queue.poll();//can use peek to get the topmost element
      if (priorityMessage == null) {
        return null;
      } else {
        Message msg = priorityMessage.getMessage();
        msg.setReceiptId(UUID.randomUUID().toString());
        msg.incrementAttempts();
        msg.setVisibleFrom(nowTime + TimeUnit.SECONDS.toMillis(visibilityTimeout));

        return new Message(msg.getBody(), msg.getReceiptId());
      }
    }
    return null;


  }

  @Override
  public void delete(String queueUrl, String receiptId) {
    PriorityBlockingQueue<PQMessage> queue = queues.get(queueUrl);
    if (queue != null) {
      long nowTime = now();

      for (PQMessage priorityMessage : queue) {
        Message message = priorityMessage.getMessage();
        if (!message.isVisibleAt(nowTime) && message.getReceiptId().equals(receiptId)) {
          queue.remove(priorityMessage);
          break;

        }
      }
    }
  }

  long now() {
    return System.currentTimeMillis();
  }

  public void printQdata() {
    for (Map.Entry<String, PriorityBlockingQueue<PQMessage>> entry : queues.entrySet()) {
      PriorityBlockingQueue<PQMessage> s = entry.getValue();
      Iterator iT = s.iterator();
      System.out.println("Contents of the queue are :");

      while (iT.hasNext()) {
        System.out.println(iT.next().toString());
      }

    }
  }
}
