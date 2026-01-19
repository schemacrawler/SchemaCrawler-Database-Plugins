/*
 * SchemaCrawler
 * http://www.schemacrawler.com
 * Copyright (c) 2000-2026, Sualeh Fatehi <sualeh@hotmail.com>.
 * All rights reserved.
 * SPDX-License-Identifier: EPL-2.0
 */

package schemacrawler.server.access;

import static schemacrawler.tools.executable.commandline.PluginCommand.newDatabasePluginCommand;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.databaseconnector.DatabaseConnectorOptions;
import schemacrawler.tools.databaseconnector.DatabaseConnectorOptionsBuilder;
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;
import us.fatehi.utility.datasource.DatabaseServerType;

public final class AccessDatabaseConnector extends DatabaseConnector {

  private static final Logger LOGGER = Logger.getLogger(AccessDatabaseConnector.class.getName());

  private static DatabaseConnectorOptions databaseConnectorOptions() {
    final DatabaseServerType dbServerType = new DatabaseServerType("access", "Microsoft Access");

    final DatabaseConnectionSourceBuilder connectionSourceBuilder =
        DatabaseConnectionSourceBuilder.builder(
                "jdbc:ucanaccess://${database};showSchema=true;sysSchema=true")
            .withDefaultPort(1527);

    final PluginCommand pluginCommand = newDatabasePluginCommand(dbServerType);
    pluginCommand
        .addOption(
            "server",
            String.class,
            "--server=access%n" + "Loads SchemaCrawler plug-in for Microsoft Access")
        .addOption("database", String.class, "Database file");

    return DatabaseConnectorOptionsBuilder.builder(dbServerType)
        .withHelpCommand(pluginCommand)
        .withUrlSupportPredicate(
            url -> url != null && Pattern.compile("jdbc:.*access:.*").matcher(url).matches())
        .withDatabaseConnectionSourceBuilder(() -> connectionSourceBuilder)
        .build();
  }

  public AccessDatabaseConnector() {
    super(databaseConnectorOptions());
    LOGGER.log(Level.INFO, "Loaded commandline for Microsoft Access");
  }
}
