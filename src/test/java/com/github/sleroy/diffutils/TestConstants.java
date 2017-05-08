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

import java.io.File;

/**
 * Test constants
 * 
 * @author simon.mittermueller@gmail.com
 *
 */
public final class TestConstants {

    /** File separator. */
    public static final String FS		     = File.separator;

    /** The base resource path. */
    public static String       BASE_FOLDER_RESOURCES = "src" + FS + "test" + FS + "resources";

    /** The base folder containing the test files. Ends with {@link #FS}. */
    public static final String MOCK_FOLDER	     = BASE_FOLDER_RESOURCES + FS + "mocks" + FS;

    private TestConstants() {
	// prevent construction.
    }

}
