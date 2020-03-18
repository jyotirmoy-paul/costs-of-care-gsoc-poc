from django.urls import re_path
from . import views

"""
API END POINTS

1. api/hospitals/hospital_name=HOSPITAL_NAME&procedure_name=PROCEDURE_NAME/

"""

urlpatterns = [
    re_path(r'^hospitals/hospital_name=(?P<hospital_name>.+)&procedure_name=(?P<procedure_name>.+)/$', views.HospitalApiView.as_view()),
]
