package base;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2014/12/19.
 */
public class SplitterTest {

    @Test
    public void testSplit() {
        final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
        Iterable<String> splits = Splitter.on(',').split(input);
        printWords(splits);
    }

    @Test
    public void testSplitString() {
        final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
        Iterable<String> splits = Splitter.on(",").split(input);
        printWords(splits);
    }

    @Test
    public void testSplitCharMatcher() {
        final String input = "Alpha, Bravo,,Charlie ;Delta;Echo";
        Iterable<String> splits = Splitter.on(CharMatcher.anyOf(",;")).split(input);
        printWords(splits);
    }

    @Test
    public void testSplitCharPattern() {
        final String input = "Alpha, Bravo,,Charlie ;Delta;Echo";
        Iterable<String> splits = Splitter.on(Pattern.compile(";|,")).split(input);
        printWords(splits);
    }

    @Test
    public void testSplitFixedLength() {
        final String input = "Alpha, Bravo,,Charlie ;Delta;Echo";
        Iterable<String> splits = Splitter.fixedLength(5).split(input);
        printWords(splits);
    }

    @Test
    public void testTrimResult() {
        final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
        Iterable<String> splits = Splitter.on(",").trimResults().split(input);
        printWords(splits);
    }

    @Test
    public void testTrimResultCharMatcher() {
        final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
        Iterable<String> splits = Splitter.on(",").trimResults(CharMatcher.anyOf("ABCabc")).split(input);
        printWords(splits);
    }

    @Test
    public void testOmitEmpty() {
        final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
        Iterable<String> splits = Splitter.on(",").omitEmptyStrings().split(input);
        printWords(splits);
    }

    @Test
    public void testLimit() {
        final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
        Iterable<String> splits = Splitter.on(",").omitEmptyStrings().limit(3).split(input);
        printWords(splits);
    }

    @Test
    public void testSplitToList() {
        final String input = "Alpha, Bravo,,Charlie ,Delta,Echo";
        List<String> splits = Splitter.on(',').omitEmptyStrings().splitToList(input);
        System.out.println(splits);
    }

    @Test
    public void testSplittoMap() {
        final String input = "A=>B , C=>D";
        Map<String, String> splits = Splitter.on(',').trimResults().withKeyValueSeparator("=>").split(input);
        System.out.println(splits);
    }

    private static void printWords(Iterable<String> words) {
        for (String word : words) {
            System.out.printf("[%s]\n", word);
        }
    }
}
