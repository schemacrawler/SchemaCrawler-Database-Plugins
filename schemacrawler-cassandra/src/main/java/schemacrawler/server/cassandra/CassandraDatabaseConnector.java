/*
 * SchemaCrawler
 * http://www.schemacrawler.com
 * Copyright (c) 2000-2025, Sualeh Fatehi <sualeh@hotmail.com>.
 * All rights reserved.
 * SPDX-License-Identifier: EPL-2.0
 */

package schemacrawler.server.cassandra;

import static schemacrawler.schemacrawler.MetadataRetrievalStrategy.none;
import static schemacrawler.schemacrawler.SchemaInfoMetadataRetrievalStrategy.foreignKeysRetrievalStrategy;
import static schemacrawler.schemacrawler.SchemaInfoMetadataRetrievalStrategy.functionsRetrievalStrategy;
import static schemacrawler.schemacrawler.SchemaInfoMetadataRetrievalStrategy.proceduresRetrievalStrategy;

import java.util.logging.Level;
import java.util.logging.Logger;
import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;
import us.fatehi.utility.datasource.DatabaseServerType;

public final class CassandraDatabaseConnector extends DatabaseConnector {

  private static final Logger LOGGER = Logger.getLogger(CassandraDatabaseConnector.class.getName());

  public CassandraDatabaseConnector() {
    super(
        new DatabaseServerType("cassandra", "Cassandra"),
        url -> url != null && url.startsWith("jdbc:cassandra:"),
        (informationSchemaViewsBuilder, connection) -> {},
        (schemaRetrievalOptionsBuilder, connection) ->
            schemaRetrievalOptionsBuilder
                .with(foreignKeysRetrievalStrategy, none)
                .with(proceduresRetrievalStrategy, none)
                .with(functionsRetrievalStrategy, none),
        limitOptionsBuilder -> {},
        () ->
            DatabaseConnectionSourceBuilder.builder("jdbc:cassandra://${host}:${port}/${database}")
                .withDefaultPort(9042));
    LOGGER.log(Level.INFO, "Loaded commandline for Cassandra");
  }

  @Override
  public PluginCommand getHelpCommand() {
    final PluginCommand pluginCommand = super.getHelpCommand();
    pluginCommand
        .addOption(
            "server",
            String.class,
            "--server=cassandra%n" + "Loads SchemaCrawler plug-in for Cassandra")
        .addOption(
            "host",
            String.class,
            "Host name%n"
                + "Optional, defaults to localhost%n"
                + "Can be a list of hosts separated with '--'%n"
                + "See https://github.com/ing-bank/cassandra-jdbc-wrapper")
        .addOption("port", Integer.class, "Port number%n" + "Optional, defaults to 9042")
        .addOption("database", String.class, "Keyspace name");
    return pluginCommand;
  }
}
