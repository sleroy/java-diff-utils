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
package com.github.sleroy.diffutils.examples;

import java.util.List;

import com.github.sleroy.difflib.DiffUtils;
import com.github.sleroy.difflib.Patch;
import com.github.sleroy.difflib.PatchFailedException;
import com.github.sleroy.diffutils.TestConstants;

public class ApplyPatch extends Example {

    static final String	ORIGINAL = TestConstants.MOCK_FOLDER + "issue10_base.txt";
    static final String	PATCH	 = TestConstants.MOCK_FOLDER + "issue10_patch.txt";

    public static void main(String[] args) throws PatchFailedException {
	List<String> original = fileToLines(ORIGINAL);
	List<String> patched = fileToLines(PATCH);

	// At first, parse the unified diff file and get the patch
	Patch<String> patch = DiffUtils.parseUnifiedDiff(patched);

	// Then apply the computed patch to the given text
	List<String> result = DiffUtils.patch(original, patch);
	System.out.println(result);
	// / Or we can call patch.applyTo(original). There is no difference.
    }
}
