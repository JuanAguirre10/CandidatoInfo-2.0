# 🗳️ CandidatoInfo - Sistema Electoral Perú 2026

<div align="center">

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Django](https://img.shields.io/badge/Django-092E20?style=for-the-badge&logo=django&logoColor=white)
![Python](https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white)

**Aplicación móvil para consultar información electoral de candidatos a las Elecciones Generales Perú 2026**

[Características](#-características) •
[Tecnologías](#-tecnologías) •
[Instalación](#-instalación) •
[Arquitectura](#-arquitectura) •
[API](#-api-backend) •
[Capturas](#-capturas-de-pantalla)

</div>

---

## 📋 Descripción

**CandidatoInfo** es una aplicación móvil Android desarrollada en Kotlin que permite a los ciudadanos peruanos consultar información detallada sobre los candidatos a las Elecciones Generales 2026, incluyendo:

- ✅ Candidatos Presidenciales
- ✅ Senadores Nacionales  
- ✅ Senadores Regionales
- ✅ Diputados
- ✅ Parlamento Andino

La aplicación incluye un **sistema de votación simulada** con validación de DNI mediante RENIEC y **estadísticas en tiempo real** de los votos registrados.

---

## ✨ Características

### 🏠 Funcionalidades Principales

#### 1. **Consulta de Candidatos**
- 📊 Listado completo de candidatos por tipo de elección
- 🔍 Filtros por partido político y región
- 📝 Información detallada: biografía, propuestas, proyectos y denuncias
- 🆚 Comparación lado a lado de dos candidatos
- 🔗 Enlaces a documentos oficiales (propuestas, hojas de vida)

#### 2. **Partidos Políticos**
- 🎨 Catálogo de todos los partidos inscritos
- 📜 Información detallada: ideología, historia, fundación
- 👥 Listado completo de candidatos por partido
- 🖼️ Logos y colores oficiales

#### 3. **Sistema de Votación Simulada** 🗳️
- ✔️ Validación de DNI con API de RENIEC
- 📱 Interfaz de votación por categorías:
  - Presidente (1 voto)
  - Senadores Nacionales (máx. 2 votos)
  - Senadores Regionales (máx. 2 votos)
  - Diputados (máx. 2 votos)
  - Parlamento Andino (máx. 2 votos)
- 🔒 Control de voto único por DNI por mes
- 📋 Resumen de votación antes de confirmar
- ✅ Confirmación con nombre del votante

#### 4. **Estadísticas de Votación** 📊
- 📈 Gráfico de barras: Top 5 candidatos más votados + Otros
- 🥧 Gráfico circular: Distribución de votos por partido político
- 🏆 Ranking completo de candidatos con porcentajes
- 🔄 Filtros por tipo de elección (5 categorías)
- 📅 Estadísticas mensuales en tiempo real

#### 5. **Características Adicionales**
- 🌍 Selección de región al iniciar
- 🎨 Interfaz Material Design 3
- 🌐 Modo offline con caché de datos
- 🔄 Actualización automática de información
- 📱 Diseño responsive para tablets

---

## 🛠️ Tecnologías

### 📱 **Frontend - Android**

| Tecnología | Versión | Uso |
|-----------|---------|-----|
| **Kotlin** | 1.9+ | Lenguaje principal |
| **Jetpack Compose** | Latest | UI declarativa |
| **Material Design 3** | Latest | Diseño de interfaz |
| **Retrofit** | 2.9+ | Cliente HTTP |
| **Coil** | 2.0+ | Carga de imágenes |
| **Coroutines** | 1.7+ | Programación asíncrona |
| **DataStore** | 1.0+ | Almacenamiento de preferencias |
| **Navigation Compose** | Latest | Navegación entre pantallas |

### 🔧 **Backend - Django REST Framework**

| Tecnología | Versión | Uso |
|-----------|---------|-----|
| **Python** | 3.10+ | Lenguaje backend |
| **Django** | 4.2+ | Framework web |
| **Django REST Framework** | 3.14+ | API REST |
| **MySQL** | 8.0+ | Base de datos |
| **Django Filters** | 23.0+ | Filtros avanzados |
| **Django CORS Headers** | 4.0+ | CORS |
| **Python Decouple** | 3.8+ | Variables de entorno |

### 🔗 **APIs Externas**

- **API RENIEC** (Decolecta): Validación de DNI

---

## 📦 Instalación

### 📱 Requisitos Previos - Android

```bash
- Android Studio Hedgehog | 2023.1.1 o superior
- JDK 17
- Android SDK 34
- Gradle 8.2+
- Emulador o dispositivo Android 8.0+ (API 26+)
```

### 🔧 Configuración del Proyecto Android

1. **Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/candidatoinfo.git
cd candidatoinfo/android
```

2. **Abrir en Android Studio**
```
File → Open → Seleccionar carpeta del proyecto
```

3. **Configurar la URL base de la API**

Edita `RetrofitClient.kt`:
```kotlin
private const val BASE_URL = "http://tu-ip:8000/"  // Cambiar por tu IP
```

4. **Sincronizar Gradle**
```
File → Sync Project with Gradle Files
```

5. **Ejecutar la app**
```
Run → Run 'app'
```

### 🖥️ Requisitos Previos - Backend

```bash
- Python 3.10+
- MySQL 8.0+
- pip 23.0+
- virtualenv
```

### ⚙️ Configuración del Backend

1. **Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/candidatoinfo.git
cd candidatoinfo/backend
```

2. **Crear entorno virtual**
```bash
python -m venv venv
source venv/bin/activate  # En Windows: venv\Scripts\activate
```

3. **Instalar dependencias**
```bash
pip install -r requirements.txt
```

4. **Configurar variables de entorno**

Crear archivo `.env`:
```env
SECRET_KEY=tu-secret-key-aqui
DEBUG=True
DATABASE_NAME=candidatoinfo_db
DATABASE_USER=root
DATABASE_PASSWORD=tu-password
DATABASE_HOST=localhost
DATABASE_PORT=3306
DNI_API_TOKEN=sk_11302.tu-token-aqui
```

5. **Crear base de datos**
```bash
mysql -u root -p
CREATE DATABASE candidatoinfo_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
exit
```

6. **Ejecutar migraciones**
```bash
python manage.py makemigrations
python manage.py migrate
```

7. **Cargar datos iniciales** (opcional)
```bash
python manage.py loaddata partidos.json
python manage.py loaddata circunscripciones.json
python manage.py loaddata candidatos.json
```

8. **Crear superusuario**
```bash
python manage.py createsuperuser
```

9. **Iniciar servidor**
```bash
python manage.py runserver 0.0.0.0:8000
```

10. **Acceder al admin**
```
http://localhost:8000/admin
```

---

## 🏗️ Arquitectura

### 📱 Arquitectura Android - MVVM

```
app/
├── data/
│   ├── local/
│   │   └── preferences/
│   │       └── PreferencesManager.kt
│   ├── remote/
│   │   ├── api/
│   │   │   ├── ApiService.kt
│   │   │   └── RetrofitClient.kt
│   │   └── dto/
│   │       ├── CandidatoDtos.kt
│   │       ├── PartidoDtos.kt
│   │       ├── VotacionDtos.kt
│   │       └── EstadisticaDtos.kt
│   └── repository/
│       ├── CandidatoRepository.kt
│       ├── PartidoRepository.kt
│       ├── VotacionRepository.kt
│       ├── EstadisticasRepository.kt
│       └── InformacionRepository.kt
├── domain/
│   └── model/
│       ├── Candidato.kt
│       ├── Partido.kt
│       ├── Propuesta.kt
│       ├── Proyecto.kt
│       └── Denuncia.kt
├── presentation/
│   ├── screens/
│   │   ├── region/
│   │   │   └── RegionSelectionScreen.kt
│   │   ├── home/
│   │   │   ├── HomeScreen.kt
│   │   │   └── HomeViewModel.kt
│   │   ├── partidos/
│   │   │   ├── PartidosScreen.kt
│   │   │   └── PartidosViewModel.kt
│   │   ├── candidatos/
│   │   │   ├── CandidatosScreen.kt
│   │   │   └── CandidatosViewModel.kt
│   │   ├── candidato_detalle/
│   │   │   ├── CandidatoDetalleScreen.kt
│   │   │   └── CandidatoDetalleViewModel.kt
│   │   ├── comparacion/
│   │   │   ├── ComparacionScreen.kt
│   │   │   └── ComparacionViewModel.kt
│   │   ├── votacion/
│   │   │   ├── VotacionScreen.kt
│   │   │   └── VotacionViewModel.kt
│   │   └── estadisticas/
│   │       ├── EstadisticasScreen.kt
│   │       └── EstadisticasViewModel.kt
│   ├── navigation/
│   │   └── AppNavigation.kt
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
└── MainActivity.kt
```

### 🔧 Arquitectura Backend - Django MVT

```
backend/
├── candidatos/
│   ├── models.py              # Modelos de candidatos
│   ├── serializers.py         # Serializadores DRF
│   ├── views.py               # ViewSets
│   └── urls.py                # Rutas de API
├── partidos/
│   ├── models.py              # Modelo de partidos
│   ├── serializers.py
│   ├── views.py
│   └── urls.py
├── circunscripciones/
│   ├── models.py              # Regiones del Perú
│   ├── serializers.py
│   ├── views.py
│   └── urls.py
├── informacion/
│   ├── models.py              # Propuestas, Proyectos, Denuncias
│   ├── serializers.py
│   ├── views.py
│   └── urls.py
├── simulacro/
│   ├── models.py              # Votos simulados
│   ├── serializers.py
│   ├── views.py               # ViewSet con endpoints especiales
│   └── urls.py
└── utils/
    ├── excel_exporter.py      # Exportar a Excel
    └── excel_importer.py      # Importar desde Excel
```

---

## 🌐 API Backend

### 📡 Endpoints Principales

#### **Candidatos**

```http
GET /api/candidatos/presidenciales/
GET /api/candidatos/senadores-nacionales/
GET /api/candidatos/senadores-regionales/?circunscripcion=1
GET /api/candidatos/diputados/?circunscripcion=1
GET /api/candidatos/parlamento-andino/
```

#### **Partidos Políticos**

```http
GET /api/partidos/
GET /api/partidos/{id}/
GET /api/partidos/{id}/candidatos_presidenciales/
```

#### **Información Adicional**

```http
GET /api/informacion/propuestas/?candidato={id}&tipo={tipo}
GET /api/informacion/proyectos/?candidato={id}&tipo={tipo}
GET /api/informacion/denuncias/?candidato={id}&tipo={tipo}
```

#### **Votación**

```http
GET  /api/simulacro/votos/validar_dni/?dni={dni}
POST /api/simulacro/votos/
     Body: {
       "dni": "12345678",
       "tipo_eleccion": "presidencial",
       "candidato_id": 1,
       "circunscripcion": 15
     }
POST /api/simulacro/votos/verificar_voto/
     Body: {"dni": "12345678", "tipo_eleccion": "presidencial"}
```

#### **Estadísticas**

```http
GET /api/simulacro/votos/resultados_por_candidato/
    ?tipo_eleccion=presidencial
    &mes_simulacro=11
    &anio_simulacro=2025

Response: {
  "tipo_eleccion": "presidencial",
  "mes": "11",
  "anio": "2025",
  "total_votos": 150,
  "resultados": [
    {
      "candidato_id": 1,
      "candidato_nombre": "Carlos Mendoza",
      "partido_id": 5,
      "partido_nombre": "Acción Popular",
      "partido_siglas": "AP",
      "foto_url": "https://...",
      "votos": 45,
      "porcentaje": 30.0
    },
    ...
  ]
}
```

#### **Circunscripciones**

```http
GET /api/circunscripciones/
GET /api/circunscripciones/{id}/
```

---

## 📊 Base de Datos

### 🗂️ Estructura Principal

```sql
-- Partidos Políticos
partidos_politicos
├── id
├── nombre
├── siglas
├── logo_url
├── color_principal
├── ideologia
└── ...

-- Candidatos Presidenciales
candidatos_presidenciales
├── id
├── partido_id (FK)
├── presidente_nombre
├── presidente_apellidos
├── presidente_foto_url
├── vicepresidente1_nombre
├── vicepresidente2_nombre
└── ...

-- Senadores Nacionales
candidatos_senadores_nacionales
├── id
├── partido_id (FK)
├── nombre
├── apellidos
├── foto_url
├── posicion_lista
└── ...

-- Votos Simulacro
simulacro_votos
├── id
├── dni
├── nombre_completo
├── tipo_eleccion
├── candidato_id
├── circunscripcion_id (FK)
├── mes_simulacro
├── anio_simulacro
├── fecha_voto
└── ip_address
```

---

## 📸 Capturas de Pantalla

### 🏠 Pantalla Principal
- Selector de región
- Acceso rápido a todas las funciones
- Menú desplegable con navegación

### 👥 Candidatos
- Lista con filtros por partido
- Selección múltiple para comparar
- Vista detallada con toda la información

### 🆚 Comparación
- Comparación lado a lado
- Información personal completa
- Propuestas, proyectos y denuncias

### 🗳️ Votación
- Validación de DNI con RENIEC
- Selección por categorías
- Resumen y confirmación

### 📊 Estadísticas
- Gráfico de barras (Top 5 + Otros)
- Gráfico circular por partido
- Ranking completo con porcentajes

---

## 🔐 Seguridad

### 🛡️ Medidas Implementadas

- ✅ Validación de DNI con API oficial de RENIEC
- ✅ Control de voto único por DNI por mes
- ✅ Registro de IP address por voto
- ✅ CORS configurado para dominios específicos
- ✅ Variables de entorno para datos sensibles
- ✅ Validación de datos en backend
- ✅ Sanitización de inputs

---

## 🚀 Características Futuras

- [ ] Notificaciones push de noticias electorales
- [ ] Sistema de favoritos
- [ ] Compartir información de candidatos
- [ ] Modo oscuro
- [ ] Multiidioma (Quechua, Aymara)
- [ ] Integración con redes sociales
- [ ] Sistema de reportes y denuncias
- [ ] Verificación de fake news
- [ ] Chat con IA sobre candidatos
- [ ] Recordatorios de fechas electorales

---

## 👥 Equipo de Desarrollo

### 💻 Desarrolladores

- **Juan Aguirre Saavedra** 
- **Luis Galvan Morales** 
- **Matias Galvan Guerrero** 
- **Samir Alfonso Solorzano**
- **Matias Galvan Guerrero**
- **wilson Lopez Aponte**
- **Yair Araujo Gabriel**
  
### 🎓 Institución

**TECSUP** - Instituto de Educación Superior
Carrera: Desarrollo de Software

---



### 🐛 Reportar Bugs
Abre un issue en: [GitHub Issues](https://github.com/tu-usuario/candidatoinfo/issues)

---

## 🙏 Agradecimientos

- **ONPE** - Por la información electoral oficial
- **JNE** - Por los datos de partidos políticos
- **RENIEC** - Por la API de validación de DNI
- **Decolecta** - Por proporcionar acceso a la API de RENIEC
- **TECSUP** - Por el apoyo educativo
- **Comunidad Android** - Por las librerías de código abierto

---


