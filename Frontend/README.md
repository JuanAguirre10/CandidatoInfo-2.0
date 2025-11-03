# ⚛️ FRONTEND - Sistema de Información Electoral

## 📋 Descripción
Frontend desarrollado en React + Vite para la gestión visual del sistema de información electoral. Interfaz moderna y responsive con todas las funcionalidades CRUD.

## 🛠️ Tecnologías
- **React** 18.3+
- **Vite** 5.4+
- **React Router DOM** 6.26+
- **Axios** 1.7+
- **Tailwind CSS** 3.4+
- **Lucide React** (Iconos)
- **Chart.js** (Gráficos)

## 📁 Estructura del Proyecto
```
Frontend/
├── public/                     # Archivos estáticos
├── src/
│   ├── components/            # Componentes React
│   │   ├── Layout.jsx
│   │   ├── Login.jsx
│   │   ├── Dashboard.jsx
│   │   ├── Partidos.jsx
│   │   ├── Circunscripciones.jsx
│   │   ├── CandidatosPresidenciales.jsx
│   │   ├── SenadoresNacionales.jsx
│   │   ├── SenadoresRegionales.jsx
│   │   ├── Diputados.jsx
│   │   ├── ParlamentoAndino.jsx
│   │   ├── Propuestas.jsx
│   │   ├── Proyectos.jsx
│   │   ├── Denuncias.jsx
│   │   └── Simulacro.jsx
│   ├── services/
│   │   └── api.js             # Configuración Axios y endpoints
│   ├── App.jsx                # Componente principal
│   ├── main.jsx              # Punto de entrada
│   └── index.css             # Estilos globales
├── package.json
├── vite.config.js
└── tailwind.config.js
```

## 🚀 Instalación y Configuración

### 1. Requisitos Previos
```bash
# Verificar Node.js (versión 20.19.5 LTS recomendada)
node --version

# Verificar npm
npm --version
```

### 2. Instalar Dependencias
```bash
cd C:\CandidatoInfo\Frontend

# Instalar todas las dependencias
npm install
```

**Contenido de `package.json`:**
```json
{
  "name": "candidato-info-frontend",
  "version": "1.0.0",
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "react": "^18.3.1",
    "react-dom": "^18.3.1",
    "react-router-dom": "^6.26.2",
    "axios": "^1.7.7",
    "lucide-react": "^0.263.1",
    "chart.js": "^4.4.0",
    "react-chartjs-2": "^5.2.0"
  },
  "devDependencies": {
    "@vitejs/plugin-react": "^4.3.3",
    "vite": "^5.4.10",
    "tailwindcss": "^3.4.14",
    "postcss": "^8.4.47",
    "autoprefixer": "^10.4.20"
  }
}
```

### 3. Configurar Variables de Entorno
Crear archivo `.env` en la raíz del Frontend:
```env
VITE_API_URL=http://localhost:8000/api
```

### 4. Configurar Tailwind CSS
Ya está configurado, pero verificar `tailwind.config.js`:
```javascript
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}
```

### 5. Ejecutar en Desarrollo
```bash
npm run dev
```

La aplicación estará disponible en: `http://localhost:5173`

### 6. Construir para Producción
```bash
npm run build
```

Los archivos optimizados estarán en `dist/`

## 🎨 Componentes Principales

### 1. Login.jsx

- Autenticación de usuarios
- Validación de credenciales
- Redirección al dashboard

### 2. Layout.jsx

- Barra de navegación lateral
- Header con usuario logueado
- Contenedor principal para rutas

### 3. Dashboard.jsx

- Estadísticas generales
- Gráficos con Chart.js
- Tarjetas informativas

### 4. Partidos.jsx

- CRUD completo de partidos
- Búsqueda y paginación
- Exportación/Importación Excel
- TODOS los campos del modelo

### 5. Circunscripciones.jsx

- CRUD completo de circunscripciones
- Visualización de mapas (futura integración)
- Exportación/Importación Excel
- TODOS los campos del modelo

### 6. CandidatosPresidenciales.jsx

- CRUD de candidatos presidenciales
- Gestión de presidente y 2 vicepresidentes
- Fotos y plan de gobierno
- TODOS los campos del modelo

### 7. SenadoresNacionales.jsx

- CRUD de senadores nacionales
- Gestión de listas y preferencias
- TODOS los campos del modelo

### 8. SenadoresRegionales.jsx

- CRUD de senadores regionales
- Filtrado por circunscripción
- Validación de naturalidad
- TODOS los campos del modelo

### 9. Diputados.jsx

