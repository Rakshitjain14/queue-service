package com.example;

import java.io.Serializable;

public class PQMessage implements Comparable<PQMessage>,Serializable{
    private Integer rank;
    private Long time;
    private Message message;

    public PQMessage() {

    }

    public PQMessage(Integer rank, Long time, Message msg) {
        this.rank = rank;
        this.time = time;
        this.message = msg;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public int compareTo(PQMessage other) {
        // Higher rank messages should come after lower rank messages
        int rankComparison = Integer.compare(this.rank, other.getRank());
        if (rankComparison == 0) {
            return Long.compare(this.time, other.time);
        }
        return rankComparison;
    }

    @Override
    public String toString() {
        return "PQMessage{" +
                "rank=" + rank +
                ", time=" + time +
                ", message=" + message.toString() +
                '}';
    }
}
