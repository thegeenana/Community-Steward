package dev.communitysteward.webhook;

import static org.assertj.core.api.Assertions.assertThat;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class GitHubSignatureVerifierTest {
    private final GitHubSignatureVerifier verifier = new GitHubSignatureVerifier(
            new WebhookProperties("It's a Secret to Everybody"));
    @Test void acceptsGitHubsPublishedSha256TestVector() {
        assertThat(verifier.isValid("Hello, World!".getBytes(StandardCharsets.UTF_8),
                "sha256=757107ea0eb2509fc211221cce984b8a37570b6d7586c22c46f4379c8b043e17")).isTrue();
    }
    @Test void rejectsUntrustedSignatures() {
        byte[] body = "Hello, World!".getBytes(StandardCharsets.UTF_8);
        assertThat(verifier.isValid(body, null)).isFalse();
        assertThat(verifier.isValid(body, "sha1=abc")).isFalse();
        assertThat(verifier.isValid(body, "sha256=not-hex")).isFalse();
        assertThat(verifier.isValid(body, "sha256=00")).isFalse();
    }
}
