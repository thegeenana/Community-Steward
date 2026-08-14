package dev.communitysteward.webhook;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("community-steward.github.webhook")
public record WebhookProperties(String secret) {
    public WebhookProperties {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("GitHub webhook secret must be configured");
        }
    }
}
