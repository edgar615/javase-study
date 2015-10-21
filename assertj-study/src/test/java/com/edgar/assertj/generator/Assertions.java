package com.edgar.assertj.generator;

public class Assertions {

    /**
     * Creates a new instance of <code>{@link PlayerAssert}</code>.
     *
     * @param actual the actual value.
     * @return the created assertion object.
     */
    public static PlayerAssert assertThat(Player actual) {
        return new PlayerAssert(actual);
    }

    /**
     * Creates a new <code>{@link Assertions}</code>.
     */
    protected Assertions() {
        // empty
    }
}