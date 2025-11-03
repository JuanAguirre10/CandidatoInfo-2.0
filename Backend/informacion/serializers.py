from rest_framework import serializers
from .models import Propuesta, ProyectoRealizado, Denuncia


class PropuestaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Propuesta
        fields = '__all__'
        read_only_fields = ['fecha_registro', 'fecha_actualizacion']


class ProyectoRealizadoSerializer(serializers.ModelSerializer):
    class Meta:
        model = ProyectoRealizado
        fields = '__all__'
        read_only_fields = ['fecha_registro', 'fecha_actualizacion']


class DenunciaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Denuncia
        fields = '__all__'
        read_only_fields = ['fecha_registro', 'fecha_actualizacion']