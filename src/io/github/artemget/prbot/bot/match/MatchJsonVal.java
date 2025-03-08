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

import io.github.artemget.teleroute.update.Wrap;
import java.util.function.Predicate;
import org.hamcrest.TypeSafeMatcher;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Wraps {@link MatchJson}.
 * Check update message's text is JSON
 * and matches attribute has expected value.
 *
 * @since 0.0.1
 */
public final class MatchJsonVal extends MatchEnvelope {

    /**
     * Ctor.
     *
     * @param value For body.object_attributes.action attribute
     */
    public MatchJsonVal(final String value) {
        this("action", value);
    }

    /**
     * Configures predefined match that
     * checks attribute has value
     * in body.object_attributes.* JSON string.
     *
     * @param attribute Json
     * @param value For json attribute
     * @todo #6:30 rewrite this structure to support
     *  strict json format. Check resources dir to
     *  find strict format. Probably move this logic to
     *  separate class with name MatchJsonStrict.
     */
    public MatchJsonVal(final String attribute, final String value) {
        this(
            new StringIsJson.Object(
                new JsonHas(
                    "body",
                    new JsonHas(
                        "object_attributes",
                        new JsonHas(attribute, value)
                    )
                )
            )
        );
    }

    /**
     * Ctor.
     *
     * @param match Json
     */
    public MatchJsonVal(final TypeSafeMatcher<String> match) {
        this(new MatchJson(match));
    }

    /**
     * Main ctor.
     *
     * @param origin Predicate match
     */
    private MatchJsonVal(final Predicate<Wrap<Update>> origin) {
        super(origin);
    }
}
