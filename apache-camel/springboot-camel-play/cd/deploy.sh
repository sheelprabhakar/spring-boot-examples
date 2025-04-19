#!/bin/bash

for tenant in tenant{1..10}; do
  echo "Deploying resources for tenant: $tenant"

  # Check if namespace exists, create if it doesn't
  if ! kubectl get namespace $tenant >/dev/null 2>&1; then
    echo "Creating namespace: $tenant"
    kubectl create namespace $tenant
  else
    echo "Namespace $tenant already exists"
  fi

  # Deploy service, deployment, and ingress using Helm
  echo "Deploying Helm chart for tenant: $tenant"
  helm upgrade --install $tenant-service ./helm-chart \
    --set namespace=$tenant \
    --set database=$tenant \
    --set ingress.host=kubernetes.docker.internal \
    --set ingress.path=$tenant \
    --namespace $tenant

  if [ $? -ne 0 ]; then
    echo "Error deploying resources for tenant: $tenant"
    exit 1
  fi

  echo "Deployment completed for tenant: $tenant"
done