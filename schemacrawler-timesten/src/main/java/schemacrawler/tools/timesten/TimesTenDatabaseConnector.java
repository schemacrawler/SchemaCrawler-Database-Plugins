/*
 * SchemaCrawler
 * http://www.schemacrawler.com
 * Copyright (c) 2000-2026, Sualeh Fatehi <sualeh@hotmail.com>.
 * All rights reserved.
 * SPDX-License-Identifier: EPL-2.0
 */

package schemacrawler.tools.timesten;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.databaseconnector.DatabaseConnectorOptions;
import schemacrawler.tools.databaseconnector.DatabaseConnectorOptionsBuilder;
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;
import us.fatehi.utility.datasource.DatabaseServerType;

public final class TimesTenDatabaseConnector extends DatabaseConnector {

  private static final Logger LOGGER = Logger.getLogger(TimesTenDatabaseConnector.class.getName());

  private static DatabaseConnectorOptions databaseConnectorOptions() {
    final DatabaseServerType dbServerType = new DatabaseServerType("timesten", "Oracle TimesTen");

    final DatabaseConnectionSourceBuilder connectionSourceBuilder =
        DatabaseConnectionSourceBuilder.builder(
                "jdbc:timesten:client:dsn=${database};TTC_SERVER=${host};TCP_PORT=${port};")
            .withDefaultPort(53397);

    final PluginCommand pluginCommand = PluginCommand.newDatabasePluginCommand(dbServerType);
    pluginCommand
        .addOption(
            "server",
            String.class,
            "--server=timesten%n" + "Loads SchemaCrawler plug-in for Oracle TimesTen")
        .addOption("host", String.class, "Host name%n" + "Optional, defaults to localhost")
        .addOption("port", Integer.class, "Port number%n" + "Optional, defaults to 53397")
        .addOption("database", String.class, "DSN name");

    return DatabaseConnectorOptionsBuilder.builder(dbServerType)
        .withHelpCommand(pluginCommand)
        .withUrlStartsWith("jdbc:timesten:")
        .withInformationSchemaViewsFromResourceFolder("/timesten.information_schema")
        .withDatabaseConnectionSourceBuilder(() -> connectionSourceBuilder)
        .build();
  }

  public TimesTenDatabaseConnector() throws IOException {
    super(databaseConnectorOptions());
    LOGGER.log(Level.INFO, "Loaded commandline for Oracle TimesTen");
  }
}
