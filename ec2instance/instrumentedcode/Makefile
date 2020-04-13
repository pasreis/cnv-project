all: solver

solver:
	javac pt/ulisboa/tecnico/cnv/solver/*.java

run: solver
	java pt.ulisboa.tecnico.cnv.server.WebServer 
clean:
	rm -rf target/*