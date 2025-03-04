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

package io.github.artemget.prbot.config;

import org.cactoos.Scalar;

/**
 * Null safe entry. Throws if wrapped
 * scalar return null.
 *
 * @param <T> Type
 * @since 0.0.1
 */
public final class ESafe<T> implements Entry<T> {
    /**
     * Scalar returning T.
     */
    private final Scalar<T> origin;

    /**
     * Ctor.
     *
     * @param origin Value
     */
    public ESafe(T origin) {
        this(() -> origin);
    }

    /**
     * Main ctor.
     *
     * @param origin Scalar
     */
    public ESafe(Scalar<T> origin) {
        this.origin = origin;
    }

    @Override
    public T value() throws EntryException {
        final T value;
        try {
            value = this.origin.value();
        } catch (final Exception exception) {
            throw new EntryException(exception);
        }
        if (value == null) {
            throw new EntryException("Empty entry");
        }
        return value;
    }
}
