package base;

import com.google.common.base.CharMatcher;
import org.junit.Test;

/**
 * Created by Administrator on 2014/12/23.
 */
public class CharMatcherTest {

    @Test
    public void test() {
        System.out.println(CharMatcher.JAVA_LOWER_CASE.matchesAllOf("abCdefg"));
    }

    @Test
    public void testAnyOf() {
        CharMatcher.anyOf("aeiou").matches('a');//true
        CharMatcher.anyOf("aeiou").matches('b');//false
    }

    @Test
    public void testNegate() {
        System.out.println(CharMatcher.anyOf("aeiou").negate().matches('a'));//false
    }

    @Test
    public void testNoneOf() {
        System.out.println(CharMatcher.noneOf("aeiou").matches('a'));//false
    }

    @Test
    public void testIs() {
        System.out.println(CharMatcher.is('a').matches('a'));
        System.out.println(CharMatcher.is('a').matches('b'));
    }

    @Test
    public void testIsNot() {
        System.out.println(CharMatcher.isNot('a').matches('a'));
        System.out.println(CharMatcher.isNot('a').matches('b'));
    }

    @Test
    public void testAnd() {
        System.out.println(CharMatcher.is('a').and(CharMatcher.inRange('b', 'e')).matches('a'));
        System.out.println(CharMatcher.is('a').and(CharMatcher.inRange('a', 'e')).matches('a'));
    }

    @Test
    public void testOr() {
        System.out.println(CharMatcher.is('a').or(CharMatcher.inRange('b', 'e')).matches('a'));
        System.out.println(CharMatcher.is('a').or(CharMatcher.inRange('a', 'e')).matches('a'));
    }

    @Test
    public void testCollapseFrom() {
        System.out.println(CharMatcher.anyOf("aeiou").collapseFrom("Hello Guava", '-'));
    }

    @Test
    public void indexIn() {
        System.out.println(CharMatcher.anyOf("aeiou").indexIn("Hello Guava"));
        System.out.println(CharMatcher.anyOf("aeiou").indexIn("Hello Guava", 5));
    }

    @Test
    public void lastIndexIn() {
        System.out.println(CharMatcher.anyOf("aeiou").lastIndexIn("Hello Guava"));
    }

    @Test
    public void matches() {
        System.out.println(CharMatcher.anyOf("aeiou").matches('a'));
    }

    @Test
    public void matchesAllOf() {
        System.out.println(CharMatcher.anyOf("aeiou").matchesAllOf("aaeeoouuii"));
    }

    @Test
    public void matchesAnyOf() {
        System.out.println(CharMatcher.anyOf("aeiou").matchesAnyOf("abcd"));
    }

    @Test
    public void matchesNoneOf() {
        System.out.println(CharMatcher.anyOf("aeiou").matchesNoneOf("bcd"));
    }

    @Test
    public void removeFrom() {
        System.out.println(CharMatcher.anyOf("aeiou").removeFrom("Hello Guava"));
    }

    @Test
    public void replaceFrom() {
        System.out.println(CharMatcher.anyOf("aeiou").replaceFrom("Hello Guava", '-'));
        System.out.println(CharMatcher.anyOf("aeiou").replaceFrom("Hello Guava", ";-"));
    }

    @Test
    public void retainFrom() {
        System.out.println(CharMatcher.anyOf("aeiou").retainFrom("Hello Guava"));
    }

    @Test
    public void trimAndCollapseFrom() {
        System.out.println(CharMatcher.anyOf("aeiou").trimAndCollapseFrom("Hello Guava", '-'));
    }

    @Test
    public void trimFrom() {
        System.out.println(CharMatcher.anyOf("aeiou").trimFrom("Hello Guava"));
    }

    @Test
    public void trimLeadingFrom() {
        System.out.println(CharMatcher.anyOf("ab").trimLeadingFrom("abacatbab"));
    }

    @Test
    public void trimTrailingFrom() {
        System.out.println(CharMatcher.anyOf("ab").trimTrailingFrom("abacatbab"));
    }
}
