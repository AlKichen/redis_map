# Setting Up Local Environment for Application (Redis in Cluster Mode Locally)

## If You Are Using Ubuntu

### Commands to Install Redis on Ubuntu

```
sudo apt update
sudo apt install redis-server
redis-server --version
redis-server --version
```

### To Start Redis

```
sudo systemctl start redis-server
```

### To Stop Redis

```
sudo systemctl stop redis-server
```

### Check If Redis Is Running

```
sudo systemctl status redis-server
```

### Create Directories for Each Node

```
sudo mkdir -p /var/lib/redis/{7000,7001,7002,7003,7004,7005}
```

### Place Configuration Files in Each Directory

```
sudo nano /var/lib/redis/7000/7000.conf
sudo nano /var/lib/redis/7001/7001.conf
sudo nano /var/lib/redis/7002/7002.conf
sudo nano /var/lib/redis/7003/7003.conf
sudo nano /var/lib/redis/7004/7004.conf
sudo nano /var/lib/redis/7005/7005.conf
```

### Content of Configuration Files
#### It is necessary to replace for each file 7000 -> 7001 ... 7002 etc.

```
port 7000
cluster-enabled yes
cluster-config-file /var/lib/redis/7000/nodes.conf
cluster-node-timeout 5000
appendonly yes
```
#### For example 7001
```
port 7001
cluster-enabled yes
cluster-config-file /var/lib/redis/7001/nodes.conf
cluster-node-timeout 5000
appendonly yes
```

### Start All Redis Instances with Corresponding Replicas

```
sudo redis-server /var/lib/redis/7000/7000.conf
sudo redis-server /var/lib/redis/7001/7001.conf
sudo redis-server /var/lib/redis/7002/7002.conf
sudo redis-server /var/lib/redis/7003/7003.conf
sudo redis-server /var/lib/redis/7004/7004.conf
sudo redis-server /var/lib/redis/7005/7005.conf
```

### Create Redis Cluster Joining All Instances

```
redis-cli --cluster create 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 --cluster-replicas 1 --cluster-yes
```

### Verify Everything Is Working

Connect to one node and get info about others:

```
redis-cli -c -p 7000
cluster info
```

## If You Are Using Windows

### Install Redis for Windows

Download and install Redis for Windows from the [official repository](https://github.com/tporadowski/redis/releases).

After downloading and installing, run the command:

```
redis-cli ping
```

You should get the response `PONG`.

### Check If Redis Is Installed and Available

```
redis-server --version
```

### Create Directories for Each Node

```
mkdir C:\RedisCluster\7000
mkdir C:\RedisCluster\7001
mkdir C:\RedisCluster\7002
mkdir C:\RedisCluster\7003
mkdir C:\RedisCluster\7004
mkdir C:\RedisCluster\7005
```

### Place Configuration Files in Each Directory

```
C:\RedisCluster\7000\7000.conf
C:\RedisCluster\7001\7001.conf
C:\RedisCluster\7002\7002.conf
C:\RedisCluster\7003\7003.conf
C:\RedisCluster\7004\7004.conf
C:\RedisCluster\7005\7005.conf
```

### Content of Configuration Files
#### It is necessary to replace for each file 7000 -> 7001 ... 7002 etc.

```
port 7000
cluster-enabled yes
cluster-config-file C:\RedisCluster\7000\nodes.conf
cluster-node-timeout 5000
appendonly yes
```
#### For example for 7001
```
port 7001
cluster-enabled yes
cluster-config-file C:\RedisCluster\7001\nodes.conf
cluster-node-timeout 5000
appendonly yes
```

### Start All Redis Instances

For node 7000:

```
redis-server C:\RedisCluster\7000\7000.conf
```

For node 7001:

```
redis-server C:\RedisCluster\7001\7001.conf
```

For node 7002:

```
redis-server C:\RedisCluster\7002\7002.conf
```

For node 7003:

```
redis-server C:\RedisCluster\7003\7003.conf
```

For node 7004:

```
redis-server C:\RedisCluster\7004\7004.conf
```

For node 7005:

```
redis-server C:\RedisCluster\7005\7005.conf
```

### Check the Version of Redis Installed

```
& "C:\Program Files\Redis\redis-cli.exe" --version
```

If the Redis version is above 3.2, then create the cluster:

```
redis-cli --cluster create 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 --cluster-replicas 1 --cluster-yes
```

### Check Cluster Status (Get Cluster Information)

```
redis-cli -c -p 7000 cluster info
```

### You can install Redis Insight to monitor the status of the created cluster.

[link for Windows](https://apps.microsoft.com/detail/xp8k1ghcb0f1r2?hl=en-us&gl=GE)