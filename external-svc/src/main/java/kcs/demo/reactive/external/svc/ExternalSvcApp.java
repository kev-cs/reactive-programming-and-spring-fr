package kcs.demo.reactive.external.svc;

import static java.time.Duration.ofSeconds;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.List;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ExternalSvcApp {

  public static void main(String[] args) {
    SpringApplication.run(ExternalSvcApp.class, args);
  }

  @Bean
  RouterFunction<?> slowTotoRoute() {
    return
        route(GET("/slow-toto"), request -> {
              return ok().body(buildDelayedStringsMono(),
                  new ParameterizedTypeReference<List<String>>() {
                  });
            }
        );
  }

  private Mono<List<String>> buildDelayedStringsMono() {
    return Mono
        .fromSupplier(() -> "toto " + UUID.randomUUID())
        .repeat(10)
        .collectList()
        .delayElement(ofSeconds(5));
  }


}
