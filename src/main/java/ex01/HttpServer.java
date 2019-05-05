package ex01;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Create by leonardo on 2019/5/5
 */
public class HttpServer {

    public static final String WEB_ROOT = System.getProperty("user.dir")+ File.separator+"webroot";
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }

    public void await() {
        ServerSocket ss = null;
        int port = 8080;
        try {
            ss = new ServerSocket(port,1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (!shutdown) {
            Socket socket = null;
            InputStream in = null;
            OutputStream out = null;
            try {
                socket = ss.accept();
                in = socket.getInputStream();
                out = socket.getOutputStream();
                //创建请求对象并解析
                Request request = new Request(in);
                request.parse();
                //创建响应对象
                Response response = new Response(out);
                response.setRequest();
                response.sendStaticResource();
                //关闭套接字
                socket.close();
                shutdown = request.getUri().equalsIgnoreCase(SHUTDOWN_COMMAND);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

}
