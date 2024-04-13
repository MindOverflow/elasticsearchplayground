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

### Посмотреть fingerprint
перейти в папку
```shell
 cd /etc/elasticsearch/certs/
 openssl x509 -fingerprint -sha256 -in http_ca.crt
```
результат отработки команды:
```text
sha256 Fingerprint=B2:B9:44:4E:A4:8B:8B:9A:E5:37:9D:B8:35:D1:C3:A9:8F:D9:29:C7:BB:67:87:9A:B4:2B:E2:19:D3:48:C7:E4
-----BEGIN CERTIFICATE-----
MIIFWjCCA0KgAwIBAgIVAPCPllne7HlgCP4hVW45VE7g5Ig8MA0GCSqGSIb3DQEB
CwUAMDwxOjA4BgNVBAMTMUVsYXN0aWNzZWFyY2ggc2VjdXJpdHkgYXV0by1jb25m
aWd1cmF0aW9uIEhUVFAgQ0EwHhcNMjQwNDA2MTQxMTAxWhcNMjcwNDA2MTQxMTAx
WjA8MTowOAYDVQQDEzFFbGFzdGljc2VhcmNoIHNlY3VyaXR5IGF1dG8tY29uZmln
dXJhdGlvbiBIVFRQIENBMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA
zBb7dQL/j9I2UlPqGBKKdFOhfjBM08X3KX0DFQHkovZB+IFGcZxAy4/BEUVCS6+v
ToSoIGwFgbX7i3uF38PJe44IohMAWt4SWjalJnkWWTqIzBYFVGQaNZC7Zeyy5TAv
viE+FYummm9CkNH87fwepgNNC7OLxb5XIvBEEnTEnC5QYu6QhUecSIXBsDIbKjaR
bMW+y8LxnvYa3V1PeoUG+QB7C5fU26TwTsfIItOTF1NUvhevrucXgs1NG970Q54R
vwdnwu6ECMtXrLGXSKfs5JM7TcWqhbtmxG/GwA8q3WQOwgIg7OOaqDsvYT2FsyGL
0TpG0a/tguuRrzCO3aU8E7SUKPYELfBsMz/v9kr/eASw+O0RhhVkN63ST9pNiaHK
+3EhFP6XEUeuQmN68v74i6aibzI+tJmYGyq8NuEr/xOop9N8yP4V9P0ZxTIwOVA6
XA3ed3dM8KCFxCKspwyMqyA/IKSMQTWo2Vft2l31F2FEyPSNycJ2spw3opCTEKKG
ZPGdYJelNCrfsnNS50hqj5vjhk+jDfIKgm71lqRBA8JmfCPpu1rVTgshyC7fxmb7
5T8hacSv6+H1BSQDmpnPyYyykcNcHf7nOI3nwen/DTur/IfnwUnYxlxWoRF2j9KS
sz/o2rBNqZlAgVjreubdJjMKtZ1/0aIc9pHaNjRXMlkCAwEAAaNTMFEwHQYDVR0O
BBYEFDTOs1nSodBeGaZf+kgleT5AbQ6DMB8GA1UdIwQYMBaAFDTOs1nSodBeGaZf
+kgleT5AbQ6DMA8GA1UdEwEB/wQFMAMBAf8wDQYJKoZIhvcNAQELBQADggIBAMaa
RFw7o8jbs31wfDpF6ZLP8tHZbY31hVdF0il64iTAyYrknhrrLslhzwllT+F06htY
xbm4a0qFZM8ZJBenimB8UPgnFTVD+MKYPyH8G24o/DSI7OYRaM/zx7gg4V+XJQEh
FaqH0hdavU1EXT/cjpH+leo4GnFw7GomUSb1uIOMYhdTeP/0lXXJaAmDfrxfWuIo
mTNb85KeRa/PxE1zju7ES/IVpiHh0JEwodFakb57ccPIoD/Zz6HakVLcPsLTdqWU
OtKbEK0hEnVOPT/kUKFkJC2LuNUmulCLE3tRSoZsAT/32cTS1BtFhVqHynZj/pQz
XHbSTkvj3N4ETv3mPb96iAS6US04LIsfb+cK/WblNKWTz2+y88LCwZ39uL7Y+j25
ThtOxe6VHVHQ+SvC5fntuZYMSFZIVTf/4/Y2fA0P3gYduB5Ym2nrsBEqo45K/5Sn
FtNV7nkR1zyY8RgWIi8UhB/aIyvRSVfJbtgdyshEzZevkcu/BBYhJegCPuch6tIN
iBCa4Cg0UqiGZa7dWO7DaFzKo9x1C1PHcGVddh1sv1FBpaKOioqGNjsM5KB+bL4t
TiMXsab9rf0GiLlv9/DgUghM6efozFVqgdp0oxk+4U6heTgpu7EOJ4+MX4mw06GX
H2T5Ehc8pByak4dwjv0sAIHh2rSUDAtNq+v54V18
-----END CERTIFICATE-----
```