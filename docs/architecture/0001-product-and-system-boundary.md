# ADR-0001: Product and system boundary

- Status: Accepted
- Date: 2026-08-14

## Decision

Community Steward is a multi-repository GitHub App with four boundaries:

1. **Intake** authenticates and deduplicates webhook deliveries.
2. **Community model** records contributions, response commitments and outcomes.
3. **Policy engine** identifies overdue acknowledgement, review and follow-up obligations.
4. **GitHub adapter** reads current state and performs explicitly enabled comments, labels and checks.

GitHub remains authoritative for repositories, actors, issues and pull requests. Steward stores GitHub identifiers and derived operational state. Raw webhook bodies are not retained by default.

## Safety rules

- Verify `X-Hub-Signature-256` over the unmodified request body.
- Deduplicate using `X-GitHub-Delivery` before creating work.
- Reconcile material actions with the GitHub API.
- Default to recommendations; require repository opt-in for automated writes.
- Never use contribution volume as a proxy for contributor value.

## Initial GitHub App permissions

| Permission | Access | Purpose |
|---|---:|---|
| Metadata | Read | Repository identity |
| Issues | Read | Observe issues and responses |
| Pull requests | Read | Observe contributions and reviews |
| Checks | Read | Explain readiness blockers |
| Contents | Read | Read community-health files |

Write permissions will be introduced only with the feature that needs them.
