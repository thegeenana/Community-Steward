# Community Steward

> **Every contribution deserves a response.**

Community Steward is a GitHub App for maintainers who want healthy repositories and supported contributors. It turns repository activity into a humane attention queue: what needs a response, why it matters and what the maintainer can do next.

## First product promise

No new issue or pull request should become silently stale.

The MVP measures acknowledgement and review time, identifies first-time contributors, explains readiness blockers and produces a weekly community-health report. It recommends actions by default; repositories must explicitly opt in before it writes comments or labels.

## Current foundation

- HMAC-SHA256 verification of GitHub webhook deliveries
- Idempotent intake using `X-GitHub-Delivery`
- PostgreSQL persistence and Flyway migrations
- Health endpoints, container build and CI
- Product and architecture decisions in [`docs/`](docs/)

## Run locally

Requirements: Java 25, Maven 3.9+ and Docker.

```bash
docker compose up -d postgres
export GITHUB_WEBHOOK_SECRET='replace-with-a-local-secret'
mvn spring-boot:run
```

Webhook: `POST /api/v1/github/webhooks`. Health: `GET /actuator/health`.

## Engineering principles

- GitHub is authoritative for repository state.
- Webhooks are authenticated notifications, not unquestioned truth.
- Delivery processing is idempotent and observable.
- Automation assists maintainers; it does not impersonate them.
- Contributor dignity matters more than activity scores.

See the [MVP definition](docs/product/mvp.md) and [system-boundary ADR](docs/architecture/0001-product-and-system-boundary.md).

Licensed under Apache License 2.0.
