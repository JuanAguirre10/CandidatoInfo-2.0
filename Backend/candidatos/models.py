from django.db import models
from partidos.models import PartidoPolitico
from circunscripciones.models import Circunscripcion

class CandidatoPresidencial(models.Model):
    GENERO_CHOICES = [
        ('M', 'Masculino'),
        ('F', 'Femenino'),
        ('Otro', 'Otro'),
    ]
    
    ESTADO_CHOICES = [
        ('inscrito', 'Inscrito'),
        ('observado', 'Observado'),
        ('excluido', 'Excluido'),
        ('aprobado', 'Aprobado'),
    ]
    
    partido = models.ForeignKey(PartidoPolitico, on_delete=models.CASCADE, related_name='candidatos_presidenciales', null=True, blank=True)
    
    presidente_nombre = models.CharField(max_length=200, null=True, blank=True)
    presidente_apellidos = models.CharField(max_length=200, null=True, blank=True)
    presidente_dni = models.CharField(max_length=8, null=True, blank=True)
    presidente_foto_url = models.CharField(max_length=500, null=True, blank=True)
    presidente_fecha_nacimiento = models.DateField(null=True, blank=True)
    presidente_profesion = models.CharField(max_length=200, null=True, blank=True)
    presidente_biografia = models.TextField(null=True, blank=True)
    presidente_genero = models.CharField(max_length=10, choices=GENERO_CHOICES, null=True, blank=True)
    
    vicepresidente1_nombre = models.CharField(max_length=200, null=True, blank=True)
    vicepresidente1_apellidos = models.CharField(max_length=200, null=True, blank=True)
    vicepresidente1_dni = models.CharField(max_length=8, null=True, blank=True)
    vicepresidente1_foto_url = models.CharField(max_length=500, null=True, blank=True)
    vicepresidente1_fecha_nacimiento = models.DateField(null=True, blank=True)
    vicepresidente1_profesion = models.CharField(max_length=200, null=True, blank=True)
    vicepresidente1_biografia = models.TextField(null=True, blank=True)
    vicepresidente1_genero = models.CharField(max_length=10, choices=GENERO_CHOICES, null=True, blank=True)
    
    vicepresidente2_nombre = models.CharField(max_length=200, null=True, blank=True)
    vicepresidente2_apellidos = models.CharField(max_length=200, null=True, blank=True)
    vicepresidente2_dni = models.CharField(max_length=8, null=True, blank=True)
    vicepresidente2_foto_url = models.CharField(max_length=500, null=True, blank=True)
    vicepresidente2_fecha_nacimiento = models.DateField(null=True, blank=True)
    vicepresidente2_profesion = models.CharField(max_length=200, null=True, blank=True)
    vicepresidente2_biografia = models.TextField(null=True, blank=True)
    vicepresidente2_genero = models.CharField(max_length=10, choices=GENERO_CHOICES, null=True, blank=True)
    
    plan_gobierno_url = models.CharField(max_length=500, null=True, blank=True)
    numero_lista = models.IntegerField(null=True, blank=True)
    estado = models.CharField(max_length=15, choices=ESTADO_CHOICES, default='inscrito', null=True, blank=True)
    fecha_inscripcion = models.DateField(null=True, blank=True)
    fecha_registro = models.DateTimeField(auto_now_add=True)
    fecha_actualizacion = models.DateTimeField(auto_now=True)
    
    class Meta:
        db_table = 'candidatos_presidenciales'
        verbose_name = 'Candidato Presidencial'
        verbose_name_plural = 'Candidatos Presidenciales'
        ordering = ['numero_lista']
    
    def __str__(self):
        return f"{self.partido.siglas if self.partido else 'Sin Partido'} - {self.presidente_nombre} {self.presidente_apellidos}"


