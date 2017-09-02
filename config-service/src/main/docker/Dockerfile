FROM openjdk:8u131-jre-alpine

WORKDIR /var/app
COPY @project.build.finalName@.@project.packaging@ .

EXPOSE 8888

ENTRYPOINT ["java"]
CMD ["-jar", "@project.build.finalName@.@project.packaging@"]
