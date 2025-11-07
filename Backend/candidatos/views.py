from rest_framework import viewsets, filters
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework import status
from django_filters.rest_framework import DjangoFilterBackend
from django_filters import FilterSet, CharFilter, ChoiceFilter, NumberFilter
from .models import (
    CandidatoPresidencial,
    CandidatoSenadorNacional,
    CandidatoSenadorRegional,
    CandidatoDiputado,
    CandidatoParlamentoAndino
)
from .serializers import (
    CandidatoPresidencialSerializer,
    CandidatoPresidencialListSerializer,
    CandidatoSenadorNacionalSerializer,
    CandidatoSenadorNacionalListSerializer,
    CandidatoSenadorRegionalSerializer,
    CandidatoSenadorRegionalListSerializer,
    CandidatoDiputadoSerializer,
    CandidatoDiputadoListSerializer,
    CandidatoParlamentoAndinoSerializer,
    CandidatoParlamentoAndinoListSerializer
)
from utils.excel_exporter import export_to_excel


class CandidatoPresidencialFilter(FilterSet):
    presidente_nombre = CharFilter(lookup_expr='icontains')
    presidente_apellidos = CharFilter(lookup_expr='icontains')
    estado = ChoiceFilter(choices=CandidatoPresidencial.ESTADO_CHOICES)
    partido = NumberFilter(field_name='partido__id')
    genero = ChoiceFilter(field_name='presidente_genero')
    
    class Meta:
        model = CandidatoPresidencial
        fields = ['presidente_nombre', 'presidente_apellidos', 'estado', 'partido']