class CandidatoSenadorNacional(models.Model):
    GENERO_CHOICES = [
        ('M', 'Masculino'),
        ('F', 'Femenino'),
        ('Otro', 'Otro'),
    ]
    
    ESTADO_CHOICES = [
        ('inscrito', 'Inscrito'),
        ('observado', 'Observado'),
        ('excluido', 'Excluido'),
        ('aprobado', 'Aprobado'),
    ]
    
    partido = models.ForeignKey(PartidoPolitico, on_delete=models.CASCADE, related_name='senadores_nacionales', null=True, blank=True)
    nombre = models.CharField(max_length=200, null=True, blank=True)
    apellidos = models.CharField(max_length=200, null=True, blank=True)
    dni = models.CharField(max_length=8, null=True, blank=True)
    foto_url = models.CharField(max_length=500, null=True, blank=True)
    fecha_nacimiento = models.DateField(null=True, blank=True)
    edad = models.IntegerField(null=True, blank=True)
    genero = models.CharField(max_length=10, choices=GENERO_CHOICES, null=True, blank=True)
    profesion = models.CharField(max_length=200, null=True, blank=True)
    experiencia_politica = models.TextField(null=True, blank=True)
    biografia = models.TextField(null=True, blank=True)
    hoja_vida_url = models.CharField(max_length=500, null=True, blank=True)
    posicion_lista = models.IntegerField(null=True, blank=True)
    numero_preferencial = models.IntegerField(null=True, blank=True)
    estado = models.CharField(max_length=15, choices=ESTADO_CHOICES, default='inscrito', null=True, blank=True)
    fecha_inscripcion = models.DateField(null=True, blank=True)
    fecha_registro = models.DateTimeField(auto_now_add=True)
    fecha_actualizacion = models.DateTimeField(auto_now=True)
    
    class Meta:
        db_table = 'candidatos_senadores_nacionales'
        verbose_name = 'Senador Nacional'
        verbose_name_plural = 'Senadores Nacionales'
        ordering = ['partido', 'posicion_lista']
    
    def __str__(self):
        return f"{self.partido.siglas if self.partido else 'Sin Partido'} - {self.nombre} {self.apellidos}"


class CandidatoSenadorRegional(models.Model):
    GENERO_CHOICES = [
        ('M', 'Masculino'),
        ('F', 'Femenino'),
        ('Otro', 'Otro'),
    ]
    
    ESTADO_CHOICES = [
        ('inscrito', 'Inscrito'),
        ('observado', 'Observado'),
        ('excluido', 'Excluido'),
        ('aprobado', 'Aprobado'),
    ]
    
    partido = models.ForeignKey(PartidoPolitico, on_delete=models.CASCADE, related_name='senadores_regionales', null=True, blank=True)
    circunscripcion = models.ForeignKey(Circunscripcion, on_delete=models.CASCADE, related_name='senadores_regionales', null=True, blank=True)
    nombre = models.CharField(max_length=200, null=True, blank=True)
    apellidos = models.CharField(max_length=200, null=True, blank=True)
    dni = models.CharField(max_length=8, null=True, blank=True)
    foto_url = models.CharField(max_length=500, null=True, blank=True)
    fecha_nacimiento = models.DateField(null=True, blank=True)
    edad = models.IntegerField(null=True, blank=True)
    genero = models.CharField(max_length=10, choices=GENERO_CHOICES, null=True, blank=True)
    profesion = models.CharField(max_length=200, null=True, blank=True)
    experiencia_politica = models.TextField(null=True, blank=True)
    biografia = models.TextField(null=True, blank=True)
    hoja_vida_url = models.CharField(max_length=500, null=True, blank=True)
    posicion_lista = models.IntegerField(null=True, blank=True)
    numero_preferencial = models.IntegerField(null=True, blank=True)
    es_natural_circunscripcion = models.BooleanField(default=False, null=True, blank=True)
    estado = models.CharField(max_length=15, choices=ESTADO_CHOICES, default='inscrito', null=True, blank=True)
    fecha_inscripcion = models.DateField(null=True, blank=True)
    fecha_registro = models.DateTimeField(auto_now_add=True)
    fecha_actualizacion = models.DateTimeField(auto_now=True)
    
    class Meta:
        db_table = 'candidatos_senadores_regionales'
        verbose_name = 'Senador Regional'
        verbose_name_plural = 'Senadores Regionales'
        ordering = ['circunscripcion', 'partido', 'posicion_lista']
    
    def __str__(self):
        return f"{self.partido.siglas if self.partido else 'Sin Partido'} - {self.nombre} {self.apellidos}"


