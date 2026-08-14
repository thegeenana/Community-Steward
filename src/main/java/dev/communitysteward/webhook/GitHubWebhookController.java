package dev.communitysteward.webhook;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/github/webhooks")
public final class GitHubWebhookController {
    private final GitHubSignatureVerifier verifier;
    private final WebhookIntakeService intake;
    public GitHubWebhookController(GitHubSignatureVerifier verifier, WebhookIntakeService intake) {
        this.verifier = verifier; this.intake = intake;
    }
    @PostMapping
    ResponseEntity<Void> receive(@RequestHeader("X-GitHub-Delivery") String deliveryId,
            @RequestHeader("X-GitHub-Event") String eventName,
            @RequestHeader("X-Hub-Signature-256") String signature,
            @RequestBody byte[] payload) throws IOException {
        if (!verifier.isValid(payload, signature)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(intake.accept(deliveryId, eventName, payload)
                ? HttpStatus.ACCEPTED : HttpStatus.OK).build();
    }
}
