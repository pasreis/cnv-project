import BIT.highBIT.*;

import java.util.Enumeration;

import java.io.File;

public class ExecutedMethods implements Visitor {
    private  static int _numberOfMethods = 0;

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
                    routine.addBefore("ExecutedMethods", "increment", new Integer(1));
                }

                classInfo.addAfter("ExecutedMethods", "notifyObserver", classInfo.getClassName());
                classInfo.write(m.getOutputDirectory() + System.getProperty("file.separator") + fileName);
            }
        }
    }

    public static synchronized void increment(int increment) {
        if (increment > 0) _numberOfMethods += increment;
    }

    public static synchronized void notifyObserver(String className) {
        String data = className + " executou " + _numberOfMethods + " metodos!";
        Observer.notify(data);
    }
}