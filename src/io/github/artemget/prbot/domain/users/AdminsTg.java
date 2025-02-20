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

import org.jdbi.v3.core.Handle;

/**
 * Telegram admin.
 *
 * @since 0.0.1
 */
public final class AdminsTg implements Admins<Long> {
    /**
     * DB source.
     */
    private final Handle src;

    /**
     * Application's users.
     */
    private final Users<Long, User> users;

    /**
     * Ctor.
     *
     * @param src DB
     */
    public AdminsTg(final Handle src) {
        this(
            src,
            new UsersTg(src)
        );
    }

    /**
     * Main Ctor.
     *
     * @param src DB
     * @param users From telegram
     */
    public AdminsTg(final Handle src, final Users<Long, User> users) {
        this.src = src;
        this.users = users;
    }

    @Override
    public boolean exists() {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public Admin user() {
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public Users<Long, Admin> add(final Long id, final String name) {
        this.src.close();
        throw new UnsupportedOperationException("unimplemented");
    }

    @Override
    public Admin user(final Long id) {
        return new AdminTg(this.users.user(id));
    }

    @Override
    public boolean exists(final Long id) {
        return this.users.exists(id);
    }
}
