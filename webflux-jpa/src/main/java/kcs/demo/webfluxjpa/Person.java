package kcs.demo.webfluxjpa;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {

  @Id
  @GeneratedValue
  private long id;

  private String name;

  private BigDecimal balance;

  public long getId() {
    return id;
  }

  public Person setId(long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Person setName(String name) {
    this.name = name;
    return this;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public Person setBalance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }
}
