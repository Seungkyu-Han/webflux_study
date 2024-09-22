package fastcampus.webhandler.practice;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class WebClientExample {

    @SneakyThrows
    public static void main(String[] args) {
        WebClient webClient = WebClient.create("http://localhost:8081");

        var response = webClient
                .get()
                .uri("/user")
                .header("X-Request-Id", "1204")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class)
                .block();
    }
}
