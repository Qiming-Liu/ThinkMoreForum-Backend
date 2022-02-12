# ThinkMoreForum-Backend

> view [API Doc](http://localhost:8080/swagger-ui/)

### 1. Start Database

```shell
docker-compose up -d
```

### 2. Run Spring Boot

```shell
docker run -p 9000:9000 -p 9001:9001 --name oss -e "MINIO_ROOT_USER=admin" -e "MINIO_ROOT_PASSWORD=development" -v D:\minio\data:/data -v D:\minio\config:/root/.minio minio/minio:RELEASE.2022-02-07T08-17-33Z server /data --console-address ":9001"
```
