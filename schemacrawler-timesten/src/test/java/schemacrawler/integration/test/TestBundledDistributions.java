package schemacrawler.integration.test;


import static org.junit.Assert.assertTrue;

import org.junit.Test;

import schemacrawler.test.utility.BaseSchemaCrawlerTest;
import schemacrawler.tools.databaseconnector.DatabaseConnectorRegistry;

public class TestBundledDistributions
  extends BaseSchemaCrawlerTest
{

  @Test
  public void testPlugin_timesten()
    throws Exception
  {
    final DatabaseConnectorRegistry registry = new DatabaseConnectorRegistry();
    assertTrue(registry.hasDatabaseSystemIdentifier("timesten"));
  }

}
