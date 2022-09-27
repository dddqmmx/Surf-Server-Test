import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("client host : ");
            String ip = scanner.next();
            System.out.println("client port : ");
            int port = scanner.nextInt();
            DatagramSocket socket = null;
            try {
                String msg = scanner.next();
                InetAddress inetAddress = InetAddress.getByName(ip);
                byte[] data = msg.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(data, data.length,inetAddress,port);
                socket = new DatagramSocket();
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (socket != null) {
                    socket.close();
                }
            }
        }
    }
}
