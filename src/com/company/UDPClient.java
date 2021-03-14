package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    public void run() throws IOException {
        System.out.println("In the run of UDPClient");
        DatagramSocket ds = new DatagramSocket();
        ds.setSoTimeout(1000);
        /**
         * 注意到客户端的DatagramSocket还调用了一个connect()方法“连接”到指定的服务器端。
         * 不是说UDP是无连接的协议吗？为啥这里需要connect()？
         * 这个connect()方法不是真连接，它是为了在客户端的DatagramSocket实例中保存服务器端的IP和端口号，
         * 确保这个DatagramSocket实例只能往指定的地址和端口发送UDP包，不能往其他地址和端口发送。这么做不是UDP的限制，而是Java内置了安全检查。
         * 如果客户端希望向两个不同的服务器发送UDP包，那么它必须创建两个 DatagramSocket 实例。后续的收发数据和服务器端是一致的。
         * 通常来说，客户端必须先发UDP包，因为客户端不发UDP包，服务器端就根本不知道客户端的地址和端口号。
         *
         * 如果客户端认为通信结束，就可以调用disconnect()断开连接：
         *      ds.disconnect();
         * 注意到disconnect()也不是真正地断开连接，
         * 它只是清除了客户端DatagramSocket实例记录的远程服务器地址和端口号，这样，DatagramSocket实例就可以连接另一个服务器端。
         */
        ds.connect(InetAddress.getByName("localhost"), 8090); // 连接指定服务器和端口
        // 发送:
        String message = "1234567890";
        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length);
        ds.send(packet);
        // 接收:
        byte[] buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        ds.receive(packet);
        String resp = new String(packet.getData(), packet.getOffset(), packet.getLength());
        System.out.println("[client receive]" + resp);
        ds.disconnect();
    }
}
