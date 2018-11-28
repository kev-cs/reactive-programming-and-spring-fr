package kcs.demo.reactive.webfluxclientsvcfn;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;

@SpringBootApplication
public class WebfluxClientSvcFnApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebfluxClientSvcFnApplication.class, args);
  }

  @Bean
  RouterFunction<?> routes(MyRequestHandler handler) {
    return
        route(GET("/"), handler::handleDefaultPath);
  }

}
