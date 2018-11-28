package kcs.demo.webfluxtomcatclientsvc;

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


  public Mono<String> getStringFromExternalSvc() {
    return webClient
        .get().uri("/slow-toto")
        .retrieve()
        .bodyToMono(String.class);
  }
}
