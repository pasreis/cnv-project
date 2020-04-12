SOLVER_SRCS:=$(wildcard pt/ulisboa/tecnico/cnv/solver/*.java)
SOLVER_CLASSES_DST_DIR:=../target/classes/solver
SOLVER_JAR_DST_DIR:=../target/jar/solver

APACHE_JAR_DST_DIR:=../target/jar/apache
APACHE_CLASSES:=org/apache/commons/cli

JSON_JAR_DST_DIR:=../target/jar/json
JSON_CLASSES:=org/json

all: solver

solver:
	mkdir -p $(SOLVER_CLASSES_DST_DIR)
	javac $(SOLVER_SRCS) -d $(SOLVER_CLASSES_DST_DIR)

install: solver
	mkdir -p $(SOLVER_JAR_DST_DIR)
	mkdir -p $(APACHE_JAR_DST_DIR)
	mkdir -p $(JSON_JAR_DST_DIR)
	jar cvf $(SOLVER_JAR_DST_DIR)/solver.jar $(SOLVER_CLASSES_DST_DIR)/*
	jar cvf $(APACHE_JAR_DST_DIR)/apache.jar $(APACHE_CLASSES)/*
	jar cvf $(JSON_JAR_DST_DIR)/json.jar $(JSON_CLASSES)/*

clean:
	rm -rf target/*