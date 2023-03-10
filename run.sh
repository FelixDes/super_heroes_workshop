docker network create keycloak
docker network create backend
docker network create kafka
docker network create frontend

cd ./kafka
docker-compose rm -f
docker-compose pull
docker-compose down --remove-orphans
docker-compose up --build -d
cd ../

cd ./keycloak
docker-compose rm -f
docker-compose pull
docker-compose down --remove-orphans
docker-compose up --build -d
cd ../

#cd ./rest_fight
#docker-compose rm -f
#docker-compose pull
#docker-compose down --remove-orphans
#mvn clean install -Dmaven.test.skip
#docker-compose up --build -d
#cd ../

cd ./rest_villain
docker-compose rm -f
docker-compose pull
docker-compose down --remove-orphans
mvn clean install -Dmaven.test.skip
docker-compose up --build -d
cd ../

cd ./rest_hero
docker-compose rm -f
docker-compose pull
docker-compose down --remove-orphans
mvn clean install -Dmaven.test.skip
docker-compose up --build -d
cd ../

#cd ./frontend
#docker-compose rm -f
#docker-compose pull
#docker-compose down --remove-orphans
#docker-compose up --build -d
#cd ../
