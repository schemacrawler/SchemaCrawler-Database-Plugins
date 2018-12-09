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

package schemacrawler.server.sapiq;


import java.io.IOException;
import java.sql.Connection;

import schemacrawler.schemacrawler.DatabaseServerType;
import schemacrawler.schemacrawler.SchemaRetrievalOptionsBuilder;
import schemacrawler.tools.databaseconnector.DatabaseConnector;
import schemacrawler.tools.iosource.ClasspathInputResource;


public final class SAPIQOdbcDatabaseConnector
  extends DatabaseConnector
{

  public SAPIQOdbcDatabaseConnector()
    throws IOException
  {
    super(new DatabaseServerType("sapiq", "SAP IQ"),
          new ClasspathInputResource("/help/Connections.sapiq.txt"),
          new ClasspathInputResource("/schemacrawler-sapiq.config.properties"),
          (informationSchemaViewsBuilder,
           connection) -> informationSchemaViewsBuilder
             .fromResourceFolder("/sapiqodbc.information_schema"),
          url -> true);
  }

  @Override
  public SchemaRetrievalOptionsBuilder getSchemaRetrievalOptionsBuilder(final Connection connection)
  {
    final SchemaRetrievalOptionsBuilder schemaRetrievalOptionsBuilder = super.getSchemaRetrievalOptionsBuilder(connection);
    // Unlike the regular JDBC driver, catalogs are not supported
    schemaRetrievalOptionsBuilder.withDoesNotSupportCatalogs();
    return schemaRetrievalOptionsBuilder;
  }

}
