#!/bin/bash

SOURCE_DIR_INSTRUMENTED=/home/ec2-user/cnv-project/ec2instance/instrumentedcode
SOURCE_DIR_INSTRUMENTATION=/home/ec2-user/cnv-project/ec2instance/instrumentationcode

source ./java-config.sh
export CLASSPATH="$CLASSPATH:$SOURCE_DIR_INSTRUMENTATION/bit/BIT:$SOURCE_DIR_INSTRUMENTATION/bit/BIT/samples:./"

cd $SOURCE_DIR_INSTRUMENTED

echo "Compilando..."
make
if [ $? -ne 0 ]
then
	echo "Problemas na compilacao!"
	exit 1
else
	echo "Compilacao concluida com sucesso!"
fi

echo "Correndo o servidor web: (Ctrl+C para parar)"
export CLASSPATH=$CLASSPATH:$SOURCE_DIR_INSTRUMENTED
java -cp ~ec2-user pt.ulisboa.tecnico.cnv.server.WebServer
echo $'\nConcluido!'

echo "Limpando os ficheiros criados..."

cd ..
make clean

if [ $? -ne 0 ]
then
	echo "Problemas a limpar os ficheiros criados!"
	exit 2
fi

echo "Conlcuido!"