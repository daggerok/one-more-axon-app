# axon again [![Build Status](https://travis-ci.org/daggerok/one-more-axon-app.svg?branch=master)](https://travis-ci.org/daggerok/one-more-axon-app)
One more axon example

## monolithic

### up and running needed services in docker

```bash
./mvnw -f docker/axon-postgres/pom.xml -P up
```

### run monolithic-app

```bash
./mvnw
bash ./apps/monolithic-app/target/*.jar
```

### run monolithic-less-app

```bash
./mvnw
bash ./apps/monolithic-less-app/target/*.jar \
        --server.application.name=monolithic-less-query-app \
        --server.port=0 --spring.profiles.active=query
bash ./apps/monolithic-less-app/target/*.jar \
        --server.application.name=monolithic-less-command-app \
        --server.port=0 --spring.profiles.active=command
bash ./apps/monolithic-less-app/target/*.jar \
        --server.application.name=monolithic-less-client-app \
        --server.port=0 --spring.profiles.active=client
```

### cleanup

```bash
./mvnw -f docker/axon-postgres/pom.xml -P down
```

## build VuePress documentation

```bash
npm i ; npm run build
```
