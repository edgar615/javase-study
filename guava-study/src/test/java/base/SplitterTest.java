package base;

import com.google.common.base.Splitter;
import org.junit.Test;

/**
 * Created by Administrator on 2014/12/19.
 */
public class SplitterTest {

    @Test
    public void testSplit() {
        final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
        Iterable<String> splits = Splitter.on(",").split(input);
        printWords(splits);
    }

    @Test
    public void testTrimResult() {
        final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
        Iterable<String> splits = Splitter.on(",").trimResults().split(input);
        printWords(splits);
    }

    @Test
    public void testOmitEmpty() {
        final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
        Iterable<String> splits = Splitter.on(",").omitEmptyStrings().split(input);
        printWords(splits);
    }

    private static void printWords(Iterable<String> words) {
        for (String word : words) {
            System.out.printf("[%s]\n", word);
        }
    }
}
