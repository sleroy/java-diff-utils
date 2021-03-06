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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.github.sleroy.difflib.DiffUtils;
import com.github.sleroy.difflib.Patch;
import com.github.sleroy.difflib.PatchFailedException;

import junit.framework.TestCase;

public class GenerateUnifiedDiffTest extends TestCase {

    public List<String> fileToLines(String filename) {
	List<String> lines = new LinkedList<String>();
	String line = "";
	BufferedReader in = null;
	try {
	    in = new BufferedReader(new FileReader(filename));
	    while ((line = in.readLine()) != null) {
		lines.add(line);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	    fail(e.getMessage());
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		    // ignore ... any errors should already have been
		    // reported via an IOException from the final flush.
		}
	    }
	}
	return lines;
    }

    public void testDiff_Issue10() {
	final List<String> baseLines = fileToLines(TestConstants.MOCK_FOLDER + "issue10_base.txt");
	final List<String> patchLines = fileToLines(TestConstants.MOCK_FOLDER + "issue10_patch.txt");
	final Patch<String> p = DiffUtils.parseUnifiedDiff(patchLines);
	try {
	    DiffUtils.patch(baseLines, p);
	} catch (PatchFailedException e) {
	    fail(e.getMessage());
	}
    }

    public void testDiff5() {
	final List<String> lines1 = fileToLines(TestConstants.MOCK_FOLDER + "5A.txt");
	final List<String> lines2 = fileToLines(TestConstants.MOCK_FOLDER + "5B.txt");
	verify(lines1, lines2, "5A.txt", "5B.txt");
    }

    /**
     * Issue 19
     */
    public void testDiffWithHeaderLineInText() {
	List<String> original = new ArrayList<String>();
	List<String> revised = new ArrayList<String>();

	original.add("test line1");
	original.add("test line2");
	original.add("test line 4");
	original.add("test line 5");

	revised.add("test line1");
	revised.add("test line2");
	revised.add("@@ -2,6 +2,7 @@");
	revised.add("test line 4");
	revised.add("test line 5");

	Patch<String> patch = DiffUtils.diff(original, revised);
	List<String> udiff = DiffUtils.generateUnifiedDiff("original", "revised", original, patch, 10);
	DiffUtils.parseUnifiedDiff(udiff);
    }

    public void testGenerateUnified() {
	List<String> origLines = fileToLines(TestConstants.MOCK_FOLDER + "original.txt");
	List<String> revLines = fileToLines(TestConstants.MOCK_FOLDER + "revised.txt");

	verify(origLines, revLines, "original.txt", "revised.txt");
    }

    public void testGenerateUnifiedDiffWithoutAnyDeltas() {
	List<String> test = Arrays.asList("abc");
	Patch<String> patch = DiffUtils.diff(test, test);
	DiffUtils.generateUnifiedDiff("abc", "abc", test, patch, 0);
    }

    public void testGenerateUnifiedWithOneDelta() {
	List<String> origLines = fileToLines(TestConstants.MOCK_FOLDER + "one_delta_test_original.txt");
	List<String> revLines = fileToLines(TestConstants.MOCK_FOLDER + "one_delta_test_revised.txt");

	verify(origLines, revLines, "one_delta_test_original.txt", "one_delta_test_revised.txt");
    }

    /**
     * Issue 12
     */
    public void testPatchWithNoDeltas() {
	final List<String> lines1 = fileToLines(TestConstants.MOCK_FOLDER + "issue11_1.txt");
	final List<String> lines2 = fileToLines(TestConstants.MOCK_FOLDER + "issue11_2.txt");
	verify(lines1, lines2, "issue11_1.txt", "issue11_2.txt");
    }

    private void verify(List<String> origLines, List<String> revLines, String originalFile, String revisedFile) {
	Patch<String> patch = DiffUtils.diff(origLines, revLines);
	List<String> unifiedDiff = DiffUtils.generateUnifiedDiff(originalFile, revisedFile, origLines, patch, 10);

	Patch<String> fromUnifiedPatch = DiffUtils.parseUnifiedDiff(unifiedDiff);
	List<String> patchedLines;
	try {
	    patchedLines = (List<String>) fromUnifiedPatch.applyTo(origLines);
	    assertTrue(revLines.size() == patchedLines.size());
	    for (int i = 0; i < revLines.size(); i++) {
		String l1 = revLines.get(i);
		String l2 = patchedLines.get(i);
		if (!l1.equals(l2)) {
		    fail("Line " + (i + 1) + " of the patched file did not match the revised original");
		}
	    }
	} catch (PatchFailedException e) {
	    fail(e.getMessage());
	}
    }
}
