#!/bin/bash

mkdir bin
javac -d bin/ $(find src/ -name *.java)
