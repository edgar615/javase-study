package com.edgar.assertj.generator;

import org.assertj.core.api.AbstractAssert;

public class PlayerAssert extends AbstractAssert<PlayerAssert, Player> {

  // javadoc and implementation omitted for brevity !

//  public PlayerAssert hasAge(int age) { ... }
//
//  public PlayerAssert hasHeight(double height) { ... }
//
//  public PlayerAssert hasHeightCloseTo(double height, double offset) { ... }
//
//  public PlayerAssert hasTeamMates(String... teamMates) { ... }
//
//  public PlayerAssert hasOnlyTeamMates(String... teamMates) { ... }
//
//  public PlayerAssert doesNotHaveTeamMates(String... teamMates) { ... }
//
//  public PlayerAssert hasNoTeamMates() { ... }
//
//  public PlayerAssert isRetired() { ... }
//
//  public PlayerAssert isNotRetired() { ... }
//
//  public PlayerAssert hasName(String name) { ... }

  public PlayerAssert(Player actual) {
    super(actual, PlayerAssert.class);
  }

  public static PlayerAssert assertThat(Player actual) {
    return new PlayerAssert(actual);
  }

}