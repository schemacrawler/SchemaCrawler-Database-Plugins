/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2022, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/

package schemacrawler.tools.timesten;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import schemacrawler.schemacrawler.DatabaseServerType;
import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;

public final class TimesTenDatabaseConnector
  extends DatabaseConnector
{

  private static final Logger LOGGER =
    Logger.getLogger(TimesTenDatabaseConnector.class.getName());

  public TimesTenDatabaseConnector()
    throws IOException
  {
    super(new DatabaseServerType("timesten", "Oracle TimesTen"),
          url -> url != null && url.startsWith("jdbc:timesten:"),
          (informationSchemaViewsBuilder, connection) -> informationSchemaViewsBuilder.fromResourceFolder(
            "/timesten.information_schema"),
          (schemaRetrievalOptionsBuilder, connection) -> {},
          (limitOptionsBuilder) -> {},
          () -> DatabaseConnectionSourceBuilder.builder(
              "jdbc:timesten:client:dsn=${database};TTC_SERVER=${host};TCP_PORT=${port};")
              .withDefaultPort(53397));
    LOGGER.log(Level.INFO, "Loaded commandline for Oracle TimesTen");
  }

  @Override
  public PluginCommand getHelpCommand()
  {
    final PluginCommand pluginCommand = super.getHelpCommand();
    pluginCommand
      .addOption("server",
                 String.class,
                 "--server=timesten%n"
         + "Loads SchemaCrawler plug-in for Oracle TimesTen")
      .addOption("host",
                 String.class,
                 "Host name%n" + "Optional, defaults to localhost")
      .addOption("port",
                 Integer.class,
                 "Port number%n" + "Optional, defaults to 53397")
      .addOption("database", String.class, "DSN name");
    return pluginCommand;
  }
  
}
