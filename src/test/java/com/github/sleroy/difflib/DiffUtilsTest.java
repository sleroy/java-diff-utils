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

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.github.sleroy.difflib.DiffUtils;
import com.github.sleroy.difflib.Patch;

/**
 * @author sleroy
 *
 */
public class DiffUtilsTest {

    /**
     * Test method for
     * {@link com.github.sleroy.difflib.DiffUtils#diff(java.io.File, java.io.File)}.
     */
    @Test
    public void testDiffFileFile() throws Exception {

	final Patch<String> diff = DiffUtils.diff(new File("src/test/resources/mocks/5A.txt"), //$NON-NLS-1$
	        new File("src/test/resources/mocks/5B.txt")); //$NON-NLS-1$
	assertEquals(1, diff.getDeltas().size());
    }

    /**
     * Test method for
     * {@link com.github.sleroy.difflib.DiffUtils#diff(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testDiffStringString() throws Exception {

	final Patch<String> diff = DiffUtils.diff("a\nb\n", "a"); //$NON-NLS-1$ //$NON-NLS-2$
	System.out.println(diff.getDeltas());
    }

}
