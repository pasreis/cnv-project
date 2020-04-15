#!/bin/bash

TIMEOUT=120 # 2 minutes

function CHECK_EXIT_STATUS () { 
	$1=EXIT_STATUS; $2=CMP_OPERATOR; $3=ERROR_CODE; $4=ERROR_MESSAGE
	if [ $EXIT_STATUS $CMP_OPERATOR $ERROR_CODE ]
	then
		echo $ERROR_MESSAGE
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
CHECK_EXIT_STATUS $? "-eq" 127 $JDK_ERROR_MSG

javac -version > /dev/null 2>&1
CHECK_EXIT_STATUS $? "-eq" 127 $JDK_ERROR_MSG
echo "Instalacao do JDK foi realizada com sucesso!"

echo $'\nCompilando o codigo do servidor web...'
INSTRUMENTED_CODE_SRC_DIR=/home/ec2-user/cnv-project/ec2instance/instrumentedcode
cd $INSTRUMENTED_CODE_SRC_DIR

make
CHECK_EXIT_STATUS $? "-ne" 0 "Erro na compilacao do servidor web. Abortando..."

echo ""
echo "Instalando o servidor web e a aplicacao SudokuSolver"
INSTALLATION_ERROR_MSG="Erro na instalacao da EC2Instance. Abortando..."
sudo sed -i "$ a java -cp $INSTRUMENTED_CODE_SRC_DIR pt.ulisboa.tecnico.cnv.server.WebServer" /etc/rc.local
CHECK_EXIT_STATUS $? "-eq" 1 $INSTALLATION_ERROR_MSG

sudo ln -sf /etc/rc.d/rc.local /etc/rc.local
CHECK_EXIT_STATUS $? "-ne" 0 $INSTALLATION_ERROR_MSG

sudo chmod +x /etc/rc.local
CHECK_EXIT_STATUS $? "-ne" 0 $INSTALLATION_ERROR_MSG
echo "Instalacao concluida!"

echo $'\nVerificando a Instalacao da EC2Instance...'
CHECK_EXIT_STATUS $EXIT_STATUS "-eq" 1 

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
	CHECK_EXIT_STATUS EXIT_STATUS "-eq" 128 " O reinicio foi automaticamente cancelado! Devera faze-lo manualmente!"
fi
