package fastcampus.webflux.practice;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.RequestPath;

import java.net.URI;

@Slf4j
public class RequestPathExample {

    @SneakyThrows
    public static void main(String[] args) {
        URI uri = new URI("http://localhost:8080/app/api/hello?name=seungkyu");
        RequestPath path = RequestPath.parse(uri, "/app");

        log.info("path.pathWithInApplication: {}", path.pathWithinApplication());
        log.info("path.contextPath: {}", path.contextPath());
    }
}
