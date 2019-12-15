import socket
import sys

HOST = "192.168.43.141"
PORT = 9999
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('Socket created')

try:
    s.bind((HOST, PORT))
    s.listen(10)
    print("Socket Listening")
    type = input("Enter send or receive(s for send, r for receive)\n")
    
    while(True):
        #Receiving part
        if type == 'r':
            conn, addr = s.accept()
            nameByte = conn.recv(1024) 
            name = nameByte.decode(encoding='UTF-8')
            print("File Name : " + name)
            if name :
                size = 0
                data = conn.recv(1024)
                f = open(name, 'wb')
                print('Receiving...')
                while data != bytes(''.encode()):
                    size += len(data)
                    f.write(data)
                    print("%f KB"%(size/1024))
                    sys.stdout.write("\033[F")
                    data = conn.recv(1024)
                print("%f KB"%(size/1024))
                print('Received Successfully:)')
        #sending part
        elif type == 's':
            filePath = input('Enter file location\n')
            fileName = filePath.split('/')
            print(fileName[len(fileName) - 1])
            print(str.encode(fileName[len(fileName) - 1]))
            conn, addr = s.accept()
            conn.send(b'elam')
    s.close()
except socket.error as err:
    print('Bind failed. Error Code : ' .format(err))
