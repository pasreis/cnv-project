package instrumentation;

import instrumentation.dataview.InputDirectoryDataView;

public class InstrumentationApp {
	public static void printHelp() {
		System.out.println();
		System.out.println("Sintaxe: java InstrumentationApp diretoria_in [diretoria_out]");
		System.out.println("\tdirectoria_in: diretoria onde se encontram os ficheiros .class a serem instrumentados");
		System.out.println("\tdirectoria_out: diretoria onde serao depositados os ficheiros instrumentados");
		System.out.println();
	}

	public static void main(String[] args) {
		System.out.println("CNV 2020 - Sudoku@Cloud Ferramenta de Instrumentacao:\n");
		
		if (args.length < 1 || args.length > 2) {
			printHelp();
			System.exit(1);
		}
		
		try {
			System.out.println("Analizando a diretoria: " + args[0]);
			InputDirectoryDataView inputDirectoryDataView = new InputDirectoryDataView(args[0]);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			printHelp();
		}
	}
}