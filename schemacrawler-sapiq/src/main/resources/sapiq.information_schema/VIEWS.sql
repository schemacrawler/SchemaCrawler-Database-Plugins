-- SchemaCrawler
-- http://www.schemacrawler.com
-- Copyright (c) 2000-2026, Sualeh Fatehi <sualeh@hotmail.com>.
-- All rights reserved.
-- SPDX-License-Identifier: EPL-2.0

select DB_NAME() AS TABLE_CATALOG, trim(vcreator) AS TABLE_SCHEMA, trim(viewname) AS TABLE_NAME, trim(viewtext) AS VIEW_DEFINITION
from sys.SYSVIEWS
