from rest_framework import serializers
from .models import (
    CandidatoPresidencial,
    CandidatoSenadorNacional,
    CandidatoSenadorRegional,
    CandidatoDiputado,
    CandidatoParlamentoAndino
)
from partidos.serializers import PartidoPoliticoSerializer
from circunscripciones.serializers import CircunscripcionSerializer

class CandidatoPresidencialSerializer(serializers.ModelSerializer):
    partido_detalle = PartidoPoliticoSerializer(source='partido', read_only=True)
    
    class Meta:
        model = CandidatoPresidencial
        fields = '__all__'
        read_only_fields = ['fecha_registro', 'fecha_actualizacion']

class CandidatoPresidencialListSerializer(serializers.ModelSerializer):
    """Serializer simplificado para listar candidatos presidenciales"""
    partido_nombre = serializers.CharField(source='partido.nombre', read_only=True)
    partido_siglas = serializers.CharField(source='partido.siglas', read_only=True)
    partido_logo = serializers.CharField(source='partido.logo_url', read_only=True)
    
    class Meta:
        model = CandidatoPresidencial
        fields = [
            'id', 'partido', 'partido_nombre', 'partido_siglas', 'partido_logo',
            'presidente_nombre', 'presidente_apellidos', 'presidente_dni', 
            'presidente_foto_url', 'presidente_genero', 'presidente_profesion',
            'vicepresidente1_nombre', 'vicepresidente1_apellidos', 'vicepresidente1_profesion',
            'vicepresidente2_nombre', 'vicepresidente2_apellidos', 'vicepresidente2_profesion',
            'numero_lista', 'estado'
        ]


class CandidatoSenadorNacionalSerializer(serializers.ModelSerializer):
    partido_detalle = PartidoPoliticoSerializer(source='partido', read_only=True)
    
    class Meta:
        model = CandidatoSenadorNacional
        fields = '__all__'
        read_only_fields = ['fecha_registro', 'fecha_actualizacion']

class CandidatoSenadorNacionalListSerializer(serializers.ModelSerializer):
    """Serializer simplificado para listar senadores nacionales"""
    partido_nombre = serializers.CharField(source='partido.nombre', read_only=True, allow_null=True)
    partido_siglas = serializers.CharField(source='partido.siglas', read_only=True, allow_null=True)
    partido_logo = serializers.CharField(source='partido.logo_url', read_only=True, allow_null=True)
    nombre_completo = serializers.SerializerMethodField()
    
    class Meta:
        model = CandidatoSenadorNacional
        fields = [
            'id', 'partido', 'partido_nombre', 'partido_siglas', 'partido_logo',
            'nombre', 'apellidos', 'nombre_completo', 'dni', 'foto_url', 
            'genero', 'edad', 'fecha_nacimiento', 'posicion_lista',
            'numero_preferencial', 'profesion', 'experiencia_politica',
            'biografia', 'hoja_vida_url', 'estado', 'fecha_inscripcion'
        ]
    
    def get_nombre_completo(self, obj):
        return f"{obj.nombre} {obj.apellidos}" if obj.nombre and obj.apellidos else obj.nombre or obj.apellidos or ""


class CandidatoSenadorRegionalSerializer(serializers.ModelSerializer):
    partido_detalle = PartidoPoliticoSerializer(source='partido', read_only=True)
    circunscripcion_detalle = CircunscripcionSerializer(source='circunscripcion', read_only=True)
    
    class Meta:
        model = CandidatoSenadorRegional
        fields = '__all__'
        read_only_fields = ['fecha_registro', 'fecha_actualizacion']

class CandidatoSenadorRegionalListSerializer(serializers.ModelSerializer):
    partido_nombre = serializers.CharField(source='partido.nombre', read_only=True)
    partido_siglas = serializers.CharField(source='partido.siglas', read_only=True)
    circunscripcion_nombre = serializers.CharField(source='circunscripcion.nombre', read_only=True)
    nombre_completo = serializers.SerializerMethodField()
    
    class Meta:
        model = CandidatoSenadorRegional
        fields = [
            'id', 'partido', 'partido_nombre', 'partido_siglas',
            'circunscripcion', 'circunscripcion_nombre',
            'nombre', 'apellidos', 'nombre_completo', 'foto_url', 'genero', 'posicion_lista',
            'profesion', 'estado'
        ]
    
    def get_nombre_completo(self, obj):
        return f"{obj.nombre} {obj.apellidos}"


class CandidatoDiputadoSerializer(serializers.ModelSerializer):
    partido_detalle = PartidoPoliticoSerializer(source='partido', read_only=True)
    circunscripcion_detalle = CircunscripcionSerializer(source='circunscripcion', read_only=True)
    
    class Meta:
        model = CandidatoDiputado
        fields = '__all__'
        read_only_fields = ['fecha_registro', 'fecha_actualizacion']

class CandidatoDiputadoListSerializer(serializers.ModelSerializer):
    partido_nombre = serializers.CharField(source='partido.nombre', read_only=True)
    partido_siglas = serializers.CharField(source='partido.siglas', read_only=True)
    circunscripcion_nombre = serializers.CharField(source='circunscripcion.nombre', read_only=True)
    nombre_completo = serializers.SerializerMethodField()
    
    class Meta:
        model = CandidatoDiputado
        fields = [
            'id', 'partido', 'partido_nombre', 'partido_siglas',
            'circunscripcion', 'circunscripcion_nombre',
            'nombre', 'apellidos', 'nombre_completo', 'foto_url', 'genero', 'posicion_lista',
            'profesion', 'estado'
        ]
    
    def get_nombre_completo(self, obj):
        return f"{obj.nombre} {obj.apellidos}"


class CandidatoParlamentoAndinoSerializer(serializers.ModelSerializer):
    partido_detalle = PartidoPoliticoSerializer(source='partido', read_only=True)
    
    class Meta:
        model = CandidatoParlamentoAndino
        fields = '__all__'
        read_only_fields = ['fecha_registro', 'fecha_actualizacion']

class CandidatoParlamentoAndinoListSerializer(serializers.ModelSerializer):
    partido_nombre = serializers.CharField(source='partido.nombre', read_only=True)
    partido_siglas = serializers.CharField(source='partido.siglas', read_only=True)
    nombre_completo = serializers.SerializerMethodField()
    
    class Meta:
        model = CandidatoParlamentoAndino
        fields = [
            'id', 'partido', 'partido_nombre', 'partido_siglas',
            'nombre', 'apellidos', 'nombre_completo', 'foto_url', 'genero', 'posicion_lista',
            'profesion', 'idiomas', 'estado'
        ]
    
    def get_nombre_completo(self, obj):
        return f"{obj.nombre} {obj.apellidos}"