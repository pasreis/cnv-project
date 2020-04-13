package instrumentation.dataview;

import java.util.HashMap;

public class GlobalStaticMetricsDataView extends MetricsDataView {
	private int _numberOfClasses = 0;
	private int _numberOfMethods = 0;
	private int _numberOfBasicBlocks = 0;
	private int _numberOfInstructions = 0;
	private HashMap<String, SingleClassStaticMetricsDataView> _singleClassData = new HashMap<String, SingleClassStaticMetricsDataView>();

	// Statistics
	private float _averageOfInstructionsPerBasicBlock = 0;
	private float _averageOfInstructionsPerMethod = 0;
	private float _averageOfInstructionsPerClass = 0;
	private float _averageOfBasicBlocksPerMethod = 0;
	private float _averageOfBasicBlocksPerClass = 0;
	private float _averageOfMethodsPerClass = 0;

	// Getters
	public int getNumberOfClasses() { return _numberOfClasses; }
	public int getNumberOfMethods() { return _numberOfMethods; }
	public int getNumberOfBasicBlocks() { return _numberOfBasicBlocks; }
	public int getNumberOfInstructions() { return _numberOfInstructions; }
	public SingleClassStaticMetricsDataView getSingleClassData(String className) { return _singleClassData.get(className); }

	// Statistics getters
	public float getAverageOfInstructionsPerBasicBlock() { return _averageOfInstructionsPerBasicBlock; }
	public float getAverageOfInstructionsPerMethod() { return _averageOfInstructionsPerMethod; }
	public float getAverageOfInstructionsPerClass() { return _averageOfInstructionsPerClass; }
	public float getAverageOfBasicBlocksPerMethod() { return _averageOfBasicBlocksPerMethod; }
	public float getAverageOfBasicBlocksPerClass() { return _averageOfBasicBlocksPerClass; }
	public float getAverageOfMethodsPerClass() { return _averageOfMethodsPerClass; }
	
	public void addSingleClassData(SingleClassStaticMetricsDataView singleClassData) {
		_numberOfClasses++;
		_numberOfMethods += singleClassData.getNumberOfMethods();
		_numberOfBasicBlocks += singleClassData.getNumberOfBasicBlocks();
		_numberOfInstructions += singleClassData.getNumberOfInstructions();
		_singleClassData.put(singleClassData.getClassName(), singleClassData);
		
		_averageOfInstructionsPerBasicBlock = _numberOfInstructions / _numberOfBasicBlocks;
		_averageOfInstructionsPerMethod = _numberOfInstructions / _numberOfMethods;
		_averageOfInstructionsPerClass = _numberOfInstructions / _numberOfClasses;
		_averageOfBasicBlocksPerMethod = _numberOfBasicBlocks / _numberOfMethods;
		_averageOfBasicBlocksPerClass = _numberOfBasicBlocks / _numberOfClasses;
		_averageOfMethodsPerClass = _numberOfMethods / _numberOfClasses;
	}

	@Override
	public String toString() {
		return "Numero de classes:        " + _numberOfClasses + "\n" +
			"Numero de metodos:        " + _numberOfMethods + "\n" +
			"Numero de blocos basicos: " + _numberOfBasicBlocks + "\n" +
			"Numero de instrucoes:     " + _numberOfInstructions + "\n\n" + 
			"Media de instrucoes por bloco basico: " + _averageOfInstructionsPerBasicBlock + "\n" +
			"Media de instrucoes por metodo:       " + _averageOfInstructionsPerMethod + "\n" + 
			"Media de instrucoes por classe:       " + _averageOfInstructionsPerClass + "\n" +
			"Media de blocos basicos por metodo:   " + _averageOfBasicBlocksPerMethod + "\n" +
			"Media de blocos basicos por classe:   " + _averageOfBasicBlocksPerClass + "\n" +
			"Media de metodos por classe:          " + _averageOfMethodsPerClass + "\n";
	}
}