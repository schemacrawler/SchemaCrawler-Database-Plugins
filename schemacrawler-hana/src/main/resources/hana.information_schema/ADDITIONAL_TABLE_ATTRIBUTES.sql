-- SchemaCrawler
-- http://www.schemacrawler.com
-- Copyright (c) 2000-2025, Sualeh Fatehi <sualeh@hotmail.com>.
-- All rights reserved.
-- SPDX-License-Identifier: EPL-2.0

SELECT
  NULL AS TABLE_CATALOG,
  TABLES.SCHEMA_NAME AS TABLE_SCHEMA,
  TABLES.TABLE_NAME,
  TABLES.*
FROM
  SYS.TABLES
ORDER BY
  TABLES.SCHEMA_NAME,
  TABLES.TABLE_NAME
