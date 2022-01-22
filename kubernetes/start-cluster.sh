#!/bin/sh 
minikube version
kubectl version
# Achtung: Setting ist für Linux Systeme mit Docker und mindestens 16GB RAM und 8 vCores
# Unter Windows / Mac würde nicht der Docker-Driver verwendet.

# minikube start --driver docker --cpus 6 --memory 8192 --ports 80:80

# Setup für Zugriff von Prometheus auf Kubernetes Komponenten via Token
minikube start --driver docker --cpus 6 --memory 8192 --ports 80:80 \
   --extra-config=kubelet.authentication-token-webhook=true \
   --extra-config=kubelet.authorization-mode=Webhook  \
   --extra-config=scheduler.address=0.0.0.0 \
   --extra-config=controller-manager.address=0.0.0.0

# Kubernetes interne Metriken für Dashboard, falls kein Prometheus eingesetzt wird
# minikube addons enable metrics-server
# Interne Metriken können zukünftig auch von Prometheus bezogen werden
minikube addons disable metrics-server
minikube addons enable dashboard

kubectl apply -f manifests/ingress/02-crd.yaml

# Auf CRDs warten
sleep 4

kubectl apply -f manifests/ingress/


minikube service list
