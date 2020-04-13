package instrumentation.dataview;

import java.util.HashMap;

public class GlobalStaticMetricsDataView extends MetricsDataView {
	private int _numberOfClasses = 0;
	private int _numberOfMethods = 0;
	private int _numberOfBasicBlocks = 0;
	private int _numberOfInstructions = 0;
	private HashMap<String, SingleClassStaticMetricsDataView> _singleClassData = new HashMap<String, SingleClassStaticMetricsDataView>();

	public void addSingleClassData(SingleClassStaticMetricsDataView singleClassData) {
		_numberOfClasses++;
		_numberOfMethods += singleClassData.getNumberOfMethods();
		_numberOfBasicBlocks += singleClassData.getNumberOfBasicBlocks();
		_numberOfInstructions += singleClassData.getNumberOfInstructions();
		_singleClassData.put(singleClassData.getClassName(), singleClassData);
	}

	public int getNumberOfClasses() { return _numberOfClasses; }
	public int getNumberOfMethods() { return _numberOfMethods; }
	public int getNumberOfBasicBlocks() { return _numberOfBasicBlocks; }
	public int getNumberOfInstructions() { return _numberOfInstructions; }
	public SingleClassStaticMetricsDataView getSingleClassData(String className) { return _singleClassData.get(className); }
	@Override
	public String toString() {
		return "Numero de classes: " + _numberOfClasses + "\n" +
			"Numero de metodos: " + _numberOfMethods + "\n" +
			"Numero de blocos basicos: " + _numberOfBasicBlocks + "\n" +
			"Numero de instrucoes: " + _numberOfInstructions + "\n";
	}
}