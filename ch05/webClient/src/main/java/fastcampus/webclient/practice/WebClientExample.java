package fastcampus.webclient.practice;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

@Slf4j
public class WebClientExample {

    @Data
    private static class UserInfo{
        private final String id;
        private final String name;
        private final String email;
    }

    public static void main(String[] args) {
        WebClient webClient = WebClient.create("http://localhost:8081");


        ResponseEntity<UserInfo> responseEntity = webClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/api/users")
                                .pathSegment("1234")
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(UserInfo.class)
                .block();

        assert responseEntity.getBody().getId().equals("1234");
        assert responseEntity.getStatusCode().is2xxSuccessful();
        assert responseEntity.getHeaders().getContentType()
                .toString().equals("application/json");
        assert responseEntity.getHeaders().get("X-Request-Id")
                .get(0).equals("1234");
    }
}
