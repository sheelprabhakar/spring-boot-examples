REM Crete k8s namespace
kubectl apply -f cd/backend/namespace.yml
REM Deploy Backing services
kubectl apply -f cd/backend/mysql-configmap.yml
kubectl apply -f cd/backend/mysql.yml
kubectl apply -f cd/backend/redis.yml
kubectl apply -f cd/backend/rabbitmq.yml

REM Deploy Ingress services
kubectl apply -f cd/ingress/nginx-ingress-deploy.yaml
timeout 30 > NUL
kubectl apply -f cd/ingress/ingress.yaml
timeout 30 > NUL
REM Deploy Service Backends
kubectl delete -f service-deployment.yaml
kubectl apply -f service-deployment.yaml