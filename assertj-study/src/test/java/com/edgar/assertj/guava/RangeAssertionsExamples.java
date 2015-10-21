package com.edgar.assertj.guava;

import static org.assertj.guava.api.Assertions.assertThat;

import com.edgar.assertj.AbstractAssertionsExamples;
import org.junit.Test;
import com.google.common.collect.Range;

/**
 * {@link Range} assertions example.
 *
 * @author Joel Costigliola
 */
public class RangeAssertionsExamples extends AbstractAssertionsExamples {
    @Test
    public void range_assertions_examples() {
        Range<Integer> range = Range.closed(10, 12);
        assertThat(range).isNotEmpty()
                .contains(10, 11, 12)
                .hasClosedLowerBound()
                .hasLowerEndpointEqualTo(10);
    }
}