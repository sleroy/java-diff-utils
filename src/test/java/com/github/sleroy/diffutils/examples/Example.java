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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public abstract class Example {

    /** File separator. */
    protected static final String FS	    = File.separator;
    /** The base resource path. */
    protected static String	  BASE_PATH = "src" + FS + "test" + FS + "resources";

    /**
     * Tries to read the file and split it into a list of lines.
     * 
     * @param filename
     *            The filename as path.
     * @return A list of lines.
     */
    public static List<String> fileToLines(String filename) {
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
	} finally {
	    if (in != null) {
		IOUtils.closeQuietly(in);

	    }
	}
	return lines;
    }
}
