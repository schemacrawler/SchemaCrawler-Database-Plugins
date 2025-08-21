/*
 * SchemaCrawler
 * http://www.schemacrawler.com
 * Copyright (c) 2000-2025, Sualeh Fatehi <sualeh@hotmail.com>.
 * All rights reserved.
 * SPDX-License-Identifier: EPL-2.0
 */

package schemacrawler.tools.hana;

import java.util.logging.Level;
import java.util.logging.Logger;
import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;
import us.fatehi.utility.datasource.DatabaseServerType;

public final class HanaDatabaseConnector extends DatabaseConnector {

  private static final Logger LOGGER = Logger.getLogger(HanaDatabaseConnector.class.getName());

  public HanaDatabaseConnector() {
    super(
        new DatabaseServerType("hana", "SAP HANA"),
        url -> url != null && url.startsWith("jdbc:sap:"),
        (informationSchemaViewsBuilder, connection) ->
            informationSchemaViewsBuilder.fromResourceFolder("/hana.information_schema"),
        (schemaRetrievalOptionsBuilder, connection) -> {},
        limitOptionsBuilder -> {},
        () ->
            DatabaseConnectionSourceBuilder.builder(
                    "jdbc:sap://${host}:${port}/?databaseName=${database}")
                .withDefaultPort(30015));
    LOGGER.log(Level.INFO, "Loaded commandline for SAP HANA");
  }

  @Override
  public PluginCommand getHelpCommand() {
    final PluginCommand pluginCommand = super.getHelpCommand();
    pluginCommand
        .addOption(
            "server", String.class, "--server=hana%n" + "Loads SchemaCrawler plug-in for SAP HANA")
        .addOption("host", String.class, "Host name%n" + "Optional, defaults to localhost")
        .addOption("port", Integer.class, "Port number%n" + "Optional, defaults to 30015")
        .addOption("database", String.class, "Database name");
    return pluginCommand;
  }
}
