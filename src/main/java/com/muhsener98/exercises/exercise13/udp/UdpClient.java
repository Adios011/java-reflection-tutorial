package com.muhsener98.exercises.exercise13.udp;

public class UdpClient {

    public void sendAndForget(String requestPayload) {
        System.out.println(String.format("Request : %s was sent through UPD", requestPayload));
    }
}
