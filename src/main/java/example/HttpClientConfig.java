package example;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.pool.PoolStats;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class HttpClientConfig {

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(50);
        connectionManager.setDefaultMaxPerRoute(20);
        return connectionManager;
    }

    @Bean
    public CloseableHttpClient closeableHttpClient(PoolingHttpClientConnectionManager connectionManager) {
        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();
    }


}
