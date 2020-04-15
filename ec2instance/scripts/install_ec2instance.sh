#!/bin/bash

TIMEOUT=120 # 2 minutes

function CHECK_INSTALLATION () { 
	# $1=EXIT_STATUS $2=ERROR_CODE $2=ERROR_MESSAGE
	if [ $1 -eq $2 ]
	then
		echo $3
		exit 1
	fi
}

echo "Instalando EC2Instance..."

echo "Atualizando o sistema..."
sudo yum -y update
echo "Atualizacao concluida!"

echo $'\nInstalando o JDK...'
sudo yum -y install java-1.7.0-openjdk-devel.x86_64
echo "Instalacao do JDK concluida!"

echo $'\nVerificando a instacalacao do JDK...'
JDK_ERROR_MSG="Erro na instalacao do JDK. Abortando..."
java -version > /dev/null 2>&1
CHECK_INSTALLATION $? 127 $JDK_ERROR_MSG

javac -version > /dev/null 2>&1
CHECK_INSTALLATION $? 127 $JDK_ERROR_MSG
echo "Instalacao do JDK foi realizada com sucesso!"

echo ""
echo "Instalando o servidor web e a aplicacao SudokuSolver"
sudo sed -i "$ a /home/ec2-user/cnv-project/ec2instance/scripts/run-solver-httpserver.sh" /etc/rc.local
EXIT_STATUS=$?
echo "Instalacao concluida!"

echo $'\nVerificando a Instalacao da EC2Instance...'
CHECK_INSTALLATION $EXIT_STATUS 1 "Erro na instalacao da EC2Instance. Abortando..."

read -n 1 -t $TIMEOUT -p $'\nPressione em qualquer tecla para reiniciar...\nESC para cancelar (e necessario reiniciar)\n' KEY
EXIT_STATUS=$?

if [ $EXIT_STATUS -eq 0 ]
then
	if [ $KEY = $'\e' ]
	then
		echo " O reinicio foi cancelado! Devera faze-lo manualmente"
		exit 1
	else
		echo " Reiniciando..."
		sudo init 6
	fi
else
	CHECK_INSTALLATION EXIT_STATUS 128 " O reinicio foi automaticamente cancelado! Devera faze-lo manualmente!"
fi
