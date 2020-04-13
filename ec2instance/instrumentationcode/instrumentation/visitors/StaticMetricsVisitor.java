package instrumentation.visitors;

import instrumentation.Metric;

import instrumentation.dataview.InputDirectoryDataView;
import instrumentation.dataview.SingleClassStaticMetricsDataView;
import instrumentation.dataview.GlobalStaticMetricsDataView;

import instrumentation.observers.StdoutObserver;
import instrumentation.observers.Observer;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import java.lang.NullPointerException;

import BIT.highBIT.*;

public class StaticMetricsVisitor extends MetricsVisitor {
	private ArrayList<String> _classNames;

	public StaticMetricsVisitor(InputDirectoryDataView inputDirectoryData) {
		if (inputDirectoryData == null) {
			throw new NullPointerException("Dados da diretoria sao null");
		}
		_classNames = inputDirectoryData.getAbsoluteClassPaths();

		addObserver(new StdoutObserver());
	}

	@Override
	public void visit(Metric m) {
		System.out.println("Aplicando metricas estaticas...");

		GlobalStaticMetricsDataView globalDataView = new GlobalStaticMetricsDataView();

		for (String fileName : _classNames) {
			ClassInfo classInfo = new ClassInfo(fileName);
			Vector routines = classInfo.getRoutines();
			String className = classInfo.getClassName();
			int numberOfMethods = routines.size();
			int numberOfBasicBlocks = 0;
			int numberOfInstructions = 0;
						
			for (Enumeration e = routines.elements(); e.hasMoreElements();) {
				Routine routine = (Routine) e.nextElement();
				
				BasicBlockArray basicBlockArray = routine.getBasicBlocks();
				numberOfBasicBlocks = basicBlockArray.size();

				InstructionArray instructionArray = routine.getInstructionArray();
				numberOfInstructions = instructionArray.size();
			}			

			try { 
				SingleClassStaticMetricsDataView classeDataView = new SingleClassStaticMetricsDataView(className, numberOfMethods, numberOfBasicBlocks, numberOfInstructions);
				notifyObservers(classeDataView);
				globalDataView.addSingleClassData(classeDataView);
			} catch (NullPointerException ex) {
				ex.printStackTrace();
			}
		}

		notifyObservers(globalDataView);
	}

	private void notifyObservers(SingleClassStaticMetricsDataView classeDataView) {
		for (Observer o : _observers) {
			o.notify(classeDataView);
		}
	}

	private void notifyObservers(GlobalStaticMetricsDataView globalDataView) {
		for (Observer o : _observers) {
			o.notify(globalDataView);
		}
	}
}