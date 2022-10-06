#!/usr/bin/env bash

cd ..

mvn clean package

echo 'Copy files...'

scp -P 2222 -i /d/Ubuntu\ Server/ssh/ \
 target/Sweater-0.0.1-SNAPSHOT.jar \
  vavilonbase@192.168.174.9:/home/vavilonbase/

echo 'Restart server...'


ssh -p 2222 -i /d/Ubuntu\ Server/ssh/ vavilonbase@192.168.174.9 << EOF

pgrep java | xargs kill -9
nohup java -jar Sweater-0.0.1-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye'