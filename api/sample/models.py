from django.db import models

# Create your models here.
class Hospitals(models.Model):
    hospital_name = models.CharField(max_length=200)
    procedure_name = models.CharField(max_length=200)
    estimated_cost = models.DecimalField(decimal_places=2,
        max_digits=1000000)