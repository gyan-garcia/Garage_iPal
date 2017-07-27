# -*- coding: utf-8 -*-
"""
Created on Tue Jul 25 13:54:56 2017

@author: ggarcia
"""

import socket
#for exit
import sys

#import call_cortana_api

# Symbolic name meaning all available interfaces
HOST = ''
# Arbitrary non-privileged port
PORT = 8888
 
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print ('Socket created')
 
try:
    s.bind((HOST, PORT))
except:
    print ('Bind failed')
    sys.exit()
   
print ('Socket bind complete')

s.listen(10)
print ('Socket now listening')

# Wait to accept a connection - blocking call
conn, addr = s.accept()
print ('Connected with ' + addr[0] + ':' + str(addr[1]))


buffer_size_str = conn.recv(8)

print ("Buffer size received", buffer_size_str)
buffer_size = int(buffer_size_str)

pic_data = bytearray()

# Now keep talking with the client
while True:
    message = ""
    data = conn.recv(buffer_size)
    
    pic_data = pic_data + data
    try:
        message = data#data.decode("utf-8") 
        if message=="quit":
            print ("quit")
            break
        #message = message.strip(' \t\n\r')
    except Exception:
        print ("exception")
        pass
    #m
    #message = message.strip(' \t\n\r')
    #, "pic_data", len(pic_data)
    if data[len(data) - 4] == 113 and data[len(data) - 3] == 117:
        print ("found quit in array")
        break
    
    print ("Data received", len(data))
    #print ("Data: ", message)
    if not data:
        break
    
    #conn.sendall(data)
 

#    data = conn.recv(1024)
#    message = data.decode("utf-8") 
#    message = message.strip(' \t\n\r')
#    emotion = call_cortana_api(message)
#    print ("Data: ", message)
#    if not data or message=="quit":
#        break
#    conn.sendall(emotion)
#    #conn.sendall(data)
 
conn.close()
s.close()
