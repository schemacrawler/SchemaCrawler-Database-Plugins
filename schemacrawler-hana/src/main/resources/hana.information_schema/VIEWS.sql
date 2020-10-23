SELECT
  NULL AS TABLE_CATALOG,
  VIEWS.SCHEMA_NAME AS TABLE_SCHEMA,
  VIEWS.VIEW_NAME AS TABLE_NAME,
  VIEWS.DEFINITION AS VIEW_DEFINITION,
  VIEWS.HAS_CHECK_OPTION AS CHECK_OPTION,
  CASE WHEN VIEWS.IS_READ_ONLY = 'N' THEN 'Y' ELSE 'N' END AS IS_UPDATABLE
FROM
  SYS.VIEWS
