docker-compose -f $PWD/docker-compose-$1.yaml -p passion-mansour-ide-server-$1 down
docker-compose -f $PWD/docker-compose-$1.yaml -p passion-mansour-ide-server-$1 up -d --force-recreate

docker logs -f passion-mansour-ide-server-$1