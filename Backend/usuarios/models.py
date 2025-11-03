from django.db import models

class Usuario(models.Model):
    ROL_CHOICES = [
        ('admin', 'Administrador'),
        ('editor', 'Editor'),
        ('visualizador', 'Visualizador'),
    ]
    
    ESTADO_CHOICES = [
        ('activo', 'Activo'),
        ('inactivo', 'Inactivo'),
        ('suspendido', 'Suspendido'),
    ]
    
    username = models.CharField(max_length=100, null=True, blank=True)
    email = models.EmailField(max_length=200, null=True, blank=True)
    password_hash = models.CharField(max_length=255, null=True, blank=True)
    nombre_completo = models.CharField(max_length=200, null=True, blank=True)
    rol = models.CharField(max_length=15, choices=ROL_CHOICES, default='visualizador', null=True, blank=True)
    avatar_url = models.CharField(max_length=500, null=True, blank=True)
    estado = models.CharField(max_length=15, choices=ESTADO_CHOICES, default='activo', null=True, blank=True)
    ultimo_acceso = models.DateTimeField(null=True, blank=True)
    fecha_registro = models.DateTimeField(auto_now_add=True)
    fecha_actualizacion = models.DateTimeField(auto_now=True)
    
    class Meta:
        db_table = 'usuarios'
        verbose_name = 'Usuario'
        verbose_name_plural = 'Usuarios'
        ordering = ['-fecha_registro']
    
    def __str__(self):
        return f"{self.username} - {self.rol}"