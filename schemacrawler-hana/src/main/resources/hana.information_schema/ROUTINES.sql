SELECT
  NULL AS ROUTINE_CATALOG,
  PROCEDURES.SCHEMA_NAME AS ROUTINE_SCHEMA,
  PROCEDURES.PROCEDURE_NAME AS ROUTINE_NAME,
  NULL AS SPECIFIC_NAME,
  CASE WHEN PROCEDURES.PROCEDURE_TYPE = 'SQLSCRIPT2' THEN 'SQL' ELSE 'EXTERNAL' END AS ROUTINE_BODY,
  PROCEDURES.DEFINITION AS ROUTINE_DEFINITION
FROM
  SYS.PROCEDURES
UNION ALL  
SELECT
  NULL AS ROUTINE_CATALOG,
  FUNCTIONS.SCHEMA_NAME AS ROUTINE_SCHEMA,
  FUNCTIONS.FUNCTION_NAME AS ROUTINE_NAME,
  FUNCTIONS.FUNCTION_NAME AS SPECIFIC_NAME,
  CASE WHEN FUNCTIONS.FUNCTION_TYPE = 'SQLSCRIPT2' THEN 'SQL' ELSE 'EXTERNAL' END AS ROUTINE_BODY,
  FUNCTIONS.DEFINITION AS ROUTINE_DEFINITION
FROM
  SYS.FUNCTIONS