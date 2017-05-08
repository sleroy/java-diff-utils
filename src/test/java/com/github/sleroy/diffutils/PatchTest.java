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
package com.github.sleroy.diffutils;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

import com.github.sleroy.difflib.DiffUtils;
import com.github.sleroy.difflib.Patch;
import com.github.sleroy.difflib.PatchFailedException;

public class PatchTest extends TestCase {

    public void testPatch_Change() {
	final List<String> changeTest_from = Arrays.asList("aaa", "bbb", "ccc", "ddd");
	final List<String> changeTest_to = Arrays.asList("aaa", "bxb", "cxc", "ddd");

	final Patch<String> patch = DiffUtils.diff(changeTest_from, changeTest_to);
	try {
	    assertEquals(changeTest_to, DiffUtils.patch(changeTest_from, patch));
	} catch (PatchFailedException e) {
	    fail(e.getMessage());
	}
    }

    public void testPatch_Delete() {
	final List<String> deleteTest_from = Arrays.asList("ddd", "fff", "ggg", "hhh");
	final List<String> deleteTest_to = Arrays.asList("ggg");

	final Patch<String> patch = DiffUtils.diff(deleteTest_from, deleteTest_to);
	try {
	    assertEquals(deleteTest_to, DiffUtils.patch(deleteTest_from, patch));
	} catch (PatchFailedException e) {
	    fail(e.getMessage());
	}
    }

    public void testPatch_Insert() {
	final List<String> insertTest_from = Arrays.asList("hhh");
	final List<String> insertTest_to = Arrays.asList("hhh", "jjj", "kkk", "lll");

	final Patch<String> patch = DiffUtils.diff(insertTest_from, insertTest_to);
	try {
	    assertEquals(insertTest_to, DiffUtils.patch(insertTest_from, patch));
	} catch (PatchFailedException e) {
	    fail(e.getMessage());
	}
    }
}
