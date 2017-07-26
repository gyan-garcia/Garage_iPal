from django.conf.urls import url, include
from rest_framework import routers 
from app.logic.viewsets import *

def get_router():
    router = routers.DefaultRouter(trailing_slash=False)
    router.register(r'people_detection', PeopleDetectionViewSet)
    return router
