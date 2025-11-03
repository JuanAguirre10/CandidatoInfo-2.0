from rest_framework import serializers
from .models import PartidoPolitico

class PartidoPoliticoSerializer(serializers.ModelSerializer):
    class Meta:
        model = PartidoPolitico
        fields = '__all__'
        read_only_fields = ['fecha_registro', 'fecha_actualizacion']