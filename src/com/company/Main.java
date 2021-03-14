package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
        // String type = "tcp";
        String type = "udp";
        if(type.equals("tcp")){
            TCPClient client = new TCPClient();
            client.run();
        } else if (type.equals("udp")) {
            UDPClient client = new UDPClient();
            client.run();
        }
    }
}

