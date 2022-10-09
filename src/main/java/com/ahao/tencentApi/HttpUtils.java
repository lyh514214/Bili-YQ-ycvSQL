package com.ahao.tencentApi;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpUtils {

    @Bean
    public String getData() throws IOException {
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(30000)
                .setConnectTimeout(30000)
                .setConnectionRequestTimeout(30000)
                .build();

        CloseableHttpClient httpClient = null;
        HttpGet request = null;
        CloseableHttpResponse response = null;

        try {
            httpClient = HttpClients.createDefault();
            request = new HttpGet("https://c.m.163.com/ug/api/wuhan/app/data/list-total");
            request.setConfig(config);

            response = httpClient.execute(request);

            int code = response.getStatusLine().getStatusCode();

            if (code == 200){
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity,"UTF-8");
            }else {
                throw new HttpResponseException(code,"响应异常");
            }
        } finally {
            if (response != null) {
                response.close();
            }
            if (request != null) {
                request.releaseConnection();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
    }
}
