
package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    public static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.trace("We've just greeted the user!");
        logger.info("Hello1");
        logger.debug("Hello2");
        System.out.println("Hello3");

//        FileQueueService fileQueueService = new FileQueueService();
//        fileQueueService.push("https://sqs.us-east-1.amazonaws.com/1234/tempq","Hello Rakshit5");
        //fileQueueService.pull("https://sqs.us-east-1.amazonaws.com/1234/tempq");
       // fileQueueService.delete("https://sqs.us-east-1.amazonaws.com/1234/tempq","46b0e9f0-74b1-426b-8382-88695c3de4b2");
        //fileQueueService.purgeQueue("https://sqs.us-east-1.amazonaws.com/1234/tempq");
        InMemoryQueueService imqueueService = new InMemoryQueueService();
        imqueueService.push("url1","Hello Rakshit 1",4);
        imqueueService.push("url1","Hello Rakshit 2",2);
        imqueueService.push("url1","Hello Rakshit 3",1);
        imqueueService.push("url1","Hello Rakshit 4",1);

        imqueueService.printQdata();
        System.out.println("After pushing.....................");

        imqueueService.pull("url1");

        imqueueService.printQdata();


//        imqueueService.push("url1","Hello Rakshit1");
//        imqueueService.push("url1","Hello Rakshit2");
//        imqueueService.push("url1","Hello Rakshit3");
//        System.out.println(imqueueService.pull("url1").toString());
//        imqueueService.delete("url1","9dc91eb3-8a45-483d-834b-9bf4161c44fc");
//        System.out.println(imqueueService.pull("url1").toString());
    }
}