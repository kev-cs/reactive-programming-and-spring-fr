package kcs.demo.webfluxtest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class ExternalSvcReactiveRepo {

  private WebClient webClient;

  public ExternalSvcReactiveRepo(@Value("${externalsvc.uri}") String externalSvcUri) {
    webClient = WebClient.create(externalSvcUri);
  }


  public Mono<String> getOneStringFromExternalSvc() {
    return webClient
        .get().uri("/slow-toto")
        .retrieve()
        .bodyToMono(String[].class)
        .map(strings -> strings[0])
        .map(str -> str.split(" ")[0])
        .log("rest client");
  }
}
