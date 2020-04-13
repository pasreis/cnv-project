package instrumentation.dataview;

import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;

import java.io.File;

import java.util.ArrayList;

public class InputDirectoryDataView {
	ArrayList<String> _absoluteFilePaths;
	ArrayList<String> _absoluteClassPaths;

	public InputDirectoryDataView(String directoryName) throws NullPointerException, IllegalArgumentException {
		if (directoryName == null) {
			throw new NullPointerException("O caminho para o directorio e null");
		}

		File inputDirectory = new File(directoryName);

		if (!inputDirectory.isDirectory()) {
			throw new IllegalArgumentException("O caminho nao corresponde a uma diretoria valida");
		} else {
			String[] files = inputDirectory.list();

			if (files == null) {
				throw new NullPointerException("Erro na leitura dos ficheiros na diretoria. O caminho podera nao corresponder a uma diretoria valida?");
			}

			if (files.length == 0) {
				throw new IllegalArgumentException("A diretoria esta vazia");
			}

			String absoluteDirectoryPath = inputDirectory.getAbsolutePath();
			
			_absoluteClassPaths = new ArrayList<String>();
			_absoluteFilePaths = new ArrayList<String>();

			for (int i = 0; i < files.length; ++i) {
				String absoluteFilePath = absoluteDirectoryPath + "/" + files[i];
				
				if (absoluteFilePath.endsWith(".class")) {
					_absoluteClassPaths.add(absoluteFilePath);
				} else {
					_absoluteFilePaths.add(absoluteFilePath);
				}
			}

			if (_absoluteClassPaths.size() == 0) {
				throw new IllegalArgumentException("A diretoria " + directoryName + " nao contem ficheiros .class");
			}

			System.out.println("Caminho absoluto para a diretoria " + directoryName + ": " + absoluteDirectoryPath);
			System.out.println("A diretoria " + directoryName + " contem os seguintes ficheiros: ");

			for (String fileName : _absoluteFilePaths) {
				System.out.println(fileName);
			}

			System.out.println("A diretoria " + directoryName + " contem as seguintes classes: ");

			for (String fileName : _absoluteClassPaths) {
				System.out.println(fileName);
			}

			System.out.println();
		}
	}

	public ArrayList<String> getAbsoluteFilePaths() { return _absoluteFilePaths; }
	public ArrayList<String> getAbsoluteClassPaths() { return _absoluteClassPaths; }
}