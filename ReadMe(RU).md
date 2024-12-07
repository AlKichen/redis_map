# О приложении

Данное приложение демонстрирует, как работает реализация Map, в которой под капотом данные хранятся в Redis.  
Приложение поддерживает работу с разными режимами Redis:
- **standalone**
- **sentinel**
- **redis-cluster**

## Запуск в режиме dev
Для локального использования в режиме dev:
1. Необходимо настроить окружение
   - Если захотите использовать режим **standalone**, то необходимо запустить docker [docker-compose](redis-db-dev/redis-standalone/docker-compose.yaml)
   - Если хотите использовать режим **redis-cluster**, то только локальное развертывание (без использования docker или k8s).
     Инструкция по запуску [redis-cluster-locally](redis-db-dev/redis-cluster-locally/ReadMe(RU).md).
   - Если хотите использовать режим **sentinel**, то тоже только локальное развертывание (без использования docker или k8s).
     Инструкция по запуску [redis-sentinel](redis-db-dev/redis-sentinel/ReadMe(RU).md).
     Или если у вас уже есть Redis, просто правильно задайте настройки в файле `application-dev.yml`, для того случая который есть у вас.
2. Задайте правильные настройки в файле `application-dev.yml` - создайте его убрав постфикс `.sample` из примера ниже.  
   Пример конфигурации вы найдёте в файле [application-dev.yml](src/main/resources/application-dev.yml.sample).
   По примеру настроек, должно быть понятно где и что задавать.   
3. Запустите приложение с профилем **`dev`**.
4. Для тестирования работоспособности приложения создан тестовый контроллер, который предоставляет два endpoints:
    - **POST** `/test/save?key=TestKey&value=123`
      Где `key` — ключ, который необходимо записать (строка), а `value` — значение, которое необходимо записать под этим ключом (целочисленное).
    - **GET** `/test/check-direct?key=TestKey`
      Где `key` — ключ, по которому необходимо проверить значение.
С помощью данных endpoints можно проверить, действительно ли значения сохраняются в Redis.  
Метод `/test/check-direct` проверяет значение напрямую в Redis, минуя реализацию **RedisMap**.

## Запуск тестов и сборка
Процесс сборки запускается командой ` ./mvnw clean install` из корня проекта. Поскольку для сборки необходимо успешное прохождение тестов
Нужно либо настроить тестовое окружение, либо запускать сборку без тестов используя команду `./mvnw clean install -DskipTests`.

### Настройка тестового окружения
Необходимо использовать файл [application.yml.sample](src/test/resources/application.yml.sample) из него создать файл `application.yml`
который будет использоваться для настройки приложения в тестовом режиме.
Запустить тесты [RedisMapTests](src/test/java/com/task/test_task/tests/RedisMapTests.java).

## Проблемы с настройкой Redis-Cluster
К сожалению, мне не удалось собрать рабочий конфиг для Redis в режиме **redis-cluster** с использованием Kubernetes или Docker.  
Основная сложность заключалась в том, что локально запущенное приложение (вне кластера Kubernetes или Docker-контейнера) не видело все ноды кластера.

### Возможная причина
Скорее всего, проблема связана с сетевой доступностью.  
Попробованные решения, найденные в интернете, не заработали на моём ПК. Примеры неработающих конфигураций:
- [Redis Cluster Using Docker](https://medium.com/@ahmettuncertr/redis-cluster-using-docker-1c8458a93d4b)
- [Setup a Redis in a Local Machine - Redis Clustering](https://medium.com/@ishara11rathnayake/setup-a-redis-in-a-local-machine-redis-clustering-120289f71df5)
- [External Access to a Kubernetes Redis Cluster](https://dev.to/kermodebear/external-access-to-a-kuberenetes-redis-cluster-46n6)
- [How to Deploy a Redis Cluster in Kubernetes](https://medium.com/@dmitry.romanoff/how-to-deploy-a-redis-cluster-in-kubernetes-65c967887f6f)

### Альтернатива
Для решения проблемы я записал подробную инструкцию по запуску Redis в режиме **redis-cluster**, установив его локально на ПК.  
Инструкция [redis-cluster-locally](redis-db-dev/redis-cluster-locally/ReadMe(RU).md).
А так же для режима [redis-sentinel](redis-db-dev/redis-sentinel/ReadMe(RU).md).