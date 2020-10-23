SELECT
  NULL AS SEQUENCE_CATALOG,
  SEQUENCES.SCHEMA_NAME AS SEQUENCE_SCHEMA,
  SEQUENCES.SEQUENCE_NAME AS SEQUENCE_NAME,
  SEQUENCES.INCREMENT_BY AS "INCREMENT",
  SEQUENCES.START_NUMBER AS START_VALUE,
  SEQUENCES.MIN_VALUE AS MINIMUM_VALUE,
  SEQUENCES.MAX_VALUE AS MAXIMUM_VALUE,
  SEQUENCES.IS_CYCLED AS CYCLE_OPTION,
  NULL AS ORDER_FLAG,
  SEQUENCES.CACHE_SIZE,
  NULL AS LAST_NUMBER,
  SEQUENCES.SEQUENCE_OID,
  SEQUENCES.RESET_BY_QUERY,
  SEQUENCES.CREATE_TIME
FROM
  SYS.SEQUENCES
ORDER BY
  SEQUENCES.SCHEMA_NAME,
  SEQUENCES.SEQUENCE_NAME
