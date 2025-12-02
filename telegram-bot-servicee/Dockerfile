FROM openjdk:17-jdk-slim
WORKDIR /app

# Копируем собранный JAR файл
COPY target/telegram-bot-service-1.0.0.jar app.jar

# Указываем переменные окружения
ENV TELEGRAM_BOT_TOKEN=your_token_here
ENV API_GATEWAY_URL=http://api-gateway:8080
ENV EUREKA_SERVER=http://eureka:8761/eureka

# Открываем порт
EXPOSE 8085

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]