package de.arthurpicht.utils.test;

import de.arthurpicht.utils.io.nio2.FileUtils;

import java.nio.file.Path;

public class CleanUp {

    public static void cleanTestPath(Object testCase, Path parentDir, String packageString) {
        Path testCasePath = TestPaths.getCaseRelatedDir(testCase, parentDir, packageString);
//        System.out.println("delete path: " + testCasePath.toAbsolutePath());
         FileUtils.rmDirSilently(testCasePath);
    }

}
