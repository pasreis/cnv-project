package instrumentation.visitors;

import instrumentation.Metric;

import instrumentation.dataview.InputDirectoryDataView;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import java.lang.NullPointerException;

import BIT.highBIT.*;

public class StaticMetricsVisitor implements MetricsVisitor {
	private ArrayList<String> _classNames;

	// Statistics
	private int _numberOfClases = 0;
	private int _numberOfMethods = 0;
	private int _numberOfBasicBlocks = 0;
	private int _numberOfInstructions = 0;

	public StaticMetricsVisitor(InputDirectoryDataView inputDirectoryData) {
		if (inputDirectoryData == null) {
			throw new NullPointerException("Dados da diretoria sao null");
		}
		_classNames = inputDirectoryData.getAbsoluteClassPaths();
	}

	@Override
	public void visit(Metric m) {
		System.out.println("Aplicando metricas estaticas...");

		_numberOfClases = _classNames.size();

		for (String fileName : _classNames) {
			ClassInfo classInfo = new ClassInfo(fileName);
			Vector routines = classInfo.getRoutines();
			_numberOfMethods += routines.size();

			for (Enumeration e = routines.elements(); e.hasMoreElements();) {
				Routine routine = (Routine) e.nextElement();
				
				BasicBlockArray basicBlockArray = routine.getBasicBlocks();
				_numberOfBasicBlocks += basicBlockArray.size();

				InstructionArray instructionArray = routine.getInstructionArray();
				_numberOfInstructions += instructionArray.size();
			}
		}

		System.out.println("Numero de classes: " + _numberOfClases);
		System.out.println("Numero de metodos: " + _numberOfMethods);
		System.out.println("Numero de blocos basicos: " + _numberOfBasicBlocks);
		System.out.println("Numero de instrucoes: " + _numberOfInstructions);
	}
}