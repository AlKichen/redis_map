# Setting up the local environment for the application (Redis in Sentinel mode)

## If you have Ubuntu

# Installing Redis on Ubuntu
```
sudo apt update
sudo apt install redis-server
redis-server --version
```

# Setting up master-slave configuration

# Create three Redis instances
```
sudo mkdir -p /var/lib/redis/{master,slave1,slave2}
```

# Create configuration files for each node

# For master
```
sudo nano /var/lib/redis/master/redis.conf
```
# File content:
```
port 6390
dir /var/lib/redis/master
dbfilename dump.rdb
appendonly yes
```

# For slave1
```
sudo nano /var/lib/redis/slave1/redis.conf
```
# File content:
```
port 6391
dir /var/lib/redis/slave1
dbfilename dump.rdb
appendonly yes
replicaof 127.0.0.1 6390
```

# For slave2
```
sudo nano /var/lib/redis/slave2/redis.conf
```
# File content:
```
port 6392
dir /var/lib/redis/slave2
dbfilename dump.rdb
appendonly yes
replicaof 127.0.0.1 6390
```

# Start all Redis instances
```
sudo redis-server /var/lib/redis/master/redis.conf
sudo redis-server /var/lib/redis/slave1/redis.conf
sudo redis-server /var/lib/redis/slave2/redis.conf
```

# Setting up Redis Sentinel

# Create directories for each Sentinel instance
```
sudo mkdir -p /var/lib/redis/sentinel/{sentinel1,sentinel2,sentinel3}
```

# Create configuration files for Sentinel

# For sentinel1
```
sudo nano /var/lib/redis/sentinel/sentinel1/sentinel.conf
```
# File content:
```
port 26390
dir /var/lib/redis/sentinel/sentinel1
sentinel monitor mymaster 127.0.0.1 6390 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 10000
sentinel parallel-syncs mymaster 1
```

# For sentinel2
```
sudo nano /var/lib/redis/sentinel/sentinel2/sentinel.conf
```
# File content:
```
port 26391
dir /var/lib/redis/sentinel/sentinel2
sentinel monitor mymaster 127.0.0.1 6390 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 10000
sentinel parallel-syncs mymaster 1
```
# For sentinel3
```
sudo nano /var/lib/redis/sentinel/sentinel3/sentinel.conf
```
# File content:
```
port 26392
dir /var/lib/redis/sentinel/sentinel3
sentinel monitor mymaster 127.0.0.1 6390 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 10000
sentinel parallel-syncs mymaster 1
```

# Start all Sentinel instances
```
sudo redis-server /var/lib/redis/sentinel/sentinel1/sentinel.conf --sentinel
sudo redis-server /var/lib/redis/sentinel/sentinel2/sentinel.conf --sentinel
sudo redis-server /var/lib/redis/sentinel/sentinel3/sentinel.conf --sentinel
```

# Verifying Sentinel operation

# Connect to any Sentinel instance
```
redis-cli -p 26390
```


# Run the command to check the status
```
sentinel masters
```

# Check information about slaves
```
sentinel slaves mymaster
```


## If you have Windows

# Install Redis for Windows
Download and install Redis for Windows from the [official repository](https://github.com/tporadowski/redis/releases).

After downloading and installing, run the command:

```
redis-cli ping
```

The response should be `PONG`.

# Verify Redis installation
```
redis-server --version
```

# Setting up master-slave configuration

# Create directories for each node
```
mkdir C:\RedisCluster\master
mkdir C:\RedisCluster\slave1
mkdir C:\RedisCluster\slave2
```

# Create configuration files

# For master
```
C:\RedisCluster\master\redis.conf
```
# File content:
```
port 6390
dir C:\RedisCluster\master
dbfilename dump.rdb
appendonly yes
```

# For slave1
```
C:\RedisCluster\slave1\redis.conf
```
# File content:
```
port 6391
dir C:\RedisCluster\slave1
dbfilename dump.rdb
appendonly yes
replicaof 127.0.0.1 6390
```

# For slave2
```
C:\RedisCluster\slave2\redis.conf
```
# File content:
```
port 6392
dir C:\RedisCluster\slave2
dbfilename dump.rdb
appendonly yes
replicaof 127.0.0.1 6390
```

# Start Redis instances

# For master
```
redis-server C:\RedisCluster\master\redis.conf
```

# For slave1
```
redis-server C:\RedisCluster\slave1\redis.conf
```

# For slave2
```
redis-server C:\RedisCluster\slave2\redis.conf
```

# Setting up Redis Sentinel

# Create directories for Sentinel
```
mkdir C:\RedisCluster\sentinel1
mkdir C:\RedisCluster\sentinel2
mkdir C:\RedisCluster\sentinel3
```

# Create configuration files

# For sentinel1
```
C:\RedisCluster\sentinel1\sentinel.conf
```
# File content:
```
port 26390
dir C:\RedisCluster\sentinel1
sentinel monitor mymaster 127.0.0.1 6390 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 10000
sentinel parallel-syncs mymaster 1
```

# For sentinel2
```
C:\RedisCluster\sentinel2\sentinel.conf
```
# File content:
```
port 26391
dir C:\RedisCluster\sentinel2
sentinel monitor mymaster 127.0.0.1 6390 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 10000
sentinel parallel-syncs mymaster 1
```

# For sentinel3
```
C:\RedisCluster\sentinel3\sentinel.conf
```
# File content:
```
port 26392
dir C:\RedisCluster\sentinel3
sentinel monitor mymaster 127.0.0.1 6390 2
sentinel down-after-milliseconds mymaster 5000
sentinel failover-timeout mymaster 10000
sentinel parallel-syncs mymaster 1
```

# Start Sentinel instances

# For sentinel1
```
redis-server C:\RedisCluster\sentinel1\sentinel.conf --sentinel
```

# For sentinel2
```
redis-server C:\RedisCluster\sentinel2\sentinel.conf --sentinel
```

# For sentinel3
```
redis-server C:\RedisCluster\sentinel3\sentinel.conf --sentinel
```

# Verifying Sentinel status

# Connect to any Sentinel instance
```
redis-cli -p 26390
```

# Run the command to check the status
```
sentinel masters
```
