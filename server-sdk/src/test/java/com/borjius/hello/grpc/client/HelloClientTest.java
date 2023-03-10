package com.borjius.hello.grpc.client;

// not a normal test, just to validate against the main code and server running
public class HelloClientTest {

    public static void main(String[] args) {
        final BasicCredentials credentials = new BasicCredentials("userMine", "password22");
        final HelloClient client = new HelloClient(credentials);

        System.out.println(client.hello("John", "Doe"));

    }
}
