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

package io.github.artemget.prbot.domain.users;

import io.github.artemget.prbot.domain.action.Feature;
import java.util.UUID;
import org.jdbi.v3.core.Handle;

/**
 * Application's user registered in telegram.
 *
 * @since 0.0.1
 */
public final class UserTg implements User {
    /**
     * DB source.
     */
    private final Handle src;

    /**
     * Identity.
     */
    private final Long id;

    /**
     * Main Ctor.
     *
     * @param src DB source
     * @param id Identity
     */
    public UserTg(final Handle src, final Long id) {
        this.src = src;
        this.id = id;
    }

    @Override
    public UUID identity() throws UserNotFoundException {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public String named() {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public void request(final Admin admin, final Feature feature) {
        admin.allows(this, feature);
        this.src.close();
        this.id.hashCode();
    }
}
