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