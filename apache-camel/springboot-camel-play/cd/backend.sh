#!/bin/bash

# Deploy Ingress services
kubectl apply -f ingress/nginx-ingress-deploy.yaml

# Deploy Backing services
kubectl apply -f backend/mysql/mysql-configmap.yml
kubectl apply -f backend/mysql/mysql.yml
kubectl apply -f backend/redis/redis.yml
kubectl apply -f backend/rabbit/rabbitmq.yml
kubectl apply -f backend/network-policy.yaml