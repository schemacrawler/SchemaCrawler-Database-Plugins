/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2023, Sualeh Fatehi <sualeh@hotmail.com>.
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

package schemacrawler.server.access;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import schemacrawler.schemacrawler.DatabaseServerType;
import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;

public final class AccessDatabaseConnector extends DatabaseConnector {

  private static final Logger LOGGER = Logger.getLogger(AccessDatabaseConnector.class.getName());

  public AccessDatabaseConnector() {
    super(
        new DatabaseServerType("access", "Microsoft Access"),
        url -> url != null && Pattern.compile("jdbc:.*access:.*").matcher(url).matches(),
        (informationSchemaViewsBuilder, connection) -> {},
        (schemaRetrievalOptionsBuilder, connection) -> {},
        limitOptionsBuilder -> {},
        () ->
            DatabaseConnectionSourceBuilder.builder(
                    "jdbc:ucanaccess://${database};showSchema=true;sysSchema=true")
                .withDefaultPort(1527));
    LOGGER.log(Level.INFO, "Loaded commandline for Microsoft Access");
  }

  @Override
  public PluginCommand getHelpCommand() {
    final PluginCommand pluginCommand = super.getHelpCommand();
    pluginCommand
        .addOption(
            "server",
            String.class,
            "--server=access%n" + "Loads SchemaCrawler plug-in for Microsoft Access")
        .addOption("database", String.class, "Database file");
    return pluginCommand;
  }
}
