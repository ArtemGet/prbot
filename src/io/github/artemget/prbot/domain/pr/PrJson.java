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

import io.github.artemget.prbot.domain.users.User;
import io.github.artemget.prbot.domain.users.UserJson;
import java.io.StringReader;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class PrJson implements PullRequest {
    private final Supplier<JsonObject> json;

    public PrJson(String json) {
        this(
            () -> {
                try (JsonReader read = Json.createReader(new StringReader(json))) {
                    return read.readObject();
                }
            }
        );
    }

    public PrJson(Supplier<JsonObject> json) {
        this.json = json;
    }

    @Override
    public Status status() {
        return switch (
            this.payload()
                .getJsonObject("object_attributes")
                .getString("state")
            ) {
            case "opened" -> Status.OPENED;
            case "closed" -> Status.CLOSED;
            case "merged" -> Status.MERGED;
            default -> throw new RuntimeException();
        };
    }

    @Override
    public Account from() {
        return new UserJson(this.payload().getJsonObject("user"));
    }

    @Override
    public List<Account> assigners() {
        return this.payload().getJsonArray("assignees")
            .stream()
            .map(val -> new AccJson(val.asJsonObject()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Account> reviewers() {
        return this.payload().getJsonArray("reviewers")
            .stream()
            .map(val -> new AccJson(val.asJsonObject()))
            .collect(Collectors.toList());
    }

    @Override
    public Branch branchFrom() {
        return null;
    }

    @Override
    public Branch branchTo() {
        return null;
    }

    private JsonObject payload() {
        return this.json.get().getJsonObject("payload");
    }
}
