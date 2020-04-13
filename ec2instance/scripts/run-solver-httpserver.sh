#!/bin/bash

source ./java-config.sh
source ./config-bit.sh

cd ..

echo "Compilando..."
make
if [ $? -ne 0 ]
then
	echo "Problemas na compilacao!"
	exit 1
else
	echo "Compilacao concluida com sucesso!"
fi

cd instrumentedcode

echo "Correndo o servidor web: (Ctrl+C para parar)"
java pt.ulisboa.tecnico.cnv.server.WebServer
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