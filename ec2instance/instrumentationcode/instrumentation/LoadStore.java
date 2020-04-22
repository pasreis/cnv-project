import BIT.highBIT.*;

import java.util.Enumeration;

import java.io.File;

public class LoadStore implements Visitor {
    private static int _numberOfFieldLoads = 0;
    private static int _numberOfFieldStores = 0;
    private static int _numberOfRegularLoads = 0;
    private static int _numberOfRegularStores = 0;

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

                    for (Enumeration instructions = routine.getInstructionArray().elements(); instructions.hasMoreElements(); ) {
                        Instruction instruction = (Instruction) instructions.nextElement();

                        int opcode = instruction.getOpcode();

                        if (opcode == InstructionTable.getfield) {
                            instruction.addBefore("LoadStore", "incrementGetFiled", new Integer(1));
                        } else if (opcode == InstructionTable.putfield) {
                            instruction.addBefore("LoadStore", "incrementPutFiled", new Integer(1));
                        } else {
                            short instructionType = InstructionTable.InstructionTypeTable[opcode];

                            if (instructionType == InstructionTable.LOAD_INSTRUCTION) {
                                instruction.addBefore("LoadStore", "incrementLoad", new Integer(1));
                            } else if (instructionType == InstructionTable.STORE_INSTRUCTION) {
                                instruction.addBefore("LoadStore", "incrementStore", new Integer(1));
                            }
                        }
                    }
                }

                classInfo.addAfter("LoadStore", "notifyObserver", classInfo.getClassName());
                classInfo.write(m.getOutputDirectory() + System.getProperty("file.separator") + fileName);
            }
        }
    }

    public static synchronized void incrementGetFiled(int increment) {
        if (increment > 0) _numberOfFieldLoads += increment;
    }

    public static synchronized void incrementPutFiled(int increment) {
        if (increment > 0) _numberOfFieldStores += increment;
    }

    public static synchronized void incrementLoad(int increment) {
        if (increment > 0) _numberOfRegularLoads += increment;
    }

    public static synchronized void incrementStore(int increment) {
        if (increment > 0) _numberOfRegularStores += increment;
    }

    public static synchronized void notifyObserver(String className) {
        String data = className + " executou:\n" +
            "\t- " + _numberOfFieldLoads + " instrucoes de \'load field'\n" +
            "\t- " + _numberOfFieldStores + " instrucoes de \'store field'\n" +
            "\t- " + _numberOfRegularLoads  + " instrucoes de \'regular load'\n" +
            "\t- " + _numberOfRegularStores + " instrucoes de \'regular store'\n";
        Observer.notify(data);
    }
}