# Электронная библиотека

CRUD электронная библиотека на Spring MVC, Hibernate, JPA, Spring Security и с простеньким Frontend на шаблонизаторе Thymeleaf

## Инструкция по сборке проекта

1. Клонировать репозиторий:
   ```bash
   git clone https://github.com/aleksguz00/e-library-hib-jpa

2. Перейти в директорию проекта:
   ```bash
    cd

3. Запустить скрипт для создания файла с конфигом БД:
    ```bash
   ./create_hibernate_config.sh
   
4. Далее заполнить созданный файл hibernate.properties своими данными

5. Собрать проект:
   ```bash
   mvn clean package

6. В файле docker-compose.yml указать свои параметры БД

7. Поднять docker-контейнер с БД
   ```bash
   docker-compose up -d

8. Запустить проект
- Либо из IDE
- Либо через команды запуска Tomcat:

    - скопировать .war файл в папку webapps Tomcat
        ```bash
        cp /path/to/your/project/target/your-app.war /path/to/tomcat/webapps/

    - Перейти в папку bin Tomcat и запустить сервер
        ```bash 
        cd /path/to/tomcat/bin
        ./catalina.sh start

## Тестирование

Тестировать удобнее через frontend. Есть 2 главных эндпоинты - /people и /books
