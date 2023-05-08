package de.arthurpicht.utils.test;

import de.arthurpicht.utils.io.nio2.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesCompare {

    public static class FilePair {
        private final Path testFile;
        private final Path expectedFile;

        public FilePair(Path expectedFile, Path testFile) {
            this.testFile = testFile;
            this.expectedFile = expectedFile;
        }

        public Path getTestFile() {
            return testFile;
        }

        public String getTestFileAsString() throws IOException {
            return Files.readString(this.testFile);
        }

        public Path getExpectedFile() {
            return expectedFile;
        }

        public String getExpectedFileAsString() throws IOException {
            return Files.readString(this.expectedFile);
        }

        public boolean existsExpectedFile() {
            return FileUtils.isExistingRegularFile(this.expectedFile);
        }

        public String toString() {
            return "[" + this.expectedFile.toAbsolutePath() + "][" + this.testFile.toAbsolutePath() + "]";
        }
    }

    private final Path expectedPath;
    private final Path testPath;
    private final String expectedPostfix;
    private final boolean assertExistingFile;
    private final List<FilePair> filePairList;

    public FilesCompare(Path expectedPath, Path testPath, String expectedPostfix, boolean assertExistingFile) throws IOException {
        this.expectedPath = expectedPath;
        this.testPath = testPath;
        this.expectedPostfix = expectedPostfix;
        this.assertExistingFile = assertExistingFile;
        this.filePairList = obtainFilePairs();
    }

    public List<FilePair> getFilePairList() {
        return this.filePairList;
    }

    private List<FilePair> obtainFilePairs() throws IOException {
        List<Path> testFiles = obtainTestFiles();

        System.out.println("testFiles:");
        for (Path path : testFiles) {
            System.out.println(path.toAbsolutePath());
        }

        List<Path> expectedFiles = inferExpectedFiles(testFiles);

        System.out.println("expectedFiles:");
        for (Path path : expectedFiles) {
            System.out.println(path.toAbsolutePath());
        }

        return buildFilePairs(testFiles, expectedFiles, this.assertExistingFile);
    }

    private List<Path> obtainTestFiles() throws IOException {
        List<Path> testFiles;
        try (Stream<Path> stream = Files.walk(this.testPath)) {
            testFiles = stream
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        return testFiles;
    }

    private List<Path> inferExpectedFiles(List<Path> testFiles) {
        return testFiles.stream()
                .map(this.testPath::relativize)
                .map(this.expectedPath::resolve)
                .map(Path::toString)
                .map(s -> s + this.expectedPostfix)
                .map(Paths::get)
                .collect(Collectors.toList());
    }

    private List<FilePair> buildFilePairs(List<Path> testFiles, List<Path> expectedFiles, boolean assertExpectedFile) {
        List<FilePair> filePairs = new ArrayList<>();
        for (int i = 0; i < testFiles.size(); i++) {
            Path testFile = testFiles.get(i);
            Path expectedFile = expectedFiles.get(i);
            if (assertExpectedFile && !FileUtils.isExistingRegularFile(expectedFile))
                throw new IllegalStateException("Expected file not found: [" + expectedFile.toAbsolutePath() + "].");
            FilePair filePair = new FilePair(expectedFile, testFile);
            filePairs.add(filePair);
        }
        return filePairs;
    }

}
