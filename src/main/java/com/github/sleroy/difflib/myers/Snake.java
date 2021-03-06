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

// TODO: Auto-generated Javadoc
/**
 * Represents a snake in a diffpath.
 * <p>
 * 
 * {@link DiffNode DiffNodes} and {@link Snake Snakes} allow for compression of
 * diffpaths, as each snake is represented by a single {@link Snake Snake} node
 * and each contiguous series of insertions and deletions is represented by a
 * single {@link DiffNode DiffNodes}.
 *
 * @author <a href="mailto:juanco@suigeneris.org">Juanco Anez</a>
 * @version $Revision: 69 $ $Date: 2003-10-13 11:00:44 +0300 (Пнд, 13 Окт 2003)
 *          $
 */
public final class Snake extends PathNode {
    
    /**
     * Constructs a snake node.
     *
     * @param i the i
     * @param j the j
     * @param prev            the previous node in the path.
     */
    public Snake(int i, int j, PathNode prev) {
	super(i, j, prev);
    }

    /* (non-Javadoc)
     * @see com.github.sleroy.difflib.myers.PathNode#isSnake()
     */
    public boolean isSnake() {
	return true;
    }

}