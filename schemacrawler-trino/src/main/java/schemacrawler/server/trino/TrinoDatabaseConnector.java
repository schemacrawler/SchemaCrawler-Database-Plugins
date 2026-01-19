/*
 * SchemaCrawler
 * http://www.schemacrawler.com
 * Copyright (c) 2000-2026, Sualeh Fatehi <sualeh@hotmail.com>.
 * All rights reserved.
 * SPDX-License-Identifier: EPL-2.0
 */

package schemacrawler.server.trino;

import static schemacrawler.schemacrawler.MetadataRetrievalStrategy.none;
import static schemacrawler.schemacrawler.SchemaInfoMetadataRetrievalStrategy.indexesRetrievalStrategy;

import java.util.logging.Level;
import java.util.logging.Logger;
import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.databaseconnector.DatabaseConnectorOptions;
import schemacrawler.tools.databaseconnector.DatabaseConnectorOptionsBuilder;
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;
import us.fatehi.utility.datasource.DatabaseServerType;

public final class TrinoDatabaseConnector extends DatabaseConnector {

  private static final Logger LOGGER = Logger.getLogger(TrinoDatabaseConnector.class.getName());

  private static DatabaseConnectorOptions databaseConnectorOptions() {
    final DatabaseServerType dbServerType = new DatabaseServerType("trino", "Trino");

    final DatabaseConnectionSourceBuilder connectionSourceBuilder =
        DatabaseConnectionSourceBuilder.builder("jdbc:trino://${host}:${port}/${database}")
            .withDefaultPort(8080);

    final PluginCommand pluginCommand = PluginCommand.newDatabasePluginCommand(dbServerType);
    pluginCommand
        .addOption(
            "server", String.class, "--server=trino%n" + "Loads SchemaCrawler plug-in for Trino")
        .addOption("database", String.class, "Database name");

    return DatabaseConnectorOptionsBuilder.builder(dbServerType)
        .withHelpCommand(pluginCommand)
        .withUrlStartsWith("jdbc:trino:")
        .withSchemaRetrievalOptionsBuilder(
            (schemaRetrievalOptionsBuilder, connection) ->
                schemaRetrievalOptionsBuilder.with(indexesRetrievalStrategy, none))
        .withDatabaseConnectionSourceBuilder(() -> connectionSourceBuilder)
        .build();
  }

  public TrinoDatabaseConnector() {
    super(databaseConnectorOptions());
    LOGGER.log(Level.INFO, "Loaded commandline for Trino");
  }
}
