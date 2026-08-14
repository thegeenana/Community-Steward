package dev.communitysteward.webhook;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Clock;
import org.springframework.stereotype.Service;

@Service
public final class WebhookIntakeService {
    private final ObjectMapper mapper;
    private final WebhookDeliveryRepository repository;
    private final Clock clock;

    public WebhookIntakeService(ObjectMapper mapper, WebhookDeliveryRepository repository) {
        this(mapper, repository, Clock.systemUTC());
    }
    WebhookIntakeService(ObjectMapper mapper, WebhookDeliveryRepository repository, Clock clock) {
        this.mapper = mapper; this.repository = repository; this.clock = clock;
    }
    public boolean accept(String id, String event, byte[] payload) throws IOException {
        JsonNode root = mapper.readTree(payload);
        return repository.recordIfFirst(new WebhookDelivery(id, event, text(root, "action"),
                number(root.path("installation"), "id"), number(root.path("repository"), "id"), clock.instant()));
    }
    private static String text(JsonNode n, String f) { return n.hasNonNull(f) ? n.get(f).asText() : null; }
    private static Long number(JsonNode n, String f) {
        return n.hasNonNull(f) && n.get(f).canConvertToLong() ? n.get(f).asLong() : null;
    }
}
