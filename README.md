### Приложение для работы с тестовым elasticsearch-сервером ###

Сервер, на котором запущен Elasticsearch:
```textmate
185.198.152.125
```
Подключиться к серверу можно, выполнив в командной строке команду:
```text
ssh root@185.198.152.125
```
[Выполнение установки elasticsearch на Ubuntu](https://www.elastic.co/guide/en/elasticsearch/reference/current/deb.html)

Если все свойства безопасности на сервере переведены в значение false, то в таком случае, проверить, что elasticsearch работает корректно - можно с помощью команды:
```shell
curl -X GET https://localhost:9200
```
Если служба linux elasticsearch работает корректно, то тогда ответ на запрос будет примерно такой:
```json
{
  "name" : "elasticexperiments.ru",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "19PeE-B8Tmy7EKgUvQWeEQ",
  "version" : {
    "number" : "8.13.1",
    "build_flavor" : "default",
    "build_type" : "deb",
    "build_hash" : "9287f29bba5e270bd51d557b8daccb7d118ba247",
    "build_date" : "2024-03-29T10:05:29.787251984Z",
    "build_snapshot" : false,
    "lucene_version" : "9.10.0",
    "minimum_wire_compatibility_version" : "7.17.0",
    "minimum_index_compatibility_version" : "7.0.0"
  },
  "tagline" : "You Know, for Search"
}
```
Конфигурация elasticsearch находится на сервере в папке:
```shell
cd /etc/elasticsearch
```
Можно сразу же отредактировать конфигурацию в файле `/etc/elasticsearch/elasticsearch.yml` с помощью midnight commander, выполнив команду на сервере:
```shell
 mcedit /etc/elasticsearch/elasticsearch.yml
```

Для того чтобы при подключении к elasticsearch ничего не требовалось, в файле `/etc/elasticsearch/elasticsearch.yml` заданы следующие настройки
```yaml
# Enable security features
xpack.security.enabled: false

xpack.security.enrollment.enabled: false

# Enable encryption for HTTP API client connections, such as Kibana, Logstash, and Agents
xpack.security.http.ssl:
  enabled: false
#  keystore.path: certs/http.p12

# Enable encryption and mutual authentication between cluster nodes
xpack.security.transport.ssl:
  enabled: false
#  verification_mode: certificate
#  keystore.path: certs/transport.p12
#  truststore.path: certs/transport.p12
```

Базовая установка и запуск elasticsearch выполняются с такими нстройками безопасности:
```yaml
# Enable security features
xpack.security.enabled: true

xpack.security.enrollment.enabled: true

# Enable encryption for HTTP API client connections, such as Kibana, Logstash, and Agents
xpack.security.http.ssl:
  enabled: true
  keystore.path: certs/http.p12

# Enable encryption and mutual authentication between cluster nodes
xpack.security.transport.ssl:
  enabled: true
  verification_mode: certificate
  keystore.path: certs/transport.p12
  truststore.path: certs/transport.p12
```
Как правильно настраивать в текущей версии `5.2.4` пакета `spring-data-elasticsearch`
```xml
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-elasticsearch</artifactId>
    <version>5.2.4</version>
</dependency>
```
[конфигурацию подключения к elasticsearch](https://docs.spring.io/spring-data/elasticsearch/reference/elasticsearch/clients.html#elasticsearch.clients.configuration)
Пакет `spring-data-elasticsearch` версии `5.2.4` подключен к приложению `ru.sberbank.elasticsearchplayground.ElasticsearchplaygroundApplication` 