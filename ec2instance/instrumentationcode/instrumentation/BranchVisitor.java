import BIT.highBIT.*;
import BIT.lowBIT.*;

import java.io.File;

import java.util.Enumeration;

public class BranchVisitor implements Visitor {
    public static int _branchPC = 0;
    public static int _branchNumber = 0;
    public static BranchStructure[] _branchInfo;
    public static String _branchMethodName;
    public static String _branchClassName;

    @Override
    public void visit(Metric m) {
        File inputDirectory = new File(m.getInputDirectory());
        String fileNames[] = inputDirectory.list();
        int total = 0, k = 0;

        for (int i = 0; i < fileNames.length; ++i) {
            String fileName = fileNames[i];

            if (fileName.endsWith(".class")) {
                ClassInfo classInfo = new ClassInfo(m.getInputDirectory() + System.getProperty("file.separator") + fileName);

                for (Enumeration e = classInfo.getRoutines().elements(); e.hasMoreElements(); ) {
                    Routine routine = (Routine) e.nextElement();
                    Instruction[] instructions = routine.getInstructions();

                    for (Enumeration b = routine.getBasicBlocks().elements(); b.hasMoreElements(); ) {
                        BasicBlock basicBlock = (BasicBlock) b.nextElement();
                        Instruction instruction = instructions[basicBlock.getEndAddress()];

                        short instructionType = InstructionTable.InstructionTypeTable[instruction.getOpcode()];

                        if (instructionType == InstructionTable.CONDITIONAL_INSTRUCTION) {
                            total++;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < fileNames.length; ++i) {
            String fileName = fileNames[i];

            if (fileName.endsWith(".class")) {
                ClassInfo classInfo = new ClassInfo(m.getInputDirectory() + System.getProperty("file.separator") + fileName);

                for (Enumeration e = classInfo.getRoutines().elements(); e.hasMoreElements(); ) {
                    Routine routine = (Routine) e.nextElement();
                    routine.addBefore("BranchVisitor", "setBranchMethodName", routine.getMethodName());

                    InstructionArray instructions = routine.getInstructionArray();

                    for (Enumeration b = routine.getBasicBlocks().elements(); b.hasMoreElements(); ) {
                        BasicBlock basicBlock = (BasicBlock) b.nextElement();
                        Instruction instruction = (Instruction) instructions.elementAt(basicBlock.getEndAddress());
                        short instructionType = InstructionTable.InstructionTypeTable[instruction.getOpcode()];

                        if (instructionType == InstructionTable.CONDITIONAL_INSTRUCTION) {
                            instruction.addBefore("BranchVisitor", "setBranchPC", new Integer(instruction.getOffset()));
                            instruction.addBefore("BranchVisitor", "updateBranchNumber", new Integer(k));
                            instruction.addBefore("BranchVisitor", "updateBranchOutcome", "BranchOutcome");
                            k++;
                        }
                    }
                }

                classInfo.addBefore("BranchVisitor", "setClassName", classInfo.getClassName());
                classInfo.addBefore("BranchVisitor", "init", new Integer(total));
                classInfo.addAfter("BranchVisitor", "print", classInfo.getClassName());
                classInfo.write(m.getOutputDirectory() + System.getProperty("file.separator") + fileName);
            }
        }
    }

    public static synchronized void setBranchMethodName(String methodName) {
        _branchMethodName = methodName;
    }

    public static synchronized void setBranchPC(int pc) {
        _branchPC = pc;
    }

    public static synchronized void updateBranchNumber(int number) {
        _branchNumber = number;

        if (_branchInfo[_branchNumber] == null) {
            _branchInfo[_branchNumber] = new BranchStructure(_branchClassName, _branchMethodName, _branchPC);
        }
    }

    public static synchronized void setClassName(String className) {
        _branchClassName = className;
    }

    public static synchronized void init(int totalOfBranches) {
        if (_branchInfo == null) {
            _branchInfo = new BranchStructure[totalOfBranches];
        }
    }

    public static synchronized void updateBranchOutcome(int branchOutcome) {
        if (branchOutcome == 0) {
            _branchInfo[_branchNumber].incrementNotTaken();
        } else {
            _branchInfo[_branchNumber].incrementTaken();
        }
    }

    public static synchronized void print(String className) {
        for (int i = 0; i < _branchInfo.length; ++i) {
            if (_branchInfo[i] != null) {
                System.out.println(_branchInfo[i]);
            }
        }
    }
}

class BranchStructure {
    String _className;
    String _methodName;
    int _pc;
    int _taken = 0;
    int _notTaken = 0;

    public BranchStructure(String className, String methodName, int pc) {
        _className = className;
        _methodName = methodName;
        _pc = pc;
    }

    public void incrementTaken() {
        _taken++;
    }

    public void incrementNotTaken() {
        _notTaken++;
    }

    @Override
    public String toString() {
        return "Nome da classe:     " + _className + "\n" +
            "Nome do metodo:     " + _methodName + "\n" +
            "PC:                 " + _pc + "\n" + 
            "Ramos cobertos:     " + _taken + "\n" + 
            "Ramos nao cobertos: " + _notTaken;
    }
}