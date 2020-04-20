
public class Metric {
	private String _inputDirectory;
	private String _outputDirectory;

	public Metric(String inputDirectory, String outputDirectory) {
		_inputDirectory = inputDirectory;
		_outputDirectory = outputDirectory;
	}

	// Getters
	public String getInputDirectory()  { return _inputDirectory; }
	public String getOutputDirectory() { return _outputDirectory; }
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}