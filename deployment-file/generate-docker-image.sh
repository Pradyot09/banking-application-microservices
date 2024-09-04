#! /bin/sh

cd /Users/prady/IntelliJ/Section\ 2-JavaExpress/eurekaserver || exit
mvn clean install
docker build -t pradyot09/eurekaserver:0.0.1 .
docker push pradyot09/eurekaserver:0.0.1

cd /Users/prady/IntelliJ/Section\ 2-JavaExpress/accounts || exit
mvn clean install
docker build -t pradyot09/accounts:0.0.1 .
docker push pradyot09/accounts:0.0.1

cd /Users/prady/IntelliJ/Section\ 2-JavaExpress/loans || exit
mvn clean install
docker build -t pradyot09/loans:0.0.1 .
docker push pradyot09/loans:0.0.1

cd /Users/prady/IntelliJ/Section\ 2-JavaExpress/cards || exit
mvn clean install
docker build -t pradyot09/cards:0.0.1 .
docker push pradyot09/cards:0.0.1

cd /Users/prady/IntelliJ/Section\ 2-JavaExpress/gateway-server || exit
mvn clean install
docker build -t pradyot09/gateway-server:0.0.1 .
docker push pradyot09/gateway-server:0.0.1