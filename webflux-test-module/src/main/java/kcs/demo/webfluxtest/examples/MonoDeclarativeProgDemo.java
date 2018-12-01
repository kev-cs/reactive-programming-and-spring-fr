package kcs.demo.webfluxtest.examples;

import kcs.demo.webfluxtest.ExternalSvcReactiveRepo;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MonoDeclarativeProgDemo {

  private static Logger LOG = LoggerFactory.getLogger(MonoDeclarativeProgDemo.class);

  @Autowired
  private ExternalSvcReactiveRepo externalSvcReactiveRepo;

  @GetMapping("/dp-demo-mono")
  public Mono<String> callbacksDemo() {
    Mono<String> stringMono = createStringMono();
    LOG.info("mono created");
    return stringMono;
  }

  public Mono<String> createStringMono() {
    return Flux
        .range(0, 3)
        .flatMap(this::callExtSvcWithInt)
        .map(this::toUpperCase)
        .map(this::ajouterEspace)
        .reduce(String::concat)
        .map(this::ajouterPointExclamation)
        .log("cb demo service");
  }

  private Publisher<String> callExtSvcWithInt(Integer i) {
    return externalSvcReactiveRepo
        .getOneStringFromExternalSvc()
        .map(str -> i + "-" + str);
  }

  private String toUpperCase(String str) {
    return str.toUpperCase();
  }

  private String ajouterEspace(String str) {
    return str + ' ';
  }

  private String ajouterPointExclamation(String str) {
    return str + '!';
  }
}
