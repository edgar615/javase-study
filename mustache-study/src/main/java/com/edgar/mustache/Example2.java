package com.edgar.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Example2 {

  static class Feature {
    Feature(String description) {
      this.description = description;
    }

    String description;
  }

  public static void main(String[] args) throws IOException {
    HashMap<String, Object> scopes = new HashMap<String, Object>();
    scopes.put("name", "Mustache");
    scopes.put("feature", new Feature("Perfect!"));

    Writer writer = new OutputStreamWriter(System.out);
    MustacheFactory mf = new DefaultMustacheFactory();
    Mustache mustache = mf.compile(new StringReader("{{name}}, {{feature.description}}!"), "example");
    mustache.execute(writer, scopes);
    writer.flush();
  }
}