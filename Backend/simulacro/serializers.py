from rest_framework import serializers
from .models import SimulacroVoto
from circunscripciones.serializers import CircunscripcionSerializer
from candidatos.models import (
    CandidatoPresidencial,
    CandidatoSenadorNacional,
    CandidatoSenadorRegional,
    CandidatoDiputado,
    CandidatoParlamentoAndino
)


class SimulacroVotoSerializer(serializers.ModelSerializer):
    circunscripcion_nombre = serializers.CharField(
        source='circunscripcion.nombre', 
        read_only=True
    )
    candidato_nombre = serializers.SerializerMethodField()
    partido_nombre = serializers.SerializerMethodField()
    partido_siglas = serializers.SerializerMethodField()
    partido_logo = serializers.SerializerMethodField()
    tipo_eleccion_display = serializers.CharField(
        source='get_tipo_eleccion_display', 
        read_only=True
    )
    
    class Meta:
        model = SimulacroVoto
        fields = [
            'id', 'dni', 'nombre_completo', 'tipo_eleccion', 
            'tipo_eleccion_display', 'candidato_id', 'candidato_nombre',
            'partido_nombre', 'partido_siglas', 'partido_logo',
            'circunscripcion', 'circunscripcion_nombre',
            'mes_simulacro', 'anio_simulacro', 'fecha_voto', 'ip_address'
        ]
        read_only_fields = ['fecha_voto', 'nombre_completo']
    
    def get_candidato_nombre(self, obj):
        """Obtiene el nombre del candidato según el tipo de elección"""
        try:
            if obj.tipo_eleccion == 'presidencial':
                candidato = CandidatoPresidencial.objects.get(id=obj.candidato_id)
                return f"{candidato.presidente_nombre} {candidato.presidente_apellidos}"
            
            elif obj.tipo_eleccion == 'senador_nacional':
                candidato = CandidatoSenadorNacional.objects.get(id=obj.candidato_id)
                return f"{candidato.nombre} {candidato.apellidos}"
            
            elif obj.tipo_eleccion == 'senador_regional':
                candidato = CandidatoSenadorRegional.objects.get(id=obj.candidato_id)
                return f"{candidato.nombre} {candidato.apellidos}"
            
            elif obj.tipo_eleccion == 'diputado':
                candidato = CandidatoDiputado.objects.get(id=obj.candidato_id)
                return f"{candidato.nombre} {candidato.apellidos}"
            
            elif obj.tipo_eleccion == 'parlamento_andino':
                candidato = CandidatoParlamentoAndino.objects.get(id=obj.candidato_id)
                return f"{candidato.nombre} {candidato.apellidos}"
            
            return f"Candidato ID {obj.candidato_id}"
        except Exception as e:
            return f"Candidato ID {obj.candidato_id}"
    
    def get_partido_nombre(self, obj):
        """Obtiene el nombre del partido político"""
        try:
            candidato = self._get_candidato_object(obj)
            if candidato and candidato.partido:
                return candidato.partido.nombre
            return None
        except:
            return None
    
    def get_partido_siglas(self, obj):
        """Obtiene las siglas del partido político"""
        try:
            candidato = self._get_candidato_object(obj)
            if candidato and candidato.partido:
                return candidato.partido.siglas
            return None
        except:
            return None
    
    def get_partido_logo(self, obj):
        """Obtiene la URL del logo del partido político"""
        try:
            candidato = self._get_candidato_object(obj)
            if candidato and candidato.partido:
                return candidato.partido.logo_url
            return None
        except:
            return None
    
    def _get_candidato_object(self, obj):
        """Método auxiliar para obtener el objeto candidato"""
        try:
            if obj.tipo_eleccion == 'presidencial':
                return CandidatoPresidencial.objects.select_related('partido').get(id=obj.candidato_id)
            elif obj.tipo_eleccion == 'senador_nacional':
                return CandidatoSenadorNacional.objects.select_related('partido').get(id=obj.candidato_id)
            elif obj.tipo_eleccion == 'senador_regional':
                return CandidatoSenadorRegional.objects.select_related('partido').get(id=obj.candidato_id)
            elif obj.tipo_eleccion == 'diputado':
                return CandidatoDiputado.objects.select_related('partido').get(id=obj.candidato_id)
            elif obj.tipo_eleccion == 'parlamento_andino':
                return CandidatoParlamentoAndino.objects.select_related('partido').get(id=obj.candidato_id)
            return None
        except:
            return None


class SimulacroVotoCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = SimulacroVoto
        fields = ['dni', 'tipo_eleccion', 'candidato_id', 'circunscripcion', 'mes_simulacro', 'anio_simulacro']

    def validate_dni(self, value):
        if not value.isdigit() or len(value) != 8:
            raise serializers.ValidationError("El DNI debe tener exactamente 8 dígitos numéricos")
        return value

    def validate(self, data):
        existe_voto = SimulacroVoto.objects.filter(
            dni=data['dni'],
            tipo_eleccion=data['tipo_eleccion'],
            mes_simulacro=data['mes_simulacro'],
            anio_simulacro=data['anio_simulacro']
        ).exists()
        if existe_voto:
            raise serializers.ValidationError(
                f"Ya existe un voto registrado para este DNI en {data['tipo_eleccion']} "
                f"del mes {data['mes_simulacro']}/{data['anio_simulacro']}"
            )
        return data


class EstadisticasSimulacroSerializer(serializers.Serializer):
    tipo_eleccion = serializers.CharField()
    mes_simulacro = serializers.IntegerField()
    anio_simulacro = serializers.IntegerField()
    total_votos = serializers.IntegerField()
    candidato_id = serializers.IntegerField()
    votos_candidato = serializers.IntegerField()