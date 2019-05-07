package ex02;

import java.io.IOException;

/**
 * Create by leonardo on 2019/5/7
 */
public class StaticResourceProcessor {
    public void process(Request request,Response response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
