package edu.rit.se;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.rit.se.Swatch.Model;

/**
 * <p>
 * The JPalette is a command line tool showcasing the use of the {@link Swatch}
 * </p>
 * 
 * @author Ryan Hoffmann (rch4495)
 */
public class JPalette
{
	public static void main(String[] args) throws IOException
	{
		if (args.length != 1) {
			System.out.println("Usage: java JPalette <name>");
			return;
		}

		// The name is the first argument
		String name = args[0];
		final Swatch sw = SwatchFactory.byName(name);

		System.out.println("Welcome to JPalette!");
		System.out.println("The current swatch is named: " + name);
		System.out.println("Commands:\n");
		System.out.println("\ta\tAdd color");
		System.out.println("\tr\tRemove color");
		System.out.println("\ts\tSave swatch");
		System.out.println("\tp\tPrint out swatch");
		System.out.println("\trgb\tPrint out swatch in RGB");
		System.out.println("\tcymk\tPrint out swatch in CYMK");
		System.out.println("\thex\tPrint out swatch in HEX");
		System.out.println();
		System.out.println("Ex: a (r:255,g:255,b:255)");
		System.out.println("Ex: a (c:.50,y:.50,m:.50,k:.50)");
		System.out.println("Ex: a 0xFEFEFE");
		System.out.println();

		BufferedReader r;
		r = new BufferedReader(new InputStreamReader(System.in));

      int a = 0;

		String line;
		quit: while ((line = r.readLine()) != null) {
			String[] tok = line.trim().split("\\s+");
			if (tok.length == 0) {
				System.out.println("Bad command!");
			} else if (tok.length == 1) {
				// Commands having no parameters
				String cmd = tok[0];
				switch (cmd) {
				case "p":
					System.out.println(sw);
					break;
				case "rgb":
					System.out.println(sw
						.toStringInModel(Model.RGB));
					break;
				case "cymk":
					System.out.println(sw
						.toStringInModel(Model.CYMK));
					break;
				case "hex":
					System.out.println(sw
						.toStringInModel(Model.HEX));
					break;
				case "s":
					if (sw.save()) {
						System.out.println("Saved");
					}
					break;
				case "q":
					break quit;
				default:
					System.out.println("Bad command!");
				}
			} else {
				// Commands having one parameter
				Color c;

				String cmd = tok[0];
				String arg = tok[1];

				switch (cmd) {
				case "a":
					c = ColorFactory.fromString(arg);
					if (c != null && sw.addColor(c)) {
						System.out.println("Added");
					}
					break;
				case "r":
					c = ColorFactory.fromString(arg);
					if (c != null
						&& sw.removeColor(c) != null) {
						System.out.println("Removed");
					}
					break;
				default:
					System.out.println("Bad command!");
				}
			}
		}

		System.out.println("JPalette Terminated");
	}
}
