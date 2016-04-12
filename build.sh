#!/bin/bash

[ -d bin ] || mkdir bin
javac -d bin/ -cp "/usr/share/java/bcprov.jar" $(find src/ -name *.java)
