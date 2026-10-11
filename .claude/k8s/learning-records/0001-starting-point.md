# Starting point: new to Kubernetes, fluent with Docker Compose

The learner stated (2026-10-11) they are brand new to Kubernetes and Helm; they already run this project with Docker and Docker Compose (multi-container stack, volumes, env vars, healthchecks, port mapping). Goals: deploy at work + interviews.

**Implications:** anchor every new concept to its Compose equivalent (service ↔ Deployment+Service, `volumes:` ↔ PVC, `environment:` ↔ ConfigMap/Secret, `healthcheck` ↔ probes, `depends_on` ↔ init container/readiness). Don't re-teach containers or images. Interview framing: name things precisely and contrast them.
