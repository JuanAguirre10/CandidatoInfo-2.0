from django.contrib import admin
from django.urls import path, include
from django.conf import settings
from django.conf.urls.static import static
from rest_framework.routers import DefaultRouter
from partidos.views import PartidoPoliticoViewSet
from circunscripciones.views import CircunscripcionViewSet
from candidatos.views import (
    CandidatoPresidencialViewSet,
    CandidatoSenadorNacionalViewSet,
    CandidatoSenadorRegionalViewSet,
    CandidatoDiputadoViewSet,
    CandidatoParlamentoAndinoViewSet
)
from simulacro.views import SimulacroVotoViewSet
from usuarios.views import UsuarioViewSet
from informacion.views import PropuestaViewSet, ProyectoRealizadoViewSet, DenunciaViewSet


router = DefaultRouter()

# Registro de ViewSets
router.register(r'partidos', PartidoPoliticoViewSet, basename='partido')
router.register(r'circunscripciones', CircunscripcionViewSet, basename='circunscripcion')
router.register(r'candidatos/presidenciales', CandidatoPresidencialViewSet, basename='candidato-presidencial')
router.register(r'candidatos/senadores-nacionales', CandidatoSenadorNacionalViewSet, basename='senador-nacional')
router.register(r'candidatos/senadores-regionales', CandidatoSenadorRegionalViewSet, basename='senador-regional')
router.register(r'candidatos/diputados', CandidatoDiputadoViewSet, basename='diputado')
router.register(r'candidatos/parlamento-andino', CandidatoParlamentoAndinoViewSet, basename='parlamento-andino')
router.register(r'simulacro/votos', SimulacroVotoViewSet, basename='simulacro-voto')
router.register(r'usuarios', UsuarioViewSet, basename='usuario')
router.register(r'informacion/propuestas', PropuestaViewSet, basename='propuesta')
router.register(r'informacion/proyectos', ProyectoRealizadoViewSet, basename='proyecto-realizado')
router.register(r'informacion/denuncias', DenunciaViewSet, basename='denuncia')

urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/', include(router.urls)),
]

# Servir archivos media en desarrollo
if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
    urlpatterns += static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)