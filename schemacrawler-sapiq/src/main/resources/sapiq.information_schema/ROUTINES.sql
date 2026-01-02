-- SchemaCrawler
-- http://www.schemacrawler.com
-- Copyright (c) 2000-2026, Sualeh Fatehi <sualeh@hotmail.com>.
-- All rights reserved.
-- SPDX-License-Identifier: EPL-2.0

SELECT
  DB_NAME() AS ROUTINE_CATALOG,
  trim(usr.user_name) AS ROUTINE_SCHEMA,
  trim(prc.proc_name) AS ROUTINE_NAME,
  'SQL' AS ROUTINE_BODY,
  trim(prc.source) AS ROUTINE_DEFINITION
FROM
  sys.SYSPROCEDURE prc
  INNER JOIN sys.SYSUSER usr
    ON usr.user_id = prc.creator
