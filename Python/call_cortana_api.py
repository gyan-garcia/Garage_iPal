import http.client, urllib.request, urllib.parse, urllib.error, base64
def getEmotion(img):
    headers = {
        # Request headers
        'Content-Type': 'application/octet-stream',
        'Ocp-Apim-Subscription-Key': '1492e36d5c0b4135bdd99282da703b7c',
    }
    params = urllib.urlencode({
    })

    try:
        conn = httplib.HTTPSConnection('westus.api.cognitive.microsoft.com')
        conn.request("POST", "/emotion/v1.0/recognize?%s" % params, img, headers)
        response = conn.getresponse()
        data = response.read()
        print(data)
        conn.close()
    except Exception as e:
        print("[Errno {0}] {1}".format(e.errno, e.strerror))

    ####################################

    ########### Python 3.2 #############
    import http.client, urllib.request, urllib.parse, urllib.error, base64

    headers = {
        # Request headers
        'Content-Type': 'application/octet-stream',
        'Ocp-Apim-Subscription-Key': '1492e36d5c0b4135bdd99282da703b7c',
    }

    params = urllib.parse.urlencode({
    })

    try:
        conn = http.client.HTTPSConnection('westus.api.cognitive.microsoft.com')
        conn.request("POST", "/emotion/v1.0/recognize?%s" % params, img, headers)
        response = conn.getresponse()
        data = response.read()
        obj = json.loads(data)
        scores = obj['scores']
        best =  max(scores, key = lambda x : scores[x])
        conn.close()
        return best
    except Exception as e:
        print("[Errno {0}] {1}".format(e.errno, e.strerror))


