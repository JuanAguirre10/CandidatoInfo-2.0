from django.db import models

class Circunscripcion(models.Model):
    TIPO_CHOICES = [
        ('departamento', 'Departamento'),
        ('provincia_constitucional', 'Provincia Constitucional'),
        ('exterior', 'Exterior'),
    ]
    
    nombre = models.CharField(max_length=100, null=True, blank=True)
    codigo = models.CharField(max_length=10, null=True, blank=True)
    tipo = models.CharField(max_length=30, choices=TIPO_CHOICES, null=True, blank=True)
    poblacion = models.IntegerField(null=True, blank=True)
    electores_registrados = models.IntegerField(null=True, blank=True)
    capital = models.CharField(max_length=100, null=True, blank=True)
    imagen_url = models.CharField(max_length=500, null=True, blank=True)
    bandera_url = models.CharField(max_length=500, null=True, blank=True)
    escudo_url = models.CharField(max_length=500, null=True, blank=True)
    numero_diputados = models.IntegerField(default=1, null=True, blank=True)
    numero_senadores = models.IntegerField(default=1, null=True, blank=True)
    latitud = models.DecimalField(max_digits=10, decimal_places=8, null=True, blank=True)
    longitud = models.DecimalField(max_digits=11, decimal_places=8, null=True, blank=True)
    descripcion = models.TextField(null=True, blank=True)
    fecha_registro = models.DateTimeField(auto_now_add=True)
    
    class Meta:
        db_table = 'circunscripciones'
        verbose_name = 'Circunscripción'
        verbose_name_plural = 'Circunscripciones'
        ordering = ['nombre']
    
    def __str__(self):
        return f"{self.codigo} - {self.nombre}"