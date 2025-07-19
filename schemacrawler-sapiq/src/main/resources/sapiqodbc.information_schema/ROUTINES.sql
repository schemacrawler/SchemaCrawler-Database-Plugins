-- SchemaCrawler
-- http://www.schemacrawler.com
-- Copyright (c) 2000-2025, Sualeh Fatehi <sualeh@hotmail.com>.
-- All rights reserved.
-- SPDX-License-Identifier: EPL-2.0

-- catalogs not supported in IQ ODBC Driver
select NULL AS ROUTINE_CATALOG, trim(usr.user_name) AS ROUTINE_SCHEMA, trim(prc.proc_name) AS ROUTINE_NAME, 'SQL' AS ROUTINE_BODY, trim(prc.source) AS ROUTINE_DEFINITION
from sys.SYSPROCEDURE prc
inner join sys.SYSUSER usr on usr.user_id = prc.creator
