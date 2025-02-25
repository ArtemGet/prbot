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

package io.github.artemget.prbot.bot.route;

import io.github.artemget.prbot.bot.command.CmdPrMerge;
import io.github.artemget.prbot.bot.command.CmdPrOpen;
import io.github.artemget.teleroute.telegrambots.update.TgBotWrap;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Test case {@link RouteHkGitlab}.
 *
 * @since 0.0.1
 */
class RouteHkGitlabTest {

    @Test
    public void routesToOpen() {
        MatcherAssert.assertThat(
            "Routes not to open command",
            new RouteHkGitlab("123")
                .route(
                    RouteHkGitlabTest.mocked(
                        """
                            {
                              "token": "123",
                              "gitp": "gitlab",
                              "body": {
                                "object_attributes": {
                                  "action": "open"
                                }
                              }
                            }"""
                    )
                ).get().getClass(),
            Matchers.equalTo(CmdPrOpen.class)
        );
    }

    @Test
    public void routesToMerge() {
        MatcherAssert.assertThat(
            "Routes not to merge command",
            new RouteHkGitlab("123")
                .route(
                    RouteHkGitlabTest.mocked(
                        """
                            {
                              "token": "123",
                              "gitp": "gitlab",
                              "body": {
                                "object_attributes": {
                                  "action": "merge"
                                }
                              }
                            }"""
                    )
                ).get().getClass(),
            Matchers.equalTo(CmdPrMerge.class)
        );
    }

    @Test
    public void routesNotWhenUnauthorized() {
        MatcherAssert.assertThat(
            "Routes when token not match",
            new RouteHkGitlab("123")
                .route(
                    RouteHkGitlabTest.mocked(
                        """
                            {
                              "token": "",
                              "gitp": "gitlab",
                              "body": {
                                "object_attributes": {
                                  "action": "merge"
                                }
                              }
                            }"""
                    )
                ).isEmpty(),
            Matchers.equalTo(true)
        );
    }

    @Test
    public void routesNotWhenWrongGitP() {
        MatcherAssert.assertThat(
            "Routes to gitlab but gitp is set to github",
            new RouteHkGitlab("123")
                .route(
                    RouteHkGitlabTest.mocked(
                        """
                            {
                              "token": "123",
                              "gitp": "github",
                              "body": {
                                "object_attributes": {
                                  "action": "merge"
                                }
                              }
                            }"""
                    )
                ).isEmpty(),
            Matchers.equalTo(true)
        );
    }

    @Test
    public void routesNotWhenCorruptedJson() {
        MatcherAssert.assertThat(
            "Routes to command, but json is corrupted",
            new RouteHkGitlab("123")
                .route(
                    RouteHkGitlabTest.mocked(
                        """
                            {
                              "token": "123",
                              "gitp": "github","""
                    )
                ).isEmpty(),
            Matchers.equalTo(true)
        );
    }

    @Test
    public void routesNotWhenWrongJson() {
        MatcherAssert.assertThat(
            "Routes to command, but json has different fields",
            new RouteHkGitlab("123")
                .route(
                    RouteHkGitlabTest.mocked(
                        """
                            {
                              "object_attributes": {
                                "action": "merge"
                               }
                            }"""
                    )
                ).isEmpty(),
            Matchers.equalTo(true)
        );
    }

    /**
     * Mocked Update.
     *
     * @param msg Text
     * @return Update with message
     * @todo #6:30min refactor this private static method.
     *  I suggest using junit's {@link ParameterResolver}
     *  to create mocked {@link Update} with provided
     *  text.
     */
    private static TgBotWrap mocked(final String msg) {
        final Message message = new Message();
        message.setText(msg);
        final Update update = new Update();
        update.setMessage(message);
        return new TgBotWrap(update);
    }
}
