package com.borjius.hello.grpc.client;

// not a normal test, just to validate against the main code and server running
public class HelloClientTest {

    public static void main(String[] args) {
        System.out.println("Testing hello method");
        final BasicCredentials credentials = new BasicCredentials("userMine", "password22");
        HelloClient client = new HelloClient(credentials);

        System.out.println(client.hello("John", "Doe"));


        System.out.println("Testing Numbers method");

        client = new HelloClient();
        System.out.println(client.numbers("John", "Doe"));
    }


}
