## Учебный проект для Яндекс.Практикум "myblog" 

### Описание страниц:
- #### Лента постов ({/posts, /}) <br>
    Отображаются все посты с пагинацией (по умолчанию 5 постов/стр) и строкой поиска по тегам.

- #### Детальная страница поста (/posts/{id}) <br>
    Отображается полный текст поста с возможностью добавления реакций и комментариев

- #### Страница добавления/обновления поста.<br>
    Содержит в себе формы для заполнения всех возможных полей поста, включая добавление изображений.
    


### Стек
- Java 21
- Gradle
- Spring MVC
- База H2 (in-memory)
- Hibernate
- HTML + Thymeleaf



### Сборка и запуск

- В папке проекта запустить команду gradlew clean bootJar
- Jar-файл с названием *myblogApp.jar* появится в папке build/libs/
- Запустить jar-файл командой 
> java -jar myblogApp.jar
- Перейти по адресу http://localhost:8020

### Запуск тестов

- В папке проекта запустить команду gradlew clean test
