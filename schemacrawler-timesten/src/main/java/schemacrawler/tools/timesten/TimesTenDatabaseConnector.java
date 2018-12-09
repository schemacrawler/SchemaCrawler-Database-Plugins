/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2019, Sualeh Fatehi <sualeh@hotmail.com>.
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

package schemacrawler.tools.timesten;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import schemacrawler.schemacrawler.DatabaseServerType;
import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.iosource.ClasspathInputResource;

public final class TimesTenDatabaseConnector
  extends DatabaseConnector
{

  private static final Logger LOGGER = Logger
    .getLogger(TimesTenDatabaseConnector.class.getName());

  public TimesTenDatabaseConnector()
    throws IOException
  {
    super(new DatabaseServerType("timesten", "Oracle TimesTen"),
          new ClasspathInputResource("/help/Connections.timesten.txt"),
          new ClasspathInputResource("/schemacrawler-timesten.config.properties"),
          (informationSchemaViewsBuilder,
           connection) -> informationSchemaViewsBuilder
             .fromResourceFolder("/timesten.information_schema"),
          url -> Pattern.matches("jdbc:timesten:.*", url));
    LOGGER.log(Level.INFO, "Loaded plugin for Oracle TimesTen");
  }

}
