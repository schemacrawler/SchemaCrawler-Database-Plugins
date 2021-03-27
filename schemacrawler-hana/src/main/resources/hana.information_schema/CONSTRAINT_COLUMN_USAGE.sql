SELECT
  NULL AS CONSTRAINT_CATALOG,
  CONSTRAINTS.SCHEMA_NAME AS CONSTRAINT_SCHEMA,
  CONSTRAINTS.CONSTRAINT_NAME,
  NULL AS TABLE_CATALOG,
  CONSTRAINTS.SCHEMA_NAME AS TABLE_SCHEMA,
  CONSTRAINTS.TABLE_NAME,
  CONSTRAINTS.COLUMN_NAME AS COLUMN_NAME,
  0 AS ORDINAL_POSITION
FROM
  SYS.CONSTRAINTS
ORDER BY
  CONSTRAINTS.SCHEMA_NAME,
  CONSTRAINTS.CONSTRAINT_NAME