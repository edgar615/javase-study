package com.edgar.assertj.generator;

import java.util.List;

public class Player {
  // no getter needed to generate assertion for public fields
  // private fields getters and setters omitted for brevity

  public String name; // Object assertion generated
  private int age; // whole number assertion generated
  private double height; // real number assertion generated
  private boolean retired; // boolean assertion generated
  private List<String> teamMates;  // Iterable assertion generated
}