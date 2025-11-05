from rest_framework import viewsets, filters, status
from rest_framework.decorators import action
from rest_framework.response import Response
from django_filters.rest_framework import DjangoFilterBackend
from django_filters import FilterSet, CharFilter, ChoiceFilter
from .models import PartidoPolitico
from .serializers import PartidoPoliticoSerializer
from utils.excel_exporter import export_to_excel
import logging

logger = logging.getLogger(__name__)


class PartidoPoliticoFilter(FilterSet):
    nombre = CharFilter(lookup_expr='icontains')
    siglas = CharFilter(lookup_expr='icontains')
    tipo = ChoiceFilter(choices=PartidoPolitico.TIPO_CHOICES)
    estado = ChoiceFilter(choices=PartidoPolitico.ESTADO_CHOICES)
    ideologia = CharFilter(lookup_expr='icontains')
    
    class Meta:
        model = PartidoPolitico
        fields = ['nombre', 'siglas', 'tipo', 'estado', 'ideologia']


class PartidoPoliticoViewSet(viewsets.ModelViewSet):
    queryset = PartidoPolitico.objects.all()
    serializer_class = PartidoPoliticoSerializer
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_class = PartidoPoliticoFilter
    search_fields = ['nombre', 'siglas', 'ideologia', 'lider']
    ordering_fields = ['nombre', 'siglas', 'fecha_registro', 'fundacion_año']
    ordering = ['nombre']

    @action(detail=False, methods=['get'], pagination_class=None)
    def select_list(self, request):
        """Endpoint sin paginación para poblar selects en formularios"""
        partidos = self.queryset.filter(estado='activo').order_by('nombre')
        serializer = self.get_serializer(partidos, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def activos(self, request):
        partidos = self.queryset.filter(estado='activo')
        serializer = self.get_serializer(partidos, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def por_tipo(self, request):
        partidos_count = self.queryset.filter(tipo='partido', estado='activo').count()
        alianzas_count = self.queryset.filter(tipo='alianza', estado='activo').count()
        return Response({
            'partidos': partidos_count,
            'alianzas': alianzas_count,
            'total': partidos_count + alianzas_count
        })
    
    @action(detail=False, methods=['get'])
    def exportar_excel(self, request):
        partidos = self.filter_queryset(self.get_queryset())
        
        headers = [
            'ID', 'Nombre', 'Siglas', 'Logo URL', 'Color Principal', 'Tipo', 
            'Ideología', 'Líder', 'Secretario General', 'Año Fundación', 
            'Descripción', 'Sitio Web', 'Estado'
        ]
        
        data = []
        for partido in partidos:
            data.append([
                partido.id,
                partido.nombre or '',
                partido.siglas or '',
                partido.logo_url or '',
                partido.color_principal or '',
                partido.tipo or '',
                partido.ideologia or '',
                partido.lider or '',
                partido.secretario_general or '',
                partido.fundacion_año or '',
                partido.descripcion or '',
                partido.sitio_web or '',
                partido.estado or ''
            ])
        
        return export_to_excel(data, 'partidos_politicos', headers, 'Partidos Políticos')
    
    @action(detail=False, methods=['post'])
    def importar_excel(self, request):
        from utils.excel_importer import import_from_excel
        
        logger.info("Iniciando importación de partidos políticos")
        
        if 'file' not in request.FILES:
            logger.error("No se proporcionó archivo en la petición")
            return Response(
                {'error': 'No se proporcionó archivo'}, 
                status=status.HTTP_400_BAD_REQUEST
            )
        
        file = request.FILES['file']
        logger.info(f"Archivo recibido: {file.name}, tamaño: {file.size} bytes")
        
        field_mapping = {
            'ID': 'id',
            'Nombre': 'nombre',
            'Siglas': 'siglas',
            'Logo URL': 'logo_url',
            'Color Principal': 'color_principal',
            'Tipo': 'tipo',
            'Ideología': 'ideologia',
            'Líder': 'lider',
            'Secretario General': 'secretario_general',
            'Año Fundación': 'fundacion_año',
            'Descripción': 'descripcion',
            'Sitio Web': 'sitio_web',
            'Estado': 'estado'
        }
        
        try:
            result = import_from_excel(file, PartidoPolitico, field_mapping)
            logger.info(f"Resultado de importación: {result}")
            
            return Response({
                'message': 'Importación completada',
                'creados': result['created'],
                'actualizados': result['updated'],
                'errores': result['errors'],
                'total_procesados': result['total_processed']
            }, status=status.HTTP_200_OK)
            
        except Exception as e:
            logger.error(f"Error durante la importación: {str(e)}", exc_info=True)
            return Response(
                {'error': f'Error durante la importación: {str(e)}'}, 
                status=status.HTTP_500_INTERNAL_SERVER_ERROR
            )