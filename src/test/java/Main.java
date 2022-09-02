import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
            Socket socket = null;
            try {
                socket = new Socket("127.0.0.1", 2042);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //从socket通信管道中得到一个字节输入流
            OutputStream os = null;
            try {
                os = socket.getOutputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //包装为高级打印流
            PrintStream ps = new PrintStream(os);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("command","connect");
            jsonObject.put("system","android");
            ps.println(jsonObject);
            ps.flush();
            socket.close();

    }
}
