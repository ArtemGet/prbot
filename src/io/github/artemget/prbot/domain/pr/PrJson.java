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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class PrJson implements PullRequest {
    private final JsonObject json;

    public PrJson(String json) {
        this(PrJson.parsed(json));
    }

    public PrJson(JsonObject json) {
        this.json = json;
    }

    @Override
    public String identity() throws EmptyArgumentException {
        if (!this.json.containsKey("id")) {
            throw new EmptyArgumentException(
                String.format("Empty pull-request id. Request: %s", this.json)
            );
        }
        try {
            return this.json.getString("id");
        } catch (final ClassCastException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Wrong pull-request id format: %s. Request: %s",
                    this.json.get("id").getValueType(),
                    this.json
                ),
                exception
            );
        }
    }

    @Override
    public Status status() throws EmptyArgumentException {
        if (!this.json.containsKey("status")) {
            throw new EmptyArgumentException(
                String.format("Empty pull-request status. Request: %s", this.json)
            );
        }
        final String status;
        try {
            status = this.json.getString("status");
        } catch (final ClassCastException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Wrong pull-request status format: %s. Request: %s",
                    this.json.get("status").getValueType(),
                    this.json
                ),
                exception
            );
        }
        try {
            return PullRequest.Status.valueOf(status);
        } catch (final IllegalArgumentException exception) {
            throw new EmptyArgumentException(
                String.format("Unknown pull-request status: %s. Request: %s", status, this.json),
                exception
            );
        }
    }

    @Override
    public Account from() throws EmptyArgumentException {
        if (!this.json.containsKey("from")) {
            throw new EmptyArgumentException(String.format("Empty pull-request creator. Request: %s", this.json));
        }
        try {
            return new AccJson(this.json.getJsonObject("from"));
        } catch (final ClassCastException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Wrong pull-request creator format: %s. Request: %s",
                    this.json.get("from").getValueType(),
                    this.json
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
        if (!this.json.containsKey(field)) {
            throw new EmptyArgumentException(
                String.format("Empty pull-request %s. Request: %s", field, this.json)
            );
        }
        final JsonArray values;
        try {
            values = this.json.getJsonArray(field);
        } catch (final ClassCastException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Wrong array format for pull-request %s: %s. Request: %s",
                    field,
                    this.json.get(field).getValueType(),
                    this.json
                ),
                exception
            );
        }
        final List<Account> accounts = new ArrayList<>();
        for (final JsonValue assignee : values) {
            try {
                accounts.add(new AccJson(assignee.asJsonObject()));
            } catch (final ClassCastException exception) {
                throw new EmptyArgumentException(
                    String.format(
                        "Wrong pull-request %s format: %s. Request: %s",
                        field,
                        assignee.getValueType(),
                        this.json
                    ),
                    exception
                );
            }
        }
        return accounts;
    }

    @Override
    public Branch branchFrom() {
        return this.branch("");
    }

    @Override
    public Branch branchTo() {
        return null;
    }

    @Override
    public String toString() {
        return this.json.toString();
    }

    private List<Branch> branch(String field) throws EmptyArgumentException {
        final List<Branch> branches = new ArrayList<>();
        for (final JsonValue branch : this.opened(field)) {
            try {
                branches.add(new BrJson(branch.asJsonObject()));
            } catch (final ClassCastException exception) {
                throw new EmptyArgumentException(
                    String.format(
                        "Wrong pull-request %s format: %s. Request: %s",
                        field,
                        branch.getValueType(),
                        this.json
                    ),
                    exception
                );
            }
        }
        return branches;
    }

    private JsonArray opened(String field) throws EmptyArgumentException {
        if (!this.json.containsKey(field)) {
            throw new EmptyArgumentException(
                String.format("Empty pull-request %s. Request: %s", field, this.json)
            );
        }
        try {
            return this.json.getJsonArray(field);
        } catch (final ClassCastException exception) {
            throw new EmptyArgumentException(
                String.format(
                    "Wrong array format for pull-request %s: %s. Request: %s",
                    field,
                    this.json.get(field).getValueType(),
                    this.json
                ),
                exception
            );
        }
    }

    private static JsonObject parsed(String json) {
        try (JsonReader read = Json.createReader(new StringReader(json))) {
            return read.readObject();
        }
    }
}
