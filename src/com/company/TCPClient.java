package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    public void run() throws IOException {
        Socket sock = new Socket("localhost", 8090); // 连接指定服务器和端口
        try (InputStream input = sock.getInputStream()) {
            try (OutputStream output = sock.getOutputStream()) {
                handle(input, output);
            }
        }
        sock.close();
        System.out.println("disconnected.");
    }

    private static void handle(InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
        Scanner scanner = new Scanner(System.in);
        System.out.println("[server] " + reader.readLine());
        for (;;) {
            System.out.print(">>> "); // 打印提示
            String s = scanner.nextLine(); // 读取一行输入
            writer.write(s);
            writer.newLine(); //它的 server 端是一读读一行，所以需要客户端发送 行分隔符
            writer.flush();
            String resp = reader.readLine();
            System.out.println("<<< " + resp);
            if (resp.equals("bye")) {
                break;
            }
        }
    }

}
