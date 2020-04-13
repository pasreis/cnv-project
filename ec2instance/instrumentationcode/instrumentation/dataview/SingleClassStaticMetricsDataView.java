package instrumentation.dataview;

import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;

public class SingleClassStaticMetricsDataView extends MetricsDataView {
	private int _numberOfMethods;
	private int _numberOfBasicBlocks;
	private int _numberOfInstructions;
	private String _className;

	// Statistics
	private float _averageOfInstructionsPerBasicBlock;
	private float _averageOfInstructionsPerMethod;
	private float _averageOfBasicBlocksPerMethod;

	public SingleClassStaticMetricsDataView(String className, int numberOfMethods, int numberOfBasicBlocks, int numberOfInstructions) throws NullPointerException, IllegalArgumentException{
		if (className == null) {
			throw new NullPointerException("O nome da classe e null");
		}

		if (numberOfMethods < 0 || numberOfInstructions < 0 || numberOfBasicBlocks < 0) {
			throw new IllegalArgumentException("Dados invalidos");
		}

		_numberOfMethods = numberOfMethods;
		_numberOfBasicBlocks = numberOfBasicBlocks;
		_numberOfInstructions = numberOfInstructions;
		_className = className;

		_averageOfInstructionsPerBasicBlock = _numberOfInstructions / _numberOfBasicBlocks;
		_averageOfInstructionsPerMethod = _numberOfInstructions / _numberOfMethods;
		_averageOfBasicBlocksPerMethod = _numberOfBasicBlocks / _numberOfMethods;
	}

	// Getters
	public int getNumberOfMethods() { return _numberOfMethods; }
	public int getNumberOfBasicBlocks()  { return _numberOfBasicBlocks; }
	public int getNumberOfInstructions() { return _numberOfInstructions; }
	public String getClassName() { return _className; }
	
	// Statistics Getters
	public float getAverageOfInstructionsPerBasicBlock() { return _averageOfInstructionsPerBasicBlock; }
	public float getAverageOfInstructionsPerMethod() { return _averageOfInstructionsPerMethod; }
	public float getAverageOfBasicBlocksPerMethod() { return _averageOfBasicBlocksPerMethod; }

	@Override
	public String toString() {
		return "[ " + _className + " ]:\n" + 
			"Numero de metodos:        " + _numberOfMethods + "\n" +
			"Numero de blocos basicos: " + _numberOfBasicBlocks +  "\n" +
			"Numero de instrucoes:     " + _numberOfInstructions + "\n\n" + 
			"Media de instrucoes por bloco basico: " + _averageOfInstructionsPerBasicBlock + "\n" + 
			"Media de instrucoes por metodo:       " + _averageOfInstructionsPerMethod + "\n" + 
			"Media de blocos basicos por metodo:   " + _averageOfBasicBlocksPerMethod + "\n";
	}
}