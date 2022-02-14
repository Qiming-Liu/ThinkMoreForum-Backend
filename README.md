# ThinkMoreForum-Backend

## Prerequisites

* Openjdk11  
* Gradle = gradle-7.3.3-bin
* PostgreSQL = 14.1 
* MinIO = RELEASE.2022-02-07T08-17-33Z 
> Details in docker-compose.yml

## How to Build for Production
```shell
$ chmod +x gradlew
$ ./gradlew build
```
> The build is folder `./build/libs`

## How to Run the Production (FYI)
start.sh  
```shell
rm -f tpid

nohup java -jar forum-0.0.1.jar --name="forum" --server.port=443 > log.log 2>&1 &

echo $! > tpid

echo Start Success!
```

stop.sh  
```shell
APP_NAME=forum-0.0.1

tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
    echo 'Stop Process...'
    kill -15 $tpid
fi
sleep 5
tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
    echo 'Kill Process!'
    kill -9 $tpid
else
    echo 'Stop Success!'
fi
```
