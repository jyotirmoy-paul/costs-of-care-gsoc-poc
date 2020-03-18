"""
This sample code snippet shows how to scrap the web and find details of hospitals and their procedures
"""

import os
# Configure settings for project
# Need to run this before calling models from application!
os.environ.setdefault('DJANGO_SETTINGS_MODULE','api.settings')

import django
django.setup()

import pandas as pd
from sample.models import Hospitals

from faker import Faker
from random import uniform

def add_to_database(hp, pn, ec):
    Hospitals.objects.get_or_create(
            hospital_name = hp,
            procedure_name = pn,
            estimated_cost = ec
        )

"""
Hard coding data for mock up purpose
"""

def generate_fake_data():

    sample_procedures = ['HC INJ OF SCLEROSING SOLUTI SING',
        'HC FACIAL BONES 3 VIEWS',
        'HC THORACIC SPINE 2 VIEWS',
        'HC MAPS MUSCLE TEST NONPARASPINAL',
        'HC REMOVE AORTIC ASSIST DEVICE',
        'HC HSV 1 IGG AB',
        'HC THYROIDIMG W/VASCULAR FLOW',
        'HC VARICELLA-ZOSTER, IGM, CSF',
        'HC KNEE ONE OR TWO VIEWS',
        'HC BCRABL QNT MAJOR P210',
        'HC ART. (THORAC. AORTA) S/I',
        'HC PPM OTHER THAN SGL/DUAL  MEDTR',
        'HC ALLERGEN, FOOD, SEVERE PEANUT ARA H 2',
        'HC KNEE(3 VIEWS)',
        'HC PLASMA HEMOGLOBIN',
        'HC MAPS METHYLPREDNISOLONE INJECTION',
        'HC MAPS SINGLE OR MULTIPLE EVENT MO',
        'HC MAPS WHO COCK-UP NONMOLDE PRE OTS',
        'HC SOLID TUMOR-ERBB4',
        'HC POLAR HEART DATA ANALYSIS',
        'HC DEB BONE ADD-ON',
        'HC NUTRITION EDUCATION(30 MIN)',
        'HC SRP 7.6-12.5CM FC,ER,ELD,NS,LP',
        'HC STAB PHLEBECTOMY<10 INCISIONS',
        'HC MAPS INCISIONAL BIOPSY-SKIN(EG,WEDGE) (INCLUDNG SIMPL CLOSR,WHEN PERFRMD);EA SEP/ADDTL LESN',
        'HC RBC MORPHOLOGY             T/F',
        'HC CATHEPDIAG/ABL,N3DNCOOL>/=8MM',
        'HC PLACE CATH VERTEBRAL ART',
        'HC BLOOD BANK CONSULT FEE',
        'HC MAPS UPPER EXTREMITY STUDY',
        'HC ALLERG,AMERICAN BEECH TREE',
        'HC CV2 AUTOAB',
        'HC CREATININE, URINE OR OTHER',
        'HC IMMUNOGLOBULIN G',
        'HC ULTRASOUND INTRAOPERATIVE',
        'HC POLAR HEART DEVICE EXCHANGE',
        'HC RN TC99M ALBUMIN(UP TO 10MCI)',
        'HC HCV NS3/4A DRUG RESIST ASSAY',
        'HC RUBEOLA TITER, IGM',
        'HC INTRAOSSEOUS NEEDLE PLACEMENT',
        'HC MAPS REMOVAL OF IMPACTED WAX MD',
        'HC ECHO 2D (RESTING)',
        'HC B.PERTUSSIS IGG W/REF',
        'HC URINE IODINE',
        'HC DRUG ASSAY OF HALOPERIDOL',
        'HC LEAD AICD NON-SINGLE/DUAL',
        'HC MAPS I&D SIMPLE HEMATOMA',
        'HC BL SMEAR W/DIFF WBC COUNT',
        'HC TREATMENT OF NOSE FRACTURE WIT',
        'HC Tooth Extraction']

    # sample class
    class Data:
        def __init__(self, hospital_name, procedure_name, estimated_cost):
            self.hospital_name = hospital_name
            self.procedure_name = procedure_name
            self.estimated_cost = estimated_cost

    datas = []

    
    fakegen = Faker()

    for _ in range(100):
        hospital_name = fakegen.name() + ' Medical Center'
        for i in range(50):
            procedure_name = sample_procedures[i]
            estimated_cost = round(uniform(100, 20000), 2)

            datas.append(Data(hospital_name, procedure_name, estimated_cost))



    # adding to the backend database
    for data in datas:
        add_to_database(
            data.hospital_name,
            data.procedure_name,
            data.estimated_cost
        )


"""
fetching details for Penn Presbyterian Medical Center
using the url: https://www.pennmedicine.org/-/media/documents%20and%20audio/non%20patient%20instructions/health%20system/ppmc_cdm_01172020.ashx?la=en
"""
def fetch_original():
    
    url = 'https://www.pennmedicine.org/-/media/documents%20and%20audio/non%20patient%20instructions/health%20system/ppmc_cdm_01172020.ashx?la=en'
    
    data = pd.read_csv(url, encoding='unicode_escape')

    for _, row in data.iterrows():
        price = row['Total Price'].replace(',','')

        try:
            price = float(price)
        except:
            price = -1 # ERX
        
        add_to_database(
            'Penn Presbyterian Medical Center',
            row['Procedure Name'],
            float(price)
        )


if __name__ == '__main__':
    fetch_original()
    generate_fake_data()