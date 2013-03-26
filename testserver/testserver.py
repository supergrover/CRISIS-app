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

def randpos(pos = None):
    if pos:
        pos.x = int(random.random() * fieldsize[0]);
        pos.y = int(random.random() * fieldsize[1]);

    return Position(x = int(random.random() * fieldsize[0]),
                    y = int(random.random() * fieldsize[1]))


def gen_environment():
    env = Environment()
    randpos(env.crisis.pos);
    env.crisis.rotation = random.random()*360;
    for x in range(1, 20):
        env.elements.extend([Environment.Element(
                                size = random.random(),
                                pos = randpos(),
                                type = Environment.Element.Sunflower)])
    env.field_size.x = fieldsize[0]
    env.field_size.y = fieldsize[1]
    return env

clients = []

while True:
    (client, address) = server.accept()
    print('Client')
    cmd = Command(type=Command.Environment, env=gen_environment())
    buf = cmd.SerializeToString()

    hdr = Header(size=len(buf)).SerializeToString()

    client.send(hdr + buf)

    clients.append(client)

    #client.shutdown(socket.SHUT_RDWR)
    #client.close()

