# Udacity - Popular Movies - Stage 1

![alt text](https://raw.githubusercontent.com/tiagofrbarbosa/MovieOps/master/screenshots/cap01.png)
![alt text](https://raw.githubusercontent.com/tiagofrbarbosa/MovieOps/master/screenshots/cap02.png)
![alt text](https://raw.githubusercontent.com/tiagofrbarbosa/MovieOps/master/screenshots/cap03.png)
![alt text](https://raw.githubusercontent.com/tiagofrbarbosa/MovieOps/master/screenshots/cap04.png)

## Project overview

Many of us can make an association with the action of relaxing on the mattress and enjoying 
a movie with friends and family. In this project, you will develop an application that allows 
users to find the most popular movies that are on display. Let's split application development 
into two stages. First, let's talk about Stage 1. At this stage, you'll create the core experience 
of your movie application.

## Your application will:

Present to the user a grid with posters of films in release.
Allow the user to change the sorting order via configuration:
The sort order may be based on the popularity or rating of the movie
Allow the user to touch the poster of a movie and go to a detail screen 
with additional information such as:

- original title
- Movie Poster Image Miniature
- a plot synopsis (called the overview in the api)
- user evaluation (called vote_average in api)
- release date of

## What will I learn after stage 1?

You will get data on the Internet with theMovieDB API.
You will use adapters and custom list layouts to populate listviews.
You will incorporate libraries to simplify the amount of code you need to write


## THEMOVIESDB - API KEY

Add the api key of the movies db at constant API_KEY, line 39 of ActivityFragment

In this line: private static final String API_KEY = "API_KEY_HERE";

Replace API_KEY with your key

## References

### Links:

RecyclerView, Holder, Adapter
- http://mateoj.com/2015/10/06/creating-movies-app-retrofit-picasso-android/
- http://mateoj.com/2015/10/07/creating-movies-app-retrofit-picass-android-part2/

### Books:

Fragments, DebugActivity
- Google Android - Aprenda a criar aplicações para dispositivos móveis com o Android SDK
- 5ª Edição, Autor: Ricardo R. Lecheta, ISBN: 978-85-7522-468-7

SQLite, DAO, Databasehelper
- Google Android - crie aplicações para celulares e tablets
- 1º Edição, Autor: João Bosco Monteiro, ISBN: 978-85-66250-02-2
