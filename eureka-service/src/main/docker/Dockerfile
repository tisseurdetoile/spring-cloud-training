FROM openjdk:8u131-jre-alpine

WORKDIR /var/app
COPY @project.build.finalName@.@project.packaging@ .

EXPOSE 8761

ENTRYPOINT ["java"]
CMD ["-jar", "@project.build.finalName@.@project.packaging@"]
