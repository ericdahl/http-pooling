package example;

import org.apache.http.HttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class StatsEmitter {

    private static final Logger logger = LoggerFactory.getLogger(StatsEmitter.class);

    private final PoolingHttpClientConnectionManager connectionManager;

    public StatsEmitter(PoolingHttpClientConnectionManager poolingConnManager) {
        this.connectionManager = poolingConnManager;
    }

    
    @Scheduled(fixedRate = 5000)
    public void printPoolStats() {
        PoolStats totalStats = connectionManager.getTotalStats();

        for (HttpRoute route : connectionManager.getRoutes()) {

            logger.info("{}: {}", route, connectionManager.getStats(route));

            try {
                // Access the 'pool' field
                Field poolField = PoolingHttpClientConnectionManager.class.getDeclaredField("pool");
                poolField.setAccessible(true); // Make the field accessible

                // Get the 'available' list from the 'pool' field
                Object pool = poolField.get(connectionManager);
                Field availableField = pool.getClass().getSuperclass().getDeclaredField("available");
                availableField.setAccessible(true); // Make the field accessible

                // Now access the available list
                List<?> availableList = (List<?>) availableField.get(pool);

                // Loop through the available connections
                for (Object entry : availableList) {
                    // Get the 'conn' field from each entry
                    Field connField = entry.getClass().getSuperclass().getDeclaredField("conn");
                    connField.setAccessible(true); // Make the field accessible

                    HttpClientConnection connection = (HttpClientConnection) connField.get(entry);
                    logger.info("{}", connection);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

}
