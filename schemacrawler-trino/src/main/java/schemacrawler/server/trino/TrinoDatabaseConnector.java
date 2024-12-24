/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2025, Sualeh Fatehi <sualeh@hotmail.com>.
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

package schemacrawler.server.trino;

import static schemacrawler.schemacrawler.MetadataRetrievalStrategy.none;
import static schemacrawler.schemacrawler.SchemaInfoMetadataRetrievalStrategy.indexesRetrievalStrategy;

import java.util.logging.Level;
import java.util.logging.Logger;

import schemacrawler.schemacrawler.DatabaseServerType;
import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;

public final class TrinoDatabaseConnector extends DatabaseConnector {

  private static final Logger LOGGER = Logger.getLogger(TrinoDatabaseConnector.class.getName());

  public TrinoDatabaseConnector() {
    super(
        new DatabaseServerType("trino", "Trino"),
        url -> url != null && url.startsWith("jdbc:trino:"),
        (informationSchemaViewsBuilder, connection) -> {},
        (schemaRetrievalOptionsBuilder, connection) ->
            schemaRetrievalOptionsBuilder.with(indexesRetrievalStrategy, none),
        limitOptionsBuilder -> {},
        () ->
            DatabaseConnectionSourceBuilder.builder("jdbc:trino://${host}:${port}/${database}")
                .withDefaultPort(8080));
    LOGGER.log(Level.INFO, "Loaded commandline for Trino");
  }

  @Override
  public PluginCommand getHelpCommand() {
    final PluginCommand pluginCommand = super.getHelpCommand();
    pluginCommand
        .addOption(
            "server", String.class, "--server=trino%n" + "Loads SchemaCrawler plug-in for Trino")
        .addOption("database", String.class, "Database name");
    return pluginCommand;
  }
}
