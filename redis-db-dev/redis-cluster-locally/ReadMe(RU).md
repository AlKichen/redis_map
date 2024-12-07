# Подготовка локального окружения для работы приложения (redis локально в режиме кластера)

## Если у Вас Ubuntu

### Команды для установки редис на ubuntu

```
sudo apt update
sudo apt install redis-server
redis-server --version
redis-server --version
```

### Для запуска redis

```
sudo systemctl start redis-server
```

### Для остановки

```
sudo systemctl stop redis-server
```

### Убедиться что он запущен

```
sudo systemctl status redis-server
```

### Создать директории для каждой ноды

```
sudo mkdir -p /var/lib/redis/{7000,7001,7002,7003,7004,7005}
```

### Положить в каждую директорию по файлу

```
sudo nano /var/lib/redis/7000/7000.conf
sudo nano /var/lib/redis/7001/7001.conf
sudo nano /var/lib/redis/7002/7002.conf
sudo nano /var/lib/redis/7003/7003.conf
sudo nano /var/lib/redis/7004/7004.conf
sudo nano /var/lib/redis/7005/7005.conf
```

### Содержимое файлов
#### Необходимо заменять для каждого файла 7000 -> 7001 ... 7002 и тд.
```
port 7000
cluster-enabled yes
cluster-config-file /var/lib/redis/7000/nodes.conf
cluster-node-timeout 5000
appendonly yes
```
#### Пример для 7001
```
port 7001
cluster-enabled yes
cluster-config-file /var/lib/redis/7001/nodes.conf
cluster-node-timeout 5000
appendonly yes
```

### Запустить все экземпляры redis с соответствующими репликами

```
sudo redis-server /var/lib/redis/7000/7000.conf
sudo redis-server /var/lib/redis/7001/7001.conf
sudo redis-server /var/lib/redis/7002/7002.conf
sudo redis-server /var/lib/redis/7003/7003.conf
sudo redis-server /var/lib/redis/7004/7004.conf
sudo redis-server /var/lib/redis/7005/7005.conf
```

### Создать кластер redis объединяющий все экземляры

```
redis-cli --cluster create 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 --cluster-replicas 1 --cluster-yes
```

### Проверить что все ок

Подключиться к одной ноде и узнать инфу про остальные:

```
redis-cli -c -p 7000
cluster info
```

## Если у вас Windows

### Установить redis для Windows

Скачайте и установите redis для Windows с [официального репозитория](https://github.com/tporadowski/redis/releases).

После скачивания и установки, выполните команду:

```
redis-cli ping
```

В ответ будет `PONG`.

### Проверить что redis установлен и доступен

```
redis-server --version
```

### Создать директории для каждой ноды

```
mkdir C:\RedisCluster\7000
mkdir C:\RedisCluster\7001
mkdir C:\RedisCluster\7002
mkdir C:\RedisCluster\7003
mkdir C:\RedisCluster\7004
mkdir C:\RedisCluster\7005
```

### Положить в каждую директорию по файлу

```
C:\RedisCluster\7000\7000.conf
C:\RedisCluster\7001\7001.conf
C:\RedisCluster\7002\7002.conf
C:\RedisCluster\7003\7003.conf
C:\RedisCluster\7004\7004.conf
C:\RedisCluster\7005\7005.conf
```

### Содержимое файлов
#### Необходимо заменять для каждого файла 7000 -> 7001 ... 7002 и тд.

```
port 7000
cluster-enabled yes
cluster-config-file C:\RedisCluster\7000\nodes.conf
cluster-node-timeout 5000
appendonly yes
```
#### Пример для 7001
```
port 7001
cluster-enabled yes
cluster-config-file C:\RedisCluster\7001\nodes.conf
cluster-node-timeout 5000
appendonly yes
```

### Запустить все экземпляры redis

Для ноды 7000:

```
redis-server C:\RedisCluster\7000\7000.conf
```

Для ноды 7001:

```
redis-server C:\RedisCluster\7001\7001.conf
```

Для ноды 7002:

```
redis-server C:\RedisCluster\7002\7002.conf
```

Для ноды 7003:

```
redis-server C:\RedisCluster\7003\7003.conf
```

Для ноды 7004:

```
redis-server C:\RedisCluster\7004\7004.conf
```

Для ноды 7005:

```
redis-server C:\RedisCluster\7005\7005.conf
```

### Узнать версию установленного redis

```
& "C:\Program Files\Redis\redis-cli.exe" --version
```

Если версия redis выше 3.2, тогда создайте кластер:

```
redis-cli --cluster create 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 --cluster-replicas 1 --cluster-yes
```

### Проверка состояния кластера (получение информации о кластере)

```
redis-cli -c -p 7000 cluster info
```

### Можете установить Redis Insight для мониторинга состояния созданного кластера

[ссылка для Windows](https://apps.microsoft.com/detail/xp8k1ghcb0f1r2?hl=en-us&gl=GE)
