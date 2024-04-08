FROM eclipse-temurin:22
WORKDIR /home
COPY ./target/primes-service-0.0.1-SNAPSHOT.jar primes-service.jar
ENTRYPOINT ["java","-jar","primes-service.jar"]