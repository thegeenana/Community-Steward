package dev.communitysteward.webhook;

import static org.assertj.core.api.Assertions.assertThat;
import tools.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

class WebhookIntakeServiceTest {
    @Test void extractsOnlyTheRequiredEnvelopeFacts() throws Exception {
        AtomicReference<WebhookDelivery> saved = new AtomicReference<>();
        Clock clock = Clock.fixed(Instant.parse("2026-08-14T18:00:00Z"), ZoneOffset.UTC);
        WebhookIntakeService service = new WebhookIntakeService(new ObjectMapper(), d -> { saved.set(d); return true; }, clock);
        byte[] body = """
            {"action":"opened","installation":{"id":12},"repository":{"id":34},"secret":"discard"}
            """.getBytes(StandardCharsets.UTF_8);
        assertThat(service.accept("delivery-1", "issues", body)).isTrue();
        assertThat(saved.get()).isEqualTo(new WebhookDelivery("delivery-1", "issues", "opened", 12L, 34L, clock.instant()));
    }
}
