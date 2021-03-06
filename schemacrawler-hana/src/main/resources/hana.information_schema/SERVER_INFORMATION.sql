SELECT 
  'SYSTEM_ID' AS NAME,
  SYSTEM_ID AS VALUE,
  '' AS DESCRIPTION
FROM 
  SYS.M_DATABASE
UNION ALL
SELECT 
  'DATABASE_NAME' AS NAME,
  DATABASE_NAME AS VALUE,
  '' AS DESCRIPTION
FROM 
  SYS.M_DATABASE
UNION ALL
SELECT 
  'HOST' AS NAME,
  HOST AS VALUE,
  '' AS DESCRIPTION
FROM 
  SYS.M_DATABASE  
UNION ALL
SELECT 
  'START_TIME' AS NAME,
  CAST(START_TIME AS VARCHAR) AS VALUE,
  '' AS DESCRIPTION
FROM 
  SYS.M_DATABASE  
