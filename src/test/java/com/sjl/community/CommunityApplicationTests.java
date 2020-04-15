package com.sjl.community;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class CommunityApplicationTests {

    @Test
    void contextLoads() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url("http://open.iciba.com/dsapi")
                .build();
        try (Response response = client.newCall(request).execute()){
            String string = response.body().string();
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
