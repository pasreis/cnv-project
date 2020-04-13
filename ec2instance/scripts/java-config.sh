echo "Configurando o Java para as versoes apropriadas..."

export JAVA_HOME=/usr/lib/jvm/java-7-oracle/
export JAVA_ROOT=/usr/lib/jvm/java-7-oracle/
export JDK_HOME=/usr/lib/jvm/java-7-oracle/
export JRE_HOME=/usr/lib/jvm/java-7-oracle/jre
export PATH=/usr/lib/jvm/java-7-oracle/bin/:$PATH
export SDK_HOME=/usr/lib/jvm/java-7-oracle/
export _JAVA_OPTIONS="-XX:-UseSplitVerifier "$_JAVA_OPTIONS

echo "Concluido!"