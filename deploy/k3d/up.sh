#!/usr/bin/env bash
# Build images, load them into the local k3d cluster "maku", and install/upgrade the Helm release.
# Usage: deploy/k3d/up.sh            (run from anywhere)
# Every step is a plain command — read it, the lessons explain each one.
set -euo pipefail

HERE="$(cd "$(dirname "$0")" && pwd)"
BACKEND="$(cd "$HERE/../.." && pwd)"
FRONTEND="$(cd "$BACKEND/../maku-admin-frontend" && pwd)"
CLUSTER=maku
NAMESPACE=maku
TAG="${TAG:-5.0.0}"
API_URL="http://api.maku.localhost:8088"

# 1. Cluster (idempotent)
if ! k3d cluster list -o json | grep -q "\"name\":\"$CLUSTER\""; then
  k3d cluster create --config "$HERE/cluster.yaml"
fi
kubectl config use-context "k3d-$CLUSTER"

# 2. Images. The frontend bakes the API URL in at build time.
docker build -t "maku-boot:$TAG" "$BACKEND"
docker build -t "maku-admin:$TAG" --build-arg VITE_API_URL="$API_URL" "$FRONTEND"

# 3. Copy the images into the cluster's nodes (k3d has no access to your local Docker images)
k3d image import "maku-boot:$TAG" "maku-admin:$TAG" -c "$CLUSTER"

# 4. Install or upgrade the release
helm upgrade --install maku "$BACKEND/deploy/helm/maku" \
  --namespace "$NAMESPACE" --create-namespace \
  -f "$BACKEND/deploy/helm/maku/values-k3d.yaml" \
  --set backend.image.tag="$TAG" --set frontend.image.tag="$TAG" \
  --wait --timeout 10m

kubectl -n "$NAMESPACE" get pods,svc,ingress
echo "UI: http://maku.localhost:8088   API: $API_URL   (admin / admin)"
