package com.edgar.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Properties;

/**
 * Created by Edgar on 2015/12/2.
 *
 * @author Edgar  Date 2015/12/2
 */
public class ArgOptionMain {
  public static void main(String[] args) throws ParseException {
    Options options = new Options();
    Option logfile = Option.builder("logfile").longOpt("block-size")
            .hasArg(true)
            .desc
                    ("use given file for log").argName("file").build();
  options.addOption(logfile);
    Option logger = Option.builder("logger")
            .hasArg(true)
            .desc
                    ("the class which is to perform logging").argName("classname").build();
    options.addOption(logger);

    options.addOption(new Option("help", false, "help information"));

    Option property  = Option.builder("D").hasArgs().valueSeparator('=').desc("use value for give"
                                                                              + " property")
            .argName("property=value").build();
  options.addOption(property);

    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(options, args);

    if (cmd.hasOption("help")) {
      // print the date and time
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("ant", options);
    }

    if (cmd.hasOption("D")) {
      // get c option value
      Properties countryCode = cmd.getOptionProperties("D");
      System.out.println("Properties:" + countryCode);
    }
  }
}
