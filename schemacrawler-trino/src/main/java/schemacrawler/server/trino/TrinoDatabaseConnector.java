/*
 * SchemaCrawler
 * http://www.schemacrawler.com
 * Copyright (c) 2000-2025, Sualeh Fatehi <sualeh@hotmail.com>.
 * All rights reserved.
 * SPDX-License-Identifier: EPL-2.0
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
