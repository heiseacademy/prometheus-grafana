#!/bin/sh

[ ! -d kube-prometheus ] && git clone --depth 1 --branch v0.8.0 https://github.com/prometheus-operator/kube-prometheus.git

cd kube-prometheus || exit

# Create the namespace and CRDs, and then wait for them to be available before creating the remaining resources
kubectl create -f manifests/setup
until kubectl get servicemonitors --all-namespaces ; do date; sleep 1; echo ""; done
kubectl create -f manifests/

cd ..

# Ingress
kubectl apply -f manifests/prometheus/

kubectl get all -n monitoring