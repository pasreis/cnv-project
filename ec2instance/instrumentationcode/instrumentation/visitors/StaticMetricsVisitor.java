package instrumentation.visitors;

import instrumentation.Metric;

import instrumentation.dataview.InputDirectoryDataView;
import instrumentation.dataview.SingleClassStaticMetricsDataView;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import java.lang.NullPointerException;

import BIT.highBIT.*;

public class StaticMetricsVisitor implements MetricsVisitor {
	private ArrayList<String> _classNames;

	// Statistics
	private int _totalNumberOfClasses = 0;
	private int _totalNumberOfMethods = 0;
	private int _totalNumberOfBasicBlocks = 0;
	private int _totalNumberOfInstructions = 0;

	public StaticMetricsVisitor(InputDirectoryDataView inputDirectoryData) {
		if (inputDirectoryData == null) {
			throw new NullPointerException("Dados da diretoria sao null");
		}
		_classNames = inputDirectoryData.getAbsoluteClassPaths();
	}

	@Override
	public void visit(Metric m) {
		System.out.println("Aplicando metricas estaticas...");

		_totalNumberOfClasses = _classNames.size();

		for (String fileName : _classNames) {
			ClassInfo classInfo = new ClassInfo(fileName);
			Vector routines = classInfo.getRoutines();
			String className = classInfo.getClassName();
			int numberOfMethods = routines.size();
			int numberOfBasicBlocks = 0;
			int numberOfInstructions = 0;
			_totalNumberOfMethods += numberOfMethods;
			
			for (Enumeration e = routines.elements(); e.hasMoreElements();) {
				Routine routine = (Routine) e.nextElement();
				
				BasicBlockArray basicBlockArray = routine.getBasicBlocks();
				numberOfBasicBlocks = basicBlockArray.size();
				_totalNumberOfBasicBlocks += numberOfBasicBlocks;

				InstructionArray instructionArray = routine.getInstructionArray();
				numberOfInstructions = instructionArray.size();
				_totalNumberOfInstructions += numberOfInstructions;
			}			

			try { 
				SingleClassStaticMetricsDataView classeDataView = new SingleClassStaticMetricsDataView(className, numberOfMethods, numberOfBasicBlocks, numberOfInstructions);
				System.out.println(classeDataView);
			} catch (NullPointerException ex) {
				ex.printStackTrace();
			}
		}

		System.out.println("Numero de classes: " + _totalNumberOfClasses);
		System.out.println("Numero de metodos: " + _totalNumberOfMethods);
		System.out.println("Numero de blocos basicos: " + _totalNumberOfBasicBlocks);
		System.out.println("Numero de instrucoes: " + _totalNumberOfInstructions);
	}
}