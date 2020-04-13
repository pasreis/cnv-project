BIT_DIR:=bit/

INSTR_DIR:=instrumentation
INSTR_OUT:=build

all: bit instr

bit:
	wget http://grupos.ist.utl.pt/meic-cnv/labs/labs-bit/BIT.zip
	mkdir -p $(BIT_DIR)
	unzip BIT.zip -d $(BIT_DIR)
	rm -rf BIT.zip

instr: 
	mkdir -p $(INSTR_OUT)
	javac $(INSTR_DIR)/*.java $(INSTR_DIR)/visitors/*.java $(INSTR_DIR)/dataview/*.java -d $(INSTR_OUT)

clean:
	rm -rf $(BIT_DIR)	
	rm -rf $(INSTR_OUT)