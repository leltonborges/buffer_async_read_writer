# Assincronos com Buffer e Batch

## Introdução
O Projeto consiste em resolução de estouro de BO (Buffer Overflow), os dados chegam para serem processados no framework, porém em alguns momentos ocorre overflow dos dados. Numa compra há uma série de procedimentos a serem seguidos, nesses processos precisar ser observado de modo crítico e essas observações são feitas em processo síncrono. No framework quando está sendo processado a leitura do mesmo, a tarefa que está chegando ele não consegue ser processado por ser um processo síncrono e entrar na fila de espera e é aí que acontece o BO. Nossa solução é fazer com que os processos sejam armazenados de alguma forma, seja em fila, processos assíncronos, mesmo se estiver em um dos modos o sistema não barrar e se caso o processo for complexo ele alternar em um dos modos para que não haja interrupção no algoritmo. 
      A complexidade do algoritmo implementado para a resolução, se encontra nas responsabilidades técnicas em um único componente de processamento e para desenvoltura em implementar, é necessário que o desenvolvedor tenha boas práticas e saiba fazer a manipulação de processos. 

# Metodologia
## Spring Batch

O Spring Batch é uma estrutura de código aberto projetada para facilitar o desenvolvimento de aplicativos robustos em lote. Ele se baseia na abordagem de desenvolvimento baseada no POJO do Spring Framework e oferece uma série de recursos para processamento em lote eficiente. 

+ Job: O Job é a unidade principal no Spring Batch, representando uma sequência de passos a serem executados em um determinado pedido. Cada trabalho pode ser composto por um ou mais passos.
+ Step: Um Step representa uma etapa ou passo no processamento em lote. Um Step pode consistir em leitura de dados (ItemReader), processamento de dados (ItemProcessor), e escrita de dados (ItemWriter). Também pode ser uma tarefa simples baseada em tasklets.
+ Job Repository: O Job Repository mantém o estado do Job, incluindo informações como duração da execução, status, erros, leituras e gravações. Esse repositório é compartilhado entre os componentes do sistema.
+ Job Launcher: O Job Launcher é responsável por executar efetivamente o Job, considerando fatores como a forma de execução (thread único, distribuído), validação de configurações e suporte a reinicialização.

Recursos do Spring Batch para atender a diversos cenários:
+ Leitura de banco de dados: Fornece componentes de acesso ao banco de dados de forma paginada, em lote, transacional, etc.
+ Manipulação de arquivos: Oferece diferentes manipuladores de arquivo para leitura e escrita, adequados à natureza dos dados.
+ Tratamento de abordagem: Mecanismos de nova tentativa e manipuladores de exceção para permitir a recuperação de falhas sem comprometer o processamento.
+ Restart: Capacidade essencial para lotes atrasados, permitindo a reinicialização do processamento a partir dos metadados salvos na última execução.
+ Paralelismo: Opções para escalar a aplicação horizontalmente (chunking e particionamento remoto) e verticalmente (passos paralelos e multithread), otimizando o tempo de processamento.
 Spring Batch é uma solução robusta, abrangente e altamente escalável para processamento em lote, fazendo parte do portfólio Spring. Ele oferece funções reutilizáveis ​​e recursos avançados para lidar com volumes significativos.

## Buffer e Buffering

  Termos que se originaram no contexto da ciência da computação e estão relacionados ao armazenamento temporário de dados. A necessidade de buffers tornou-se aparente com a introdução de dispositivos periféricos que operam a velocidades diferentes do núcleo de computador (CPU e memória principal). Por exemplo, impressoras e unidades de fita magnética eram muito mais lentas do que a CPU, então os buffers eram usados para alinhar a velocidade de processamento da CPU com a desses dispositivos. 
  A ideia de usar um espaço de armazenamento temporário para sincronizar diferentes velocidades de processamento é um conceito que pode ser encontrado em muitas tecnologias, mesmo fora da computação:  Produção e distribuição de energia, manufatura e logística, telecomunicações, áudio e vídeo, sistemas financeiros, saúde, dentre outros. No entanto, na computação, a implementação de buffers tornou-se mais sofisticada com o tempo, evoluindo de simples áreas de memória para estruturas de dados complexas que podem ser gerenciadas por software avançado para otimizar o desempenho do sistema.
  Mas o que é buffer, buffering não é buffer? Um buffer é uma região de memória física usada para armazenar temporariamente dados enquanto eles estão sendo movidos de um lugar para outro, é como uma "área de espera" para dados que serão processados ou transmitidos. Ele é crucial em situações onde há uma diferença na taxa de produção e consumo de dados entre dois processos ou sistemas. Buffering é o processo de pré-carregamento de dados em um buffer. No contexto de streaming de vídeo ou áudio, por exemplo, o buffering é o que permite que o conteúdo seja carregado alguns segundos ou minutos à frente do que está sendo atualmente reproduzido.  Isso ajuda a garantir uma reprodução suave sem interrupções, mesmo se houver uma queda temporária na velocidade da conexão de internet.

## Discussão

  O ponto a ser frizado é que alta performance tem muitas variações, assim como boas análises e testes, o que implica em bons chunks, quantidades de dados que será processado, qualidade nas implementações, e tomar ciência no tempo de execução e resposta do banco de dados utilizado para a solução ou se for um arquivo, o hardware do mesmo ter alta performance de escrita e leitura, estes são fatores cruciais para uma boa solução com Spring Batch.

## Conclusão

  A  problemática é em processo síncrono, quando está em modo escrita ou leitura, os processo que estão chegando ele não consegue armazenar e esses dados acabam se perdendo. O processamento em lote síncrono é a abordagem padrão e mais simples no Spring Batch. Ele é adequado para casos em que a ordem de execução é crítica ou quando há dependências restritas entre as etapas do processamento. No entanto, em cenários onde o desempenho é crítico e há oportunidades de paralelismo, pode ser interessante explorar estratégias de processamento em lote assíncrono. O processamento em lote síncrono refere-se à execução sequencial de operações em um lote de dados. Em outras palavras, cada etapa do processamento ocorre em uma ordem predeterminada, e a próxima etapa só começa após a conclusão da anterior. Esse modelo de execução é linear e não envolve a execução simultânea de diferentes partes do processo.
  Resolução foi processar os processos em modo assíncrono para fazer múltiplos processos.O processamento em lote assíncrono refere-se à execução de operações de processamento em lote de forma paralela, onde diferentes partes do processamento podem ocorrer simultaneamente, em vez de serem executadas sequencialmente. Isso é feito para melhorar a eficiência e o desempenho do sistema, especialmente ao lidar com grandes volumes de dados. No contexto do processamento em lote, a assincronicidade envolve a execução simultânea de diferentes etapas do processamento. Em um ambiente assíncrono, várias tarefas podem ser realizadas simultaneamente, o que é particularmente útil quando o processamento em lote envolve operações que podem ocorrer independentemente umas das outras. 
