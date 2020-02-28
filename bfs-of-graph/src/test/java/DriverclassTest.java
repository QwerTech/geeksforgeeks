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
import java.util.Arrays;
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
  public static final String TESTCASE = "testcase";
  public static final String FOLDER = "testcases";
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    System.setIn(getClass().getResourceAsStream("/testcases/testcase1.txt"));

    PrintStream out = new PrintStream(new TeeOutputStream(originalOut, outContent));
    System.setOut(out);
  }


  @AfterAll
  public static void restoreStreams() {
    System.setOut(originalOut);
    System.setIn(originalIn);
  }

  public static Stream<Arguments> params() {
    return Arrays.stream(getResourceFolderFiles(FOLDER))
        .filter(r -> r.getName().startsWith(TESTCASE) && r.getName().endsWith(".txt"))
        .map(f -> Arguments.arguments(f, getResultString(f)));
  }


  private static String getResultString(File f) {

    URL testresult = Resources
        .getResource(FOLDER + "/" + f.getName().replace(TESTCASE, "testresult"));
    String expected = null;
    try {
      expected = Resources.toString(testresult, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return expected;
  }

  private static File[] getResourceFolderFiles(String folder) {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    URL url = loader.getResource(folder);
    String path = url.getPath();
    return new File(path).listFiles();
  }

  @ParameterizedTest
  @MethodSource("params")
  public void test(File input, String expected) throws IOException {
    System.setIn(Files.asByteSource(input).openStream());

    Driverclass.main(null);

    assertEquals(expected, outContent.toString());
  }
}