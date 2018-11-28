package kcs.demo.webfluxtest.examples;

import java.util.UUID;
import java.util.concurrent.Flow.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.StringDecoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.adapter.JdkFlowAdapter;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RestController
public class ConsoleStreamDemo {

  private final Flux<String> consoleFlux;

  public ConsoleStreamDemo() {
    Flux<DataBuffer> sysInDataBufferFlux = DataBufferUtils
        .readInputStream(() -> System.in, new DefaultDataBufferFactory(), 256);
    Flux<String> decodedSysInFlux = StringDecoder.textPlainOnly()
        .decode(sysInDataBufferFlux, ResolvableType.forClass(String.class), null, null);

    this.consoleFlux = decodedSysInFlux
        .subscribeOn(Schedulers.single())
        .publish()
        .autoConnect();
  }

  @GetMapping(value = "console-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<String> subscribeToConsoleFlux() {
    return subscribeToConsole();
  }

  @GetMapping(value = "console-publisher", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Publisher<String> subscribeToConsolePublisher() {
    return JdkFlowAdapter.publisherToFlowPublisher(subscribeToConsole());
  }

  public Flux<String> subscribeToConsole() {
    UUID uuid = UUID.randomUUID();

    return consoleFlux.map(s -> {
      return uuid + " " + s;
    }).log("client flux");
  }

}
