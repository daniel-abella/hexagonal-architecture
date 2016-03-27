package com.hexagonal.architecture.price;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexagonal.architecture.image.Image;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ImageMicroserviceAdapter implements ImagePort{


    @Override
    public Image getImage(String id) {
        Image image = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:8081/images/" + id);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                String response = EntityUtils.toString(httpResponse.getEntity());
                image = new ObjectMapper().readValue(response, Image.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
