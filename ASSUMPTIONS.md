To better split and organize what needs to be done I divided the functionalities into 3 epics: `Oscar Winners`, `Rating` and `Top Rated Movie Search`. This can be followed by this [Trello board](https://trello.com/b/CP3SrUvF/backbase-challenge). Also, an epic called `General` was created to accommodate topics useful for the whole application, such as `security`, `CI/CD` and things like that.

## Assumptions

### Movie information and Oscar winners

The [CSV](src/main/resources/data/academy_awards.csv) file already gives the nominated movies (winner and losers), 
so at this point it is not necessary to connect to `OMDb API` to retrieve the nominated and winner movies. If a movie is
not found on `oscar_nominations` table an exception is retrieved, otherwise, information about movie the movie nominations
will be the result

### Rating

For the rating API it will be the first time that `OMDb API` will be used. Once the information from there hardly will change,
I will cache that search, then preventing to go many times to the API and never (or hardly) extrapolate the limit of searches
to it.

Also, once it brings information from the API I will save it on DB to be able to have the movie `Box Office Value` and make a relation
between `movie` and `rating` tables.

---

[Return to initial README](README.md)