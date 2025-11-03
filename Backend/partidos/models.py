from django.db import models

class PartidoPolitico(models.Model):
    TIPO_CHOICES = [
        ('partido', 'Partido'),
        ('alianza', 'Alianza'),
    ]
    
    ESTADO_CHOICES = [
        ('activo', 'Activo'),
        ('inactivo', 'Inactivo'),
        ('inhabilitado', 'Inhabilitado'),
    ]
    
    nombre = models.CharField(max_length=200, null=True, blank=True)
    siglas = models.CharField(max_length=50, null=True, blank=True)
    logo_url = models.CharField(max_length=500, null=True, blank=True)
    color_principal = models.CharField(max_length=50, null=True, blank=True)
    tipo = models.CharField(max_length=10, choices=TIPO_CHOICES, default='partido', null=True, blank=True)
    ideologia = models.CharField(max_length=100, null=True, blank=True)
    lider = models.CharField(max_length=200, null=True, blank=True)
    secretario_general = models.CharField(max_length=200, null=True, blank=True)
    fundacion_año = models.IntegerField(null=True, blank=True)
    descripcion = models.TextField(null=True, blank=True)
    sitio_web = models.CharField(max_length=200, null=True, blank=True)
    estado = models.CharField(max_length=15, choices=ESTADO_CHOICES, default='activo', null=True, blank=True)
    fecha_registro = models.DateTimeField(auto_now_add=True)
    fecha_actualizacion = models.DateTimeField(auto_now=True)
    
    class Meta:
        db_table = 'partidos_politicos'
        verbose_name = 'Partido Político'
        verbose_name_plural = 'Partidos Políticos'
        ordering = ['nombre']
    
    def __str__(self):
        return f"{self.siglas} - {self.nombre}"