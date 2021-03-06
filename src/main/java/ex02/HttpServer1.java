package ex02;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Create by leonardo on 2019/5/6
 */
public class HttpServer1 {

    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer1 server = new HttpServer1();
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!shutdown) {
            Socket socket = null;
            InputStream in = null;
            OutputStream output = null;
            try {
                socket = serverSocket.accept();
                in = socket.getInputStream();
                output = socket.getOutputStream();
                //创建请求对象
                Request request = new Request(in);
                request.parse();
                Response response = new Response(output);
                response.setRequest(request);
                //判断请求是servlet还是静态资源
                if (request.getUri()!=null && request.getUri().startsWith("/servlet/")) {
                    ServletProcessor processor = new ServletProcessor();
                    processor.process(request,response);
                } else {
                    StaticResourceProcessor processor = new StaticResourceProcessor();
                    processor.process(request,response);
                }
                socket.close();
                shutdown = request.getUri().equalsIgnoreCase(SHUTDOWN_COMMAND);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

}
