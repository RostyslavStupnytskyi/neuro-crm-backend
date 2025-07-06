# CRM Backend

Minimal Spring Boot project skeleton for CRM backend with REST API.

## Building

Use Maven Wrapper:

```bash
./mvnw verify
```

## Running

```bash
java -jar target/crm-classic.jar
```

Swagger UI is available at http://localhost:8080/docs after starting the application.

## API Endpoints

The application provides REST endpoints for the following resources:

- `/api/v1/accounts` - CRUD operations for accounts
- `/api/v1/contacts` - CRUD operations for contacts
- `/api/v1/deliveries` - CRUD operations for deliveries with filtering and pagination
