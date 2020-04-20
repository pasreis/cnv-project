import BIT.highBIT.*;

import java.util.Enumeration;

import java.io.File;

public class ExecutedInstructions implements Visitor {

	@Override
	public void visit(Metric m) {
		File inputDirectory = new File(m.getInputDirectory());
		String fileNames[] = inputDirectory.list();

		for (int i = 0; i < fileNames.length; ++i) {
			String fileName = fileNames[i];

			if (fileName.endsWith(".class")) {
				ClassInfo classInfo = new ClassInfo(m.getInputDirectory() + System.getProperty("file.separator") + fileName);

				for (Enumeration e = classInfo.getRoutines().elements(); e.hasMoreElements(); ) {
					Routine routine = (Routine) e.nextElement();

					for (Enumeration b = routine.getBasicBlocks().elements(); b.hasMoreElements(); ) {
						BasicBlock basicBlock = (BasicBlock) b.nextElement();
						basicBlock.addBefore("ExecutedInstructions", "increment", new Integer(basicBlock.size()));
					}
				}
				
				classInfo.addAfter("ExecutedInstructions", "print", classInfo.getClassName());
				classInfo.write(m.getInputDirectory() + System.getProperty("file.separator") + fileName);
			}
		}		
	}
	private static int _numberOfInstructions = 0;

	public static synchronized void increment(int increment) {
		_numberOfInstructions += increment;
	}

	public static synchronized void print(String className) {
		System.out.println(className + "executed " + _numberOfInstructions + " instructions!!");
	}

	public static synchronized void mcount(int i) {

	}
	
}