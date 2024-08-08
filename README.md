[![My test](https://github.com/Grad566/task-manager-test-assigmnet/actions/workflows/myTest.yml/badge.svg)](https://github.com/Grad566/task-manager-test-assigmnet/actions/workflows/myTest.yml)
[![Test Coverage](https://api.codeclimate.com/v1/badges/52b94f0c716421f7da11/test_coverage)](https://codeclimate.com/github/Grad566/task-manager-test-assigmnet/test_coverage)

## Тестовое задание от Effective Mobile

Приложение представляет собой менеджер задач.

## Локальный запуск
1) Если есть Docker и docker-compose.

```
docker-compose up
```

После чего приложение будет доступно по http://localhost:8080/

2) Запуск без докера.

Требования:
- jdk 21
- gradle 8.7

```
make dev
```

После чего приложение будет доступно по http://localhost:8080/

В качестве бд будет H2. (в докере PostgreSQL)


## Документация
Документация swagger по url:
1) http://localhost:8080/swagger-ui/index.html
2) http://localhost:8080/v3/api-docs

!! Возможно при запуске через docker-compose будет 404, 
в таком случае приложение нужно запустить через ```make dev``` и 
документация будет доступна.


## Использование и примеры запросов
Для аутентификации используйте (пользователь уже есть в БД):
```
curl -X POST http://localhost:8080/api/login \
-H "Content-Type: application/json" \
-d '{
"username": "admin@example.com",
"password": "qwerty"
}'
```
После чего можно будет создавать новых пользователей и логиниться под новыми данными.

Пример:
```
curl -X POST http://localhost:8080/api/users \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW5AZXhhbXBsZS5jb20iLCJleHAiOjE3MjMxMTQ1NDksImlhdCI6MTcyMzExMDk0OX0.a6ItX_zm7w3cLn-IzFwizIXbo7YqpCTeCndnoklJf13BbbiVBKwvE9Ri5XO29s6dLWUBDBbuGE2yHWtgbwas-cPSI5eiHW5KTvwPI0e4RU6A5D9ki50R2hBnU6m1KYzTFsyp-iAIjh4NPQRFR2p-I9lKaJhCD1pMKqkldamaMEuWZa5M3xUyxDjTQWBmPE1YoTiVS77IjI79raBX1t26409aPqBSiOVOCWTlejqE14Gv_H_BWIjup637603c_jf5qb9mI0iN8lD2zyCco3xTm6tr01g28zriVXT8aQVc_ilUe3KXLu86r2SmmZkjHosoZXYIjxZ3BiJhXMw1lhWd7g" \
-d '{
    "email": "wantToWork@gmail.com",
    "password": "123zxc"
}'
```

Дополнительные команды:
```
// запуск checkStyle
make lint 

// запуск тестов
make test 

// запуск приложения без docker-compose
make dev 
```

## Дополнительно.
В БД уже есть несколько статусов и приоритетов.

Для создания сущностей используйте сущностьCreatedDTO (подробнее в документации)

Поля отмеченные "present: true" - опциональны.
