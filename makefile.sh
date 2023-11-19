#!/bin/bash

# Check and install missing dependencies
check_dependency() {
    if ! command -v "$1" &> /dev/null; then
        echo "$1 is not installed. Installing..."
        sudo apt-get update
        sudo apt-get install -y "$1"
    else
        echo "$1 is already installed."
    fi
}

check_dependency "coreutils"
check_dependency "default-jre"
check_dependency "default-jdk"

# Compile the Java code and create the JAR archive
compile_and_create_jar() {
    echo "Compiling the Java code..."
    javac -Xlint:unchecked ./javacrypt/*.java

    echo "Creating the JAR archive..."
    jar cfm JavaCrypt.jar manifest.mf javacrypt/*.class
}

# Execute the function for compilation and JAR creation
compile_and_create_jar

# Set execution permissions for the JAR archive
echo "Setting execution permissions for the JAR archive..."
chmod +x JavaCrypt.jar
rm -rf javacrypt/*.class
echo "Done!"
