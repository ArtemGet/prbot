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

import java.util.List;

/**
 * Git pull request.
 *
 * @since 0.0.1
 */
public interface PullRequest {

    /**
     * Pull request id.
     *
     * @return Id
     * @throws EmptyArgumentException If fails
     */
    String identity() throws EmptyArgumentException;

    /**
     * Url link to pull request.
     *
     * @return Url
     * @throws EmptyArgumentException If fails
     */
    String link() throws EmptyArgumentException;

    /**
     * Pull request status.
     *
     * @return Status
     * @throws EmptyArgumentException If fails
     */
    Status status() throws EmptyArgumentException;

    /**
     * Pull request creator.
     *
     * @return Creator account
     * @throws EmptyArgumentException If fails
     */
    Account from() throws EmptyArgumentException;

    /**
     * Users assigned to pull request.
     *
     * @return Assigners accounts
     * @throws EmptyArgumentException If fails
     */
    List<Account> assigners() throws EmptyArgumentException;

    /**
     * Users assigned to review pull request.
     *
     * @return Reviewers accounts
     * @throws EmptyArgumentException If fails
     */
    List<Account> reviewers() throws EmptyArgumentException;

    /**
     * Source branch.
     *
     * @return Source branch
     * @throws EmptyArgumentException If fails
     */
    Branch branchFrom() throws EmptyArgumentException;

    /**
     * Target branch.
     *
     * @return Target branch
     * @throws EmptyArgumentException If fails
     */
    Branch branchTo() throws EmptyArgumentException;

    enum Status {
        OPENED,
        MERGED,
        CLOSED;
    }
}
