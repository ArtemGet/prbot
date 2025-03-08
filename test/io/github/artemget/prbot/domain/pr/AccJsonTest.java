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

import jakarta.json.Json;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test cases {@link AccJson}.
 *
 * @since 0.0.1
 */
class AccJsonTest {

    @Test
    void throwsAtEmptyUsername() {
        Assertions.assertThrows(
            EmptyArgumentException.class,
            () -> new AccJson(Json.createObjectBuilder().build()).username(),
            "Exception not thrown at empty username"
        );
    }

    @Test
    void throwsAtIntUsername() {
        Assertions.assertThrows(
            EmptyArgumentException.class,
            () -> new AccJson(
                Json.createObjectBuilder().add("username", 123).build()
            ).username(),
            "Exception not thrown at integer username"
        );
    }

    @Test
    void returnsUsername() throws EmptyArgumentException {
        MatcherAssert.assertThat(
            "Wrong username returned",
            new AccJson(
                Json.createObjectBuilder().add("username", "123").build()
            ).username(),
            Matchers.equalTo("123")
        );
    }

    @Test
    void throwsAtEmptyIdentity() {
        Assertions.assertThrows(
            EmptyArgumentException.class,
            () -> new AccJson(Json.createObjectBuilder().build()).identity(),
            "Exception not thrown at empty id"
        );
    }

    @Test
    void throwsAtIntegerIdentity() {
        Assertions.assertThrows(
            EmptyArgumentException.class,
            () -> new AccJson(
                Json.createObjectBuilder().add("id", 123).build()
            ).identity(),
            "Exception not thrown at integer id"
        );
    }

    @Test
    void returnsIdentity() throws EmptyArgumentException {
        MatcherAssert.assertThat(
            "Wrong id returned",
            new AccJson(
                Json.createObjectBuilder().add("id", "123").build()
            ).identity(),
            Matchers.equalTo("123")
        );
    }
}
