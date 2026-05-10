# Github Pull Request MS

Microservicio REST para gestion de Pull Requests estilo GitHub.

**Stack:** Java 17 + Spring Boot 3.3.5 + PostgreSQL + MapStruct

## Requisitos

- Java 17+
- PostgreSQL 14+
- Maven (wrapper incluido)

## Inicio Rapido

### 1. Base de datos

```bash
# Crear base de datos
createdb github_pullrequest_db

# O con Docker
docker run -d --name postgres-pullrequest \
  -e POSTGRES_DB=github_pullrequest_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5434:5432 postgres:14
```

### 2. Ejecutar aplicacion

```powershell
# Modo desarrollo (crea tablas automaticamente)
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev

# Modo produccion (requiere schema existente)
.\mvnw.cmd spring-boot:run
```

### 3. Verificar

- **API:** http://localhost:8082/api/actuator/health
- **Swagger UI:** http://localhost:8082/api/swagger-ui.html

## Estructura del Proyecto

```
src/main/java/com/githubx/githubpullrequestms/
├── config/                 # Configuraciones (OpenAPI, Security)
│   └── security/          # JWT/OAuth2
├── controller/            # Endpoints REST
├── dao/                   # Repositorios Spring Data
├── dto/
│   ├── request/          # DTOs de entrada
│   └── response/         # DTOs de salida
├── mapper/               # MapStruct mappers
├── model/                # Entidades JPA
│   └── enums/
├── service/
│   ├── contratos/        # Interfaces
│   └── implementacion/   # Implementaciones
└── util/
    └── errorhandling/    # Excepciones y handlers
```

## Endpoints API

### Pull Requests
| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | `/v1/repos/{owner}/{repo}/pulls` | Listar pull requests |
| POST | `/v1/repos/{owner}/{repo}/pulls` | Crear pull request |
| GET | `/v1/repos/{owner}/{repo}/pulls/{prNumber}` | Obtener pull request |
| PATCH | `/v1/repos/{owner}/{repo}/pulls/{prNumber}/review` | Revisar pull request |
| POST | `/v1/repos/{owner}/{repo}/pulls/{prNumber}/merge` | Mergear pull request |
| GET | `/v1/repos/{owner}/{repo}/pulls/{prNumber}/mergeability` | Verificar mergeabilidad |

### Comentarios
| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | `/v1/repos/{owner}/{repo}/pulls/{prNumber}/comments` | Listar comentarios |
| POST | `/v1/repos/{owner}/{repo}/pulls/{prNumber}/comments` | Crear comentario |

## Autenticacion

La API usa JWT Bearer tokens. Header requerido:

```
Authorization: Bearer <jwt_token>
```

Endpoints publicos (sin auth):
- `/actuator/health`
- `/actuator/info`
- `/swagger-ui/**`
- `/v3/api-docs/**`

## Variables de Entorno

| Variable | Default | Descripcion |
|----------|---------|-------------|
| `DB_HOST` | localhost | Host PostgreSQL |
| `DB_PORT` | 5432 | Puerto PostgreSQL |
| `DB_NAME` | github_pullrequest_db | Nombre de la BD |
| `DB_USERNAME` | postgres | Usuario BD |
| `DB_PASSWORD` | postgres | Password BD |
| `JWT_ISSUER_URI` | http://localhost:8180/realms/github-files | URI del emisor JWT |
| `SERVER_PORT` | 8082 | Puerto del servidor |

## Comandos Utiles

```powershell
# Compilar
.\mvnw.cmd clean compile

# Tests
.\mvnw.cmd test

# Empaquetar
.\mvnw.cmd clean package -DskipTests

# Ejecutar JAR
java -jar target/github-pullrequest-ms-0.0.1-SNAPSHOT.jar
```

## Docker

```bash
# Build
docker build -t github-pullrequest-ms .

# Run
docker run -p 8082:8080 \
  -e DB_HOST=host.docker.internal \
  -e DB_PORT=5434 \
  github-pullrequest-ms
```

## Kubernetes

```bash
# Aplicar manifiestos
kubectl apply -f k8s/
```
