import BIT.highBIT.*;

import java.util.Enumeration;

import java.io.File;

public class MemoryAllocation implements Visitor {
    private static int _numberOfNewInstructions = 0;
    private static int _numberOfNewArrayInstructions = 0;
    private static int _numberOfANewArrayInstructions = 0;
    private static int _numberOfMultiANewArrayInstructions = 0;

    @Override
    public void visit(Metric m) {
        File inputDirectoy = new File(m.getInputDirectory());
        String fileNames[] = inputDirectoy.list();

        for (int i = 0; i < fileNames.length; ++i) {
            String fileName = fileNames[i];

            if (fileName.endsWith(".class")) { 
                ClassInfo classInfo = new ClassInfo(m.getInputDirectory() + System.getProperty("file.separator") + fileName);

                for (Enumeration e = classInfo.getRoutines().elements(); e.hasMoreElements(); ) {
                    Routine routine = (Routine) e.nextElement();
                    
                    for (Enumeration instructions = routine.getInstructionArray().elements(); instructions.hasMoreElements(); ) {
                        Instruction instruction = (Instruction) instructions.nextElement();

                        int opcode = instruction.getOpcode();

                        if ((opcode == InstructionTable.NEW) || (opcode == InstructionTable.newarray) || (opcode == InstructionTable.anewarray) || (opcode == InstructionTable.multianewarray)) {
                            instruction.addBefore("MemoryAllocation", "allocationCount", new Integer(opcode));
                        }
                    }
                }

                classInfo.addAfter("MemoryAllocation", "print", classInfo.getClassName());
                classInfo.write(m.getOutputDirectory() + System.getProperty("file.separator") + fileName);
            }
        }
    }

    public static synchronized void allocationCount(int opcode) {
        switch (opcode) {
            case InstructionTable.NEW:
                _numberOfNewInstructions++;
                break;
            case InstructionTable.newarray:
                _numberOfNewArrayInstructions++;
                break;
            case InstructionTable.anewarray:
                _numberOfANewArrayInstructions++;
                break;
            case InstructionTable.multianewarray:
                _numberOfMultiANewArrayInstructions++;
                break;
        }
    }

    public static synchronized void print(String className) {
        System.out.println(className + " executou:");
        System.out.println("\t- " + _numberOfNewInstructions + " instrucoes \'new\'");
        System.out.println("\t- " + _numberOfNewArrayInstructions + " instrucoes \'newarray\'");
        System.out.println("\t- " + _numberOfANewArrayInstructions + " instrucoes \'anewarray\'");
        System.out.println("\t- " + _numberOfMultiANewArrayInstructions + " instrucoes \'multianewarray\'");
    }
}