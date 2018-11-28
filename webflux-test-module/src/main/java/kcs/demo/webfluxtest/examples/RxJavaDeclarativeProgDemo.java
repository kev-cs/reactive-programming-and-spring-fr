package kcs.demo.webfluxtest.examples;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import kcs.demo.webfluxtest.ExternalSvcReactiveRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RxJavaDeclarativeProgDemo {

  private static Logger LOG = LoggerFactory.getLogger(RxJavaDeclarativeProgDemo.class);

  @Autowired
  private ExternalSvcReactiveRepo externalSvcReactiveRepo;

  @GetMapping("/dp-demo-rx")
  public Observable<String> callbacksDemo() {
    Observable<String> stringObservable = createStringObservable();
    LOG.info("Observable created");
    return stringObservable;
  }

  public Observable<String> createStringObservable() {
    return Observable.range(0, 3)
        .flatMap(this::callExtSvcWithInt)
        .map(this::toUpperCase)
        .map(this::ajouterEspace)
        .reduce(String::concat)
        .map(this::ajouterPointExclamation)
        .toObservable();
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

  private ObservableSource<? extends String> callExtSvcWithInt(Integer i) {
    return Observable.fromPublisher(
        externalSvcReactiveRepo
            .getOneStringFromExternalSvc()
            .map(str -> str + i));
  }
}
