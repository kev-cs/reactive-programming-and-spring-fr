package kcs.demo.webfluxtomcatclientsvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MyController {

  @Autowired
  private ExternalSvcReactiveRepo externalSvcReactiveRepo;

  @GetMapping
  public Mono<String> getSomething() {
    return externalSvcReactiveRepo.getStringFromExternalSvc();
  }

}
