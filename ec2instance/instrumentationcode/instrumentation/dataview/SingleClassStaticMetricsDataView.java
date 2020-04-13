package instrumentation.dataview;

import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;

public class SingleClassStaticMetricsDataView extends MetricsDataView {
	private int _numberOfMethods;
	private int _numberOfBasicBlocks;
	private int _numberOfInstructions;
	private String _className;

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
	}

	public int getNumberOfMethods() { return _numberOfMethods; }
	public int getNumberOfBasicBlocks()  { return _numberOfBasicBlocks; }
	public int getNumberOfInstructions() { return _numberOfInstructions; }
	public String getClassName() { return _className; }
	
	@Override
	public String toString() {
		return "[ " + _className + " ]:\n" + 
			"Numero de metodos: " + _numberOfMethods + "\n" +
			"Numero de blocos basicos: " + _numberOfBasicBlocks +  "\n" +
			"Numero de instrucoes: " + _numberOfInstructions + "\n";
	}
}