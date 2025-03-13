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

import io.github.artemget.prbot.config.EntryException;
import jakarta.json.JsonObject;
import java.util.List;
import javax.json.Json;
import net.joshka.junit.json.params.JsonFileSource;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

/**
 * Test case for {@link io.github.artemget.prbot.domain.pr.PrJsonStrict}
 *
 * @since 0.0.1
 */
class PrJsonStrictTest {

    @Test
    void throwsAtWrongJsonFormatAtCreation() {
        Assertions.assertThrows(
            EntryException.class,
            () -> new PrJsonStrict("not json"),
            "Not thrown at wrong json format"
        );
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/PrStrict.json")
    void returnsIdentity(JsonObject object) throws EntryException, EmptyArgumentException {
        MatcherAssert.assertThat(
            "Wrong identity",
            new PrJsonStrict(object.toString()).identity(),
            Matchers.equalTo("10")
        );
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/PrStrict.json")
    void returnsLink(JsonObject object) throws EntryException, EmptyArgumentException {
        MatcherAssert.assertThat(
            "Wrong link",
            new PrJsonStrict(object.toString()).link(),
            Matchers.equalTo("https://github.com/ArtemGet/prbot/pull/10")
        );
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/PrStrict.json")
    void returnsStatus(JsonObject object) throws EntryException, EmptyArgumentException {
        MatcherAssert.assertThat(
            "Wrong status",
            new PrJsonStrict(object.toString()).status(),
            Matchers.equalTo(PullRequest.Status.OPENED)
        );
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/PrStrict.json")
    void returnsFrom(JsonObject object) throws EntryException, EmptyArgumentException {
        MatcherAssert.assertThat(
            "Wrong from account",
            new PrJsonStrict(object.toString()).from(),
            Matchers.equalTo(
                new AccJson(
                    Json.createObjectBuilder()
                        .add("id", "123")
                        .add("username", "ArtemGet")
                        .build()
                )
            )
        );
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/PrStrict.json")
    void returnsAssigners(JsonObject object) throws EntryException, EmptyArgumentException {
        MatcherAssert.assertThat(
            "Wrong assigner account",
            new PrJsonStrict(object.toString()).assigners(),
            Matchers.equalTo(
                List.of(
                    new AccJson(
                        Json.createObjectBuilder()
                            .add("id", "123")
                            .add("username", "ArtemGet")
                            .build()
                    )
                )
            )
        );
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/PrStrict.json")
    void returnsReviewers(JsonObject object) throws EntryException, EmptyArgumentException {
        MatcherAssert.assertThat(
            "Wrong reviewer account",
            new PrJsonStrict(object.toString()).reviewers(),
            Matchers.equalTo(
                List.of(
                    new AccJson(
                        Json.createObjectBuilder()
                            .add("id", "321")
                            .add("username", "ReviewerUser")
                            .build()
                    )
                )
            )
        );
    }

    @ParameterizedTest
    @JsonFileSource(resources = "/PrStrict.json")
    void returnsBranchFrom(JsonObject object) throws EntryException, EmptyArgumentException {
        MatcherAssert.assertThat(
            "Wrong source branch",
            new PrJsonStrict(object.toString()).branchFrom(),
            Matchers.equalTo(
                new BrJson(
                    Json.createObjectBuilder()
                        .add("name", "Eh/#6")
                        .add("link", "https://github.com/ArtemGet/prbot/tree/eh/%236")
                        .build()
                )
            )
        );
    }
    @ParameterizedTest
    @JsonFileSource(resources = "/PrStrict.json")
    void returnsBranchTo(JsonObject object) throws EntryException, EmptyArgumentException {
        MatcherAssert.assertThat(
            "Wrong target branch",
            new PrJsonStrict(object.toString()).branchTo(),
            Matchers.equalTo(
                new BrJson(
                    Json.createObjectBuilder()
                        .add("name", "main")
                        .add("link", "https://github.com/ArtemGet/prbot/tree/main")
                        .build()
                )
            )
        );
    }
}
