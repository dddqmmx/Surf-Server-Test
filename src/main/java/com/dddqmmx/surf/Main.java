package com.dddqmmx.surf;

import com.dddqmmx.surf.socket.connect.ConnectList;
import com.dddqmmx.surf.socket.tcp.TCPServer;
import com.dddqmmx.surf.socket.tcp.TCPServerThread;
import com.dddqmmx.surf.socket.udp.UDPServer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer();
        tcpServer.start();
        UDPServer udpServer = new UDPServer();
        udpServer.start();
    }
}
