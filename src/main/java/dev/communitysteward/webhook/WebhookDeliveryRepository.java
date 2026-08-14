package dev.communitysteward.webhook;

public interface WebhookDeliveryRepository {
    boolean recordIfFirst(WebhookDelivery delivery);
}
