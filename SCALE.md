### Code Scaling

For the code itself be able to evolve according new requirements come, I opted for `DDD` to model the internal domain and 
isolate the incoming and outgoing of information. In that way, even if infrastructure requirements must change, the core logic
will remain intact.

### Application Scaling

Used `kubernetes` to orchestrate the deploy and define rules for the horizontal pod scaling. In that way, even if millions of users
start using the application, it is possible to respond to the demand. Also, on `kubernetes` it is possible to manage secrets 
(very useful for the `apiKey` from OMDb), but once I didn't deploy the application I didn't see the necessity for that.

`Docker` is being used to host both database and cache locally, but the idea is that I would have a real database instance running on cloud
(and the same for cache in a memory store).

Talking about database connection, once the application is able to scale a connection pool should be configured on the DB side,
but I didn't have time to create a performance test to hold millions of request to the application and check which connection pool, bulkheads
and other configurations could be improved in order to guarantee that everything would be ok even if _"turbulent times"_ kick in