# AGENTS.md — SchemaCrawler-Database-Plugins

Additional database connectors for SchemaCrawler that are not bundled in the main distribution. **JDBC drivers are not provided** — they must be downloaded separately and added to the classpath.

## Build and Test

```bash
# Unit tests and plugin registration tests only
mvn clean verify

# With Testcontainers integration tests (requires Docker)
mvn clean verify -Dheavydb
```

## Available Plugins

| Module | Database |
|--------|---------|
| `schemacrawler-cassandra` | Apache Cassandra |
| `schemacrawler-trino` | Trino (distributed SQL) |
| `schemacrawler-access` | Microsoft Access |
| `schemacrawler-hana` | SAP HANA |
| `schemacrawler-sapiq` | SAP IQ |
| `schemacrawler-timesten` | Oracle TimesTen |
| `schemacrawler-database-plugins-library-bom` | Bill of Materials for downstream consumers |

## Database Plugin Pattern

Every plugin follows the same structure.

### 1. Connector Class

Create `{DbName}DatabaseConnector extends DatabaseConnector` in `schemacrawler.server.{dbname}`:

```java
public final class {DbName}DatabaseConnector extends DatabaseConnector {

  private static DatabaseConnectorOptions databaseConnectorOptions() {
    final DatabaseServerType dbServerType =
        new DatabaseServerType("{identifier}", "Display Name");
    final DatabaseConnectionSourceBuilder connectionSourceBuilder =
        DatabaseConnectionSourceBuilder
            .builder("jdbc:{format}://${host}:${port}/${database}")
            .withDefaultPort({defaultPort});
    final PluginCommand pluginCommand =
        PluginCommand.newDatabasePluginCommand(dbServerType);
    pluginCommand
        .addOption("server", String.class, "--server={identifier}%n...")
        .addOption("host", String.class, "Host name...")
        .addOption("port", Integer.class, "Port number...")
        .addOption("database", String.class, "Database name...");
    return DatabaseConnectorOptionsBuilder.builder(dbServerType)
        .withHelpCommand(pluginCommand)
        .withUrlStartsWith("jdbc:{format}:")
        .withDatabaseConnectionSourceBuilder(() -> connectionSourceBuilder)
        // optional: suppress or replace specific JDBC metadata retrieval
        .withSchemaRetrievalOptionsBuilder(
            (builder, conn) -> builder.with(foreignKeysRetrievalStrategy, none))
        // optional: custom SQL for metadata
        .withInformationSchemaViewsFromResourceFolder("/{dbname}.information_schema")
        .build();
  }

  public {DbName}DatabaseConnector() {
    super(databaseConnectorOptions());
  }
}
```

### 2. ServiceLoader Registration

Create `src/main/resources/META-INF/services/schemacrawler.tools.databaseconnector.DatabaseConnector` with one line:

```
schemacrawler.server.{dbname}.{DbName}DatabaseConnector
```

### 3. Custom Information Schema SQL (optional)

Create `src/main/resources/{dbname}.information_schema/` and add `.sql` files to override metadata queries. Common files: `SERVER_INFORMATION.sql`, `VIEWS.sql`, `ROUTINES.sql`, `SEQUENCES.sql`, `TRIGGERS.sql`, `TABLE_CONSTRAINTS.sql`, `CHECK_CONSTRAINTS.sql`.

## Test Structure

Each plugin has three standard test classes in `src/test/java/schemacrawler/integration/test/`:

| Test Class | Activation | Purpose |
|------------|------------|---------|
| `{DbName}Test` | `-Dheavydb` | Integration test — `@HeavyDatabaseTest` + Testcontainers container for the database |
| `CommandLineHelpTest` | always | Validates `--help` output against a baseline in `src/test/resources/command_line_help_output/` |
| `BundledDistributionTest` | always | Verifies ServiceLoader registration via `DatabaseConnectorRegistry` |

## Adding a New Plugin

1. Create `schemacrawler-{dbname}/pom.xml` — parent `us.fatehi:schemacrawler-parent`, add JDBC driver as a `provided` or `runtime` dependency.
2. Implement `{DbName}DatabaseConnector` following the pattern above.
3. Create the `META-INF/services/` registration file.
4. Optionally add `{dbname}.information_schema/` SQL overrides.
5. Create the three standard test classes; store expected outputs in `src/test/resources/`.
6. Add `<module>schemacrawler-{dbname}</module>` to the root `pom.xml`.
7. Optionally add the artifact to `schemacrawler-database-plugins-library-bom/pom.xml`.

## Coding Guidelines

- Prefer **immutability**: use `final` on fields, parameters, and local variables.
- Tests use **JUnit 5** with **Hamcrest** matchers; integration tests require `@HeavyDatabaseTest` and a matching Testcontainers container class.
- All dependency versions are managed in `schemacrawler-parent/pom.xml`; do not declare versions in sub-module POMs.
