/**
 * Copyright (c) 2012-2013, JCabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jcabi.github;

import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Mocker of {@link Comment}.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
public final class CommentMocker implements Comment {

    /**
     * Mocked comment.
     */
    private final transient Comment comment = Mockito.mock(Comment.class);

    /**
     * Public ctor.
     * @param number Comment number
     * @param issue Owner of it
     * @param author Author of it
     * @throws IOException If fails
     */
    public CommentMocker(final int number, final Issue issue,
        final User author) throws IOException {
        Mockito.doReturn(number).when(this.comment).number();
        Mockito.doReturn(issue).when(this.comment).issue();
        Mockito.doReturn(author).when(this.comment).author();
        Mockito.doReturn(
            Json.createObjectBuilder()
                .add("body", "some text")
                .build()
        ).when(this.comment).json();
        Mockito.doAnswer(
            new Answer<Void>() {
                @Override
                public Void answer(final InvocationOnMock inv)
                    throws IOException {
                    CommentMocker.this.patch(
                        JsonObject.class.cast(inv.getArguments()[0])
                    );
                    return null;
                }
            }
        ).when(this.comment).patch(Mockito.any(JsonObject.class));
    }

    @Override
    public Issue issue() {
        return this.comment.issue();
    }

    @Override
    public int number() {
        return this.comment.number();
    }

    @Override
    public User author() throws IOException {
        return this.comment.author();
    }

    @Override
    public void remove() {
        // nothing to do
    }

    @Override
    public JsonObject json() throws IOException {
        return this.comment.json();
    }

    @Override
    public void patch(final JsonObject json) throws IOException {
        Mockito.doReturn(new JsonMocker(this.comment.json()).patch(json))
            .when(this.comment).json();
    }

    @Override
    public int compareTo(final Comment cmt) {
        return new Integer(this.number()).compareTo(cmt.number());
    }

    /**
     * Get mocked object.
     * @return Mocked object
     */
    public Comment mock() {
        return this.comment;
    }

}
