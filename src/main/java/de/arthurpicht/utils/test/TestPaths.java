package de.arthurpicht.utils.test;

import de.arthurpicht.utils.io.nio2.FileUtils;

import java.nio.file.Path;

public class TestPaths {

    public static Path getCaseRelatedDir(Object testCase, Path parentDir, String packageString) {
        String testGroupId = TestIds.getTestGroupId(testCase);
        String testCaseId = TestIds.getTestCaseId(testCase);

        if (!FileUtils.isExistingDirectory(parentDir))
            throw new UtilsTestRuntimeException("Parent directory not found: [" + parentDir.toAbsolutePath() + "].");

        return parentDir
                .resolve(packageString.replace('.', '/'))
                .resolve(testGroupId)
                .resolve(testCaseId);
    }

}
