from rest_framework import serializers
from .models import SimulacroVoto
from circunscripciones.serializers import CircunscripcionSerializer

class SimulacroVotoSerializer(serializers.ModelSerializer):
    circunscripcion_detalle = CircunscripcionSerializer(source='circunscripcion', read_only=True)
    
    class Meta:
        model = SimulacroVoto
        fields = '__all__'
        read_only_fields = ['fecha_voto', 'nombre_completo']

class SimulacroVotoCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = SimulacroVoto
        fields = ['dni', 'tipo_eleccion', 'candidato_id', 'circunscripcion', 'mes_simulacro', 'anio_simulacro']
    
    def validate_dni(self, value):
        """Valida que el DNI tenga 8 dígitos"""
        if not value.isdigit() or len(value) != 8:
            raise serializers.ValidationError("El DNI debe tener exactamente 8 dígitos numéricos")
        return value
    
    def validate(self, data):
        """Valida que no exista un voto duplicado"""
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
    """Serializer para estadísticas del simulacro"""
    tipo_eleccion = serializers.CharField()
    mes_simulacro = serializers.IntegerField()
    anio_simulacro = serializers.IntegerField()
    total_votos = serializers.IntegerField()
    candidato_id = serializers.IntegerField()
    votos_candidato = serializers.IntegerField()