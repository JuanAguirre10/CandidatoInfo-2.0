# 🗳️ BACKEND - Sistema de Información Electoral

## 📋 Descripción
Backend desarrollado en Django Rest Framework para gestionar información de candidatos, partidos políticos, circunscripciones y simulacros de votación para elecciones en Bolivia.

## 🛠️ Tecnologías

- Python 3.11+
- Django 5.0
- Django REST Framework 3.14+
- PostgreSQL 16
- openpyxl (Exportación/Importación Excel)
- django-cors-headers (CORS)

## 📁 Estructura del Proyecto
```
Backend/
├── CandidatoInfo/              # Configuración principal
│   ├── settings.py
│   ├── urls.py
│   └── wsgi.py
├── partidos/                   # App de Partidos Políticos
│   ├── models.py
│   ├── views.py
│   ├── serializers.py
│   └── urls.py
├── circunscripciones/          # App de Circunscripciones
├── candidatos/                 # App de Candidatos
├── propuestas/                 # App de Propuestas
├── simulacro/                  # App de Simulacro de Votos
├── usuarios/                   # App de Usuarios
└── manage.py
```

## 🚀 Instalación y Configuración

### 1. Requisitos Previos
```bash
# Verificar versión de Python
python --version  # Debe ser 3.11 o superior

# Verificar PostgreSQL
psql --version  # Debe ser 16 o superior
```

### 2. Clonar y Configurar Entorno Virtual
```bash
cd C:\CandidatoInfo\Backend

# Crear entorno virtual
python -m venv venv

# Activar entorno virtual
# Windows:
venv\Scripts\activate
# Linux/Mac:
source venv/bin/activate
```

### 3. Instalar Dependencias
```bash
pip install -r requirements.txt
```

**Contenido de `requirements.txt`:**
```
Django==5.0
djangorestframework==3.14.0
django-cors-headers==4.3.1
psycopg2-binary==2.9.9
openpyxl==3.1.2
python-dotenv==1.0.0
```

### 4. Configurar Base de Datos PostgreSQL

**Crear Base de Datos:**
```sql
-- Ejecutar en pgAdmin o psql
CREATE DATABASE candidato_info_db;
CREATE USER candidato_user WITH PASSWORD 'tu_password_seguro';
ALTER ROLE candidato_user SET client_encoding TO 'utf8';
ALTER ROLE candidato_user SET default_transaction_isolation TO 'read committed';
ALTER ROLE candidato_user SET timezone TO 'America/La_Paz';
GRANT ALL PRIVILEGES ON DATABASE candidato_info_db TO candidato_user;
```

**Configurar .env:**

Crear archivo `.env` en `Backend/CandidatoInfo/`:
```env
SECRET_KEY=tu-secret-key-super-segura-aqui
DEBUG=True
DATABASE_NAME=candidato_info_db
DATABASE_USER=candidato_user
DATABASE_PASSWORD=tu_password_seguro
DATABASE_HOST=localhost
DATABASE_PORT=5432
ALLOWED_HOSTS=localhost,127.0.0.1
CORS_ALLOWED_ORIGINS=http://localhost:5173,http://127.0.0.1:5173
```

### 5. Aplicar Migraciones
```bash
python manage.py makemigrations
python manage.py migrate
```

### 6. Crear Superusuario
```bash
python manage.py createsuperuser
# Usuario: admin
# Email: admin@example.com
# Password: (tu password)
```

### 7. Ejecutar Servidor
```bash
python manage.py runserver
```

El servidor estará disponible en: `http://localhost:8000`

## 📊 Estructura de la Base de Datos

### Tablas Principales:
- **partidos_politicos** - Partidos y alianzas políticas
- **circunscripciones** - Departamentos y circunscripciones electorales
- **candidatos_presidenciales** - Candidatos presidenciales con vicepresidentes
- **candidatos_senadores_nacionales** - Senadores nacionales
- **candidatos_senadores_regionales** - Senadores por departamento
- **candidatos_diputados** - Diputados por circunscripción
- **candidatos_parlamento_andino** - Parlamento Andino
- **propuestas** - Propuestas de campaña
- **proyectos_realizados** - Proyectos completados
- **denuncias** - Denuncias y procesos judiciales
- **simulacro_votos** - Votos del simulacro electoral
- **usuarios** - Usuarios del sistema

