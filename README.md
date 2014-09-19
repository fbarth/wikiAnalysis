Análise de conteúdo gerado em Wikis
-----------------------------------

Este espaço mantem um ferramenta que tem como objetivo detectar grupos de usuários em Wikis. Para tanto, esta ferramenta acessa o conteúdo (histórico de criação e modificação das páginas e espaços) produzido em Wikis através de uma interface XML-RPC e processa o conteúdo utilizando algoritmos para identificação de grupos em grafos e algoritmos de agrupamento hierárquico de documentos. Todos os resultados são apresentados de forma visual ao usuário.

Sub-Projetos
------------

Este projeto foi implementado utilizando a linguagem de programação Java. Ao todo foram disponibilizados quatro sub-projetos: 

* [XwikiRPC](XwikiRPC): é o projeto principal que implementa todas as funcionalidades para análise de conteúdo gerado em Wikis. Este projeto é independente dos outros e a sua licença é GPL, ou seja, fazendo o checkout apenas deste projeto você terá tudo que precisa para fazer a análise de conteúdo gerado em Wikis. O projeto XwikiRPC utiliza algumas cópias das classes existentes nos outros projetos. De qualquer forma, resolvi liberar os outros projetos também.

* [BrazilianStemmer](BrazilianStemmer): é um projeto que implementa um stemmer para textos em português do Brasil.

* [EntityDescription](EntityDescription): é um projeto que implementa um descritor para entidades recuperadas a partir de consultas feitas no Google utilizando uma API antiga disponibilizada pelo mesmo. Este projeto é dependente do projeto [Frequency](Frequency).

* [Frequency](Frequency): é um projeto que implementa algumas medidas para contagem de frequência de palavras em textos, por exemplo, TF-IDF.

Licença
-------

A licença de todos os projetos citados acima é [GPL](http://www.gnu.org/licenses/gpl.html). 

Contato e informações
---------------------

Mais informações sobre este projeto ou outros podem ser obtidas [aqui](http://fbarth.github.io) ou enviando um e-mail para fabricio dot barth at gmail dot com. Além disso, [este artigo](http://fbarth.github.io/docs/bar2010.pdf), publicado no VII Simpósio Brasileiro de Sistemas Colaborativos, descreve os algoritmos utilizados para a identificação de grupos em Wikis.  
