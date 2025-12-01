/*
 * SchemaCrawler
 * http://www.schemacrawler.com
 * Copyright (c) 2000-2026, Sualeh Fatehi <sualeh@hotmail.com>.
 * All rights reserved.
 * SPDX-License-Identifier: EPL-2.0
 */

package schemacrawler.server.access;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;
import us.fatehi.utility.datasource.DatabaseServerType;

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
