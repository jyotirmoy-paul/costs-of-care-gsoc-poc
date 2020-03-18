from rest_framework.views import APIView
from rest_framework.response import Response

from .models import Hospitals
from .serializers import HospitalModelSerializer

# Create your views here.
class HospitalApiView(APIView):

    def get(self, request, hospital_name, procedure_name, format=None):

        queryset = Hospitals.objects.all().filter(
            hospital_name__icontains=hospital_name,
            procedure_name__icontains=procedure_name).order_by('estimated_cost')

        serialized_query_field = HospitalModelSerializer(queryset, many=True)

        return Response(serialized_query_field.data)

