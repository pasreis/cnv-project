#!/bin/bash

TIMEOUT=120 # 2 minutes

echo "Instalando EC2Instance..."
sudo echo "$(pwd)/run-solver-httpserver.sh" >> /etc/rc.local
echo "Instalacao completa!"
echo

read -n 1 -t $TIMEOUT -p $'Pressione em qualquer tecla para reiniciar...\nESC para cancelar (e necessario reiniciar)\n' KEY
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
elif [ $EXIT_STATUS -gt 128 ]
then
	echo " O reinicio foi automaticamente cancelado! Devera faze-lo manualmente!"
fi
