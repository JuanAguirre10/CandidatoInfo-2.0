from django.db import models

class Propuesta(models.Model):
    TIPO_CANDIDATO_CHOICES = [
        ('presidencial', 'Presidencial'),
        ('senador_nacional', 'Senador Nacional'),
        ('senador_regional', 'Senador Regional'),
        ('diputado', 'Diputado'),
        ('parlamento_andino', 'Parlamento Andino'),
    ]
    
    candidato_id = models.IntegerField(null=True, blank=True)
    tipo_candidato = models.CharField(max_length=30, choices=TIPO_CANDIDATO_CHOICES, null=True, blank=True)
    titulo = models.CharField(max_length=300, null=True, blank=True)
    descripcion = models.TextField(null=True, blank=True)
    categoria = models.CharField(max_length=100, null=True, blank=True)
    eje_tematico = models.CharField(max_length=100, null=True, blank=True)
    costo_estimado = models.DecimalField(max_digits=15, decimal_places=2, null=True, blank=True)
    plazo_implementacion = models.CharField(max_length=100, null=True, blank=True)
    beneficiarios = models.TextField(null=True, blank=True)
    archivo_url = models.CharField(max_length=500, null=True, blank=True)
    fecha_publicacion = models.DateField(null=True, blank=True)
    fecha_registro = models.DateTimeField(auto_now_add=True)
    fecha_actualizacion = models.DateTimeField(auto_now=True)
    
    class Meta:
        db_table = 'propuestas'
        verbose_name = 'Propuesta'
        verbose_name_plural = 'Propuestas'
        ordering = ['-fecha_publicacion']
    
    def __str__(self):
        return f"{self.titulo} - {self.tipo_candidato}"


class ProyectoRealizado(models.Model):
    TIPO_CANDIDATO_CHOICES = [
        ('presidencial', 'Presidencial'),
        ('senador_nacional', 'Senador Nacional'),
        ('senador_regional', 'Senador Regional'),
        ('diputado', 'Diputado'),
        ('parlamento_andino', 'Parlamento Andino'),
    ]
    
    ESTADO_CHOICES = [
        ('completado', 'Completado'),
        ('en_ejecucion', 'En Ejecución'),
        ('suspendido', 'Suspendido'),
    ]
    
    candidato_id = models.IntegerField(null=True, blank=True)
    tipo_candidato = models.CharField(max_length=30, choices=TIPO_CANDIDATO_CHOICES, null=True, blank=True)
    titulo = models.CharField(max_length=300, null=True, blank=True)
    descripcion = models.TextField(null=True, blank=True)
    cargo_periodo = models.CharField(max_length=200, null=True, blank=True)
    fecha_inicio = models.DateField(null=True, blank=True)
    fecha_fin = models.DateField(null=True, blank=True)
    monto_invertido = models.DecimalField(max_digits=15, decimal_places=2, null=True, blank=True)
    beneficiarios = models.TextField(null=True, blank=True)
    resultados = models.TextField(null=True, blank=True)
    ubicacion = models.CharField(max_length=200, null=True, blank=True)
    evidencia_url = models.CharField(max_length=500, null=True, blank=True)
    imagen_url = models.CharField(max_length=500, null=True, blank=True)
    estado = models.CharField(max_length=20, choices=ESTADO_CHOICES, default='completado', null=True, blank=True)
    fecha_registro = models.DateTimeField(auto_now_add=True)
    fecha_actualizacion = models.DateTimeField(auto_now=True)
    
    class Meta:
        db_table = 'proyectos_realizados'
        verbose_name = 'Proyecto Realizado'
        verbose_name_plural = 'Proyectos Realizados'
        ordering = ['-fecha_inicio']
    
    def __str__(self):
        return f"{self.titulo} - {self.tipo_candidato}"


class Denuncia(models.Model):
    TIPO_CANDIDATO_CHOICES = [
        ('presidencial', 'Presidencial'),
        ('senador_nacional', 'Senador Nacional'),
        ('senador_regional', 'Senador Regional'),
        ('diputado', 'Diputado'),
        ('parlamento_andino', 'Parlamento Andino'),
    ]
    
    ESTADO_PROCESO_CHOICES = [
        ('investigando', 'Investigando'),
        ('archivada', 'Archivada'),
        ('comprobada', 'Comprobada'),
        ('sentenciado', 'Sentenciado'),
        ('absuelto', 'Absuelto'),
    ]
    
    GRAVEDAD_CHOICES = [
        ('leve', 'Leve'),
        ('moderada', 'Moderada'),
        ('grave', 'Grave'),
        ('muy_grave', 'Muy Grave'),
    ]
    
    candidato_id = models.IntegerField(null=True, blank=True)
    tipo_candidato = models.CharField(max_length=30, choices=TIPO_CANDIDATO_CHOICES, null=True, blank=True)
    titulo = models.CharField(max_length=300, null=True, blank=True)
    descripcion = models.TextField(null=True, blank=True)
    tipo_denuncia = models.CharField(max_length=100, null=True, blank=True)
    fecha_denuncia = models.DateField(null=True, blank=True)
    entidad_denunciante = models.CharField(max_length=200, null=True, blank=True)
    fuente = models.CharField(max_length=300, null=True, blank=True)
    url_fuente = models.CharField(max_length=500, null=True, blank=True)
    estado_proceso = models.CharField(max_length=20, choices=ESTADO_PROCESO_CHOICES, default='investigando', null=True, blank=True)
    monto_involucrado = models.DecimalField(max_digits=15, decimal_places=2, null=True, blank=True)
    documento_url = models.CharField(max_length=500, null=True, blank=True)
    gravedad = models.CharField(max_length=15, choices=GRAVEDAD_CHOICES, null=True, blank=True)
    fecha_registro = models.DateTimeField(auto_now_add=True)
    fecha_actualizacion = models.DateTimeField(auto_now=True)
    
    class Meta:
        db_table = 'denuncias'
        verbose_name = 'Denuncia'
        verbose_name_plural = 'Denuncias'
        ordering = ['-fecha_denuncia']
    
    def __str__(self):
        return f"{self.titulo} - {self.tipo_candidato}"