class CandidatoDiputado(models.Model):
    GENERO_CHOICES = [
        ('M', 'Masculino'),
        ('F', 'Femenino'),
        ('Otro', 'Otro'),
    ]
    
    ESTADO_CHOICES = [
        ('inscrito', 'Inscrito'),
        ('observado', 'Observado'),
        ('excluido', 'Excluido'),
        ('aprobado', 'Aprobado'),
    ]
    
    partido = models.ForeignKey(PartidoPolitico, on_delete=models.CASCADE, related_name='diputados', null=True, blank=True)
    circunscripcion = models.ForeignKey(Circunscripcion, on_delete=models.CASCADE, related_name='diputados', null=True, blank=True)
    nombre = models.CharField(max_length=200, null=True, blank=True)
    apellidos = models.CharField(max_length=200, null=True, blank=True)
    dni = models.CharField(max_length=8, null=True, blank=True)
    foto_url = models.CharField(max_length=500, null=True, blank=True)
    fecha_nacimiento = models.DateField(null=True, blank=True)
    edad = models.IntegerField(null=True, blank=True)
    genero = models.CharField(max_length=10, choices=GENERO_CHOICES, null=True, blank=True)
    profesion = models.CharField(max_length=200, null=True, blank=True)
    experiencia_politica = models.TextField(null=True, blank=True)
    biografia = models.TextField(null=True, blank=True)
    hoja_vida_url = models.CharField(max_length=500, null=True, blank=True)
    posicion_lista = models.IntegerField(null=True, blank=True)
    numero_preferencial = models.IntegerField(null=True, blank=True)
    es_natural_circunscripcion = models.BooleanField(default=False, null=True, blank=True)
    estado = models.CharField(max_length=15, choices=ESTADO_CHOICES, default='inscrito', null=True, blank=True)
    fecha_inscripcion = models.DateField(null=True, blank=True)
    fecha_registro = models.DateTimeField(auto_now_add=True)
    fecha_actualizacion = models.DateTimeField(auto_now=True)
    
    class Meta:
        db_table = 'candidatos_diputados'
        verbose_name = 'Diputado'
        verbose_name_plural = 'Diputados'
        ordering = ['circunscripcion', 'partido', 'posicion_lista']
    
    def __str__(self):
        return f"{self.partido.siglas if self.partido else 'Sin Partido'} - {self.nombre} {self.apellidos}"


class CandidatoParlamentoAndino(models.Model):
    GENERO_CHOICES = [
        ('M', 'Masculino'),
        ('F', 'Femenino'),
        ('Otro', 'Otro'),
    ]
    
    ESTADO_CHOICES = [
        ('inscrito', 'Inscrito'),
        ('observado', 'Observado'),
        ('excluido', 'Excluido'),
        ('aprobado', 'Aprobado'),
    ]
    
    partido = models.ForeignKey(PartidoPolitico, on_delete=models.CASCADE, related_name='parlamento_andino', null=True, blank=True)
    nombre = models.CharField(max_length=200, null=True, blank=True)
    apellidos = models.CharField(max_length=200, null=True, blank=True)
    dni = models.CharField(max_length=8, null=True, blank=True)
    foto_url = models.CharField(max_length=500, null=True, blank=True)
    fecha_nacimiento = models.DateField(null=True, blank=True)
    edad = models.IntegerField(null=True, blank=True)
    genero = models.CharField(max_length=10, choices=GENERO_CHOICES, null=True, blank=True)
    profesion = models.CharField(max_length=200, null=True, blank=True)
    experiencia_politica = models.TextField(null=True, blank=True)
    experiencia_internacional = models.TextField(null=True, blank=True)
    biografia = models.TextField(null=True, blank=True)
    hoja_vida_url = models.CharField(max_length=500, null=True, blank=True)
    posicion_lista = models.IntegerField(null=True, blank=True)
    numero_preferencial = models.IntegerField(null=True, blank=True)
    idiomas = models.CharField(max_length=200, null=True, blank=True)
    estado = models.CharField(max_length=15, choices=ESTADO_CHOICES, default='inscrito', null=True, blank=True)
    fecha_inscripcion = models.DateField(null=True, blank=True)
    fecha_registro = models.DateTimeField(auto_now_add=True)
    fecha_actualizacion = models.DateTimeField(auto_now=True)
    
    class Meta:
        db_table = 'candidatos_parlamento_andino'
        verbose_name = 'Candidato Parlamento Andino'
        verbose_name_plural = 'Candidatos Parlamento Andino'
        ordering = ['partido', 'posicion_lista']
    
    def __str__(self):
        return f"{self.partido.siglas if self.partido else 'Sin Partido'} - {self.nombre} {self.apellidos}"