/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.github.sleroy.difflib;

import java.util.*;

/**
 * Describes the delta between original and revised texts.
 * 
 * @author <a href="dm.naumenko@gmail.com">Dmitry Naumenko</a>
 * @param T
 *            The type of the compared elements in the 'lines'.
 */
public abstract class Delta<T> {

    /**
     * Specifies the type of the delta.
     *
     */
    public enum TYPE {
	/** A change in the original. */
	CHANGE,
	/** A delete from the original. */
	DELETE,
	/** An insert into the original. */
	INSERT
    }

    /** The original chunk. */
    private Chunk<T> original;

    /** The revised chunk. */
    private Chunk<T> revised;

    /**
     * Construct the delta for original and revised chunks
     * 
     * @param original
     *            Chunk describing the original text. Must not be {@code null}.
     * @param revised
     *            Chunk describing the revised text. Must not be {@code null}.
     */
    public Delta(Chunk<T> original, Chunk<T> revised) {
	if (original == null) {
	    throw new IllegalArgumentException("original must not be null");
	}
	if (revised == null) {
	    throw new IllegalArgumentException("revised must not be null");
	}
	this.original = original;
	this.revised = revised;
    }

    /**
     * Applies this delta as the patch for a given target
     * 
     * @param target
     *            the given target
     * @throws PatchFailedException
     */
    public abstract void applyTo(List<T> target) throws PatchFailedException;

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Delta<T> other = (Delta) obj;
	if (original == null) {
	    if (other.original != null)
		return false;
	} else if (!original.equals(other.original))
	    return false;
	if (revised == null) {
	    if (other.revised != null)
		return false;
	} else if (!revised.equals(other.revised))
	    return false;
	return true;
    }

    /**
     * @return The Chunk describing the original text.
     */
    public Chunk<T> getOriginal() {
	return original;
    }

    /**
     * @return The Chunk describing the revised text.
     */
    public Chunk<T> getRevised() {
	return revised;
    }

    /**
     * Returns the type of delta
     * 
     * @return the type enum
     */
    public abstract TYPE getType();

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((original == null) ? 0 : original.hashCode());
	result = prime * result + ((revised == null) ? 0 : revised.hashCode());
	return result;
    }

    /**
     * Cancel this delta for a given revised text. The action is opposite to
     * patch.
     * 
     * @param target
     *            the given revised text
     */
    public abstract void restore(List<T> target);

    /**
     * @param original
     *            The Chunk describing the original text to set.
     */
    public void setOriginal(Chunk<T> original) {
	this.original = original;
    }

    /**
     * @param revised
     *            The Chunk describing the revised text to set.
     */
    public void setRevised(Chunk<T> revised) {
	this.revised = revised;
    }

    /**
     * Verifies that this delta can be used to patch the given text.
     * 
     * @param target
     *            the text to patch.
     * @throws PatchFailedException
     *             if the patch cannot be applied.
     */
    public abstract void verify(List<T> target) throws PatchFailedException;

}
