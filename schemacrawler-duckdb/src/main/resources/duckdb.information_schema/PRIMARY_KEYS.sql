SELECT
  NULL AS TABLE_CAT,
  PRIMARY_KEYS.schema_name AS TABLE_SCHEM,
  PRIMARY_KEYS.table_name AS TABLE_NAME,
  'primary_key' AS PK_NAME,
  UNNEST(PRIMARY_KEYS.constraint_column_names) AS COLUMN_NAME,
  0 AS KEY_SEQ
FROM
  duckdb_constraints() AS PRIMARY_KEYS
WHERE
  PRIMARY_KEYS.constraint_type = 'PRIMARY KEY'
