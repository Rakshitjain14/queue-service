package com.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class RedisTest {

    private RedisQService redisQService = new RedisQService();




    @Test
    public void testPull() {
        redisQService.push("redistest.com", "Push test",1);
        Message message = redisQService.pull("redistest.com");
        assertEquals("Push test", message.getBody());
        assertTrue(message.getReceiptId() != null && message.getReceiptId().length() > 0);
    }

    @Test
    public void testPush() {
        redisQService.push("redistest.com", "Push test",2);
        Message message = redisQService.pull("redistest.com");

        assertEquals("Push test", message.getBody());
        assertTrue(message.getReceiptId() != null && message.getReceiptId().length() > 0);
    }

}
