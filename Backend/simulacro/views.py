from rest_framework import viewsets, status, filters
from rest_framework.decorators import action
from rest_framework.response import Response
from django_filters.rest_framework import DjangoFilterBackend
from django_filters import FilterSet, CharFilter, NumberFilter, DateTimeFilter
from django.db.models import Count
from django.utils import timezone
import requests
from decouple import config
from .models import SimulacroVoto
from .serializers import (
    SimulacroVotoSerializer,
    SimulacroVotoCreateSerializer,
    EstadisticasSimulacroSerializer
)
from utils.excel_exporter import export_to_excel


class SimulacroVotoFilter(FilterSet):
    dni = CharFilter(lookup_expr='icontains')
    tipo_eleccion = CharFilter()
    mes_simulacro = NumberFilter()
    anio_simulacro = NumberFilter()
    fecha_voto_desde = DateTimeFilter(field_name='fecha_voto', lookup_expr='gte')
    fecha_voto_hasta = DateTimeFilter(field_name='fecha_voto', lookup_expr='lte')
    
    class Meta:
        model = SimulacroVoto
        fields = ['dni', 'tipo_eleccion', 'mes_simulacro', 'anio_simulacro']


class SimulacroVotoViewSet(viewsets.ModelViewSet):
    queryset = SimulacroVoto.objects.select_related('circunscripcion').all()
    serializer_class = SimulacroVotoSerializer
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_class = SimulacroVotoFilter
    search_fields = ['dni', 'nombre_completo']
    ordering_fields = ['fecha_voto', 'mes_simulacro', 'anio_simulacro']
    ordering = ['-fecha_voto']
    
    def get_serializer_class(self):
        if self.action == 'create':
            return SimulacroVotoCreateSerializer
        return SimulacroVotoSerializer
    
    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        
        dni = serializer.validated_data['dni']
        
        nombre_completo = self.validar_dni_api(dni)
        
        if not nombre_completo:
            return Response(
                {'error': 'DNI no válido. Por favor, verifica el número de DNI ingresado.'},
                status=status.HTTP_400_BAD_REQUEST
            )
        
        serializer.validated_data['nombre_completo'] = nombre_completo
        serializer.validated_data['ip_address'] = self.get_client_ip(request)
        
        self.perform_create(serializer)
        headers = self.get_success_headers(serializer.data)
        return Response(
            {
                'message': 'Voto registrado exitosamente',
                'data': serializer.data,
                'votante': nombre_completo
            },
            status=status.HTTP_201_CREATED,
            headers=headers
        )
    
    def validar_dni_api(self, dni):
        api_token = config('DNI_API_TOKEN', default='')
        
        if not api_token:
            return f"Usuario DNI {dni}"
        
        try:
            headers = {
                'Authorization': f'Bearer {api_token}',
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
            params = {'numero': dni}
            response = requests.get(
                'https://api.decolecta.com/v1/reniec/dni',
                headers=headers,
                params=params,
                timeout=5
            )
            
            if response.status_code == 200:
                data = response.json()
                first_name = data.get('first_name', '')
                first_last_name = data.get('first_last_name', '')
                second_last_name = data.get('second_last_name', '')
                return f"{first_name} {first_last_name} {second_last_name}".strip()
            
            return None
        except Exception as e:
            print(f"Error validando DNI: {str(e)}")
            return f"Usuario DNI {dni}"
    
    def get_client_ip(self, request):
        x_forwarded_for = request.META.get('HTTP_X_FORWARDED_FOR')
        if x_forwarded_for:
            ip = x_forwarded_for.split(',')[0]
        else:
            ip = request.META.get('REMOTE_ADDR')
        return ip
    
    @action(detail=False, methods=['post'])
    def verificar_voto(self, request):
        dni = request.data.get('dni')
        tipo_eleccion = request.data.get('tipo_eleccion')
        mes = request.data.get('mes_simulacro')
        anio = request.data.get('anio_simulacro')
        
        if not all([dni, tipo_eleccion, mes, anio]):
            return Response(
                {'error': 'Debe proporcionar dni, tipo_eleccion, mes_simulacro y anio_simulacro'},
                status=status.HTTP_400_BAD_REQUEST
            )
        
        ya_voto = SimulacroVoto.objects.filter(
            dni=dni,
            tipo_eleccion=tipo_eleccion,
            mes_simulacro=mes,
            anio_simulacro=anio
        ).exists()
        
        return Response({
            'ya_voto': ya_voto,
            'mensaje': 'Ya registró su voto para este simulacro' if ya_voto else 'Puede proceder a votar'
        })
    
    @action(detail=False, methods=['get'])
    def estadisticas(self, request):
        mes = request.query_params.get('mes_simulacro')
        anio = request.query_params.get('anio_simulacro')
        
        if not mes or not anio:
            now = timezone.now()
            mes = now.month
            anio = now.year
        
        votos = SimulacroVoto.objects.filter(
            mes_simulacro=mes,
            anio_simulacro=anio
        )
        
        estadisticas_por_tipo = votos.values('tipo_eleccion').annotate(
            total_votos=Count('id')
        )
        
        return Response({
            'mes': mes,
            'anio': anio,
            'total_votos': votos.count(),
            'por_tipo_eleccion': estadisticas_por_tipo,
            'votos_unicos': votos.values('dni').distinct().count()
        })
    
    @action(detail=False, methods=['get'])
    def resultados_por_candidato(self, request):
        tipo_eleccion = request.query_params.get('tipo_eleccion')
        mes = request.query_params.get('mes_simulacro')
        anio = request.query_params.get('anio_simulacro')
        
        if not all([tipo_eleccion, mes, anio]):
            return Response(
                {'error': 'Debe especificar tipo_eleccion, mes_simulacro y anio_simulacro'},
                status=status.HTTP_400_BAD_REQUEST
            )
        
        resultados = SimulacroVoto.objects.filter(
            tipo_eleccion=tipo_eleccion,
            mes_simulacro=mes,
            anio_simulacro=anio
        ).values('candidato_id').annotate(
            votos=Count('id')
        ).order_by('-votos')
        
        total_votos = sum(r['votos'] for r in resultados)
        
        for resultado in resultados:
            resultado['porcentaje'] = round((resultado['votos'] / total_votos * 100), 2) if total_votos > 0 else 0
        
        return Response({
            'tipo_eleccion': tipo_eleccion,
            'mes': mes,
            'anio': anio,
            'total_votos': total_votos,
            'resultados': resultados
        })
    
    @action(detail=False, methods=['get'])
    def exportar_excel(self, request):
        votos = self.filter_queryset(self.get_queryset())
        
        headers = [
            'ID', 'DNI', 'Nombre Completo', 'Tipo Elección', 
            'Candidato ID', 'Circunscripción', 'Mes', 'Año', 'Fecha Voto'
        ]
        
        data = []
        for voto in votos:
            data.append([
                voto.id,
                voto.dni,
                voto.nombre_completo or '',
                voto.get_tipo_eleccion_display(),
                voto.candidato_id,
                voto.circunscripcion.nombre if voto.circunscripcion else '',
                voto.mes_simulacro,
                voto.anio_simulacro,
                voto.fecha_voto.strftime('%Y-%m-%d %H:%M:%S')
            ])
        
        return export_to_excel(data, 'simulacro_votos', headers, 'Votos Simulacro')
    
    @action(detail=False, methods=['post'])
    def importar_excel(self, request):
        from utils.excel_importer import import_from_excel
        
        if 'file' not in request.FILES:
            return Response({'error': 'No se proporcionó archivo'}, status=status.HTTP_400_BAD_REQUEST)
        
        file = request.FILES['file']
        
        field_mapping = {
            'ID': 'id',
            'DNI': 'dni',
            'Nombre Completo': 'nombre_completo',
            'Tipo Elección': 'tipo_eleccion',
            'Candidato ID': 'candidato_id',
            'Circunscripción': 'circunscripcion_id',
            'Mes': 'mes_simulacro',
            'Año': 'anio_simulacro'
        }
        
        result = import_from_excel(file, SimulacroVoto, field_mapping)
        
        return Response({
            'message': 'Importación completada',
            'creados': result['created'],
            'actualizados': result['updated'],
            'errores': result['errors'],
            'total_procesados': result['total_processed']
        })