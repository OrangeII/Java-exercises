#!/bin/bash
#this script is symlinked to my  ~/bin/ folder
#parameters:
# $1 class name

#WANNADO:
#options:
# -m generate main method
# -e <superclass> generate extends statement
# -i <interface> generate implements

#this creates a new java class with main method named as the parameter
echo -e "class $1{\n\tpublic static void main(String[] args){\n\t\t\n\t}\n}" > $1.java
#this opens the file in Atom
atom $1.java
