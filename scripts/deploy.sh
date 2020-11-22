#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i /c/Users/abdsu/.ssh/id_rsa \
    target/latest-sweeter-1.0-SNAPSHOT.jar \
    suleyman@192.168.56.101:/home/suleyman/

echo 'Restart server...'

#TODO correct  key
ssh -i /c/Users/abdsu/.ssh/id_rsa suleyman@192.168.56.101 << EOF
pgrep java | xargs kill -9
nohup java -jar latest-sweeter-1.0-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'
