-- SchemaCrawler
-- http://www.schemacrawler.com
-- Copyright (c) 2000-2025, Sualeh Fatehi <sualeh@hotmail.com>.
-- All rights reserved.
-- SPDX-License-Identifier: EPL-2.0

SELECT
  NULL AS CATALOG_NAME,  -- catalogs not supported in IQ ODBC Driver
  TRIM(USER_NAME) 
AS 
  SCHEMA_NAME FROM SYS.SYSUSER
