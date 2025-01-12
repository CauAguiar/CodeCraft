#syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o Gradle Wrapper e o arquivo build.gradle para o container
COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle

# Garante que o Gradle Wrapper tenha permissão de execução
RUN chmod +x ./gradlew

# Instala o dos2unix para converter finais de linha, caso necessário
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix ./gradlew

# Baixa as dependências do projeto
RUN ./gradlew dependencies --no-daemon

# Copia o código-fonte para o container
COPY src ./src

# Define o comando para rodar a aplicação
CMD ["./gradlew", "bootRun", "--no-daemon"]
