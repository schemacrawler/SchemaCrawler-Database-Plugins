/*
 * SchemaCrawler
 * http://www.schemacrawler.com
 * Copyright (c) 2000-2026, Sualeh Fatehi <sualeh@hotmail.com>.
 * All rights reserved.
 * SPDX-License-Identifier: EPL-2.0
 */

package schemacrawler.tools.hana;

import java.util.logging.Level;
import java.util.logging.Logger;

import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.databaseconnector.DatabaseConnectorOptions;
import schemacrawler.tools.databaseconnector.DatabaseConnectorOptionsBuilder;
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;
import us.fatehi.utility.datasource.DatabaseServerType;

public final class HanaDatabaseConnector extends DatabaseConnector {

  private static final Logger LOGGER = Logger.getLogger(HanaDatabaseConnector.class.getName());

  private static DatabaseConnectorOptions databaseConnectorOptions() {
    final DatabaseServerType dbServerType = new DatabaseServerType("hana", "SAP HANA");

    final DatabaseConnectionSourceBuilder connectionSourceBuilder =
        DatabaseConnectionSourceBuilder.builder(
                "jdbc:sap://${host}:${port}/?databaseName=${database}")
            .withDefaultPort(30015);

    final PluginCommand pluginCommand = PluginCommand.newDatabasePluginCommand(dbServerType);
    pluginCommand
        .addOption(
            "server", String.class, "--server=hana%n" + "Loads SchemaCrawler plug-in for SAP HANA")
        .addOption("host", String.class, "Host name%n" + "Optional, defaults to localhost")
        .addOption("port", Integer.class, "Port number%n" + "Optional, defaults to 30015")
        .addOption("database", String.class, "Database name");

    return DatabaseConnectorOptionsBuilder.builder(dbServerType)
        .withHelpCommand(pluginCommand)
        .withUrlStartsWith("jdbc:sap:")
        .withInformationSchemaViewsFromResourceFolder("/hana.information_schema")
        .withDatabaseConnectionSourceBuilder(() -> connectionSourceBuilder)
        .build();
  }

  public HanaDatabaseConnector() {
    super(databaseConnectorOptions());
    LOGGER.log(Level.INFO, "Loaded commandline for SAP HANA");
  }
}
