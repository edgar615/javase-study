package com.edgar.assertj.guava;

import static org.assertj.guava.api.Assertions.assertThat;

import com.edgar.assertj.AbstractAssertionsExamples;
import org.junit.Test;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
/**
* {@link Table} assertions example.
*
* @author Joel Costigliola
*/
public class TableAssertionsExamples extends AbstractAssertionsExamples {
@Test
public void table_assertions_examples() {
Table<Integer, String, String> bestMovies = HashBasedTable.create();
bestMovies.put(1970, "Palme d'Or", "M.A.S.H");
bestMovies.put(1994, "Palme d'Or", "Pulp Fiction");
bestMovies.put(2008, "Palme d'Or", "Entre les murs");
bestMovies.put(2000, "Best picture Oscar", "American Beauty");
bestMovies.put(2011, "Goldene Bär", "A Separation");
assertThat(bestMovies).hasRowCount(5).hasColumnCount(3).hasSize(5)
.containsValues("American Beauty", "A Separation", "Pulp Fiction")
.containsCell(1994, "Palme d'Or", "Pulp Fiction")
.containsColumns("Palme d'Or", "Best picture Oscar", "Goldene Bär")
.containsRows(1970, 1994, 2000, 2008, 2011);
try {
assertThat(bestMovies).containsValues("toto");
} catch (AssertionError e) {
logAssertionErrorMessage("hasRowCount", e);
}
}
}