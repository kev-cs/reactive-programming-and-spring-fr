package kcs.demo.webfluxtest.examples;

import java.time.Duration;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SignalsDemo {

  @GetMapping(value = "/signal-demo", params = "s")
  public Mono<String> signal(@RequestParam("s") String signal) {
    return Mono.just(signal).flatMap(s -> {
      if ("cancel".equals(s)) {
        return Mono.delay(Duration.ofSeconds(2)).thenReturn("time elapsed");
      }
      return Mono.just(s);
    }).map(s -> {
      switch (s) {
        case "complete":
          return "received complete signal";
        case "error":
          throw new RuntimeException("received error signal");
        default:
          return s;
      }
    }).doOnSubscribe(
        subscription -> System.out.println("new subscription : " + subscription.toString())
    ).doOnNext(
        str -> System.out.println("onNext : " + str)
    ).doOnSuccess(
        str -> System.out.println("success : " + str)
    ).doOnCancel(
        () -> System.out.println("mono cancelled")
    ).doOnError(throwable -> {
      throw new RuntimeException("onError callback, original message : " + throwable.getMessage());
    }).doOnSuccessOrError((s, throwable) -> {
      String exceptionMsg = Optional.ofNullable(throwable)
          .map(Throwable::getMessage)
          .orElse("n/a");
      System.out.println("success or err callback "
          + "\n\t input: " + s
          + "\n\t error:" + exceptionMsg);
    }).doOnTerminate(
        () -> System.out.println("terminate signal")
    ).doFinally(signalType -> {
      System.out.println("final signal : " + signalType);
    });
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(code = HttpStatus.I_AM_A_TEAPOT)
  public Mono<String> handleRuntimeException(RuntimeException re) {
    System.out.println("re catched" + re.getMessage());
    return Mono.just("Runtime exception catched : " + re.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public void handleException(Exception ex) {
    System.out.println("exception catched" + ex.getMessage());
  }
}
