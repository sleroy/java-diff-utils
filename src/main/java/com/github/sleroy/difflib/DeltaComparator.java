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

import java.io.Serializable;
import java.util.Comparator;

/**
 * The Class DeltaComparator builds a comparator.
 *
 * @author mksenzov
 */
public class DeltaComparator implements Comparator<Delta<?>>, Serializable {

    /** The Constant serialVersionUID. */
    private static final long		     serialVersionUID = 1L;

    /** The Constant INSTANCE. */
    public static final Comparator<Delta<?>> INSTANCE	      = new DeltaComparator();

    /**
     * Instantiates a new delta comparator.
     */
    private DeltaComparator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(final Delta<?> a, final Delta<?> b) {
	final int posA = a.getOriginal().getPosition();
	final int posB = b.getOriginal().getPosition();
	if (posA > posB) {
	    return 1;
	} else if (posA < posB) {
	    return -1;
	}
	return 0;
    }
}