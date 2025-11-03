from django.db import models
from circunscripciones.models import Circunscripcion

class SimulacroVoto(models.Model):
    TIPO_ELECCION_CHOICES = [
        ('presidencial', 'Presidencial'),
        ('senador_nacional', 'Senador Nacional'),
        ('senador_regional', 'Senador Regional'),
        ('diputado', 'Diputado'),
        ('parlamento_andino', 'Parlamento Andino'),
    ]
    
    dni = models.CharField(max_length=8)
    nombre_completo = models.CharField(max_length=200, null=True, blank=True)
    tipo_eleccion = models.CharField(max_length=25, choices=TIPO_ELECCION_CHOICES)
    candidato_id = models.IntegerField()
    circunscripcion = models.ForeignKey(Circunscripcion, on_delete=models.CASCADE, null=True, blank=True)
    mes_simulacro = models.IntegerField()
    anio_simulacro = models.IntegerField()
    fecha_voto = models.DateTimeField(auto_now_add=True)
    ip_address = models.CharField(max_length=45, null=True, blank=True)
    
    class Meta:
        db_table = 'simulacro_votos'
        verbose_name = 'Voto Simulacro'
        verbose_name_plural = 'Votos Simulacro'
        ordering = ['-fecha_voto']
        unique_together = [['dni', 'tipo_eleccion', 'mes_simulacro', 'anio_simulacro']]
    
    def __str__(self):
        return f"{self.dni} - {self.tipo_eleccion} ({self.mes_simulacro}/{self.anio_simulacro})"