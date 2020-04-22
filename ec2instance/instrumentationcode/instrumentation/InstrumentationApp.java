import BIT.highBIT.*;

import java.util.Enumeration;

import java.io.File;
public class InstrumentationApp {
	public static void printHelp() {
		System.out.println();
		System.out.println("Sintaxe: java InstrumentationApp diretoria_in metrica [diretoria_out]\n");
		System.out.println("directoria_in:  diretoria onde se encontram os ficheiros .class a serem instrumentados");
		System.out.println("directoria_out: diretoria onde serao depositados os ficheiros instrumentados");
		System.out.println("metrica:        a metrica pretendida: ");
		System.out.println("\t- *:        Executa todas as metricas");
		System.out.println("\t- instcont: Executa a contagem do numero de instrucoes executadas");
		System.out.println("\t- bbcont:   Executa a contagem do numero de blocos basicos executados");
		System.out.println("\t- metcont:  Executa a contagem do numero de metodos executados");
		System.out.println("\t- prevramo: Executa medicoes sobre a previsao de ramo");
		System.out.println("\t- alocmem:  Executa medicoes sobre a alocacao de memoria");
		System.out.println("\t- acessmem: Executa medicoes sobre o acesso a memoria (loads e stores)");
		System.out.println();
	}

	public static void main(String[] args) {
		if (args.length < 2 || args.length > 3) {
			printHelp();
			System.exit(1);
		}

		String inputDirectory = args[0];
		String outputDirectory;

		if (args.length == 3) {
			outputDirectory = args[2];
		} else {
			outputDirectory = inputDirectory;
		}

		Metric m = new Metric(inputDirectory, outputDirectory);
		final String metric = args[1];

		if (metric.equals("*")) {
			m.accept(new ExecutedInstructions());
			m.accept(new ExecutedBasicBlocks());
			m.accept(new ExecutedMethods());
			m.accept(new BranchVisitor());
			m.accept(new MemoryAllocation());
			m.accept(new LoadStore());
		} else if (metric.equals("instcont")) {
			m.accept(new ExecutedInstructions());
		} else if (metric.equals("bbcont")) {
			m.accept(new ExecutedBasicBlocks());
		} else if (metric.equals("metcont")) {
			m.accept(new ExecutedMethods());
		} else if (metric.equals("coberram")) {
			m.accept(new BranchVisitor());
		} else if (metric.equals("alocmem")) {
			m.accept(new MemoryAllocation());
		} else if (metric.equals("acessmem")) {
			m.accept(new LoadStore());
		} else {
			System.out.println("Opcao invalida.\n");
			printHelp();
		}		
	}
}