## 🔌 API Endpoints

### Autenticación
```
POST /api/auth/login/           - Login
POST /api/auth/logout/          - Logout
POST /api/auth/register/        - Registrar usuario
```

### Partidos Políticos
```
GET    /api/partidos/                    - Listar todos
POST   /api/partidos/                    - Crear nuevo
GET    /api/partidos/{id}/               - Obtener detalle
PUT    /api/partidos/{id}/               - Actualizar
DELETE /api/partidos/{id}/               - Eliminar
GET    /api/partidos/export_excel/       - Exportar a Excel
POST   /api/partidos/import_excel/       - Importar desde Excel
```

### Circunscripciones
```
GET    /api/circunscripciones/                    - Listar todos
POST   /api/circunscripciones/                    - Crear nuevo
GET    /api/circunscripciones/{id}/               - Obtener detalle
PUT    /api/circunscripciones/{id}/               - Actualizar
DELETE /api/circunscripciones/{id}/               - Eliminar
GET    /api/circunscripciones/export_excel/       - Exportar a Excel
POST   /api/circunscripciones/import_excel/       - Importar desde Excel
```

### Candidatos Presidenciales
```
GET    /api/candidatos-presidenciales/                    - Listar todos
POST   /api/candidatos-presidenciales/                    - Crear nuevo
GET    /api/candidatos-presidenciales/{id}/               - Obtener detalle
PUT    /api/candidatos-presidenciales/{id}/               - Actualizar
DELETE /api/candidatos-presidenciales/{id}/               - Eliminar
GET    /api/candidatos-presidenciales/export_excel/       - Exportar a Excel
POST   /api/candidatos-presidenciales/import_excel/       - Importar desde Excel
```

### Senadores Nacionales
```
GET    /api/senadores-nacionales/                    - Listar todos
POST   /api/senadores-nacionales/                    - Crear nuevo
GET    /api/senadores-nacionales/{id}/               - Obtener detalle
PUT    /api/senadores-nacionales/{id}/               - Actualizar
DELETE /api/senadores-nacionales/{id}/               - Eliminar
GET    /api/senadores-nacionales/export_excel/       - Exportar a Excel
POST   /api/senadores-nacionales/import_excel/       - Importar desde Excel
```

### Senadores Regionales
```
GET    /api/senadores-regionales/                    - Listar todos
POST   /api/senadores-regionales/                    - Crear nuevo
GET    /api/senadores-regionales/{id}/               - Obtener detalle
PUT    /api/senadores-regionales/{id}/               - Actualizar
DELETE /api/senadores-regionales/{id}/               - Eliminar
GET    /api/senadores-regionales/export_excel/       - Exportar a Excel
POST   /api/senadores-regionales/import_excel/       - Importar desde Excel
```

### Diputados
```
GET    /api/diputados/                    - Listar todos
POST   /api/diputados/                    - Crear nuevo
GET    /api/diputados/{id}/               - Obtener detalle
PUT    /api/diputados/{id}/               - Actualizar
DELETE /api/diputados/{id}/               - Eliminar
GET    /api/diputados/export_excel/       - Exportar a Excel
POST   /api/diputados/import_excel/       - Importar desde Excel
```

### Parlamento Andino
```
GET    /api/parlamento-andino/                    - Listar todos
POST   /api/parlamento-andino/                    - Crear nuevo
GET    /api/parlamento-andino/{id}/               - Obtener detalle
PUT    /api/parlamento-andino/{id}/               - Actualizar
DELETE /api/parlamento-andino/{id}/               - Eliminar
GET    /api/parlamento-andino/export_excel/       - Exportar a Excel
POST   /api/parlamento-andino/import_excel/       - Importar desde Excel
```

### Propuestas
```
GET    /api/propuestas/                    - Listar todas
POST   /api/propuestas/                    - Crear nueva
GET    /api/propuestas/{id}/               - Obtener detalle
PUT    /api/propuestas/{id}/               - Actualizar
DELETE /api/propuestas/{id}/               - Eliminar
GET    /api/propuestas/export_excel/       - Exportar a Excel
POST   /api/propuestas/import_excel/       - Importar desde Excel
```

