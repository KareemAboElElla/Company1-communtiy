FROM openjdk:8
ADD target/company1comm.jar company1comm.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","company1comm.jar"]