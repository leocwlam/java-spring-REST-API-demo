FROM openjdk:17.0.2
EXPOSE 8080
ADD target/asset-service.jar asset-service.jar

EXPOSE 3000

ENTRYPOINT ["java","-jar","/asset-service.jar"]