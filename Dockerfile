# Устанавливаем базовый образ
FROM openjdk:17-jdk-slim

# Копируем весь проект в контейнер
COPY . /app

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем ресурсы в нужную папку
COPY src/main/resources /app/resources

# Открываем порт (если нужно)
EXPOSE 8080

# Запускаем приложение
CMD ["java", "-jar", "target/swift_api-0.0.1-SNAPSHOT.jar"]
