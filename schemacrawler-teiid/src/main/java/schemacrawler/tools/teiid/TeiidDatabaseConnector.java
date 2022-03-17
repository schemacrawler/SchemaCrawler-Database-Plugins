/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2022, Sualeh Fatehi <sualeh@hotmail.com>.
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

package schemacrawler.tools.teiid;

import java.util.logging.Level;
import java.util.logging.Logger;

import schemacrawler.schemacrawler.DatabaseServerType;
import schemacrawler.tools.databaseconnector.DatabaseConnectionUrlBuilder;
import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.executable.commandline.PluginCommand;

public final class TeiidDatabaseConnector extends DatabaseConnector {

  private static final Logger LOGGER = Logger.getLogger(TeiidDatabaseConnector.class.getName());

  public TeiidDatabaseConnector() {
    super(
        new DatabaseServerType("teiid", "Teiid"),
        url -> url != null && url.startsWith("jdbc:teiid:"),
        (informationSchemaViewsBuilder, connection) -> {},
        (schemaRetrievalOptionsBuilder, connection) -> {},
        limitOptionsBuilder -> {},
        () -> DatabaseConnectionUrlBuilder.builder("jdbc:teiid://${host}:${database}"));
    LOGGER.log(Level.INFO, "Loaded commandline for Teiid");
  }

  @Override
  public PluginCommand getHelpCommand() {
    final PluginCommand pluginCommand = super.getHelpCommand();
    pluginCommand
        .addOption(
            "server", String.class, "--server=teiid%n" + "Loads SchemaCrawler plug-in for Teiid")
        .addOption("host", String.class, "Host name%n" + "Unused")
        .addOption("port", Integer.class, "Port number%n" + "Unused")
        .addOption("database", String.class, "Virtual database name");
    return pluginCommand;
  }
}
