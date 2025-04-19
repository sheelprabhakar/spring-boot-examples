REM Deploy Ingress services
kubectl apply -f cd/ingress/nginx-ingress-deploy.yaml
REM Deploy Backing services
kubectl apply -f cd/backend/mysql/mysql-configmap.yml
kubectl apply -f cd/backend/mysql/mysql.yml
kubectl apply -f cd/backend/redis/redis.yml
kubectl apply -f cd/backend/rabbit/rabbitmq.yml
kubectl apply -f cd/backend/network-policy.yml

REM timeout 30 > NUL
REM kubectl apply -f cd/ingress/ingress.yaml
REM timeout 30 > NUL
REM Deploy Service Backends
REM kubectl delete -f service-deployment.yaml
REM kubectl apply -f service-deployment.yaml