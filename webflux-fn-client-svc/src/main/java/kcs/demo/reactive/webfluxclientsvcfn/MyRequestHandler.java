package kcs.demo.reactive.webfluxclientsvcfn;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class MyRequestHandler {

  private WebClient webClient = WebClient.create();

  public Mono<ServerResponse> handleDefaultPath(ServerRequest request) {
    return ok().body(
        webClient.get().uri("http://localhost:8888/slow-toto")
            .retrieve().bodyToMono(String.class),
        String.class);
  }


}
