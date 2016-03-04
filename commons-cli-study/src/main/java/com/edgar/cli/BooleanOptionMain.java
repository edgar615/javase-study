package com.edgar.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Created by Edgar on 2015/12/2.
 *
 * @author Edgar  Date 2015/12/2
 */
public class BooleanOptionMain {
  public static void main(String[] args) throws ParseException {
//    Boolean Options
    Options options = new Options();
    Option help = new Option("help", "print this message");
    options.addOption(help);
    Option projecthelp = new Option("projecthelp", "print project help information");
    Option version = new Option("version", "print the version information and exit");
    Option quiet = new Option("quiet", "be extra quiet");
    Option verbose = new Option("verbose", "be extra verbose");
    Option debug = new Option("debug", "print debugging information");
    Option emacs = new Option("emacs",
                              "produce logging information without adornments");

    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(options, args);

    if (cmd.hasOption("help")) {
      // print the date and time
      System.out.println("help");
    }
  }
}
