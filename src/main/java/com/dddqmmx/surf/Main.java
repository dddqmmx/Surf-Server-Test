package com.dddqmmx.surf;

import com.dddqmmx.surf.socket.tcp.TCPServer;
import com.dddqmmx.surf.socket.udp.UDPServer;

public class Main {
    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer();
        tcpServer.start();
        UDPServer udpServer = new UDPServer();
        udpServer.start();
    }
}
