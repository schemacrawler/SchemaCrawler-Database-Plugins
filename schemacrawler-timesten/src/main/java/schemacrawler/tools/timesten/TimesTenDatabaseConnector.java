package schemacrawler.tools.timesten;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.databaseconnector.DatabaseServerType;
import schemacrawler.tools.iosource.ClasspathInputResource;

public final class TimesTenDatabaseConnector
  extends DatabaseConnector
{

  private static final Logger LOGGER = Logger
    .getLogger(TimesTenDatabaseConnector.class.getName());

  public TimesTenDatabaseConnector()
    throws IOException
  {
    super(new DatabaseServerType("timesten", "Oracle TimesTen"),
          new ClasspathInputResource("/help/Connections.timesten.txt"),
          new ClasspathInputResource("/schemacrawler-timesten.config.properties"),
          (informationSchemaViewsBuilder,
           connection) -> informationSchemaViewsBuilder
             .fromResourceFolder("/timesten.information_schema"),
          url -> Pattern.matches("jdbc:timesten:.*", url));
    LOGGER.log(Level.INFO, "Loaded plugin for Oracle TimesTen");
  }

}
