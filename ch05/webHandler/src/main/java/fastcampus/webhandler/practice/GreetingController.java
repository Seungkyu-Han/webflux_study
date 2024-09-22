package fastcampus.webhandler.practice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/greet")
public class GreetingController {

    @ResponseBody
    @GetMapping(params = "name")
    Mono<String> greetQueryParam(@RequestParam String name){
        String content = "Hello " + name;
        return Mono.defer(() -> Mono.just(content));
    }
}
