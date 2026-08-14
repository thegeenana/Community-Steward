package dev.communitysteward.webhook;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public final class GitHubSignatureVerifier {
    private static final String PREFIX = "sha256=";
    private final byte[] secret;

    public GitHubSignatureVerifier(WebhookProperties properties) {
        secret = properties.secret().getBytes(StandardCharsets.UTF_8);
    }

    public boolean isValid(byte[] payload, String suppliedSignature) {
        if (suppliedSignature == null || !suppliedSignature.startsWith(PREFIX)) return false;
        try {
            byte[] supplied = HexFormat.of().parseHex(suppliedSignature.substring(PREFIX.length()));
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret, "HmacSHA256"));
            return MessageDigest.isEqual(mac.doFinal(payload), supplied);
        } catch (Exception invalidSignature) {
            return false;
        }
    }
}
