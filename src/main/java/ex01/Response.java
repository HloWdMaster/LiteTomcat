package ex01;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/*
  HTTP Response = Status-Line
    *(( general-header | response-header | entity-header ) CRLF)
    CRLF
    [ message-body ]
    Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
*/

public class Response {

    private static int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            if (file.exists()) {
                fis = new FileInputStream(file);
                StringBuilder heads = new StringBuilder("HTTP/1.1 200 OK\r\n");
                heads.append("Content-Type: text/html\r\n");    //头部
                StringBuilder body = new StringBuilder();     //读取相应主体
                int len;
                while ((len=fis.read(bytes,0,BUFFER_SIZE))!=-1) {
                    body.append(new String(bytes,0,BUFFER_SIZE));
                }
                heads.append(String.format("Content-Length: %d\n",body.toString().getBytes().length));
                heads.append("\r\n");
                output.write(heads.toString().getBytes());
                output.write(body.toString().getBytes());

//                int ch = fis.read(bytes, 0, BUFFER_SIZE);
//                while (ch != -1) {
//                    sb.append(bytes.toString());
//                    ch = fis.read(bytes, 0, BUFFER_SIZE);
//                }
//                output.write(sb.toString().getBytes());
            } else {
                // file not found
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
            }
        } catch (Exception e) {
            // thrown if cannot instantiate a File object
            System.out.println(e.toString());
        } finally {
            if (fis != null)
                fis.close();
        }
    }
}