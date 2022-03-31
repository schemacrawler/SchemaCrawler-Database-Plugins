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
package schemacrawler.integration.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static schemacrawler.test.utility.ExecutableTestUtility.executableExecution;
import static schemacrawler.test.utility.FileHasContent.classpathResource;
import static schemacrawler.test.utility.FileHasContent.hasSameContentAs;
import static schemacrawler.test.utility.FileHasContent.outputOf;
import static schemacrawler.test.utility.TestUtility.javaVersion;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.teiid.adminapi.impl.ModelMetaData;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.jdbc.hsql.HsqlExecutionFactory;

import schemacrawler.schemacrawler.LoadOptionsBuilder;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder;
import schemacrawler.test.utility.BaseAdditionalDatabaseTest;
import schemacrawler.tools.command.text.schema.options.SchemaTextOptions;
import schemacrawler.tools.command.text.schema.options.SchemaTextOptionsBuilder;
import schemacrawler.tools.executable.SchemaCrawlerExecutable;

@TestInstance(Lifecycle.PER_CLASS)
public class TeiidTest extends BaseAdditionalDatabaseTest {

  private Connection connection;

  @BeforeAll
  public void createDatabase() throws Exception {

    final Properties props = System.getProperties();
    props.setProperty(
        "org.teiid.translator.jdbc.useFullSchemaNameDefault", Boolean.FALSE.toString());

    final EmbeddedServer server = new EmbeddedServer();
    final EmbeddedConfiguration config = new EmbeddedConfiguration();

    config.setUseDisk(false);
    server.start(config);

    final JDBCDataSource hsqlDataSource = new JDBCDataSource();
    hsqlDataSource.setDatabase("jdbc:hsqldb:mem:test");
    createTestDatabase(hsqlDataSource.getConnection());
    server.addConnectionFactory("java:/hsql", hsqlDataSource);

    final HsqlExecutionFactory hsqlExecutionFactory = new HsqlExecutionFactory();
    hsqlExecutionFactory.start();
    server.addTranslator("hsql-translator", hsqlExecutionFactory);

    final ModelMetaData hsqldbModel = new ModelMetaData();
    hsqldbModel.setName("HSQLDB");
    hsqldbModel.addSourceMapping("HSQLDB", "hsql-translator", "java:/hsql");
    hsqldbModel.addProperty("importer.schemaPattern", "PUBLIC");
    hsqldbModel.addProperty("importer.tableTypes", "TABLE");

    final ModelMetaData virtualModel = new ModelMetaData();
    virtualModel.setName("VIRTUAL");
    virtualModel.setModelType("VIRTUAL");
    virtualModel.addSourceMetadata(
        "DDL",
        "CREATE VIEW REGISTERED_USERS "
            + "("
            + " FIRST_NAME VARCHAR(255), "
            + " LAST_NAME VARCHAR(255)"
            + ") AS SELECT FIRST_NAME, LAST_NAME FROM REGISTRATION");

    server.deployVDB("TEEID_VDB", hsqldbModel, virtualModel);

    connection = server.getDriver().connect("jdbc:teiid:TEEID_VDB", null);
  }

  @Test
  public void testTeiidDump() throws Exception {

    final SchemaTextOptionsBuilder textOptionsBuilder = SchemaTextOptionsBuilder.builder();
    textOptionsBuilder.noInfo();
    final SchemaTextOptions textOptions = textOptionsBuilder.toOptions();

    final SchemaCrawlerExecutable executable = new SchemaCrawlerExecutable("dump");
    executable.setAdditionalConfiguration(SchemaTextOptionsBuilder.builder(textOptions).toConfig());

    final String expectedResource = "testTeiidDump.txt";
    assertThat(
        outputOf(executableExecution(connection, executable)),
        hasSameContentAs(classpathResource(expectedResource)));
  }

  @Test
  public void testTeiidWithConnection() throws Exception {

    final LoadOptionsBuilder loadOptionsBuilder =
        LoadOptionsBuilder.builder().withSchemaInfoLevel(SchemaInfoLevelBuilder.maximum());
    final SchemaCrawlerOptions schemaCrawlerOptions =
        SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions()
            .withLoadOptions(loadOptionsBuilder.toOptions());
    final SchemaTextOptionsBuilder textOptionsBuilder = SchemaTextOptionsBuilder.builder();
    textOptionsBuilder.showDatabaseInfo().showJdbcDriverInfo();
    final SchemaTextOptions textOptions = textOptionsBuilder.toOptions();

    final SchemaCrawlerExecutable executable = new SchemaCrawlerExecutable("details");
    executable.setSchemaCrawlerOptions(schemaCrawlerOptions);
    executable.setAdditionalConfiguration(SchemaTextOptionsBuilder.builder(textOptions).toConfig());

    final String expectedResource = String.format("testTeiidWithConnection.%s.txt", javaVersion());
    assertThat(
        outputOf(executableExecution(connection, executable)),
        hasSameContentAs(classpathResource(expectedResource)));
  }

  private void createTestDatabase(final Connection connection) throws SQLException {
    try (final Statement stmt = connection.createStatement()) {
      stmt.executeUpdate(
          "CREATE TABLE REGISTRATION "
              + "("
              + " ID INTEGER IDENTITY PRIMARY KEY, "
              + " FIRST_NAME VARCHAR(255), "
              + " LAST_NAME VARCHAR(255), "
              + " AGE INTEGER"
              + ")");
      stmt.executeUpdate(
          "INSERT INTO REGISTRATION(FIRST_NAME, LAST_NAME, AGE) VALUES('Sualeh', 'Fatehi', 42)");
    }
  }
}
