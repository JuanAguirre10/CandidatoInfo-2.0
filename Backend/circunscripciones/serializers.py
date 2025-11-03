from rest_framework import serializers
from .models import Circunscripcion

class CircunscripcionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Circunscripcion
        fields = '__all__'
        read_only_fields = ['fecha_registro']