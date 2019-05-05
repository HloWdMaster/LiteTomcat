package ex01;

import java.io.*;
import java.net.Socket;

/**
 * Create by leonardo on 2019/5/5
 */
public class MyClient {

    public static void main(String[] args) throws InterruptedException {
        try {
            Socket ss = new Socket("localhost", 8081);
            OutputStream os = ss.getOutputStream();
            boolean autoFlash = true;
            PrintWriter out = new PrintWriter(ss.getOutputStream(), autoFlash);
            BufferedReader in = new BufferedReader(new InputStreamReader(ss.getInputStream()));
            //发送一个http请求
            out.println("GET /index.html  HTTP/1.1");
            out.println("Host: localhost:8080");
            out.println("Connection: Close");
            out.println();
            //读取返回消息
            boolean loop = true;
            StringBuffer sb = new StringBuffer(8096);
            while (loop) {
                if (in.ready()) {
                    int i = 0;
                    while (i !=-1) {
                        i = in.read();
                        sb.append((char) i);
                    }
                    loop = false;
                }
                Thread.currentThread().sleep(50);
            }
            System.out.println(sb.toString());
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