- CRUD de diputados
- Filtrado por circunscripción
- TODOS los campos del modelo

### 10. ParlamentoAndino.jsx

- CRUD parlamento andino
- Gestión de idiomas
- TODOS los campos del modelo

### 11. Propuestas.jsx

- CRUD de propuestas de campaña
- Categorización por eje temático
- TODOS los campos del modelo

### 12. Proyectos.jsx

- CRUD de proyectos realizados
- Estados de ejecución
- TODOS los campos del modelo

### 13. Denuncias.jsx

- CRUD de denuncias
- Clasificación por gravedad
- TODOS los campos del modelo

### 14. Simulacro.jsx

- Visualización de resultados
- Gráficos de votación
- Estadísticas en tiempo real
- Filtros por tipo de elección

## 📡 Integración con API

### Configuración Axios (`src/services/api.js`)
```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8000/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor para agregar token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
```

### Ejemplo de Uso:
```javascript
import { getPartidos, createPartido } from '../services/api';

// Listar partidos
const partidos = await getPartidos();

// Crear partido
const nuevoPartido = await createPartido({
  nombre: 'Nuevo Partido',
  siglas: 'NP',
  // ... más campos
});
```

## 🎯 Funcionalidades Principales

### ✅ CRUD Completo

- **Create**: Formularios modales con validación
- **Read**: Tablas con búsqueda y paginación
- **Update**: Edición inline con formularios
- **Delete**: Confirmación antes de eliminar

### ✅ Exportación/Importación Excel

- Botón "Exportar" descarga Excel con todos los datos
- Botón "Importar" permite subir Excel para carga masiva
- Validación de formato y datos

### ✅ Búsqueda y Filtros

- Búsqueda en tiempo real
- Filtros por categorías
- Paginación automática

### ✅ Responsive Design

- Diseño adaptable a móviles, tablets y desktop
- Menú hamburguesa en móviles
- Tablas con scroll horizontal

### ✅ Validación de Formularios

- Campos requeridos marcados con *
- Validación de DNI (8 dígitos)
- Validación de URLs
- Validación de fechas

## 🎨 Personalización de Estilos

### Colores Principales (Tailwind):

- **Azul**: `bg-blue-600` - Botones primarios
- **Verde**: `bg-green-600` - Exportación
- **Rojo**: `bg-red-600` - Eliminación
- **Amarillo**: `bg-yellow-100` - Advertencias
- **Gris**: `bg-gray-50` - Fondos

### Modificar Colores:
Editar `tailwind.config.js`:
```javascript
theme: {
  extend: {
    colors: {
      primary: '#3b82f6',
      secondary: '#10b981',
    }
  }
}
```

## 🔧 Scripts Disponibles

```bash
# Desarrollo
npm run dev

# Build producción
npm run build

# Preview build
npm run preview

# Limpiar node_modules y reinstalar
rm -rf node_modules package-lock.json
npm install
```

## 🐛 Troubleshooting

### Error: "Cannot find module 'vite'"
```bash
npm install
```

### Error: TailwindCSS no funciona
```bash
npm install -D tailwindcss postcss autoprefixer
npx tailwindcss init -p
```

### Error: CORS
Verificar que el backend tenga configurado:
```python
CORS_ALLOWED_ORIGINS = [
    "http://localhost:5173",
    "http://127.0.0.1:5173",
]
```

### Error: 404 en producción
Configurar servidor web para redirigir todas las rutas a `index.html`

## 📦 Despliegue

### Build para Producción:
```bash
npm run build
```

### Servir con Nginx:
```nginx
server {
    listen 80;
    server_name candidatoinfo.bo;
    root /var/www/candidato-info/dist;
    
    location / {
        try_files $uri $uri/ /index.html;
    }
}
```

### Servir con Apache:
```apache
<VirtualHost *:80>
    ServerName candidatoinfo.bo
    DocumentRoot /var/www/candidato-info/dist
    
    <Directory /var/www/candidato-info/dist>
        Options -Indexes +FollowSymLinks
        AllowOverride All
        Require all granted
        
        RewriteEngine On
        RewriteBase /
        RewriteRule ^index\.html$ - [L]
        RewriteCond %{REQUEST_FILENAME} !-f
        RewriteCond %{REQUEST_FILENAME} !-d
        RewriteRule . /index.html [L]
    </Directory>
</VirtualHost>
```

## 🔒 Seguridad

- **Token JWT**: Almacenado en localStorage
- **HTTPS**: Recomendado en producción
- **Variables de Entorno**: No commitear `.env`
- **Validación**: Frontend + Backend
- **Sanitización**: Escape de HTML automático en React

