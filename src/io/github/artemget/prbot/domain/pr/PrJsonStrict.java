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

import io.github.artemget.prbot.config.EJsonArr;
import io.github.artemget.prbot.config.EJsonObj;
import io.github.artemget.prbot.config.EJsonStr;
import io.github.artemget.prbot.config.EntryException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Pull request from source strict json.
 *
 * @since 0.0.1
 */
public class PrJsonStrict implements PullRequest {
    /**
     * Json containing pull-request json in strict format.
     * <a href="file:../resources/PrStrict.json">PrStrict.json</a>
     */
    private final JsonObject json;

    /**
     * Ctor. Parses json string to {@link JsonObject}.
     *
     * @param json String
     * @throws EntryException If String not valid json
     */
    public PrJsonStrict(String json) throws EntryException {
        this(
            new EJsonObj(
                () -> {
                    try (JsonReader read = Json.createReader(new StringReader(json))) {
                        return read.readObject();
                    }
                }
            ).value()
        );
    }

    /**
     * Main ctor.
     *
     * @param json Object
     */
    public PrJsonStrict(JsonObject json) {
        this.json = json;
    }

    @Override
    public String identity() throws EmptyArgumentException {
        try {
            return new EJsonStr(this.json, "id").value();
        } catch (final EntryException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Failed to get strict pull request id. Source json: %s",
                    this.json.toString()
                ),
                exception
            );
        }
    }

    @Override
    public String link() throws EmptyArgumentException {
        try {
            return new EJsonStr(this.json, "link").value();
        } catch (final EntryException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Failed to get strict pull request link. Source json: %s",
                    this.json.toString()
                ),
                exception
            );
        }
    }

    @Override
    public Status status() throws EmptyArgumentException {
        final String status;
        try {
            status = new EJsonStr(this.json, "link").value();
        } catch (final EntryException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Failed to get strict pull request status. Source json: %s",
                    this.json.toString()
                ),
                exception
            );
        }
        try {
            return PullRequest.Status.valueOf(status);
        } catch (final IllegalArgumentException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Unknown strict pull-request status: %s. Source json: %s",
                    status,
                    this.json.toString()
                ),
                exception
            );
        }
    }

    @Override
    public Account from() throws EmptyArgumentException {
        try {
            return new AccJson(new EJsonObj(this.json, "from").value());
        } catch (final EntryException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Failed to get strict pull request creator account. Source json: %s",
                    this.json.toString()
                ),
                exception
            );
        }
    }

    @Override
    public List<Account> assigners() throws EmptyArgumentException {
        return this.accounts("assigners");
    }

    @Override
    public List<Account> reviewers() throws EmptyArgumentException {
        return this.accounts("reviewers");
    }

    private List<Account> accounts(String field) throws EmptyArgumentException {
        final JsonArray accounts;
        try {
            accounts = new EJsonArr(this.json, field).value();
        } catch (final EntryException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Failed to get strict pull request %s accounts. Source json: %s",
                    field,
                    this.json.toString()
                ),
                exception
            );
        }
        return accounts.stream()
            .map(account -> new AccJson(account.asJsonObject()))
            .collect(Collectors.toList());
    }

    @Override
    public Branch branchFrom() throws EmptyArgumentException {
        return this.branch("from");
    }

    @Override
    public Branch branchTo() throws EmptyArgumentException {
        return this.branch("to");
    }

    @Override
    public String toString() {
        return this.json.toString();
    }

    private Branch branch(final String field) throws EmptyArgumentException {
        try {
            return new BrJson(
                new EJsonObj(
                    new EJsonObj(this.json, "branches").value(),
                    field
                ).value()
            );
        } catch (final EntryException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Failed to get strict pull request %s branch. Source json: %s",
                    field,
                    this.json.toString()
                ),
                exception
            );
        }
    }
}
