# Network Programming Laboratory Work #2

### Author: *Petici Marina*

## Table of contents
* [Introduction](#introduction)
* [Technologies](#technologies)
* [Implementation](#implementation)
* [Results](#results)
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

1. I've built and UDP Client-Server and checked for data transmissions (one way client --> server).

2.
## Results

## Setup
1. Run server.Main
2. Run client.Main
## Status
Not finished.

