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

package io.github.artemget.prbot.webhook;

import io.github.artemget.prbot.bot.BotFk;
import io.github.artemget.prbot.config.EntryFk;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.takes.rq.RqFake;
import org.takes.rq.RqWithHeader;
import org.takes.rs.RsPrint;

class TkHooksTest {

    @Test
    void returnsUnauthenticated() throws Exception {
        MatcherAssert.assertThat(
            "Passed without token",
            new RsPrint(
                new TkHooks(new BotFk(), new EntryFk<>("123"))
                    .act(new RqFake())
            ).printBody(),
            Matchers.equalTo("unauthenticated")
        );
    }

    @Test
    void returnsOk() throws Exception {
        MatcherAssert.assertThat(
            "Unauthenticated at correct token",
            new RsPrint(
                new TkHooks(new BotFk(), new EntryFk<>("123"))
                    .act(
                        new RqWithHeader(
                            new RqFake("POST", "/webhooks/gitlab"),
                            "X-Gitlab-Token",
                            "123"
                        )
                    )
            ).printBody(),
            Matchers.equalTo("")
        );
    }

    @Test
    void returnsMethodNotAllowed() throws Exception {
        MatcherAssert.assertThat(
            "Passed without token",
            new RsPrint(
                new TkHooks(new BotFk(), new EntryFk<>("123"))
                    .act(
                        new RqWithHeader(
                            new RqFake("GET", "/webhooks/gitlab"),
                            "X-Gitlab-Token",
                            "123"
                        )
                    )
            ).printBody(),
            Matchers.equalTo("route not found")
        );
    }
}
