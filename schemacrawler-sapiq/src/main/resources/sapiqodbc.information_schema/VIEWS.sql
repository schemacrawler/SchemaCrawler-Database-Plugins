-- SchemaCrawler
-- http://www.schemacrawler.com
-- Copyright (c) 2000-2025, Sualeh Fatehi <sualeh@hotmail.com>.
-- All rights reserved.
-- SPDX-License-Identifier: EPL-2.0

-- catalogs not supported in IQ ODBC Driver
select NULL AS TABLE_CATALOG, trim(vcreator) AS TABLE_SCHEMA, trim(viewname) AS TABLE_NAME, trim(viewtext) AS VIEW_DEFINITION
from sys.SYSVIEWS
