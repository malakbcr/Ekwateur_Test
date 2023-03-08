package com.example.ekwateurProject.configuration;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ApiConnection implements IApiConnection {

    /***
     * open Http Connection to retrieve the response from the API
     * @param { String } url
     * @return { String } responseBody
     */
    @Override
    public String OpenHttpConnection(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse;
        HttpGet httpGet = new HttpGet(url);
        StringBuilder responseBody = new StringBuilder();
        try {
            httpResponse = httpClient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                responseBody.append(inputLine);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
        return responseBody.toString();
    }

}
