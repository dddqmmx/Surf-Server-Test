package com.dddqmmx.surf.socket.tcp;

import com.dddqmmx.surf.socket.connect.Connect;
import com.dddqmmx.surf.socket.connect.ConnectList;
import com.dddqmmx.surf.socket.connect.SocketSession;
import com.dddqmmx.surf.util.RandomCharacters;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class TCPServerThread extends Thread{
    private final Socket socket;
    public TCPServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //从socket通信管道中得到一个字节输入流
            InputStream is = socket.getInputStream();
            //把字节输入流转换成字符输入流
            InputStreamReader isr = new InputStreamReader(is);
            //把字符输入流包装为缓冲字符输入流
            BufferedReader br = new BufferedReader(isr);
            //按照行读取消息
            String line;
            while ((line = br.readLine())!= null){
                //消息处理
                System.out.println("TCP : "+line);

                JSONObject jsonObject = new JSONObject(line);
                //获取客户端提交json的命令
                String command = jsonObject.getString("command");

                if (command.equals("connect")){
                    //连接请求

                    //获得ip地址
                    String ip = socket.getInetAddress().getHostAddress();

                    String system = jsonObject.getString("system");
                    if (system.equals("android")){
                        /*随机生成32位数的sessionId*/
                        String sessionId = RandomCharacters.random(32);
                        while (ConnectList.hasSessionId(sessionId)) {
                            sessionId = RandomCharacters.random(32);
                        }
                        SocketSession socketSession = new SocketSession();
                        ConnectList.setSocketSession(sessionId,socketSession);
                        ConnectList.setThread(sessionId,this);
                        JSONObject comeBackJson = new JSONObject();
                        comeBackJson.put("command",command);
                        comeBackJson.put("connect","true");
                        comeBackJson.put("sessionId",sessionId);
                        System.out.println(sessionId);
                        send(comeBackJson);
                    } else {

                    }
                }
            }
            socket.close();
            System.out.println("over");
        }catch (Exception e){
            System.out.println("客户端"+socket.getRemoteSocketAddress()+"下线了。");
        }
    }

    public void send(String json){
        System.out.println("TCPSend : " + json);
        try {
            OutputStream os = socket.getOutputStream();
            PrintStream ps = new PrintStream(os);
            ps.println(json);
            ps.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void send(Object object){
        send(object.toString());
    }
}
