# Programmation réactive & Spring Boot 2

[Lien vers la présentation](https://docs.google.com/presentation/d/1RrySRA_peXjZKZOCChIdYRkcETvrYvFDN-vNP_YBv7Y/edit?usp=sharing)

## Explication des modules
- **springmvc-client-svc** : service utilisant SpringMVC pour le test de comparaison de performance. Consomme le service externe external-svc.
- **webflux-client-svc** : service utilisant WebFlux pour le test de comparaison de performance. Consomme le service externe external-svc.
- **external-svc** : service externe implémenté en webflux.fn . Permet de simuler un service externe lent qui retourne une liste de chaînes de caractères aléatoires.
- **webflux-test-module** : contient différents tests et exemples de code WebFlux / Reactor.

## Test JMeter
Pour exécuter le test dans le dossier jmeter-tests, vous pouvez le faire avec le programme JMeter ou avec cette ligne de commande :

```jmeter -n -t jmeter-test.jmx -J port=8081```