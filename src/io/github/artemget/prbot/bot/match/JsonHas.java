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

package io.github.artemget.prbot.bot.match;

import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matches json object fields.
 *
 * @since 0.1
 * @todo #20:15 remove {@link JsonHas},
 *  {@link io.github.artemget.prbot.bot.match.JsonValueIs}
 *  and {@link io.github.artemget.prbot.bot.match.StringIsJson} from this project when
 *  <a href="https://github.com/g4s8/matchers-json/issues/16">matchers-json jakarta issue</a>
 *  would be closed. Add matchers-json dependency and fix used imports through the project.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class JsonHas extends TypeSafeMatcher<JsonObject> {

    /**
     * Field name.
     */
    private final String field;

    /**
     * Value matcher.
     */
    private final Matcher<? extends JsonValue> matcher;

    /**
     * JSON has a string value for field.
     * @param field Name
     * @param value Expected string
     */
    public JsonHas(final String field, final String value) {
        this(field, new JsonValueIs(value));
    }

    /**
     * JSON has a number value for field.
     * @param field Name
     * @param value Expected string
     */
    public JsonHas(final String field, final Number value) {
        this(field, new JsonValueIs(value));
    }

    /**
     * JSON has a boolean value for field.
     * @param field Name
     * @param value Expected string
     */
    public JsonHas(final String field, final boolean value) {
        this(field, new JsonValueIs(value));
    }

    /**
     * Ctor.
     *
     * @param field Field name
     * @param matcher Value matcher
     */
    public JsonHas(final String field,
        final Matcher<? extends JsonValue> matcher) {
        super();
        this.field = field;
        this.matcher = matcher;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("field ")
            .appendValue(this.field)
            .appendText(" with ")
            .appendDescriptionOf(this.matcher);
    }

    @Override
    public boolean matchesSafely(final JsonObject item) {
        return this.matcher.matches(item.get(this.field));
    }

    @Override
    public void describeMismatchSafely(final JsonObject item,
        final Description desc) {
        desc.appendText("field ")
            .appendValue(this.field)
            .appendText(" ");
        this.matcher.describeMismatch(item.get(this.field), desc);
    }
}
