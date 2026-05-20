# ===== Stage 1: ビルド =====
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Maven Wrapper と pom.xml を先にコピー（依存関係のキャッシュ効率化）
COPY demo/mvnw .
COPY demo/.mvn .mvn
COPY demo/pom.xml .

# 実行権限を付与して依存関係を先にダウンロード
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# ソースコードをコピーしてビルド（テストはスキップ）
COPY demo/src src
RUN ./mvnw clean package -DskipTests

# ===== Stage 2: 実行 =====
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
