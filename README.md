# Why this repository

Honestly I am not a friend of cucumber. You have to write a lot of clue code to test simple features and the tests are fragile.
But I needed a clean solution to integrate cucumber in a SpringBoot environment in addition with WireMock.

# Build and run test

## Build

```shell script
$ ./mvnw clean compile
```

## Run tests

```shell script
$ ./mvnw clean test
```

## Build package

```shell script
$ ./mvnw clean package
```

# License

This solution is released under the **Apache 2.0 License**.
Please see the [LICENSE](LICENSE.txt) file for more information.

# Links

* [Cucumber](https://cucumber.io/)
* [WireMock](http://wiremock.org/)
* [SpringBoot](https://spring.io/projects/spring-boot)

# Further links

* [JUnit Runner with @CucumberOptions @ testingneeds](https://testingneeds.wordpress.com/2015/09/15/junit-runner-with-cucumberoptions/)
* [Set Up and Run Cucumber Tests In Spring Boot Application @ medium.com](https://medium.com/@bcarunmail/set-up-and-run-cucumber-tests-in-spring-boot-application-d0c149d26220)
* [Cucumber Tests in Spring Boot with Dependency Injection @ thepracticaldeveloper](https://thepracticaldeveloper.com/2018/03/31/cucumber-tests-spring-boot-dependency-injection/)
