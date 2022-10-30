To better split and organize what needs to be done I divided the functionalities into 3 epics: `Oscar Winners`, `Rating` and `Top Rated Movie Search`. This can be followed by this [Trello board](https://trello.com/b/CP3SrUvF/backbase-challenge). Also, an epic called `General` was created to accommodate topics useful for the whole application, such as `security`, `CI/CD` and things like that.

## Assumptions

### Movie information and Oscar winners

The [CSV](src/main/resources/data/academy_awards.csv) file already gives the nominated movies (winner and losers), 
so even if the integration with OMDb fails, I can retrieve the most essential information, which is know if some movie won
on the given category.

With that on mind I set to search for the Oscar Nomination directly as a fallback when integration with OMDb fails (not adding to cache tho). 
In case the integration goes fine, I save the movie data on DB to be able to retrieve box value in a search joining with `oscar_nomination` table.

