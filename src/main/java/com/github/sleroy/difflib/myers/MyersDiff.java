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

package com.github.sleroy.difflib.myers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.sleroy.difflib.*;

// TODO: Auto-generated Javadoc
/**
 * A clean-room implementation of
 * <a href="http://www.cs.arizona.edu/people/gene/"> Eugene Myers</a>
 * differencing algorithm.
 * 
 * <p>
 * See the paper at
 * <a href="http://www.cs.arizona.edu/people/gene/PAPERS/diff.ps">
 * http://www.cs.arizona.edu/people/gene/PAPERS/diff.ps</a>
 * </p>
 *
 * @author <a href="mailto:juanco@suigeneris.org">Juanco Anez</a>
 * @param <T> the generic type
 */
public class MyersDiff<T> implements DiffAlgorithm<T> {

    /**
     * Copied here from JDK 1.6
     *
     * @param <T> the generic type
     * @param original the original
     * @param from the from
     * @param to the to
     * @return the t[]
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] copyOfRange2(T[] original, int from, int to) {
	return copyOfRange2(original, from, to, (Class<T[]>) original.getClass());
    }

    /**
     * Copied here from JDK 1.6
     *
     * @param <T> the generic type
     * @param <U> the generic type
     * @param original the original
     * @param from the from
     * @param to the to
     * @param newType the new type
     * @return the t[]
     */
    @SuppressWarnings("unchecked")
    public static <T, U> T[] copyOfRange2(U[] original, int from, int to, Class<? extends T[]> newType) {
	int newLength = to - from;
	if (newLength < 0)
	    throw new IllegalArgumentException(from + " > " + to);
	T[] copy = ((Object) newType == (Object) Object[].class) ? (T[]) new Object[newLength]
	        : (T[]) Array.newInstance(newType.getComponentType(), newLength);
	System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
	return copy;
    }

    /** Default equalizer. */
    private final Equalizer<T> DEFAULT_EQUALIZER = new Equalizer<T>() {
						     public boolean equals(final T original, final T revised) {
							 return original.equals(revised);
						     }
						 };

    /** The equalizer. */
    private final Equalizer<T> equalizer;

    /**
     * Constructs an instance of the Myers differencing algorithm.
     */
    public MyersDiff() {
	equalizer = DEFAULT_EQUALIZER;
    }

    /**
     * Constructs an instance of the Myers differencing algorithm.
     * 
     * @param equalizer
     *            Must not be {@code null}.
     */
    public MyersDiff(final Equalizer<T> equalizer) {
	if (equalizer == null) {
	    throw new IllegalArgumentException("equalizer must not be null");
	}
	this.equalizer = equalizer;
    }

    /**
     * Computes the minimum diffpath that expresses de differences between the
     * original and revised sequences, according to Gene Myers differencing
     * algorithm.
     *
     * @param orig
     *            The original sequence.
     * @param rev
     *            The revised sequence.
     * @return A minimum {@link PathNode Path} accross the differences graph.
     * @throws DifferentiationFailedException
     *             if a diff path could not be found.
     */
    public PathNode buildPath(final List<T> orig, final List<T> rev) throws DifferentiationFailedException {
	if (orig == null)
	    throw new IllegalArgumentException("original sequence is null");
	if (rev == null)
	    throw new IllegalArgumentException("revised sequence is null");

	// these are local constants
	final int N = orig.size();
	final int M = rev.size();

	final int MAX = N + M + 1;
	final int size = 1 + 2 * MAX;
	final int middle = size / 2;
	final PathNode diagonal[] = new PathNode[size];

	diagonal[middle + 1] = new Snake(0, -1, null);
	for (int d = 0; d < MAX; d++) {
	    for (int k = -d; k <= d; k += 2) {
		final int kmiddle = middle + k;
		final int kplus = kmiddle + 1;
		final int kminus = kmiddle - 1;
		PathNode prev = null;

		int i;
		if ((k == -d) || (k != d && diagonal[kminus].i < diagonal[kplus].i)) {
		    i = diagonal[kplus].i;
		    prev = diagonal[kplus];
		} else {
		    i = diagonal[kminus].i + 1;
		    prev = diagonal[kminus];
		}

		diagonal[kminus] = null; // no longer used

		int j = i - k;

		PathNode node = new DiffNode(i, j, prev);

		// orig and rev are zero-based
		// but the algorithm is one-based
		// that's why there's no +1 when indexing the sequences
		while (i < N && j < M && equals(orig.get(i), rev.get(j))) {
		    i++;
		    j++;
		}
		if (i > node.i)
		    node = new Snake(i, j, node);

		diagonal[kmiddle] = node;

		if (i >= N && j >= M) {
		    return diagonal[kmiddle];
		}
	    }
	    diagonal[middle + d - 1] = null;

	}
	// According to Myers, this cannot happen
	throw new DifferentiationFailedException("could not find a diff path");
    }

