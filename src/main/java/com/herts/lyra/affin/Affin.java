package com.herts.lyra.affin;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

public class Affin {
	// Command line options that can be provided when launching the app
	public static Option sourceOption = new Option("s", "source", true, "Please provide source file path");
	public static Option resultOption = new Option("r", "result", true,
			"Please provide result file path. Existing result files will be over-written!");
	public static Option helpOption = new Option("h", "help", false, "Help");

	public static void main(String[] args) throws ParseException {

		Scanner scan = new Scanner(System.in);
		Options opt = new Options();

		opt.addOption(sourceOption);
		opt.addOption(resultOption);
		opt.addOption(helpOption);

		CommandLineParser parser = new DefaultParser();
		if (args.length != 0) {
			try {
				CommandLine cl = parser.parse(opt, args);// parse the command line input
				
				if(cl.hasOption("s")&&cl.hasOption("r"))// Validate file paths when both source and result parameters are provided
				{
					if(!isPathValid(cl.getOptionValue("s").toString())) 
					{
						System.out.println("The source file could not be found in the path provided!");
						System.exit(0);
					}
					if(!isPathValid(cl.getOptionValue("r").toString())) 
					{
						System.out.println("The result file location could not be found in the path provided!");
						System.exit(0);
					}
				}

				if (cl.hasOption("s")) {// if command line option "-s" or "source" has been provided
					if (!isPathValid(cl.getOptionValue("s").toString())) // Validate file path if only s parameter has been provided
					{
						System.out.println("The source file could not be found in the path provided!");
						System.exit(0);
					}
					System.out.println("Warning! Any existing result files will be overwritten!");
					System.out.println("Please confirm if you wish to proceed! Press Y for yes or Press N for No");
					String userInput = scan.next();
					if (userInput.equals("Y") || userInput.equals("y")) {// If user answered Y to proceed 
						ScroeCalculator sc = new ScroeCalculator(cl.getOptionValue("s").toString());
						sc.calculateReviewScores();
						System.out.println("Processing complete!");
						if (!cl.hasOption("r")) {// if -r or result option has not been provided, save the file in the app folder as myAffinRsults.tsv
							sc.generateOutputFile("");
							System.out.println("Results saved in myAffinResults.tsv");
						} else if (cl.hasOption("r")) {		// if -r or result option has been provided, parse the file path									
							sc.generateOutputFile(cl.getOptionValue("r").toString() + "\\");
							System.out.println("Result file saved to " + cl.getOptionValue("r").toString());
						}
					} else if (userInput.equals("N") || userInput.equals("n")) {//Exit program is user does not wish to proceed
						System.exit(0);
					} else if (!userInput.equals("Y") || !userInput.equals("y") || !userInput.equals("N")
							|| !userInput.equals("n")) {//Exit program if user typed anything other than "Y","y","N","n"
						System.exit(0);
					}

				} else if (cl.hasOption("h")) { // if h option has been provided, display help
					String header = "Sentiment analysis app using AFFIN algorithm";
					String fotter = "----------------------------------------------------------";
					HelpFormatter formatter = new HelpFormatter();
					formatter.printHelp("AFFIN", header, opt, fotter, true);
				}

			} catch (UnrecognizedOptionException e) {
				System.out.println("Please re-check the command line parameters provided");
				// e.printStackTrace();
			} catch (ParseException e) {
				System.out.println("An error has occured, please try again.");
			}
		} else {
			System.out.println("Please enter a valid parameter or -h for help!");
		}
	}
/**
 * Method require one file path as a String argument 
 * returns true or false depending on whether the file path exists or not.
 * @param filePath - String representing a file path 
 * @return - boolean, true if path exists false if the path does not exist
 */
	public static boolean isPathValid(String filePath) {
		File file = new File(filePath);
		try {
			if (file.isFile() | file.isDirectory()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

}
