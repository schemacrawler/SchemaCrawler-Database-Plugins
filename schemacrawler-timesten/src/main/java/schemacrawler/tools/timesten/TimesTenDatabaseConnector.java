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
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;
import us.fatehi.utility.datasource.DatabaseServerType;

public final class TimesTenDatabaseConnector extends DatabaseConnector {

  private static final Logger LOGGER = Logger.getLogger(TimesTenDatabaseConnector.class.getName());

  public TimesTenDatabaseConnector() throws IOException {
    super(
        new DatabaseServerType("timesten", "Oracle TimesTen"),
        url -> url != null && url.startsWith("jdbc:timesten:"),
        (informationSchemaViewsBuilder, connection) ->
            informationSchemaViewsBuilder.fromResourceFolder("/timesten.information_schema"),
        (schemaRetrievalOptionsBuilder, connection) -> {},
        (limitOptionsBuilder) -> {},
        () ->
            DatabaseConnectionSourceBuilder.builder(
                    "jdbc:timesten:client:dsn=${database};TTC_SERVER=${host};TCP_PORT=${port};")
                .withDefaultPort(53397));
    LOGGER.log(Level.INFO, "Loaded commandline for Oracle TimesTen");
  }

  @Override
  public PluginCommand getHelpCommand() {
    final PluginCommand pluginCommand = super.getHelpCommand();
    pluginCommand
        .addOption(
            "server",
            String.class,
            "--server=timesten%n" + "Loads SchemaCrawler plug-in for Oracle TimesTen")
        .addOption("host", String.class, "Host name%n" + "Optional, defaults to localhost")
        .addOption("port", Integer.class, "Port number%n" + "Optional, defaults to 53397")
        .addOption("database", String.class, "DSN name");
    return pluginCommand;
  }
}
