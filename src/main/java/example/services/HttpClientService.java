package example.services;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HttpClientService {

    private final CloseableHttpClient httpClient;


    public HttpClientService(CloseableHttpClient closeableHttpClient) {
        this.httpClient = closeableHttpClient;
    }
    public long count() {
        return 3;
    }

    private long fallbackCount() {
        return -1;
    }

    public String makeRequest() throws IOException {
        HttpGet request = new HttpGet("http://httpbin.org/delay/3");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return EntityUtils.toString(response.getEntity());
        }
    }
}
