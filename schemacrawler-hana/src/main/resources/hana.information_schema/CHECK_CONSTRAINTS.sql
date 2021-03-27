SELECT
  NULL AS CONSTRAINT_CATALOG,
  CONSTRAINTS.SCHEMA_NAME AS CONSTRAINT_SCHEMA,
  CONSTRAINTS.TABLE_NAME,
  CONSTRAINTS.CONSTRAINT_NAME,
  CONSTRAINTS.CHECK_CONDITION AS CHECK_CLAUSE
FROM
  SYS.CONSTRAINTS
WHERE 
  IS_PRIMARY_KEY = 'FALSE' 
  AND IS_UNIQUE_KEY = 'FALSE'