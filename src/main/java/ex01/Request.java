package ex01;

import java.io.InputStream;

/**
 * Create by leonardo on 2019/5/5
 */
public class Request {

    private InputStream input;
    private String uri;

    public Request(InputStream input) {
        this.input = input;
    }

    public void parse() {
        //读取字符
        StringBuffer request = new StringBuffer(2048);

    }

    public String getUri() {
        return uri;
    }
}
