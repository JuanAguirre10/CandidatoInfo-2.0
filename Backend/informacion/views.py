from rest_framework import viewsets, filters, status
from rest_framework.decorators import action
from rest_framework.response import Response
from django_filters.rest_framework import DjangoFilterBackend
from django_filters import FilterSet, CharFilter, NumberFilter, ChoiceFilter
from .models import Propuesta, ProyectoRealizado, Denuncia
from .serializers import PropuestaSerializer, ProyectoRealizadoSerializer, DenunciaSerializer
from utils.excel_exporter import export_to_excel


class PropuestaFilter(FilterSet):
    candidato_id = NumberFilter()
    tipo_candidato = ChoiceFilter(choices=Propuesta.TIPO_CANDIDATO_CHOICES)
    titulo = CharFilter(lookup_expr='icontains')
    categoria = CharFilter(lookup_expr='icontains')
    eje_tematico = CharFilter(lookup_expr='icontains')
    
    class Meta:
        model = Propuesta
        fields = ['candidato_id', 'tipo_candidato', 'titulo', 'categoria', 'eje_tematico']


class PropuestaViewSet(viewsets.ModelViewSet):
    queryset = Propuesta.objects.all()
    serializer_class = PropuestaSerializer
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_class = PropuestaFilter
    search_fields = ['titulo', 'descripcion', 'categoria']
    ordering_fields = ['fecha_publicacion', 'titulo', 'costo_estimado']
    ordering = ['-fecha_publicacion']
    
    @action(detail=False, methods=['get'])
    def por_candidato(self, request):
        candidato_id = request.query_params.get('candidato_id')
        tipo_candidato = request.query_params.get('tipo_candidato')
        
        if not candidato_id or not tipo_candidato:
            return Response(
                {'error': 'Debe especificar candidato_id y tipo_candidato'},
                status=status.HTTP_400_BAD_REQUEST
            )
        
        propuestas = self.queryset.filter(candidato_id=candidato_id, tipo_candidato=tipo_candidato)
        serializer = self.get_serializer(propuestas, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def exportar_excel(self, request):
        propuestas = self.filter_queryset(self.get_queryset())
        
        headers = [
            'ID', 'Candidato ID', 'Tipo Candidato', 'Título', 'Descripción',
            'Categoría', 'Eje Temático', 'Costo Estimado', 'Plazo Implementación',
            'Beneficiarios', 'Archivo URL', 'Fecha Publicación'
        ]
        
        data = []
        for prop in propuestas:
            data.append([
                prop.id,
                prop.candidato_id or '',
                prop.tipo_candidato or '',
                prop.titulo or '',
                prop.descripcion or '',
                prop.categoria or '',
                prop.eje_tematico or '',
                prop.costo_estimado or '',
                prop.plazo_implementacion or '',
                prop.beneficiarios or '',
                prop.archivo_url or '',
                prop.fecha_publicacion or ''
            ])
        
        return export_to_excel(data, 'propuestas', headers, 'Propuestas')
    
    @action(detail=False, methods=['post'])
    def importar_excel(self, request):
        from utils.excel_importer import import_from_excel
        
        if 'file' not in request.FILES:
            return Response({'error': 'No se proporcionó archivo'}, status=status.HTTP_400_BAD_REQUEST)
        
        file = request.FILES['file']
        
        field_mapping = {
            'ID': 'id',
            'Candidato ID': 'candidato_id',
            'Tipo Candidato': 'tipo_candidato',
            'Título': 'titulo',
            'Descripción': 'descripcion',
            'Categoría': 'categoria',
            'Eje Temático': 'eje_tematico',
            'Costo Estimado': 'costo_estimado',
            'Plazo Implementación': 'plazo_implementacion',
            'Beneficiarios': 'beneficiarios',
            'Archivo URL': 'archivo_url',
            'Fecha Publicación': 'fecha_publicacion'
        }
        
        result = import_from_excel(file, Propuesta, field_mapping)
        
        return Response({
            'message': 'Importación completada',
            'creados': result['created'],
            'actualizados': result['updated'],
            'errores': result['errors'],
            'total_procesados': result['total_processed']
        })


class ProyectoRealizadoFilter(FilterSet):
    candidato_id = NumberFilter()
    tipo_candidato = ChoiceFilter(choices=ProyectoRealizado.TIPO_CANDIDATO_CHOICES)
    titulo = CharFilter(lookup_expr='icontains')
    estado = ChoiceFilter(choices=ProyectoRealizado.ESTADO_CHOICES)
    ubicacion = CharFilter(lookup_expr='icontains')
    
    class Meta:
        model = ProyectoRealizado
        fields = ['candidato_id', 'tipo_candidato', 'titulo', 'estado', 'ubicacion']


class ProyectoRealizadoViewSet(viewsets.ModelViewSet):
    queryset = ProyectoRealizado.objects.all()
    serializer_class = ProyectoRealizadoSerializer
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_class = ProyectoRealizadoFilter
    search_fields = ['titulo', 'descripcion', 'ubicacion']
    ordering_fields = ['fecha_inicio', 'fecha_fin', 'monto_invertido']
    ordering = ['-fecha_inicio']
    
    @action(detail=False, methods=['get'])
    def por_candidato(self, request):
        candidato_id = request.query_params.get('candidato_id')
        tipo_candidato = request.query_params.get('tipo_candidato')
        
        if not candidato_id or not tipo_candidato:
            return Response(
                {'error': 'Debe especificar candidato_id y tipo_candidato'},
                status=status.HTTP_400_BAD_REQUEST
            )
        
        proyectos = self.queryset.filter(candidato_id=candidato_id, tipo_candidato=tipo_candidato)
        serializer = self.get_serializer(proyectos, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def exportar_excel(self, request):
        proyectos = self.filter_queryset(self.get_queryset())
        
        headers = [
            'ID', 'Candidato ID', 'Tipo Candidato', 'Título', 'Descripción',
            'Cargo Periodo', 'Fecha Inicio', 'Fecha Fin', 'Monto Invertido',
            'Beneficiarios', 'Resultados', 'Ubicación', 'Evidencia URL',
            'Imagen URL', 'Estado'
        ]
        
        data = []
        for proy in proyectos:
            data.append([
                proy.id,
                proy.candidato_id or '',
                proy.tipo_candidato or '',
                proy.titulo or '',
                proy.descripcion or '',
                proy.cargo_periodo or '',
                proy.fecha_inicio or '',
                proy.fecha_fin or '',
                proy.monto_invertido or '',
                proy.beneficiarios or '',
                proy.resultados or '',
                proy.ubicacion or '',
                proy.evidencia_url or '',
                proy.imagen_url or '',
                proy.estado or ''
            ])
        
        return export_to_excel(data, 'proyectos_realizados', headers, 'Proyectos Realizados')
    
    @action(detail=False, methods=['post'])
    def importar_excel(self, request):
        from utils.excel_importer import import_from_excel
        
        if 'file' not in request.FILES:
            return Response({'error': 'No se proporcionó archivo'}, status=status.HTTP_400_BAD_REQUEST)
        
        file = request.FILES['file']
        
        field_mapping = {
            'ID': 'id',
            'Candidato ID': 'candidato_id',
            'Tipo Candidato': 'tipo_candidato',
            'Título': 'titulo',
            'Descripción': 'descripcion',
            'Cargo Periodo': 'cargo_periodo',
            'Fecha Inicio': 'fecha_inicio',
            'Fecha Fin': 'fecha_fin',
            'Monto Invertido': 'monto_invertido',
            'Beneficiarios': 'beneficiarios',
            'Resultados': 'resultados',
            'Ubicación': 'ubicacion',
            'Evidencia URL': 'evidencia_url',
            'Imagen URL': 'imagen_url',
            'Estado': 'estado'
        }
        
        result = import_from_excel(file, ProyectoRealizado, field_mapping)
        
        return Response({
            'message': 'Importación completada',
            'creados': result['created'],
            'actualizados': result['updated'],
            'errores': result['errors'],
            'total_procesados': result['total_processed']
        })


class DenunciaFilter(FilterSet):
    candidato_id = NumberFilter()
    tipo_candidato = ChoiceFilter(choices=Denuncia.TIPO_CANDIDATO_CHOICES)
    titulo = CharFilter(lookup_expr='icontains')
    estado_proceso = ChoiceFilter(choices=Denuncia.ESTADO_PROCESO_CHOICES)
    gravedad = ChoiceFilter(choices=Denuncia.GRAVEDAD_CHOICES)
    tipo_denuncia = CharFilter(lookup_expr='icontains')
    
    class Meta:
        model = Denuncia
        fields = ['candidato_id', 'tipo_candidato', 'titulo', 'estado_proceso', 'gravedad', 'tipo_denuncia']


class DenunciaViewSet(viewsets.ModelViewSet):
    queryset = Denuncia.objects.all()
    serializer_class = DenunciaSerializer
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_class = DenunciaFilter
    search_fields = ['titulo', 'descripcion', 'entidad_denunciante']
    ordering_fields = ['fecha_denuncia', 'gravedad', 'monto_involucrado']
    ordering = ['-fecha_denuncia']
    
    @action(detail=False, methods=['get'])
    def por_candidato(self, request):
        candidato_id = request.query_params.get('candidato_id')
        tipo_candidato = request.query_params.get('tipo_candidato')
        
        if not candidato_id or not tipo_candidato:
            return Response(
                {'error': 'Debe especificar candidato_id y tipo_candidato'},
                status=status.HTTP_400_BAD_REQUEST
            )
        
        denuncias = self.queryset.filter(candidato_id=candidato_id, tipo_candidato=tipo_candidato)
        serializer = self.get_serializer(denuncias, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def exportar_excel(self, request):
        denuncias = self.filter_queryset(self.get_queryset())
        
        headers = [
            'ID', 'Candidato ID', 'Tipo Candidato', 'Título', 'Descripción',
            'Tipo Denuncia', 'Fecha Denuncia', 'Entidad Denunciante', 'Fuente',
            'URL Fuente', 'Estado Proceso', 'Monto Involucrado', 'Documento URL', 'Gravedad'
        ]
        
        data = []
        for den in denuncias:
            data.append([
                den.id,
                den.candidato_id or '',
                den.tipo_candidato or '',
                den.titulo or '',
                den.descripcion or '',
                den.tipo_denuncia or '',
                den.fecha_denuncia or '',
                den.entidad_denunciante or '',
                den.fuente or '',
                den.url_fuente or '',
                den.estado_proceso or '',
                den.monto_involucrado or '',
                den.documento_url or '',
                den.gravedad or ''
            ])
        
        return export_to_excel(data, 'denuncias', headers, 'Denuncias')
    
    @action(detail=False, methods=['post'])
    def importar_excel(self, request):
        from utils.excel_importer import import_from_excel
        
        if 'file' not in request.FILES:
            return Response({'error': 'No se proporcionó archivo'}, status=status.HTTP_400_BAD_REQUEST)
        
        file = request.FILES['file']
        
        field_mapping = {
            'ID': 'id',
            'Candidato ID': 'candidato_id',
            'Tipo Candidato': 'tipo_candidato',
            'Título': 'titulo',
            'Descripción': 'descripcion',
            'Tipo Denuncia': 'tipo_denuncia',
            'Fecha Denuncia': 'fecha_denuncia',
            'Entidad Denunciante': 'entidad_denunciante',
            'Fuente': 'fuente',
            'URL Fuente': 'url_fuente',
            'Estado Proceso': 'estado_proceso',
            'Monto Involucrado': 'monto_involucrado',
            'Documento URL': 'documento_url',
            'Gravedad': 'gravedad'
        }
        
        result = import_from_excel(file, Denuncia, field_mapping)
        
        return Response({
            'message': 'Importación completada',
            'creados': result['created'],
            'actualizados': result['updated'],
            'errores': result['errors'],
            'total_procesados': result['total_processed']
        })