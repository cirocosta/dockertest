#!/bin/bash

set -o errxit

readonly KV_NODE_NAME="kv"
readonly MANAGER_NODE_NAME="manager"

main () {
  create_kv
  configure_kv

  create_manager
  configure_manager

  for i in `seq 1 3`; do
    add_worker $i &    
  done
  wait

  echo "Done!"
}


create_kv () {
  docker-machine create \
    -d virtualbox \
    --engine-opt="label=com.function=consul" \
    $KV_NODE_NAME
}


configure_kv () {
  docker `docker-machine config $KV_NODE_NAME` \
    run \
    --restart=unless-stopped \
    -d \
    -p 8500:8500 \
    -h consul \
    progrium/consul \
      -server \
      -bootstrap
}


create_manager () {
  docker-machine create \
    -d virtualbox \
    --engine-opt="label=com.function=manager" \
    --engine-opt="cluster-store=consul://$(docker-machine ip $KV_NODE_NAME):8500" \
    --engine-opt="cluster-advertise=eth1:2376" \
    $MANAGER_NODE_NAME
}


configure_manager () {
  docker run `docker-machine config $MANAGER_NODE_NAME` \
    --restart=unless-stopped \
    -d \
    -p 3376:2375 \
    -v /var/lib/boot2docker:/certs:ro \
    swarm \
      manage \
      --tlsverify \
      --tlscacert=/certs/ca.pem \
      --tlscert=/certs/server.pem \
      --tlskey=/certs/server-key.pem \
      consul://$(docker-machine ip $KV_NODE_NAME):8500
}


add_worker () {
  local node_id=$1

  create_worker $node_id
  configure_worker $node_id
}


create_worker () {
  local node_id=$1
  local name=worker-$node_id

  docker-machine create \
    -d virtualbox \
    --engine-opt="label=com.function=$name" \
    --engine-opt="cluster-store=consul://$(docker-machine ip $KV_NODE_NAME):8500" \
    --engine-opt="cluster-advertise=eth1:2376" \
    $name
}


configure_worker () {
  local node_id=$1
  local name=worker-$node_id

  docker run `docker-machine config $name` \
    -d \
    swarm \
      join \
      --addr=$(docker-machine ip $name):2376 \
      consul://$(docker-machine ip $KV_NODE_NAME):8500
}


main

