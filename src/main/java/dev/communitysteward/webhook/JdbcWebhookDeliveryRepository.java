package dev.communitysteward.webhook;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
final class JdbcWebhookDeliveryRepository implements WebhookDeliveryRepository {
    private final JdbcClient jdbc;
    JdbcWebhookDeliveryRepository(JdbcClient jdbc) { this.jdbc = jdbc; }

    @Override
    public boolean recordIfFirst(WebhookDelivery d) {
        try {
            jdbc.sql("""
                insert into webhook_delivery
                  (delivery_id,event_name,action,installation_id,repository_id,received_at)
                values (:deliveryId,:eventName,:action,:installationId,:repositoryId,:receivedAt)
                """)
                .param("deliveryId", d.deliveryId()).param("eventName", d.eventName())
                .param("action", d.action()).param("installationId", d.installationId())
                .param("repositoryId", d.repositoryId()).param("receivedAt", d.receivedAt()).update();
            return true;
        } catch (DuplicateKeyException duplicate) {
            return false;
        }
    }
}
