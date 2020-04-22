import BIT.highBIT.*;

import java.util.Enumeration;

import java.io.File;

public class ExecutedBasicBlocks implements Visitor {
    private static int _numberOfBasicBlocks = 0;

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
                    BasicBlockArray basicBlockArray = routine.getBasicBlocks();
                    routine.addBefore("ExecutedBasicBlocks", "increment", new Integer(basicBlockArray.size()));
                }

                classInfo.addAfter("ExecutedBasicBlocks", "notifyObserver", classInfo.getClassName()); 
                classInfo.write(m.getOutputDirectory() + System.getProperty("file.separator") + fileName);
            }
        }
    }

    public static synchronized void increment(int increment) {
        if (increment > 0) _numberOfBasicBlocks += increment;
    }

    public static synchronized void notifyObserver(String className) {
        String data = className + " executou " + _numberOfBasicBlocks + " blocos basicos";
        Observer.notify(data);
    }
}