package com.edgar.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Created by Edgar on 2015/12/2.
 *
 * @author Edgar  Date 2015/12/2
 */
public class Main {
  public static void main(String[] args) throws ParseException {
    // create Options object
    Options options = new Options();

// add t option
//    The addOption method has three parameters. The first parameter is a java.lang.String that
// represents the option. The second parameter is a boolean that specifies whether the option
// requires an argument or not. In the case of a boolean option (sometimes referred to as a flag)
// an argument value is not present so false is passed. The third parameter is the description of
// the option. This description will be used in the usage text of the application.
    options.addOption("t", false, "display current time");
    options.addOption("c", true, "country code");

//    Parsing the command line arguments
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(options, args);

    if (cmd.hasOption("t")) {
      // print the date and time
      System.out.println("datetime");
    } else {
      // print the date
      System.out.println("date");
    }

    if (cmd.hasOption("c")) {
      // get c option value
      String countryCode = cmd.getOptionValue("c");

      if (countryCode == null) {
        System.out.println("countryCode is null");
      } else {
        System.out.println("countryCode:" + countryCode);
      }
    }

  }
}
