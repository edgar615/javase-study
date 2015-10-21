package com.edgar.assertj.guava;

import com.edgar.assertj.AbstractAssertionsExamples;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;

import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.guava.api.Assertions.assertThat;
import static org.assertj.guava.api.Assertions.entry;

public class MultimapAssertionsExamples extends AbstractAssertionsExamples {
    @Test
    public void multimap_assertions_examples() {
        Multimap<String, String> nbaTeams = ArrayListMultimap.create();
        nbaTeams.putAll("Lakers", newArrayList("Kobe Bryant", "Magic Johnson", "Kareem Abdul Jabbar"));
        nbaTeams.putAll("Spurs", newArrayList("Tony Parker", "Tim Duncan", "Manu Ginobili"));
        assertThat(nbaTeams).hasSize(6);
        assertThat(nbaTeams).containsKeys("Lakers", "Spurs");
        assertThat(nbaTeams).contains(entry("Lakers", "Kobe Bryant"), entry("Spurs", "Tim Duncan"));
        assertThat(nbaTeams).containsValues("Kareem Abdul Jabbar", "Tony Parker");
        assertThat(nbaTeams).hasSameEntriesAs(nbaTeams);
        assertThat(nbaTeams).containsAllEntriesOf(nbaTeams);
        Multimap<String, String> emptyMultimap = ArrayListMultimap.create();
        assertThat(emptyMultimap).isEmpty();
    }
}