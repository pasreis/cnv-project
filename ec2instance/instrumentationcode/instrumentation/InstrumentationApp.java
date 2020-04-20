import BIT.highBIT.*;

import java.util.Enumeration;

import java.io.File;
public class InstrumentationApp {
	public static void printHelp() {
		System.out.println();
		System.out.println("Sintaxe: java InstrumentationApp diretoria_in [diretoria_out]");
		System.out.println("\tdirectoria_in: diretoria onde se encontram os ficheiros .class a serem instrumentados");
		System.out.println("\tdirectoria_out: diretoria onde serao depositados os ficheiros instrumentados");
		System.out.println();
	}

	public static void main(String[] args) {
		if (args.length < 1 || args.length > 2) {
			printHelp();
			System.exit(1);
		}

		String inputDirectory = args[0];
		String outputDirectory;

		if (args.length == 2) {
			outputDirectory = args[1];
		} else {
			outputDirectory = inputDirectory;
		}
		
		Metric m = new Metric(inputDirectory, outputDirectory);
		m.accept(new BranchVisitor());
	}
}