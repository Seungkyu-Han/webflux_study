package fastcampus.sse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RequestMapping("/sse")
@Controller
public class SSeController {

    @ResponseBody
    @GetMapping(path = "/simple", produces = "text/event-stream")
    Flux<String> simpleSse(){
        return Flux.interval(Duration.ofMillis(100))
                .map(i -> "hello " + i);
    }
}
