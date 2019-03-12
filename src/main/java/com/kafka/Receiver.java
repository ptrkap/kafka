package com.kafka;

public class Receiver {

    private static final int MESSAGE_COUNT = 1000;
    private static final String CLIENT_ID = "client1";
    private static final String TOPIC_NAME = "foobar";
    private static final String GROUP_ID_CONFIG = "group1";
    private static final int MAX_NO_MESSAGE_FOUND_COUNT = 100;
    private static final String OFFSET_RESET_LATEST = "latest";
    private static final String OFFSET_RESET_EARLIER = "earliest";
    private static final int MAX_POLL_RECORDS = 1;
}
