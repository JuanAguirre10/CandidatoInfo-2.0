from rest_framework import serializers
from .models import Usuario
import bcrypt

class UsuarioSerializer(serializers.ModelSerializer):
    class Meta:
        model = Usuario
        fields = ['id', 'username', 'email', 'nombre_completo', 'rol', 'avatar_url', 
                  'estado', 'ultimo_acceso', 'fecha_registro', 'fecha_actualizacion']
        read_only_fields = ['fecha_registro', 'fecha_actualizacion', 'ultimo_acceso']

class UsuarioCreateSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True, required=True)
    
    class Meta:
        model = Usuario
        fields = ['username', 'email', 'password', 'nombre_completo', 'rol', 'avatar_url']
    
    def create(self, validated_data):
        password = validated_data.pop('password')
        password_hash = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')
        validated_data['password_hash'] = password_hash
        return Usuario.objects.create(**validated_data)

class LoginSerializer(serializers.Serializer):
    username = serializers.CharField(required=True)
    password = serializers.CharField(required=True, write_only=True)

class ChangePasswordSerializer(serializers.Serializer):
    old_password = serializers.CharField(required=True, write_only=True)
    new_password = serializers.CharField(required=True, write_only=True)