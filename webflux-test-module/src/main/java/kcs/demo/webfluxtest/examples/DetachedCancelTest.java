package kcs.demo.webfluxtest.examples;

import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class DetachedCancelTest {

  @GetMapping(value = "/detached-cancel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Integer> launchCancelTest() {
    return Flux
        .range(1, 5)
        .delayElements(Duration.ofSeconds(1))
        .doOnNext(System.out::println)
        .publish()
        .autoConnect();

  }

}
