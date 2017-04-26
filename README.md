# Visão geral do projeto
Muitos de nós podem fazer uma associação com a ação de relaxar no colchão e curtir um filme com amigos e a família. Neste projeto, você desenvolverá um aplicativo que permite aos usuários encontrar os filmes mais populares que estão em exibição. Vamos dividir o desenvolvimento do aplicativo em dois estágios. Primeiro, vamos falar sobre o estágio 1. Nesse estágio, você criará a experiência central de seu aplicativo de filmes.

## Seu aplicativo vai:

Apresentar ao usuário uma grade com cartazes de filmes em lançamento.
Permitir que o usuário altere a ordem de classificação via configuração:
A ordem de classificação pode ser baseada na popularidade ou na avaliação do filme
Permitir que o usuário toque no cartaz de um filme e passe para uma tela de detalhes com informações adicionais, como:
título original
miniatura da imagem do cartaz do filme
uma sinopse da trama (chamada de visão geral na api)
avaliação do usuário (chamada vote_average na api)
data de lançamento

## O que eu vou aprender depois do estágio 1?
Você obterá dados na Internet com a API theMovieDB.
Você usará adaptadores e layouts de lista personalizados para preencher listviews.
Você vai incorporar bibliotecas para simplificar a quantidade de código que é necessário escrever


# THEMOVIESDB - APP KEY

Adicionar a api key do the movies db no metódo RetroMovies na MainActivity na linha 183

Nesta linha: request.addEncodedQueryParam("api_key","APP_KEY");

subistituir APP_KEY pela sua key



Add the api key of the movies db in the method RetroMovies in the MainActivity in line 183

In this line: request.addEncodedQueryParam ("api_key", "APP_KEY");

Replace APP_KEY with your key

Este projeto teve como base o projeto desenvolvido por Jose Mateo:


http://mateoj.com/2015/10/06/creating-movies-app-retrofit-picasso-android/


http://mateoj.com/2015/10/07/creating-movies-app-retrofit-picass-android-part2/