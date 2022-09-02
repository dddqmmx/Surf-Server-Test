package com.dddqmmx.surf.socket.udp;

import com.dddqmmx.surf.socket.connect.ConnectList;
import com.dddqmmx.surf.util.RandomCharacters;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDPServerThread extends Thread {


    //前台传过来的信息
    DatagramPacket datagramPacket = null;
    String info = null;
    InetAddress inetAddress = null;
    int port = 0;

    //构造方法对UDPServer传进来的消息进行赋值
    public UDPServerThread(DatagramPacket packet, String info, InetAddress inetAddress, int port) {
        this.datagramPacket = packet;
        this.info = info;
        this.inetAddress = inetAddress;
        this.port = port;
    }

    //具体地处理逻辑
    @Override
    public void run() {
        String host = inetAddress.getHostAddress();
        System.out.println("UDP : "+info);
        JSONObject jsonObject = new JSONObject(info);
        //获取客户端提交json的命令
        String command = jsonObject.getString("command");
        if (command.equals("setIpPort")){
            int setPort = jsonObject.getInt("port");
            String session = RandomCharacters.random(50);
            while (ConnectList.hasSession(host,session)) {
                session = RandomCharacters.random(50);
            }
            ConnectList.setPost(host,session,setPort);
            JSONObject returnJson = new JSONObject();
            returnJson.put("command","setSession");
            returnJson.put("session",session);
            send(returnJson.toString());
        }
    }

    //发送消息
    public boolean send(String data){
        return send(data.getBytes(StandardCharsets.UTF_8));
    }
    public boolean send(byte[] data) {
        //不发为空的消息
        if (data == null){
            return false;
        }
        //debug用的
        System.out.println("UDP : " + new String(data));

        //发送回去的消息包
        DatagramPacket datagramPacket =  new DatagramPacket(data, data.length, inetAddress,port);

        //发送到客户端的socket
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            socket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            //最终关闭流
            socket.close();
        }
        return true;
    }
}