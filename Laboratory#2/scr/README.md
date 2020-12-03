# Network Programming Laboratory Work #2

### Author: *Petici Marina*

## Table of contents
* [Introduction](#introduction)
* [Task](#task)
* [Technologies](#technologies)
* [Implementation](#implementation)
* [Setup](#setup)
* [Status](#status)

## Introduction

I have the task to implementing a protocol stack, namely a transport protocol based on UDP, 
a session-level security protocol inspired by SSL/TLS, and an application-level protocol. 

I must present this project as a client and a server, both using a library that contains the protocol logic. 
The library must be made up of 3 modules, for each level of the protocol stack, with a well-defined API and 
that adheres to the layered architecture. 

For transport and session level protocols the BSD Sockets API is a recommended source of inspiration, 
while for the application-level protocol 
something that resembles an HTTP client API is a recommended source of inspiration.

## Tasks

- [x] implement a protocol atop UDP, with error checking and retransmissions. Limit the number of retries for retransmission.
- [x] make the connection secure, using either a CA to get the public key of the receiver and encrypt data with it, or using Diffie-Helman to get a shared connection key between client and server, ensure that the traffic is encrypted.
- [x] Regarding the application-level protocol, you have 3 options:

        - make an FTP-like protocol for data transfer, thus you will need to ensure data splitting and in-order delivery and reassembly at the destination. The protocol must support URIs, file creation and update (PUT), file fetching (GET) and metadata retrieval (OPTIONS)
        
        - make a protocol based on the workings (state machine) of an ATM
        
        - make a protocol based on the workings (state machine) of a stationary telephone

## Technologies

* Intellij IDEA
* Java JDK 15

## Implementation

1. For implementation of UDP protocol, I've built a console application [UDP Client-Server] made sure it has data transmissions (one way client --> server). (client.Main & server.Main)
```
        Client client = new Client();
        client.newClient();
```
```
        Server server = new Server();
        server.newServer();
```
![alt text](https://github.com/marina01p/Network_Programming/blob/master/Laboratory%232/scr/screenshots/screen1.png)

![alt text](https://github.com/marina01p/Network_Programming/blob/master/Laboratory%232/scr/screenshots/screen2.png)

For error checking, I calculated the checksum with utilities.ErrorChecker - getCRC32Checksum:

 ```   
public static long getCRC32Checksum(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return crc32.getValue();
    }
```

Regarding the retransmission, I've used a variable, called RETRANSMISSION (duh! ), which limits the do-while loop to 3 tries of sending data. It has a timeout of 1000ms as well.

```(!echoString.equals("exit") && (tries < RETRANSMISSION ));```

![alt text](https://github.com/marina01p/Network_Programming/blob/master/Laboratory%232/scr/screenshots/screen3.png)

2. In order for the connection to be secure, I used the Diffie Hellman key exchange method.
The traffic is secured with the help of an AES algorithm, and a "AES/ECB/PKCS5Padding" cipher transformation. 

Client:
```
String encryptedData = DiffieHellman.encrypt(echoString, secretKey);
DatagramPacket sendEncryptedData = new DatagramPacket(encryptedData.getBytes(), encryptedData.getBytes().length, address, CLIENT_PORT);
```

Server:
```
String decryptedString = DiffieHellman.decrypt(returnString, secretKey);
```

3. Regarding the application-level protocol, I've started to work on the ATM machine but faced some troubles, and did not menage to finish it.
 
## Setup
1. Run server.Main
2. Run client.Main

## Status
Not finished.

