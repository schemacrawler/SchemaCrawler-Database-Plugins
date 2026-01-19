/*
 * SchemaCrawler
 * http://www.schemacrawler.com
 * Copyright (c) 2000-2026, Sualeh Fatehi <sualeh@hotmail.com>.
 * All rights reserved.
 * SPDX-License-Identifier: EPL-2.0
 */

package schemacrawler.server.sapiq;

import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.databaseconnector.DatabaseConnectorOptions;
import schemacrawler.tools.databaseconnector.DatabaseConnectorOptionsBuilder;
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;
import us.fatehi.utility.datasource.DatabaseServerType;

public final class SAPIQOdbcDatabaseConnector extends DatabaseConnector {

  private static DatabaseConnectorOptions databaseConnectorOptions() {
    final DatabaseServerType dbServerType = new DatabaseServerType("sapiq", "SAP IQ");

    final DatabaseConnectionSourceBuilder connectionSourceBuilder =
        DatabaseConnectionSourceBuilder.builder("jdbc:sybase:Tds:${host}:${port}")
            .withDefaultPort(50000);

    final PluginCommand pluginCommand = PluginCommand.newDatabasePluginCommand(dbServerType);
    pluginCommand
        .addOption(
            "server", String.class, "--server=sqpiq%n" + "Loads SchemaCrawler plug-in for SAP IQ")
        .addOption("host", String.class, "Host name%n" + "Optional, defaults to localhost")
        .addOption("port", Integer.class, "Port number%n" + "Optional, defaults to 50000")
        .addOption("database", String.class, "Database name");

    return DatabaseConnectorOptionsBuilder.builder(dbServerType)
        .withHelpCommand(pluginCommand)
        .withUrlSupportPredicate(url -> true)
        .withInformationSchemaViewsFromResourceFolder("/sapiqodbc.information_schema")
        .withSchemaRetrievalOptionsBuilder(
            (schemaRetrievalOptionsBuilder, connection) ->
                schemaRetrievalOptionsBuilder.withDoesNotSupportCatalogs())
        .withDatabaseConnectionSourceBuilder(() -> connectionSourceBuilder)
        .build();
  }

  public SAPIQOdbcDatabaseConnector() {
    super(databaseConnectorOptions());
  }
}
