#!/usr/bin/env bash
# Remove the release (keeps the cluster), or the whole cluster with --cluster.
set -euo pipefail
if [[ "${1:-}" == "--cluster" ]]; then
  k3d cluster delete maku
else
  helm uninstall maku -n maku || true
  echo "Note: PVCs (MySQL data, uploads) are kept. Delete them with: kubectl -n maku delete pvc --all"
fi