### Proyectos Realizados
```
GET    /api/proyectos/                    - Listar todos
POST   /api/proyectos/                    - Crear nuevo
GET    /api/proyectos/{id}/               - Obtener detalle
PUT    /api/proyectos/{id}/               - Actualizar
DELETE /api/proyectos/{id}/               - Eliminar
GET    /api/proyectos/export_excel/       - Exportar a Excel
POST   /api/proyectos/import_excel/       - Importar desde Excel
```

### Denuncias
```
GET    /api/denuncias/                    - Listar todas
POST   /api/denuncias/                    - Crear nueva
GET    /api/denuncias/{id}/               - Obtener detalle
PUT    /api/denuncias/{id}/               - Actualizar
DELETE /api/denuncias/{id}/               - Eliminar
GET    /api/denuncias/export_excel/       - Exportar a Excel
POST   /api/denuncias/import_excel/       - Importar desde Excel
```

### Simulacro de Votación
```
GET    /api/simulacro/                              - Listar votos
POST   /api/simulacro/                              - Registrar voto
GET    /api/simulacro/estadisticas/                 - Estadísticas generales
GET    /api/simulacro/resultados-por-candidato/    - Resultados por candidato
GET    /api/simulacro/export_excel/                 - Exportar a Excel
```

## 📤 Exportación/Importación Excel

### Formato de Exportación
Todas las exportaciones incluyen:

- Encabezados con todos los campos del modelo
- Formato Excel (.xlsx) con estilos
- Nombre de archivo con timestamp

### Formato de Importación
Para importar, el archivo Excel debe:

- Tener los mismos encabezados que la exportación
- Primera fila: Encabezados
- Siguientes filas: Datos
- ID vacío: Crea nuevo registro
- ID con valor: Actualiza registro existente

**Ejemplo de Uso:**
```bash
# Exportar
curl -O http://localhost:8000/api/partidos/export_excel/

# Importar
curl -X POST -F "file=@partidos.xlsx" http://localhost:8000/api/partidos/import_excel/
```

## 🔧 Comandos Útiles

### Gestión de Base de Datos
```bash
# Crear migraciones
python manage.py makemigrations

# Aplicar migraciones
python manage.py migrate

# Resetear base de datos
python manage.py flush

# Crear backup
pg_dump -U candidato_user candidato_info_db > backup.sql

# Restaurar backup
psql -U candidato_user candidato_info_db < backup.sql
```

### Gestión de Usuarios
```bash
# Crear superusuario
python manage.py createsuperuser

# Cambiar password
python manage.py changepassword admin
```

### Testing
```bash
# Ejecutar todos los tests
python manage.py test

# Test específico
python manage.py test partidos.tests
```

## 🐛 Troubleshooting

### Error: "No module named 'psycopg2'"
```bash
pip install psycopg2-binary
```

### Error: "FATAL: password authentication failed"
Verificar credenciales en `.env` y que el usuario tenga permisos.

### Error: "Port 8000 already in use"
```bash
# Windows
netstat -ano | findstr :8000
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8000 | xargs kill -9
```

### Error en migraciones
```bash
# Resetear migraciones
python manage.py migrate --fake <app_name> zero
python manage.py migrate <app_name>
```

## 📝 Notas de Desarrollo

### Agregar nuevo modelo:

1. Crear modelo en `models.py`
2. Crear serializer en `serializers.py`
3. Crear viewset en `views.py`
4. Agregar URL en `urls.py`
5. Ejecutar `makemigrations` y `migrate`

### Agregar funcionalidad de exportación:
```python
@action(detail=False, methods=['get'])
def export_excel(self, request):
    # Ver ejemplos en partidos/views.py
    pass
```

## 🔒 Seguridad

- **CSRF Protection**: Habilitado por defecto
- **CORS**: Configurado para frontend específico
- **SQL Injection**: Protegido por ORM de Django
- **XSS**: Escapado automático en templates
- **Autenticación**: Token-based con DRF

