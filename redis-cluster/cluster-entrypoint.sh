#!/bin/bash

# Запуск Redis сервер
redis-server /usr/local/etc/redis/redis.conf &

# Ожидаем запуска всех Redis-нод
sleep 5

# Проверяем, является ли нода первой
if [ "$IS_FIRST_NODE" = "true" ]; then
    echo "Создаем Redis кластер..."

    # Используем redis-cli для создания кластера
    echo "yes" | redis-cli --cluster create \
        ${REDIS_NODES} \
        --cluster-replicas 1
fi

# Ожидание завершения Redis сервера
wait
