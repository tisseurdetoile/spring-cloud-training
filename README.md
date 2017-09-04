# spring-cloud-training
Code for training course on Spring Cloud and Netflix OSS components

## Running

### Development Environment

A few services need MongoDB to run and the Notification service needs Redis.
The `docker/dev` directory contains a Docker Composition that starts both and maps their ports to the host.

```
$ docker-compose up -d
```

Once this is done any project can be run using the Spring Boot Maven plugin. You should set a different port for each service using the `server.port` property or `SERVER_PORT` environment variable.

```
$ mvn spring-boot:run -DskipTests -Drun.jvmArguments="-Dserver.port=xxxx"
```

The Config service must be started before any others.

### Production Environment

The `docker/prod` directory contains another Docker Composition that sets up a full production environment using ELK for log aggregation.

The ELK stack must be started first.

```
$ docker-compose up -d elastic logstash kibana
```

Followed by the Config service.

```
$ docker-compose up -d config
```

Finally all other services can be started.

```
$ docker-compose up -d
```

#### Logs

The logs can be accessed through Kibana on http://localhost:5601/.
Connect using the `elastic` user; the password is `changeme`.
