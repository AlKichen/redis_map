# Подготовка локального окружения для работы приложения (Redis в режиме Sentinel)

## Если у Вас Ubuntu

# Установка Redis на Ubuntu
```
sudo apt update
sudo apt install redis-server
redis-server --version
```

# Настройка мастер-слейв конфигурации

# Создайте три экземпляра Redis
```
sudo mkdir -p /var/lib/redis/{master,slave1,slave2}
```

# Создайте конфигурационные файлы для каждой ноды

# Для master
```
sudo nano /var/lib/redis/master/redis.conf
```
# Содержимое файла:
```
port 6390
dir /var/lib/redis/master
dbfilename dump.rdb
appendonly yes
```

# Для slave1
```
sudo nano /var/lib/redis/slave1/redis.conf
```
# Содержимое файла:
```
port 6391
dir /var/lib/redis/slave1
dbfilename dump.rdb
appendonly yes
replicaof 127.0.0.1 6390
```

# Для slave2
```
sudo nano /var/lib/redis/slave2/redis.conf
```
# Содержимое файла:
```
port 6392
dir /var/lib/redis/slave2
dbfilename dump.rdb
appendonly yes
replicaof 127.0.0.1 6390
```

# Запустите все экземпляры Redis
```
sudo redis-server /var/lib/redis/master/redis.conf
sudo redis-server /var/lib/redis/slave1/redis.conf
sudo redis-server /var/lib/redis/slave2/redis.conf
```

# Настройка Redis Sentinel

# Создайте директории для каждого экземпляра Sentinel
```
sudo mkdir -p /var/lib/redis/sentinel/{sentinel1,sentinel2,sentinel3}
```

# Создайте конфигурационные файлы для Sentinel

# Для sentinel1
```
sudo nano /var/lib/redis/sentinel/sentinel1/sentinel.conf
```
# Содержимое файла:
```
port 26390
dir /var/lib/redis/sentinel/sentinel1
sentinel monitor mymaster 127.0.0.1 6390 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 10000
sentinel parallel-syncs mymaster 1
```

# Для sentinel2
```
sudo nano /var/lib/redis/sentinel/sentinel2/sentinel.conf
```
# Содержимое файла:
```
port 26391
dir /var/lib/redis/sentinel/sentinel2
sentinel monitor mymaster 127.0.0.1 6390 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 10000
sentinel parallel-syncs mymaster 1
```

# Для sentinel3
```
sudo nano /var/lib/redis/sentinel/sentinel3/sentinel.conf
```
# Содержимое файла:
```
port 26392
dir /var/lib/redis/sentinel/sentinel3
sentinel monitor mymaster 127.0.0.1 6390 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 10000
sentinel parallel-syncs mymaster 1
```

# Запустите все экземпляры Sentinel
```
sudo redis-server /var/lib/redis/sentinel/sentinel1/sentinel.conf --sentinel
sudo redis-server /var/lib/redis/sentinel/sentinel2/sentinel.conf --sentinel
sudo redis-server /var/lib/redis/sentinel/sentinel3/sentinel.conf --sentinel
```

# Проверка работы Sentinel

# Подключитесь к любому из экземпляров Sentinel
```
redis-cli -p 26390
```

# Выполните команду для проверки статуса
```
sentinel masters
```

# Проверьте информацию о слейвах
```
sentinel slaves mymaster
```



## Если у Вас Windows

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

# Настройка мастер-слейв конфигурации

# Создайте директории для каждой ноды
```
mkdir C:\RedisCluster\master
mkdir C:\RedisCluster\slave1
mkdir C:\RedisCluster\slave2
```

# Создайте конфигурационные файлы

# Для master
```
C:\RedisCluster\master\redis.conf
```
# Содержимое файла:
```
port 6390
dir C:\RedisCluster\master
dbfilename dump.rdb
appendonly yes
```

# Для slave1
```
C:\RedisCluster\slave1\redis.conf
```
# Содержимое файла:
```
port 6391
dir C:\RedisCluster\slave1
dbfilename dump.rdb
appendonly yes
replicaof 127.0.0.1 6390
```

# Для slave2
```
C:\RedisCluster\slave2\redis.conf
```
# Содержимое файла:
```
port 6392
dir C:\RedisCluster\slave2
dbfilename dump.rdb
appendonly yes
replicaof 127.0.0.1 6390
```

# Запустите экземпляры Redis

# Для master
```
redis-server C:\RedisCluster\master\redis.conf
```

# Для slave1
```
redis-server C:\RedisCluster\slave1\redis.conf
```

# Для slave2
```
redis-server C:\RedisCluster\slave2\redis.conf
```

# Настройка Redis Sentinel

# Создайте директории для Sentinel
```
mkdir C:\RedisCluster\sentinel1
mkdir C:\RedisCluster\sentinel2
mkdir C:\RedisCluster\sentinel3
```

# Создайте конфигурационные файлы

# Для sentinel1
```
C:\RedisCluster\sentinel1\sentinel.conf
```
# Содержимое файла:
```
port 26390
dir C:\RedisCluster\sentinel1
sentinel monitor mymaster 127.0.0.1 6390 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 10000
sentinel parallel-syncs mymaster 1
```

# Для sentinel2
```
C:\RedisCluster\sentinel2\sentinel.conf
```
# Содержимое файла:
```
port 26391
dir C:\RedisCluster\sentinel2
sentinel monitor mymaster 127.0.0.1 6390 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 10000
sentinel parallel-syncs mymaster 1
```

# Для sentinel3
```
C:\RedisCluster\sentinel3\sentinel.conf
```
# Содержимое файла:
```
port 26392
dir C:\RedisCluster\sentinel3
sentinel monitor mymaster 127.0.0.1 6390 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 10000
sentinel parallel-syncs mymaster 1
```

# Запустите экземпляры Sentinel

# Для sentinel1
```
redis-server C:\RedisCluster\sentinel1\sentinel.conf --sentinel
```

# Для sentinel2
```
redis-server C:\RedisCluster\sentinel2\sentinel.conf --sentinel
```

# Для sentinel3
```
redis-server C:\RedisCluster\sentinel3\sentinel.conf --sentinel
```

# Проверка состояния Sentinel

# Подключитесь к любому экземпляру Sentinel
```
redis-cli -p 26390
```

# Выполните команду для проверки статуса
```
sentinel masters
```
