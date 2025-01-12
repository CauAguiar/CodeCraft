# Etapa de build
FROM openjdk:23-jdk-slim as builder

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie o arquivo build.gradle, settings.gradle, gradlew e o diretório gradle
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./

# Dê permissões de execução ao script gradlew
RUN chmod +x gradlew

# Copie o código-fonte do projeto
COPY src ./src

# Execute o Gradle para baixar as dependências e construir o projeto
RUN ./gradlew build --no-daemon

# Etapa de execução
FROM openjdk:23-jdk-slim

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie o JAR gerado na imagem de build
COPY --from=builder /app/build/libs/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Comando para rodar o JAR no container
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]
