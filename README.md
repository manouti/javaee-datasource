
Sample Web app that uses a configured data source in a Payara Server (should also work on GlassFish) runtime.

Requirements:
===============

- Payara Server: https://www.payara.fish/software/downloads/
- JDK supported by Payara.
- A JDBC compliant database. Payara comes with pre-configured H2 and Derby data sources, but you can also add another one as described below.


Configuring a new data source
===============

If you want to use a database that is different than the pre-configured ones, follow the next steps to configure it.

First, the JDBC driver Jar needs to be added to the server in order for it to access the DataSource implementation. To add the Jar to Payara, run the command:

    asadmin add-library path_to_jar

In the Admin Console at http://localhost:4848, go to Resources -> JDBC -> JDBC Connection Pools in the left navigation panel. A list of already existing connection pools exist, e.g. for H2 which comes by default with Payara. Click the New button to create a new Connection Pool.

Give the pool a name and specify the driver vendor. Upon providing the implementation of DataSource, Payara uses it to derive a list of properties to configure it, e.g. the host, port and database, which need to be filled. After filling all needed properties and saving the JDBC Connection Pool, make sure that clicking the Ping button successfully connects to it.

Now that the JDBC Connection Pool is created, we can register a JNDI binding for the DataSource, by going to Resources -> JDBC -> JDBC Resources, and selecting New. The JDBC Resource must reference the Pool just created and have a unique name. Make sure the JNDI name matches the one used in the code (`java/myapp/jdbc_ds`).

