from rest_framework import viewsets, filters
from rest_framework.decorators import action
from rest_framework.response import Response
from django_filters.rest_framework import DjangoFilterBackend
from django_filters import FilterSet, CharFilter, ChoiceFilter, NumberFilter
from .models import Circunscripcion
from .serializers import CircunscripcionSerializer
from utils.excel_exporter import export_to_excel
from rest_framework import status

class CircunscripcionFilter(FilterSet):
    nombre = CharFilter(lookup_expr='icontains')
    codigo = CharFilter(lookup_expr='icontains')
    tipo = ChoiceFilter(choices=Circunscripcion.TIPO_CHOICES)
    capital = CharFilter(lookup_expr='icontains')
    poblacion_min = NumberFilter(field_name='poblacion', lookup_expr='gte')
    poblacion_max = NumberFilter(field_name='poblacion', lookup_expr='lte')
    
    class Meta:
        model = Circunscripcion
        fields = ['nombre', 'codigo', 'tipo', 'capital']


class CircunscripcionViewSet(viewsets.ModelViewSet):
    queryset = Circunscripcion.objects.all()
    serializer_class = CircunscripcionSerializer
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_class = CircunscripcionFilter
    search_fields = ['nombre', 'codigo', 'capital']
    ordering_fields = ['nombre', 'poblacion', 'electores_registrados', 'numero_diputados']
    ordering = ['nombre']

    @action(detail=False, methods=['get'], pagination_class=None)
    def select_list(self, request):
        circunscripciones = self.queryset.all().order_by('nombre')
        serializer = self.get_serializer(circunscripciones, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'], pagination_class=None)
    def todas(self, request):
        circunscripciones = Circunscripcion.objects.all().order_by('nombre')
        serializer = self.get_serializer(circunscripciones, many=True)
        return Response({
            'count': circunscripciones.count(),
            'results': serializer.data
        })
    
    @action(detail=False, methods=['get'])
    def departamentos(self, request):
        circunscripciones = self.queryset.filter(tipo='departamento')
        serializer = self.get_serializer(circunscripciones, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def estadisticas(self, request):
        return Response({
            'total': self.queryset.count(),
            'departamentos': self.queryset.filter(tipo='departamento').count(),
            'provincias_constitucionales': self.queryset.filter(tipo='provincia_constitucional').count(),
            'exterior': self.queryset.filter(tipo='exterior').count(),
            'total_diputados': sum(c.numero_diputados for c in self.queryset.all()),
            'total_senadores_regionales': sum(c.numero_senadores for c in self.queryset.all())
        })
    
    @action(detail=False, methods=['get'])
    def exportar_excel(self, request):
        circunscripciones = self.filter_queryset(self.get_queryset())
        
        headers = [
            'ID', 'Nombre', 'Código', 'Tipo', 'Capital', 'Población', 
            'Electores Registrados', 'Imagen URL', 'Bandera URL', 'Escudo URL',
            'N° Diputados', 'N° Senadores', 'Latitud', 'Longitud', 'Descripción'
        ]
        
        data = []
        for circ in circunscripciones:
            data.append([
                circ.id,
                circ.nombre or '',
                circ.codigo or '',
                circ.tipo or '',
                circ.capital or '',
                circ.poblacion or '',
                circ.electores_registrados or '',
                circ.imagen_url or '',
                circ.bandera_url or '',
                circ.escudo_url or '',
                circ.numero_diputados or '',
                circ.numero_senadores or '',
                circ.latitud or '',
                circ.longitud or '',
                circ.descripcion or ''
            ])
        
        return export_to_excel(data, 'circunscripciones', headers, 'Circunscripciones')
    
    @action(detail=False, methods=['post'])
    def importar_excel(self, request):
        from utils.excel_importer import import_from_excel
        
        if 'file' not in request.FILES:
            return Response({'error': 'No se proporcionó archivo'}, status=status.HTTP_400_BAD_REQUEST)
        
        file = request.FILES['file']
        
        field_mapping = {
            'ID': 'id',
            'Nombre': 'nombre',
            'Código': 'codigo',
            'Tipo': 'tipo',
            'Capital': 'capital',
            'Población': 'poblacion',
            'Electores Registrados': 'electores_registrados',
            'Imagen URL': 'imagen_url',
            'Bandera URL': 'bandera_url',
            'Escudo URL': 'escudo_url',
            'N° Diputados': 'numero_diputados',
            'N° Senadores': 'numero_senadores',
            'Latitud': 'latitud',
            'Longitud': 'longitud',
            'Descripción': 'descripcion'
        }
        
        result = import_from_excel(file, Circunscripcion, field_mapping)
        
        return Response({
            'message': 'Importación completada',
            'creados': result['created'],
            'actualizados': result['updated'],
            'errores': result['errors'],
            'total_procesados': result['total_processed']
        })