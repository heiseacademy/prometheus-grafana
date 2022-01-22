#!/bin/sh 

docker run --rm \
  --net=mariadb --name mysqlslap \
  diceone/mysqlslap \
  mysqlslap --user=user --password=user --host=mariadb \
  --concurrency=50 --iterations=100  \
  --number-int-cols=5 --number-char-cols=20 \
  --create-schema=demo --auto-generate-sql --verbose