    /**
     * Constructs a {@link Patch} from a difference path.
     *
     * @param path            The path.
     * @param orig            The original sequence.
     * @param rev            The revised sequence.
     * @return A {@link Patch} script corresponding to the path.
     */
    public Patch<T> buildRevision(PathNode path, List<T> orig, List<T> rev) {
	if (path == null)
	    throw new IllegalArgumentException("path is null");
	if (orig == null)
	    throw new IllegalArgumentException("original sequence is null");
	if (rev == null)
	    throw new IllegalArgumentException("revised sequence is null");

	Patch<T> patch = new Patch<T>();
	if (path.isSnake())
	    path = path.prev;
	while (path != null && path.prev != null && path.prev.j >= 0) {
	    if (path.isSnake())
		throw new IllegalStateException("bad diffpath: found snake when looking for diff");
	    int i = path.i;
	    int j = path.j;

	    path = path.prev;
	    int ianchor = path.i;
	    int janchor = path.j;

	    Chunk<T> original = new Chunk<T>(ianchor, copyOfRange(orig, ianchor, i));
	    Chunk<T> revised = new Chunk<T>(janchor, copyOfRange(rev, janchor, j));
	    Delta<T> delta = null;
	    if (original.size() == 0 && revised.size() != 0) {
		delta = new InsertDelta<T>(original, revised);
	    } else if (original.size() > 0 && revised.size() == 0) {
		delta = new DeleteDelta<T>(original, revised);
	    } else {
		delta = new ChangeDelta<T>(original, revised);
	    }

	    patch.addDelta(delta);
	    if (path.isSnake())
		path = path.prev;
	}
	return patch;
    }

    /**
     * Creates a new list containing the elements returned by
     * {@link List#subList(int, int)}.
     * 
     * @param original
     *            The original sequence. Must not be {@code null}.
     * @param fromIndex
     *            low endpoint (inclusive) of the subList.
     * @param to
     *            high endpoint (exclusive) of the subList.
     * @return A new list of the specified range within the original list.
     * 
     */
    private List<T> copyOfRange(final List<T> original, final int fromIndex, final int to) {
	return new ArrayList<T>(original.subList(fromIndex, to));
    }

    /**
     * {@inheritDoc}
     *
     * Return empty diff if get the error while procession the difference.
     */
    public Patch<T> diff(final List<T> original, final List<T> revised) {
	if (original == null) {
	    throw new IllegalArgumentException("original list must not be null");
	}
	if (revised == null) {
	    throw new IllegalArgumentException("revised list must not be null");
	}
	PathNode path;
	try {
	    path = buildPath(original, revised);
	    return buildRevision(path, original, revised);
	} catch (DifferentiationFailedException e) {
	    e.printStackTrace();
	}
	return new Patch<T>();
    }

    /**
     * {@inheritDoc}
     *
     * @return Returns an empty diff if get the error while procession the
     *         difference.
     */
    public Patch<T> diff(final T[] original, final T[] revised) {
	return diff(Arrays.asList(original), Arrays.asList(revised));
    }

    /**
     * Equals.
     *
     * @param orig the orig
     * @param rev the rev
     * @return true, if successful
     */
    private boolean equals(T orig, T rev) {
	return equalizer.equals(orig, rev);
    }

}
