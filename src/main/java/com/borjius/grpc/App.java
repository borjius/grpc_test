package com.borjius.grpc;

public class App {
    public static void main( String[] args ) {
        new MyServer()
                .launchServer();
    }
}
