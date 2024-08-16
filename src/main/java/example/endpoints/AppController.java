package example.endpoints;

import example.services.HttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class AppController {

    @Value("${HOSTNAME:unknown}")
    private String hostname;

    private final HttpClientService counterService;

    @Autowired
    public AppController(HttpClientService counterService) {
        this.counterService = counterService;
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String index() throws IOException {
        return counterService.makeRequest();
//        return "Hello from " + hostname;
    }
}
