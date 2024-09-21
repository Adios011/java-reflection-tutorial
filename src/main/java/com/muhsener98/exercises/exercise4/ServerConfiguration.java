package com.muhsener98.exercises.exercise4;

import java.net.InetSocketAddress;

public class ServerConfiguration {
    private final InetSocketAddress serverAddress;
    private final String greetingMessage;

    private static ServerConfiguration INSTANCE;

    private ServerConfiguration(int port, String greetingMessage) {
        this.serverAddress = new InetSocketAddress("localhost", port);
        this.greetingMessage = greetingMessage;

        if (INSTANCE == null) INSTANCE = this;
    }


    public InetSocketAddress getServerAddress() {
        return serverAddress;
    }

    public String getGreetingMessage() {
        return greetingMessage;
    }


    public static ServerConfiguration getInstance() {
        return INSTANCE;
    }
}
