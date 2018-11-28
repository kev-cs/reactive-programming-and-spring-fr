package kcs.demo.springmvc.clientsvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@RestController
public class MyController {

  private RestTemplate restTemplate;

  public MyController(@Value("${externalsvc.uri}") String externalSvcUri) {
    this.restTemplate = new RestTemplate();
    restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(
        externalSvcUri));
  }

  @GetMapping
  public String[] getSomething() {
    return restTemplate.getForObject("/slow-toto", String[].class);
  }

}
