/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2023, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/

package schemacrawler.server.sapiq;

import schemacrawler.schemacrawler.DatabaseServerType;
import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.executable.commandline.PluginCommand;
import us.fatehi.utility.datasource.DatabaseConnectionSourceBuilder;

public final class SAPIQOdbcDatabaseConnector extends DatabaseConnector {

  public SAPIQOdbcDatabaseConnector() {
    super(
        new DatabaseServerType("sapiq", "SAP IQ"),
        url -> true,
        (informationSchemaViewsBuilder, connection) ->
            informationSchemaViewsBuilder.fromResourceFolder("/sapiqodbc.information_schema"),
        (schemaRetrievalOptionsBuilder, connection) ->
            schemaRetrievalOptionsBuilder.withDoesNotSupportCatalogs(),
        limitOptionsBuilder -> {},
        () ->
            DatabaseConnectionSourceBuilder.builder("jdbc:sybase:Tds:${host}:${port}")
                .withDefaultPort(50000));
  }

  @Override
  public PluginCommand getHelpCommand() {
    final PluginCommand pluginCommand = super.getHelpCommand();
    pluginCommand
        .addOption(
            "server", String.class, "--server=sqpiq%n" + "Loads SchemaCrawler plug-in for SAP IQ")
        .addOption("host", String.class, "Host name%n" + "Optional, defaults to localhost")
        .addOption("port", Integer.class, "Port number%n" + "Optional, defaults to 50000")
        .addOption("database", String.class, "Database name");
    return pluginCommand;
  }
}
