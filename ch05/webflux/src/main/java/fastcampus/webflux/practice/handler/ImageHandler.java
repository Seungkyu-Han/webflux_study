package fastcampus.webflux.practice.handler;

import fastcampus.webflux.practice.handler.dto.ImageResponse;
import fastcampus.webflux.practice.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageHandler {

    private final ImageService imageService;

    public Mono<ServerResponse> getImageById(ServerRequest serverRequest){
        String imageId = serverRequest.pathVariable("imageId");

        log.info("imageId: {}", imageId);

        return imageService.getImageById(imageId)
                .map(image -> new ImageResponse(
                        image.getId(), image.getName(), image.getUrl()
                )).flatMap(
                        imageResponse ->
                                ServerResponse.ok().bodyValue(imageResponse)
                )
                .onErrorMap(
                        (e) -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }
}
