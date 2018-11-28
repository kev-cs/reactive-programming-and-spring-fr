package kcs.demo.webfluxjpa;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@RestController
public class PersonController {

  private final PersonRepository personRepository;
  private final Scheduler scheduler;

  public PersonController(
      @Value("${spring.datasource.hikari.maximum-pool-size}") Integer connectionPoolSize,
      PersonRepository personRepository,
      TransactionTemplate trxTemplate) {
    this.personRepository = personRepository;
    this.scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPoolSize));
  }

  @GetMapping("persons")
  public Flux<Person> getPersons() {
    return monoWrap(personRepository::findAll).flatMapIterable(p -> p);
  }

  @GetMapping("persons/{id}")
  public Mono<Person> getPersons(@PathVariable("id") long id) {
    return monoWrap(() -> personRepository.findById(id).orElse(null));
  }

  @PostMapping("persons")
  public Mono<Person> createPerson(Mono<Person> personMono) {
    return personMono
        .flatMap(person -> monoWrap(() -> personRepository.save(person)));
  }

  private <T> Mono<T> monoWrap(Callable<T> callable) {
    return Mono.fromCallable(callable).publishOn(scheduler);
  }

  @PostConstruct
  private void init() {
    Iterable<Person> personsToInit = Flux.range(1, 1000)
        .map(i ->
            new Person()
                .setName("kev-" + i)
                .setBalance(BigDecimal.valueOf(Math.random() * 9_999_999.99))
        ).toIterable();
    personRepository.saveAll(
        personsToInit
    );
  }
}
