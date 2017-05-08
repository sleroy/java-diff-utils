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

import java.util.Arrays;
import java.util.List;

import com.github.sleroy.difflib.DiffRow;
import com.github.sleroy.difflib.DiffRowGenerator;

import junit.framework.TestCase;

public class DiffRowGeneratorTest extends TestCase {

    private void print(List<DiffRow> diffRows) {
	for (DiffRow row : diffRows) {
	    System.out.println(row);
	}
    }

    private List<String> split(String content) {
	return Arrays.asList(content.split("\n"));
    }

    public void testGenerator_Default() {
	String first = "anything \n \nother";
	String second = "anything\n\nother";

	DiffRowGenerator generator = new DiffRowGenerator.Builder().columnWidth(Integer.MAX_VALUE) // do
	                                                                                           // not
	                                                                                           // wrap
	        .build();
	List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
	print(rows);

	assertEquals(3, rows.size());
    }

    public void testGenerator_IgnoreWhitespaces() {
	String first = "anything \n \nother\nmore lines";
	String second = "anything\n\nother\nsome more lines";

	DiffRowGenerator generator = new DiffRowGenerator.Builder().ignoreWhiteSpaces(true)
	        .columnWidth(Integer.MAX_VALUE) // do not wrap
	        .build();
	List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
	print(rows);

	assertEquals(4, rows.size());
	assertEquals(rows.get(0).getTag(), DiffRow.Tag.EQUAL);
	assertEquals(rows.get(1).getTag(), DiffRow.Tag.EQUAL);
	assertEquals(rows.get(2).getTag(), DiffRow.Tag.EQUAL);
	assertEquals(rows.get(3).getTag(), DiffRow.Tag.CHANGE);
    }

    public void testGenerator_InlineDiff() {
	String first = "anything \n \nother";
	String second = "anything\n\nother";

	DiffRowGenerator generator = new DiffRowGenerator.Builder().showInlineDiffs(true).columnWidth(Integer.MAX_VALUE) // do
	                                                                                                                 // not
	                                                                                                                 // wrap
	        .build();
	List<DiffRow> rows = generator.generateDiffRows(split(first), split(second));
	print(rows);

	assertEquals(3, rows.size());
	assertTrue(rows.get(0).getOldLine().indexOf("<span") > 0);
    }
}
