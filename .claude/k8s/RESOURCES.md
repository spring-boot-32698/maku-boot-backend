# Kubernetes & Helm Resources

## Knowledge

### Primary documentation (highest trust)
- [Kubernetes Basics tutorial (kubernetes.io)](https://kubernetes.io/docs/tutorials/kubernetes-basics/) — official interactive intro: deploy, expose, scale, update. Use for: a second explanation of lessons 1–3.
- [Kubernetes Components](https://kubernetes.io/docs/concepts/overview/components/) — control plane vs node components. Use for: lesson 1, interview "what happens when you kubectl apply".
- [Objects in Kubernetes](https://kubernetes.io/docs/concepts/overview/working-with-objects/) — spec vs status, desired state. Use for: the core mental model.
- [Pods](https://kubernetes.io/docs/concepts/workloads/pods/) · [Pod lifecycle](https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle/) · [Init containers](https://kubernetes.io/docs/concepts/workloads/pods/init-containers/)
- [Deployments](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/) · [StatefulSets](https://kubernetes.io/docs/concepts/workloads/controllers/statefulset/)
- [Service](https://kubernetes.io/docs/concepts/services-networking/service/) · [DNS for Services and Pods](https://kubernetes.io/docs/concepts/services-networking/dns-pod-service/) · [Ingress](https://kubernetes.io/docs/concepts/services-networking/ingress/)
- [ConfigMaps](https://kubernetes.io/docs/concepts/configuration/configmap/) · [Secrets](https://kubernetes.io/docs/concepts/configuration/secret/) — note: Secrets are base64-encoded, not encrypted, by default.
- [Persistent Volumes](https://kubernetes.io/docs/concepts/storage/persistent-volumes/) — PV, PVC, StorageClass, access modes, reclaim policy.
- [Liveness, Readiness and Startup Probes](https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/) · [Resource requests and limits](https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/)
- [Recommended labels](https://kubernetes.io/docs/concepts/overview/working-with-objects/common-labels/) — the `app.kubernetes.io/*` labels our chart uses.
- [Debug Pods](https://kubernetes.io/docs/tasks/debug/debug-application/debug-pods/) · [Debug Running Pods](https://kubernetes.io/docs/tasks/debug/debug-application/debug-running-pod/) · [Debug Services](https://kubernetes.io/docs/tasks/debug/debug-application/debug-service/) — the official troubleshooting flows (lesson 11).
- [Gateway API (kubernetes.io)](https://kubernetes.io/docs/concepts/services-networking/gateway/) · [gateway-api.sigs.k8s.io](https://gateway-api.sigs.k8s.io/) — Ingress's successor (lesson 07).
- [Ingress NGINX Retirement: What You Need to Know](https://kubernetes.io/blog/2025/11/11/ingress-nginx-retirement/) — Nov 2025 announcement; best-effort maintenance ended March 2026. Use for: why not to pick ingress-nginx for new work.
- [HorizontalPodAutoscaler](https://kubernetes.io/docs/tasks/run-application/horizontal-pod-autoscale/) · [walkthrough](https://kubernetes.io/docs/tasks/run-application/horizontal-pod-autoscale-walkthrough/) (lesson 12).
- [Disruptions / PodDisruptionBudget](https://kubernetes.io/docs/concepts/workloads/pods/disruptions/) · [Network Policies](https://kubernetes.io/docs/concepts/services-networking/network-policies/) · [Pod Security Standards](https://kubernetes.io/docs/concepts/security/pod-security-standards/) · [RBAC](https://kubernetes.io/docs/reference/access-authn-authz/rbac/) · [Resource Quotas](https://kubernetes.io/docs/concepts/policy/resource-quotas/) · [Images & pull secrets](https://kubernetes.io/docs/concepts/containers/images/) — production hardening (lesson 12).
- [kubectl Quick Reference](https://kubernetes.io/docs/reference/kubectl/quick-reference/) — the cheat sheet to keep open.

### Helm
- [Using Helm](https://helm.sh/docs/intro/using_helm/) — install/upgrade/rollback/values. Use for: release lifecycle.
- [Charts](https://helm.sh/docs/topics/charts/) — chart layout, Chart.yaml, versions.
- [Chart Template Guide](https://helm.sh/docs/chart_template_guide/getting_started/) — templating language, values, named templates, `include`, `nindent`. Use for: reading `templates/`.
- [Chart Best Practices](https://helm.sh/docs/chart_best_practices/) — naming, labels, values conventions.
- [Chart Hooks](https://helm.sh/docs/topics/charts_hooks/) — how `helm test` (our `tests/test-backend.yaml`) works.
- [Template functions & pipelines](https://helm.sh/docs/chart_template_guide/functions_and_pipelines/) · [control structures](https://helm.sh/docs/chart_template_guide/control_structures/) · [named templates](https://helm.sh/docs/chart_template_guide/named_templates/) · [values files](https://helm.sh/docs/chart_template_guide/values_files/) (lesson 08).
- [helm upgrade](https://helm.sh/docs/helm/helm_upgrade/) · [helm rollback](https://helm.sh/docs/helm/helm_rollback/) (lesson 10).
- [Helm 4 overview](https://helm.sh/docs/overview/) — **verified**: `--atomic` → `--rollback-on-failure`, `--force` → `--force-replace`; new installs use server-side apply; `apiVersion: v2` charts work unchanged. [Helm 4 release post](https://helm.sh/blog/helm-4-released/): Helm 3 security fixes until 2026-11-11.

### Delivery & secrets tooling (lesson 12)
- [Argo CD](https://argo-cd.readthedocs.io/en/stable/) · [Flux](https://fluxcd.io/flux/) — GitOps controllers.
- [External Secrets Operator](https://external-secrets.io/latest/) · [Sealed Secrets](https://github.com/bitnami-labs/sealed-secrets) — keeping secrets out of git / syncing from secret managers.

### Tools in this setup
- [k3d docs](https://k3d.io/stable/) · [k3d config file](https://k3d.io/stable/usage/configfile/) — our `deploy/k3d/cluster.yaml`.
- [k3s networking (Traefik, ServiceLB)](https://docs.k3s.io/networking/networking-services) — why ingress works on k3d without installing anything.
- [Spring Boot Actuator endpoints](https://docs.spring.io/spring-boot/reference/actuator/endpoints.html) and [Deploying to the cloud / Kubernetes](https://docs.spring.io/spring-boot/how-to/deployment/cloud.html) — liveness/readiness groups used by our probes.

### Career / certification
- [CKAD exam (Linux Foundation)](https://training.linuxfoundation.org/certification/certified-kubernetes-application-developer-ckad/) and [CNCF curriculum](https://github.com/cncf/curriculum) — a good checklist of what "knowing Kubernetes as a developer" means, even if you don't take the exam.

## Wisdom (Communities)
- [Kubernetes Slack](https://slack.k8s.io/) — channels `#kubernetes-novice`, `#kubernetes-users`, `#helm-users`. Use for: "is this the right way?" questions.
- [r/kubernetes](https://www.reddit.com/r/kubernetes/) — practitioner discussion, war stories, tooling opinions.
- [Kubernetes community page](https://kubernetes.io/community/) — meetups and KubeCon (CNCF) events near you; local CNCF meetups are great for interview-relevant wisdom.
- Your platform/DevOps team at work — ask how *they* deploy (Helm? Kustomize? Argo CD?) and adapt lesson 11 to it.

## Gaps
- No primary source yet for k3d-specific gotchas beyond the k3d docs; lessons rely on behaviour verified on this machine (k3d 5.8.3, k3s v1.31.5, Traefik 2.11).
