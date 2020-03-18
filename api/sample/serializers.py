from rest_framework.serializers import ModelSerializer

from .models import Hospitals
from rest_framework import serializers

class HospitalModelSerializer(ModelSerializer):
    class Meta:
        model = Hospitals
        fields = [
            'hospital_name',
            'procedure_name',
            'estimated_cost'
        ]