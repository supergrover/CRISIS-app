#! /usr/bin/python

from commands import Command, Environment, Position, Header
import socket
import random
import struct

fieldsize = (100, 100)


server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
server.bind(('', 1337))
server.listen(5)

env = Environment()
env.crisis.x = 10;
env.crisis.y = 10;
env.elements.extend([Environment.Element(
                    size = 0.5,
                    pos = Position(x = int(random.random()*fieldsize[0]), 
                                            y = int(random.random()*fieldsize[1])),
                    type = Environment.Element.Sunflower)])

clients = []

try:
    while True:
        (client, address) = server.accept()
        print('Client')
        cmd = Command(type=Command.Environment, env=env)
        buf = cmd.SerializeToString()

        hdr = Header(size=len(buf)).SerializeToString()

        client.send(hdr + buf)

        clients.append(client)

        #client.shutdown(socket.SHUT_RDWR)
        #client.close()
except Exception as e:
    server.shutdown(socket.SHUT_RDWR)
    server.close()
    raise e

