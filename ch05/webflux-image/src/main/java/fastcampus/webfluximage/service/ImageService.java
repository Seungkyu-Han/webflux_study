package fastcampus.webfluximage.service;

import fastcampus.webfluximage.entity.common.Image;
import fastcampus.webfluximage.repository.ImageReactorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ImageService {

    private ImageReactorRepository imageReactorRepository = new ImageReactorRepository();

    public Mono<Image> getImageById(String imageId){
        return imageReactorRepository.findById(imageId)
                .map(imageEntity ->
                        new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl()));
    }
}