class CandidatoPresidencialViewSet(viewsets.ModelViewSet):
    queryset = CandidatoPresidencial.objects.select_related('partido').all()
    serializer_class = CandidatoPresidencialSerializer
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_class = CandidatoPresidencialFilter
    search_fields = ['presidente_nombre', 'presidente_apellidos', 'partido__nombre', 'partido__siglas']
    ordering_fields = ['numero_lista', 'fecha_inscripcion']
    ordering = ['numero_lista']
    
    def get_serializer_class(self):
        if self.action == 'list':
            return CandidatoPresidencialListSerializer
        return CandidatoPresidencialSerializer
    
    @action(detail=False, methods=['get'])
    def aprobados(self, request):
        candidatos = self.queryset.filter(estado='aprobado')
        serializer = CandidatoPresidencialListSerializer(candidatos, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def por_partido(self, request):
        partido_id = request.query_params.get('partido_id')
        if partido_id:
            candidato = self.queryset.filter(partido_id=partido_id).first()
            if candidato:
                serializer = self.get_serializer(candidato)
                return Response(serializer.data)
        return Response({'error': 'Partido no encontrado'}, status=404)
    
    @action(detail=False, methods=['get'], pagination_class=None)
    def todos(self, request):
        candidatos = self.queryset.filter(estado='inscrito')
        serializer = CandidatoPresidencialListSerializer(candidatos, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def exportar_excel(self, request):
        candidatos = self.filter_queryset(self.get_queryset())
        
        headers = [
            'ID', 'Partido ID', 'Presidente Nombre', 'Presidente Apellidos', 'Presidente DNI', 
            'Presidente Género', 'Presidente Fecha Nacimiento', 'Presidente Profesión', 
            'Presidente Biografía', 'Presidente Foto URL', 'Vicepresidente1 Nombre', 
            'Vicepresidente1 Apellidos', 'Vicepresidente1 DNI', 'Vicepresidente1 Género', 
            'Vicepresidente1 Fecha Nacimiento', 'Vicepresidente1 Profesión', 
            'Vicepresidente1 Biografía', 'Vicepresidente1 Foto URL', 'Vicepresidente2 Nombre',
            'Vicepresidente2 Apellidos', 'Vicepresidente2 DNI', 'Vicepresidente2 Género',
            'Vicepresidente2 Fecha Nacimiento', 'Vicepresidente2 Profesión', 
            'Vicepresidente2 Biografía', 'Vicepresidente2 Foto URL', 'Plan Gobierno URL',
            'Número Lista', 'Estado', 'Fecha Inscripción'
        ]
        
        data = []
        for cand in candidatos:
            data.append([
                cand.id,
                cand.partido.id if cand.partido else '',
                cand.presidente_nombre or '',
                cand.presidente_apellidos or '',
                cand.presidente_dni or '',
                cand.presidente_genero or '',
                cand.presidente_fecha_nacimiento or '',
                cand.presidente_profesion or '',
                cand.presidente_biografia or '',
                cand.presidente_foto_url or '',
                cand.vicepresidente1_nombre or '',
                cand.vicepresidente1_apellidos or '',
                cand.vicepresidente1_dni or '',
                cand.vicepresidente1_genero or '',
                cand.vicepresidente1_fecha_nacimiento or '',
                cand.vicepresidente1_profesion or '',
                cand.vicepresidente1_biografia or '',
                cand.vicepresidente1_foto_url or '',
                cand.vicepresidente2_nombre or '',
                cand.vicepresidente2_apellidos or '',
                cand.vicepresidente2_dni or '',
                cand.vicepresidente2_genero or '',
                cand.vicepresidente2_fecha_nacimiento or '',
                cand.vicepresidente2_profesion or '',
                cand.vicepresidente2_biografia or '',
                cand.vicepresidente2_foto_url or '',
                cand.plan_gobierno_url or '',
                cand.numero_lista or '',
                cand.estado or '',
                cand.fecha_inscripcion or ''
            ])
        
        return export_to_excel(data, 'candidatos_presidenciales', headers, 'Candidatos Presidenciales')
    
    @action(detail=False, methods=['post'])
    def importar_excel(self, request):
        from utils.excel_importer import import_from_excel
        
        if 'file' not in request.FILES:
            return Response({'error': 'No se proporcionó archivo'}, status=status.HTTP_400_BAD_REQUEST)
        
        file = request.FILES['file']
        
        field_mapping = {
            'ID': 'id',
            'Partido ID': 'partido_id',
            'Presidente Nombre': 'presidente_nombre',
            'Presidente Apellidos': 'presidente_apellidos',
            'Presidente DNI': 'presidente_dni',
            'Presidente Género': 'presidente_genero',
            'Presidente Fecha Nacimiento': 'presidente_fecha_nacimiento',
            'Presidente Profesión': 'presidente_profesion',
            'Presidente Biografía': 'presidente_biografia',
            'Presidente Foto URL': 'presidente_foto_url',
            'Vicepresidente1 Nombre': 'vicepresidente1_nombre',
            'Vicepresidente1 Apellidos': 'vicepresidente1_apellidos',
            'Vicepresidente1 DNI': 'vicepresidente1_dni',
            'Vicepresidente1 Género': 'vicepresidente1_genero',
            'Vicepresidente1 Fecha Nacimiento': 'vicepresidente1_fecha_nacimiento',
            'Vicepresidente1 Profesión': 'vicepresidente1_profesion',
            'Vicepresidente1 Biografía': 'vicepresidente1_biografia',
            'Vicepresidente1 Foto URL': 'vicepresidente1_foto_url',
            'Vicepresidente2 Nombre': 'vicepresidente2_nombre',
            'Vicepresidente2 Apellidos': 'vicepresidente2_apellidos',
            'Vicepresidente2 DNI': 'vicepresidente2_dni',
            'Vicepresidente2 Género': 'vicepresidente2_genero',
            'Vicepresidente2 Fecha Nacimiento': 'vicepresidente2_fecha_nacimiento',
            'Vicepresidente2 Profesión': 'vicepresidente2_profesion',
            'Vicepresidente2 Biografía': 'vicepresidente2_biografia',
            'Vicepresidente2 Foto URL': 'vicepresidente2_foto_url',
            'Plan Gobierno URL': 'plan_gobierno_url',
            'Número Lista': 'numero_lista',
            'Estado': 'estado',
            'Fecha Inscripción': 'fecha_inscripcion'
        }
        
        result = import_from_excel(file, CandidatoPresidencial, field_mapping)
        
        return Response({
            'message': 'Importación completada',
            'creados': result['created'],
            'actualizados': result['updated'],
            'errores': result['errors'],
            'total_procesados': result['total_processed']
        })


class CandidatoSenadorNacionalFilter(FilterSet):
    nombre = CharFilter(lookup_expr='icontains')
    apellidos = CharFilter(lookup_expr='icontains')
    dni = CharFilter(lookup_expr='icontains')
    estado = ChoiceFilter(choices=CandidatoSenadorNacional.ESTADO_CHOICES)
    partido = NumberFilter(field_name='partido__id')
    genero = ChoiceFilter()
    
    class Meta:
        model = CandidatoSenadorNacional
        fields = ['nombre', 'apellidos', 'dni', 'estado', 'partido', 'genero']


class CandidatoSenadorNacionalViewSet(viewsets.ModelViewSet):
    queryset = CandidatoSenadorNacional.objects.select_related('partido').all()
    serializer_class = CandidatoSenadorNacionalSerializer
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_class = CandidatoSenadorNacionalFilter
    search_fields = ['nombre', 'apellidos', 'partido__nombre', 'partido__siglas', 'dni']
    ordering_fields = ['posicion_lista', 'partido', 'fecha_inscripcion']
    ordering = ['partido', 'posicion_lista']
    
    def get_serializer_class(self):
        if self.action == 'list':
            return CandidatoSenadorNacionalListSerializer
        return CandidatoSenadorNacionalSerializer
    
    @action(detail=False, methods=['get'])
    def aprobados(self, request):
        candidatos = self.queryset.filter(estado='aprobado')
        serializer = CandidatoSenadorNacionalListSerializer(candidatos, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def por_partido(self, request):
        partido_id = request.query_params.get('partido_id')
        if partido_id:
            candidatos = self.queryset.filter(partido_id=partido_id, estado='aprobado')
            serializer = CandidatoSenadorNacionalListSerializer(candidatos, many=True)
            return Response(serializer.data)
        return Response({'error': 'Debe especificar partido_id'}, status=400)
    
    @action(detail=False, methods=['get'], pagination_class=None)
    def todos(self, request):
        candidatos = self.queryset.filter(estado='inscrito')
        serializer = CandidatoSenadorNacionalListSerializer(candidatos, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def exportar_excel(self, request):
        candidatos = self.filter_queryset(self.get_queryset())
        
        headers = [
            'ID', 'Partido ID', 'Nombre', 'Apellidos', 'DNI', 'Género', 
            'Fecha Nacimiento', 'Edad', 'Profesión', 'Experiencia Política',
            'Biografía', 'Foto URL', 'Hoja Vida URL', 'Posición Lista', 
            'Número Preferencial', 'Estado', 'Fecha Inscripción'
        ]
        
        data = []
        for cand in candidatos:
            data.append([
                cand.id,
                cand.partido.id if cand.partido else '',
                cand.nombre or '',
                cand.apellidos or '',
                cand.dni or '',
                cand.genero or '',
                cand.fecha_nacimiento or '',
                cand.edad or '',
                cand.profesion or '',
                cand.experiencia_politica or '',
                cand.biografia or '',
                cand.foto_url or '',
                cand.hoja_vida_url or '',
                cand.posicion_lista or '',
                cand.numero_preferencial or '',
                cand.estado or '',
                cand.fecha_inscripcion or ''
            ])
        
        return export_to_excel(data, 'senadores_nacionales', headers, 'Senadores Nacionales')
    
    @action(detail=False, methods=['post'])
    def importar_excel(self, request):
        from utils.excel_importer import import_from_excel
        
        if 'file' not in request.FILES:
            return Response({'error': 'No se proporcionó archivo'}, status=status.HTTP_400_BAD_REQUEST)
        
        file = request.FILES['file']
        
        field_mapping = {
            'ID': 'id',
            'Partido ID': 'partido_id',
            'Nombre': 'nombre',
            'Apellidos': 'apellidos',
            'DNI': 'dni',
            'Género': 'genero',
            'Fecha Nacimiento': 'fecha_nacimiento',
            'Edad': 'edad',
            'Profesión': 'profesion',
            'Experiencia Política': 'experiencia_politica',
            'Biografía': 'biografia',
            'Foto URL': 'foto_url',
            'Hoja Vida URL': 'hoja_vida_url',
            'Posición Lista': 'posicion_lista',
            'Número Preferencial': 'numero_preferencial',
            'Estado': 'estado',
            'Fecha Inscripción': 'fecha_inscripcion'
        }
        
        result = import_from_excel(file, CandidatoSenadorNacional, field_mapping)
        
        return Response({
            'message': 'Importación completada',
            'creados': result['created'],
            'actualizados': result['updated'],
            'errores': result['errors'],
            'total_procesados': result['total_processed']
        })


class CandidatoSenadorRegionalFilter(FilterSet):
    nombre = CharFilter(lookup_expr='icontains')
    apellidos = CharFilter(lookup_expr='icontains')
    dni = CharFilter(lookup_expr='icontains')
    estado = ChoiceFilter(choices=CandidatoSenadorRegional.ESTADO_CHOICES)
    partido = NumberFilter(field_name='partido__id')
    circunscripcion = NumberFilter(field_name='circunscripcion__id')
    genero = ChoiceFilter()
    
    class Meta:
        model = CandidatoSenadorRegional
        fields = ['nombre', 'apellidos', 'dni', 'estado', 'partido', 'circunscripcion', 'genero']


class CandidatoSenadorRegionalViewSet(viewsets.ModelViewSet):
    queryset = CandidatoSenadorRegional.objects.select_related('partido', 'circunscripcion').all()
    serializer_class = CandidatoSenadorRegionalSerializer
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_class = CandidatoSenadorRegionalFilter
    search_fields = ['nombre', 'apellidos', 'partido__nombre', 'circunscripcion__nombre', 'dni']
    ordering_fields = ['posicion_lista', 'partido', 'circunscripcion', 'fecha_inscripcion']
    ordering = ['circunscripcion', 'partido', 'posicion_lista']
    
    def get_serializer_class(self):
        if self.action == 'list':
            return CandidatoSenadorRegionalListSerializer
        return CandidatoSenadorRegionalSerializer
    
    @action(detail=False, methods=['get'])
    def aprobados(self, request):
        candidatos = self.queryset.filter(estado='aprobado')
        serializer = CandidatoSenadorRegionalListSerializer(candidatos, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def por_circunscripcion(self, request):
        circunscripcion_id = request.query_params.get('circunscripcion_id')
        if circunscripcion_id:
            candidatos = self.queryset.filter(circunscripcion_id=circunscripcion_id, estado='aprobado')
            serializer = CandidatoSenadorRegionalListSerializer(candidatos, many=True)
            return Response(serializer.data)
        return Response({'error': 'Debe especificar circunscripcion_id'}, status=400)
    
    @action(detail=False, methods=['get'])
    def por_partido_y_circunscripcion(self, request):
        partido_id = request.query_params.get('partido_id')
        circunscripcion_id = request.query_params.get('circunscripcion_id')
        if partido_id and circunscripcion_id:
            candidatos = self.queryset.filter(
                partido_id=partido_id,
                circunscripcion_id=circunscripcion_id,
                estado='aprobado'
            )
            serializer = CandidatoSenadorRegionalListSerializer(candidatos, many=True)
            return Response(serializer.data)
        return Response({'error': 'Debe especificar partido_id y circunscripcion_id'}, status=400)
    
    @action(detail=False, methods=['get'], pagination_class=None)
    def todos(self, request):
        candidatos = self.queryset.filter(estado='inscrito')
        serializer = CandidatoSenadorRegionalListSerializer(candidatos, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def exportar_excel(self, request):
        candidatos = self.filter_queryset(self.get_queryset())
        
        headers = [
            'ID', 'Partido ID', 'Circunscripción ID', 'Nombre', 'Apellidos', 'DNI', 
            'Género', 'Fecha Nacimiento', 'Edad', 'Profesión', 'Experiencia Política',
            'Biografía', 'Foto URL', 'Hoja Vida URL', 'Posición Lista', 
            'Número Preferencial', 'Es Natural Circunscripción', 'Estado', 'Fecha Inscripción'
        ]
        
        data = []
        for cand in candidatos:
            data.append([
                cand.id,
                cand.partido.id if cand.partido else '',
                cand.circunscripcion.id if cand.circunscripcion else '',
                cand.nombre or '',
                cand.apellidos or '',
                cand.dni or '',
                cand.genero or '',
                cand.fecha_nacimiento or '',
                cand.edad or '',
                cand.profesion or '',
                cand.experiencia_politica or '',
                cand.biografia or '',
                cand.foto_url or '',
                cand.hoja_vida_url or '',
                cand.posicion_lista or '',
                cand.numero_preferencial or '',
                cand.es_natural_circunscripcion or '',
                cand.estado or '',
                cand.fecha_inscripcion or ''
            ])
        
        return export_to_excel(data, 'senadores_regionales', headers, 'Senadores Regionales')
    
    @action(detail=False, methods=['post'])
    def importar_excel(self, request):
        from utils.excel_importer import import_from_excel
        
        if 'file' not in request.FILES:
            return Response({'error': 'No se proporcionó archivo'}, status=status.HTTP_400_BAD_REQUEST)
        
        file = request.FILES['file']
        
        field_mapping = {
            'ID': 'id',
            'Partido ID': 'partido_id',
            'Circunscripción ID': 'circunscripcion_id',
            'Nombre': 'nombre',
            'Apellidos': 'apellidos',
            'DNI': 'dni',
            'Género': 'genero',
            'Fecha Nacimiento': 'fecha_nacimiento',
            'Edad': 'edad',
            'Profesión': 'profesion',
            'Experiencia Política': 'experiencia_politica',
            'Biografía': 'biografia',
            'Foto URL': 'foto_url',
            'Hoja Vida URL': 'hoja_vida_url',
            'Posición Lista': 'posicion_lista',
            'Número Preferencial': 'numero_preferencial',
            'Es Natural Circunscripción': 'es_natural_circunscripcion',
            'Estado': 'estado',
            'Fecha Inscripción': 'fecha_inscripcion'
        }
        
        result = import_from_excel(file, CandidatoSenadorRegional, field_mapping)
        
        return Response({
            'message': 'Importación completada',
            'creados': result['created'],
            'actualizados': result['updated'],
            'errores': result['errors'],
            'total_procesados': result['total_processed']
        })


class CandidatoDiputadoFilter(FilterSet):
    nombre = CharFilter(lookup_expr='icontains')
    apellidos = CharFilter(lookup_expr='icontains')
    dni = CharFilter(lookup_expr='icontains')
    estado = ChoiceFilter(choices=CandidatoDiputado.ESTADO_CHOICES)
    partido = NumberFilter(field_name='partido__id')
    circunscripcion = NumberFilter(field_name='circunscripcion__id')
    genero = ChoiceFilter()
    
    class Meta:
        model = CandidatoDiputado
        fields = ['nombre', 'apellidos', 'dni', 'estado', 'partido', 'circunscripcion', 'genero']


class CandidatoDiputadoViewSet(viewsets.ModelViewSet):
    queryset = CandidatoDiputado.objects.select_related('partido', 'circunscripcion').all()
    serializer_class = CandidatoDiputadoSerializer
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_class = CandidatoDiputadoFilter
    search_fields = ['nombre', 'apellidos', 'partido__nombre', 'circunscripcion__nombre', 'dni']
    ordering_fields = ['posicion_lista', 'partido', 'circunscripcion', 'fecha_inscripcion']
    ordering = ['circunscripcion', 'partido', 'posicion_lista']
    
    def get_serializer_class(self):
        if self.action == 'list':
            return CandidatoDiputadoListSerializer
        return CandidatoDiputadoSerializer
    
    @action(detail=False, methods=['get'])
    def aprobados(self, request):
        candidatos = self.queryset.filter(estado='aprobado')
        serializer = CandidatoDiputadoListSerializer(candidatos, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def por_circunscripcion(self, request):
        circunscripcion_id = request.query_params.get('circunscripcion_id')
        if circunscripcion_id:
            candidatos = self.queryset.filter(circunscripcion_id=circunscripcion_id, estado='aprobado')
            serializer = CandidatoDiputadoListSerializer(candidatos, many=True)
            return Response(serializer.data)
        return Response({'error': 'Debe especificar circunscripcion_id'}, status=400)
    
    @action(detail=False, methods=['get'])
    def por_partido_y_circunscripcion(self, request):
        partido_id = request.query_params.get('partido_id')
        circunscripcion_id = request.query_params.get('circunscripcion_id')
        if partido_id and circunscripcion_id:
            candidatos = self.queryset.filter(
                partido_id=partido_id,
                circunscripcion_id=circunscripcion_id,
                estado='aprobado'
            )
            serializer = CandidatoDiputadoListSerializer(candidatos, many=True)
            return Response(serializer.data)
        return Response({'error': 'Debe especificar partido_id y circunscripcion_id'}, status=400)
    
    @action(detail=False, methods=['get'], pagination_class=None)
    def todos(self, request):
        candidatos = self.queryset.filter(estado='inscrito')
        serializer = CandidatoDiputadoListSerializer(candidatos, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def exportar_excel(self, request):
        candidatos = self.filter_queryset(self.get_queryset())
        
        headers = [
            'ID', 'Partido ID', 'Circunscripción ID', 'Nombre', 'Apellidos', 'DNI', 
            'Género', 'Fecha Nacimiento', 'Edad', 'Profesión', 'Experiencia Política',
            'Biografía', 'Foto URL', 'Hoja Vida URL', 'Posición Lista', 
            'Número Preferencial', 'Es Natural Circunscripción', 'Estado', 'Fecha Inscripción'
        ]
        
        data = []
        for cand in candidatos:
            data.append([
                cand.id,
                cand.partido.id if cand.partido else '',
                cand.circunscripcion.id if cand.circunscripcion else '',
                cand.nombre or '',
                cand.apellidos or '',
                cand.dni or '',
                cand.genero or '',
                cand.fecha_nacimiento or '',
                cand.edad or '',
                cand.profesion or '',
                cand.experiencia_politica or '',
                cand.biografia or '',
                cand.foto_url or '',
                cand.hoja_vida_url or '',
                cand.posicion_lista or '',
                cand.numero_preferencial or '',
                cand.es_natural_circunscripcion or '',
                cand.estado or '',
                cand.fecha_inscripcion or ''
            ])
        
        return export_to_excel(data, 'diputados', headers, 'Diputados')
    
    @action(detail=False, methods=['post'])
    def importar_excel(self, request):
        from utils.excel_importer import import_from_excel
        
        if 'file' not in request.FILES:
            return Response({'error': 'No se proporcionó archivo'}, status=status.HTTP_400_BAD_REQUEST)
        
        file = request.FILES['file']
        
        field_mapping = {
            'ID': 'id',
            'Partido ID': 'partido_id',
            'Circunscripción ID': 'circunscripcion_id',
            'Nombre': 'nombre',
            'Apellidos': 'apellidos',
            'DNI': 'dni',
            'Género': 'genero',
            'Fecha Nacimiento': 'fecha_nacimiento',
            'Edad': 'edad',
            'Profesión': 'profesion',
            'Experiencia Política': 'experiencia_politica',
            'Biografía': 'biografia',
            'Foto URL': 'foto_url',
            'Hoja Vida URL': 'hoja_vida_url',
            'Posición Lista': 'posicion_lista',
            'Número Preferencial': 'numero_preferencial',
            'Es Natural Circunscripción': 'es_natural_circunscripcion',
            'Estado': 'estado',
            'Fecha Inscripción': 'fecha_inscripcion'
        }
        
        result = import_from_excel(file, CandidatoDiputado, field_mapping)
        
        return Response({
            'message': 'Importación completada',
            'creados': result['created'],
            'actualizados': result['updated'],
            'errores': result['errors'],
            'total_procesados': result['total_processed']
        })


class CandidatoParlamentoAndinoFilter(FilterSet):
    nombre = CharFilter(lookup_expr='icontains')
    apellidos = CharFilter(lookup_expr='icontains')
    dni = CharFilter(lookup_expr='icontains')
    estado = ChoiceFilter(choices=CandidatoParlamentoAndino.ESTADO_CHOICES)
    partido = NumberFilter(field_name='partido__id')
    genero = ChoiceFilter()
    
    class Meta:
        model = CandidatoParlamentoAndino
        fields = ['nombre', 'apellidos', 'dni', 'estado', 'partido', 'genero']


class CandidatoParlamentoAndinoViewSet(viewsets.ModelViewSet):
    queryset = CandidatoParlamentoAndino.objects.select_related('partido').all()
    serializer_class = CandidatoParlamentoAndinoSerializer
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_class = CandidatoParlamentoAndinoFilter
    search_fields = ['nombre', 'apellidos', 'partido__nombre', 'partido__siglas', 'dni']
    ordering_fields = ['posicion_lista', 'partido', 'fecha_inscripcion']
    ordering = ['partido', 'posicion_lista']
    
    def get_serializer_class(self):
        if self.action == 'list':
            return CandidatoParlamentoAndinoListSerializer
        return CandidatoParlamentoAndinoSerializer
    
    @action(detail=False, methods=['get'])
    def aprobados(self, request):
        candidatos = self.queryset.filter(estado='aprobado')
        serializer = CandidatoParlamentoAndinoListSerializer(candidatos, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def por_partido(self, request):
        partido_id = request.query_params.get('partido_id')
        if partido_id:
            candidatos = self.queryset.filter(partido_id=partido_id, estado='aprobado')
            serializer = CandidatoParlamentoAndinoListSerializer(candidatos, many=True)
            return Response(serializer.data)
        return Response({'error': 'Debe especificar partido_id'}, status=400)
    
    @action(detail=False, methods=['get'], pagination_class=None)
    def todos(self, request):
        candidatos = self.queryset.filter(estado='inscrito')
        serializer = CandidatoParlamentoAndinoListSerializer(candidatos, many=True)
        return Response(serializer.data)
    
    @action(detail=False, methods=['get'])
    def exportar_excel(self, request):
        candidatos = self.filter_queryset(self.get_queryset())
        
        headers = [
            'ID', 'Partido ID', 'Nombre', 'Apellidos', 'DNI', 'Género', 
            'Fecha Nacimiento', 'Edad', 'Profesión', 'Experiencia Política',
            'Experiencia Internacional', 'Biografía', 'Foto URL', 'Hoja Vida URL', 
            'Posición Lista', 'Número Preferencial', 'Idiomas', 'Estado', 'Fecha Inscripción'
        ]
        
        data = []
        for cand in candidatos:
            data.append([
                cand.id,
                cand.partido.id if cand.partido else '',
                cand.nombre or '',
                cand.apellidos or '',
                cand.dni or '',
                cand.genero or '',
                cand.fecha_nacimiento or '',
                cand.edad or '',
                cand.profesion or '',
                cand.experiencia_politica or '',
                cand.experiencia_internacional or '',
                cand.biografia or '',
                cand.foto_url or '',
                cand.hoja_vida_url or '',
                cand.posicion_lista or '',
                cand.numero_preferencial or '',
                cand.idiomas or '',
                cand.estado or '',
                cand.fecha_inscripcion or ''
            ])
        
        return export_to_excel(data, 'parlamento_andino', headers, 'Parlamento Andino')
    
    @action(detail=False, methods=['post'])
    def importar_excel(self, request):
        from utils.excel_importer import import_from_excel
        
        if 'file' not in request.FILES:
            return Response({'error': 'No se proporcionó archivo'}, status=status.HTTP_400_BAD_REQUEST)
        
        file = request.FILES['file']
        
        field_mapping = {
            'ID': 'id',
            'Partido ID': 'partido_id',
            'Nombre': 'nombre',
            'Apellidos': 'apellidos',
            'DNI': 'dni',
            'Género': 'genero',
            'Fecha Nacimiento': 'fecha_nacimiento',
            'Edad': 'edad',
            'Profesión': 'profesion',
            'Experiencia Política': 'experiencia_politica',
            'Experiencia Internacional': 'experiencia_internacional',
            'Biografía': 'biografia',
            'Foto URL': 'foto_url',
            'Hoja Vida URL': 'hoja_vida_url',
            'Posición Lista': 'posicion_lista',
            'Número Preferencial': 'numero_preferencial',
            'Idiomas': 'idiomas',
            'Estado': 'estado',
            'Fecha Inscripción': 'fecha_inscripcion'
        }
        
        result = import_from_excel(file, CandidatoParlamentoAndino, field_mapping)
        
        return Response({
            'message': 'Importación completada',
            'creados': result['created'],
            'actualizados': result['updated'],
            'errores': result['errors'],
            'total_procesados': result['total_processed']
        })