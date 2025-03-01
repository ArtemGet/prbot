package io.github.artemget.prbot.domain.pr;

import io.github.artemget.prbot.config.Entry;
import io.github.artemget.prbot.config.EntryException;
public interface RequestWebhook {
    String token();

    String action();

    String platform();

    Project project();

    PullRequest pullRequest();

    final class JsonStrict implements Entry<String> {
        private final RequestWebhook request;

        public JsonStrict(RequestWebhook request) {
            this.request = request;
        }

        @Override
        public String value() throws EntryException {
            return null;
        }
    }
}
