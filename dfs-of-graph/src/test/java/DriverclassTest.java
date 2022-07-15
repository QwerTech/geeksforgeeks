import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.io.Files;
import com.google.common.io.Resources;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.io.output.TeeOutputStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DriverclassTest {

    private final static PrintStream originalOut = System.out;
    private final static InputStream originalIn = System.in;
    public static final String TESTCASE = "testcase.txt";
    public static final String TESTRESULT = "testresult.txt";
    public static final String FOLDER = "testcases";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        PrintStream out = new PrintStream(new TeeOutputStream(originalOut, outContent));
        System.setOut(out);
    }


    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    public static Stream<Arguments> params() {
        return Arrays.stream(getResourceDirSubDirs(FOLDER))
                .map(f -> Arrays.stream(getResourceFiles(FOLDER + "/" + f.getName()))
                        .filter(r -> r.getName().equals(TESTCASE))
                        .findFirst()
                        .orElseThrow(IllegalStateException::new))
                .map(f -> Arguments.arguments(f, getResultString(f)));
    }


    private static String getResultString(File f) {
        try {
            URL testresult = Path.of(f.getAbsolutePath().replace(TESTCASE, TESTRESULT)).toUri().toURL();
            return Resources.toString(testresult, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File[] getResourceDirSubDirs(String dir) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(dir);
        String path = Objects.requireNonNull(url).getPath();
        return new File(path).listFiles(File::isDirectory);
    }

    private static File[] getResourceFiles(String dir) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(dir);
        String path = Objects.requireNonNull(url).getPath();
        return new File(path).listFiles(File::isFile);
    }

    @ParameterizedTest
    @MethodSource("params")
    public void test(File input, String expected) throws IOException {
        System.setIn(Files.asByteSource(input).openStream());

        DriverClass.main(null);

        assertEquals(expected, outContent.toString());
    }


    @ParameterizedTest
    @MethodSource("paramsCourses")
    void testCanFinishCourses(int num, int[][] pre, boolean expected) {
        assertEquals(expected, new Solution().canFinish(num, pre));
    }

    public static Stream<Arguments> paramsCourses() {
        return Stream.of(
                Arguments.arguments(2, new int[][]{{1, 0}}, true),
                Arguments.arguments(2, new int[][]{{1, 0}, {0, 1}}, false),
                Arguments.arguments(1, new int[][]{}, true),
                Arguments.arguments(1, new int[][]{{0, 0}}, false)
        );
    }
}