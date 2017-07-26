
from rest_framework.parsers import JSONParser
from rest_framework import viewsets, response, status, views
from rest_framework.decorators import list_route, detail_route#, permission_classes

from django.core.validators import URLValidator
from django.core.exceptions import ValidationError

from django.core.exceptions import ObjectDoesNotExist
from django.core import serializers
from django.http import HttpResponse

from app.models import *
from app.logic.serializers import *
import json


class PeopleDetectionViewSet(viewsets.ModelViewSet):
    queryset = ImageModel.objects.none()
    serializer_class = ImageModelSerializer

    # http://127.0.0.1:8000/api/people_detection/0/get_distance_from_people/
    @detail_route(methods=['POST'], )
    def get_distance_from_people2(self, request, pk=None):
        try:
            data = JSONParser().parse(request)
            print (data)
            #title = GetUrlPageTitle(data['target_url'])
            #text_type, recipe_type = GetPageTextType(data['target_url'])
            #json_response = json.dumps({"title": title, "text_type": text_type, "recipe_type": recipe_type}, sort_keys=True)
            
            return HttpResponse(json.dumps({"title": "This is a test"}), content_type="application/json")

        except ObjectDoesNotExist:
            return response.Response(status = status.HTTP_404_NOT_FOUND)

    @detail_route(methods=['GET'], )
    def get_target_url_data(self, request, pk=None):
        try:
            #data = JSONParser().parse(request)
            #title = GetUrlPageTitle(data['target_url'])
            #text_type, recipe_type = GetPageTextType(data['target_url'])
            #json_response = json.dumps({"title": title, "text_type": text_type, "recipe_type": recipe_type}, sort_keys=True)
            return HttpResponse(json.dumps({"title": "This is a test"}), content_type="application/json")

        except ObjectDoesNotExist:
            return response.Response(status = status.HTTP_404_NOT_FOUND)
