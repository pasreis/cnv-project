# Código de Instrumentação
--------------------------

## Como compilar o código de Instrumentação
--------------------------

Dentro da diretoria `instrumentationcode` correr o seguinte comando:

```
$ source config-bit.sh
```

Se seguida é possivel compilar o código com o seguinte comando:

```
$ make
```

Este comando criará uma diretoria `bit` onde reside o código da biblioteca BIT usada para a instrumentação e uma directoria `build` onde reside o código usado para a instrumentação.

É também possivel correr o seguinte comando para limpar todos os ficheiros criados pela compilação do código:

```
$ make
```

## Como correr o código de Instrumentação
--------------------------

Na diretoria `build` gerada após a compilação do código, correr o seguinte commando:

```
$ java InstrumentationApp <directoria/input/> <metrica_de_instrumentacao> [diretoria/output/]
```

Argumentos:

- `directoria/input` é o caminho até à directoria onde se encontra o código a instrumentar. Este argumento é obrigatório.

- `metrica_de_instrumentação` é a métrica a aplicar durante a instrumentação ao código a ser instrumentado. Para uma descrição mais alargada dos possiveis valores, consultar a secção [Argumentos](##Argumentos)

- `directoria/ouput` é o caminho até à directoria onde será escrito o código instrumento. Este argumento é opcional. **Valor por defeito:** `directoria/input`

## Argumentos
--------------------------

- `*`: Executa todas as metricas

- `instcont`: Executa a contagem do numero de instrucoes executadas

- `bbcont`: Executa a contagem do numero de blocos basicos executados

- `metcont`: Executa a contagem do numero de metodos executados

- `prevramo`: Executa medicoes sobre a previsao de ramo

- `alocmem`: Executa medicoes sobre a alocacao de memoria

- `acessmem`: Executa medicoes sobre o acesso a memoria (loads e stores)