package com.edgar.assertj.guava;

import static org.assertj.guava.api.Assertions.assertThat;
import java.io.IOException;

import com.edgar.assertj.AbstractAssertionsExamples;
import org.junit.Test;
import com.google.common.io.ByteSource;
/**
* {@link ByteSource} assertions example.
*
* @author Andrew Gaul
*/
public class ByteSourceAssertionsExamples extends AbstractAssertionsExamples {
@Test
public void bytesource_assertions_examples() throws IOException {
int size = 1;
ByteSource actual = ByteSource.wrap(new byte[size]);
assertThat(actual).hasSize(size);
assertThat(actual).hasSameContentAs(ByteSource.wrap(new byte[size]));
ByteSource emptyByteSource = ByteSource.wrap(new byte[0]);
assertThat(emptyByteSource).isEmpty();
}
}