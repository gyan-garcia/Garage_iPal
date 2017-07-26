"""
Definition of models.
"""

from django.db import models

# Create your models here.

# Create your models here.
class ImageModel(models.Model):
    img_class = models.CharField(max_length = 100, default='0')
    image_json = models.CharField(max_length = 100000, default='0')
    x_coordinates = models.CharField(max_length = 100000, default='0')
    y_coordinates = models.CharField(max_length = 100000, default='0')
    cluster =  models.CharField(max_length = 500000, default='0')

    def as_json(self):
        return dict(
            img_class = self.img_class,
            image_json = self.image_json)