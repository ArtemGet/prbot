/*
 * MIT License
 *
 * Copyright (c) 2024-2025. Artem Getmanskii
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.artemget.prbot.domain.pr;

import io.github.artemget.prbot.config.Entry;
import io.github.artemget.prbot.config.EntryException;

/**
 * Source code platform webhook request.
 *
 * @since 0.0.1
 */
public interface RequestWebhook {
    /**
     * Request's auth token.
     *
     * @return Token
     * @throws EmptyArgumentException If fails
     */
    String token() throws EmptyArgumentException;

    /**
     * Action taken at pull-request. OPENED, UPDATED,
     * APPROVED, UNAPPROVED, MERGED.
     *
     * @return Action status
     * @throws EmptyArgumentException If fails
     */
    String action() throws EmptyArgumentException;

    /**
     * Source code platform name.
     *
     * @return Platform name
     * @throws EmptyArgumentException If fails
     */
    String platform() throws EmptyArgumentException;

    /**
     * Project.
     *
     * @return Project
     * @throws EmptyArgumentException Source code platform name
     */
    Project project() throws EmptyArgumentException;

    /**
     * Pull-request payload.
     *
     * @return Pull-request payload
     */
    PullRequest pullRequest();

    /**
     * Entry that makes strict request from any format.
     *
     * @since 0.0.1
     */
    final class RequestsStrict implements Entry<RequestWebhook> {
        /**
         * Request webhook of any format.
         */
        private final RequestWebhook request;

        public RequestsStrict(final RequestWebhook request) {
            this.request = request;
        }

        @Override
        public RequestWebhook value() throws EntryException {
            throw new UnsupportedOperationException("unimplemented");
        }
    }
}
