/*
 * SchemaCrawler
 * http://www.schemacrawler.com
 * Copyright (c) 2000-2025, Sualeh Fatehi <sualeh@hotmail.com>.
 * All rights reserved.
 * SPDX-License-Identifier: EPL-2.0
 */

package schemacrawler.server.derby;

import java.util.logging.Level;
import java.util.logging.Logger;

import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;
import us.fatehi.utility.datasource.DatabaseServerType;

public final class DerbyDatabaseConnector extends DatabaseConnector {

  private static final Logger LOGGER = Logger.getLogger(DerbyDatabaseConnector.class.getName());

  public DerbyDatabaseConnector() {
    super(
        new DatabaseServerType("derby", "Apache Derby"),
        url -> url != null && url.startsWith("jdbc:derby:"),
                (informationSchemaViewsBuilder, connection) ->
        informationSchemaViewsBuilder.fromResourceFolder("/derby.information_schema"),
        (schemaRetrievalOptionsBuilder, connection) -> {},
        limitOptionsBuilder -> {},
        () ->
            DatabaseConnectionSourceBuilder.builder("jdbc:derby://${host}:${port}/${database}")
                .withDefaultPort(1527));
    LOGGER.log(Level.INFO, "Loaded commandline for Derby");
  }

  @Override
  public PluginCommand getHelpCommand() {
    final PluginCommand pluginCommand = super.getHelpCommand();
    pluginCommand
        .addOption(
            "server",
            String.class,
            "--server=derby%n" + "Loads SchemaCrawler plug-in for Apache Derby")
        .addOption("database", String.class, "Database name");
    return pluginCommand;
  }
}
