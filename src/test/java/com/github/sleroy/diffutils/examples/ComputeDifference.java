package com.github.sleroy.diffutils.examples;

import java.util.List;

import com.github.sleroy.difflib.Delta;
import com.github.sleroy.difflib.DiffUtils;
import com.github.sleroy.difflib.Patch;
import com.github.sleroy.diffutils.TestConstants;

public class ComputeDifference extends Example {
    
     
    static final String ORIGINAL = TestConstants.MOCK_FOLDER + "original.txt";
    static final String RIVISED = TestConstants.MOCK_FOLDER + "revised.txt";

    public static void main(String[] args) {
        List<String> original = fileToLines(ORIGINAL);
        List<String> revised  = fileToLines(RIVISED);

        // Compute diff. Get the Patch object. Patch is the container for computed deltas.
        Patch<String> patch = DiffUtils.diff(original, revised);

        for (Delta<String> delta: patch.getDeltas()) {
            System.out.println(delta);
        }
    }
}
