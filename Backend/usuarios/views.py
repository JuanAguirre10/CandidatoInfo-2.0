from rest_framework import viewsets, status
from rest_framework.decorators import action
from rest_framework.response import Response
from django.utils import timezone
import bcrypt
from .models import Usuario
from .serializers import (
    UsuarioSerializer,
    UsuarioCreateSerializer,
    LoginSerializer,
    ChangePasswordSerializer
)

class UsuarioViewSet(viewsets.ModelViewSet):
    queryset = Usuario.objects.all()
    serializer_class = UsuarioSerializer
    
    def get_serializer_class(self):
        if self.action == 'create':
            return UsuarioCreateSerializer
        return UsuarioSerializer
    
    @action(detail=False, methods=['post'])
    def login(self, request):
        """Endpoint de login para usuarios"""
        serializer = LoginSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        
        username = serializer.validated_data['username']
        password = serializer.validated_data['password']
        
        try:
            usuario = Usuario.objects.get(username=username, estado='activo')
        except Usuario.DoesNotExist:
            return Response(
                {'error': 'Usuario o contraseña incorrectos'},
                status=status.HTTP_401_UNAUTHORIZED
            )
        
        # Verificar contraseña
        if not bcrypt.checkpw(password.encode('utf-8'), usuario.password_hash.encode('utf-8')):
            return Response(
                {'error': 'Usuario o contraseña incorrectos'},
                status=status.HTTP_401_UNAUTHORIZED
            )
        
        # Actualizar último acceso
        usuario.ultimo_acceso = timezone.now()
        usuario.save()
        
        # Retornar datos del usuario
        usuario_data = UsuarioSerializer(usuario).data
        return Response({
            'message': 'Login exitoso',
            'usuario': usuario_data
        })
    
    @action(detail=True, methods=['post'])
    def cambiar_password(self, request, pk=None):
        """Cambiar contraseña de usuario"""
        usuario = self.get_object()
        serializer = ChangePasswordSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        
        old_password = serializer.validated_data['old_password']
        new_password = serializer.validated_data['new_password']
        
        # Verificar contraseña antigua
        if not bcrypt.checkpw(old_password.encode('utf-8'), usuario.password_hash.encode('utf-8')):
            return Response(
                {'error': 'La contraseña actual es incorrecta'},
                status=status.HTTP_400_BAD_REQUEST
            )
        
        # Actualizar contraseña
        new_password_hash = bcrypt.hashpw(new_password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')
        usuario.password_hash = new_password_hash
        usuario.save()
        
        return Response({'message': 'Contraseña actualizada exitosamente'})
    
    @action(detail=True, methods=['post'])
    def cambiar_estado(self, request, pk=None):
        """Cambiar estado de un usuario (activo/inactivo/suspendido)"""
        usuario = self.get_object()
        nuevo_estado = request.data.get('estado')
        
        if nuevo_estado not in ['activo', 'inactivo', 'suspendido']:
            return Response(
                {'error': 'Estado inválido. Debe ser: activo, inactivo o suspendido'},
                status=status.HTTP_400_BAD_REQUEST
            )
        
        usuario.estado = nuevo_estado
        usuario.save()
        
        return Response({
            'message': f'Estado del usuario actualizado a {nuevo_estado}',
            'usuario': UsuarioSerializer(usuario).data
        })