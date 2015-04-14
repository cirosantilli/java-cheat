# Spring Framework

- [XML property value](xml-property-value/)
- [XML property ref](xml-property-ref/)
- [XML constructor-arg](xml-constructor-arg/)
- [XML autowire](xml-autowire/)
- [@Configuration and @Bean](configuration-bean/)
- [Life Cycle - initMethod and destroyMethod](life-cycle/)
- [@Import](import/)
- [@Autowired](xml-autowire/)

TODO:

- `@PropertySource`
- `@Property`
- `@Bean` method that takes parameters. What to do?
- Multiple `@Bean` that return the same type?

Docs: <http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/>

## Quick start

## Boot

Spring Boot is an opinionated way to get started quickly.

<https://github.com/spring-projects/spring-boot> is an opinionated way of getting started with apps quickly. The `spring-boot-samples/` directory contains tons of examples. `cd` into an example, then:

    mvn install
    java -jar target/*.jar

TODO how to configure port?

Sprint boot is documented at: <http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/>

## Artifacts

## Modules

Spring offers many artifacts to parts of the entire framework.

Table of available artifacts: <http://docs.spring.io/spring/docs/4.1.6.RELEASE/spring-framework-reference/htmlsingle/#dependency-management>

Use them with:

    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <version>4.1.5.RELEASE</version>
    </dependency>

Description of the modules: <http://docs.spring.io/spring/docs/4.1.6.RELEASE/spring-framework-reference/htmlsingle/#overview-module-0>

TODO check All classes of a module are withing a package that has the same name as the module, e.g. `spring-beans` has a single top level package:

    org.springframework.beans
