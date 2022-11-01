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

### Movies search

For movies search (the one that returns ordered by average rating and box value) I decided to introduce paging and slicing.
It goes aligned a bit with my concept of code scalability but also because "bring ten" sounds a bit arbitrary, but bring 10 as default
sounds more ok-ish (of course I would question it more in a real world, but once I must create assumptions, that was mine).

That being said, also validations were added for these filters to prevent heavy data processing (like top 100000000 movies rated). 
Only will be returned rated movies. If no movies are rated, then an empty list will be returned

---

[Return to initial README](README.md)