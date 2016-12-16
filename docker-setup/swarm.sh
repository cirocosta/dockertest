#!/bin/bash

set -o errexit
set -o xtrace

readonly KV_NODE_NAME="kv"
readonly MANAGER_NODE_NAME="manager"


main () {
  create_kv
  configure_kv

  create_manager

  for i in `seq 1 3`; do
    create_worker worker-$i &    
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
    --swarm \
    --swarm-master \
    --swarm-discovery="consul://$(docker-machine ip $KV_NODE_NAME):8500" \
    --engine-opt="label=com.function=manager" \
    --engine-opt="cluster-store=consul://$(docker-machine ip $KV_NODE_NAME):8500" \
    --engine-opt="cluster-advertise=eth1:2376" \
    $MANAGER_NODE_NAME
}


create_worker () {
  local name=$1

  docker-machine create \
    -d virtualbox \
    --swarm \
    --swarm-discovery="consul://$(docker-machine ip $KV_NODE_NAME):8500" \
    --engine-opt="label=com.function=$name" \
    --engine-opt="cluster-store=consul://$(docker-machine ip $KV_NODE_NAME):8500" \
    --engine-opt="cluster-advertise=eth1:2376" \
    $name
}


main

