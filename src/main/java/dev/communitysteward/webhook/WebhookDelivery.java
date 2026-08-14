package dev.communitysteward.webhook;

import java.time.Instant;

public record WebhookDelivery(String deliveryId, String eventName, String action,
                              Long installationId, Long repositoryId, Instant receivedAt) {}
