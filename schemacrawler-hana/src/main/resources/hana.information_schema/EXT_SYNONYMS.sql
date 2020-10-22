SELECT
  NULL AS SYNONYM_CATALOG,
  SYNONYMS.SCHEMA_NAME AS SYNONYM_SCHEMA,
  SYNONYMS.SYNONYM_NAME,
  NULL AS REFERENCED_OBJECT_CATALOG,
  SYNONYMS.OBJECT_SCHEMA AS REFERENCED_OBJECT_SCHEMA,
  SYNONYMS.OBJECT_NAME AS REFERENCED_OBJECT_NAME
FROM
  SYS.SYNONYMS
ORDER BY
  SYNONYMS.SCHEMA_NAME,
  SYNONYMS.SYNONYM_NAME
