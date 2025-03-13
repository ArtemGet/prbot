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

import javax.json.Json;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test cases {@link BrJson}.
 *
 * @since 0.0.1
 */
class BrJsonTest {
    @Test
    void throwsAtEmptyName() {
        Assertions.assertThrows(
            EmptyArgumentException.class,
            () -> new BrJson(Json.createObjectBuilder().build()).name(),
            "Exception not thrown at empty branch name"
        );
    }

    @Test
    void throwsAtIntName() {
        Assertions.assertThrows(
            EmptyArgumentException.class,
            () -> new BrJson(
                Json.createObjectBuilder().add("name", 123).build()
            ).name(),
            "Exception not thrown at integer branch name"
        );
    }

    @Test
    void returnsName() throws EmptyArgumentException {
        MatcherAssert.assertThat(
            "Wrong branch name returned",
            new BrJson(
                Json.createObjectBuilder().add("name", "123").build()
            ).name(),
            Matchers.equalTo("123")
        );
    }

    @Test
    void throwsAtEmptyLink() {
        Assertions.assertThrows(
            EmptyArgumentException.class,
            () -> new BrJson(Json.createObjectBuilder().build()).link(),
            "Exception not thrown at empty link"
        );
    }

    @Test
    void throwsAtIntegerLink() {
        Assertions.assertThrows(
            EmptyArgumentException.class,
            () -> new BrJson(
                Json.createObjectBuilder().add("link", 123).build()
            ).link(),
            "Exception not thrown at integer link"
        );
    }

    @Test
    void returnsLink() throws EmptyArgumentException {
        MatcherAssert.assertThat(
            "Wrong branch link returned",
            new BrJson(
                Json.createObjectBuilder().add("link", "http://ex.com").build()
            ).link(),
            Matchers.equalTo("http://ex.com")
        );
    }
}
