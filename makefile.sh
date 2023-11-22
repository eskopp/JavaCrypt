#!/bin/bash

# Function to print error message in red
print_error() {
    echo -e "\e[91m$1\e[0m"
}

# Function to prompt the user with [Y/n] and return true for Y, false for n
prompt_user() {
    read -p "$1 [Y/n]: " response
    case "$response" in
        [yY]|"") return 0 ;; # Accept "Yes" if no input is given
        *) print_error "Aborted. Exiting..."; exit 1 ;;
    esac
}

# Check and install missing dependencies
check_dependency() {
    if ! command -v "$1" &> /dev/null; then
        print_error "$1 is not installed."
        if prompt_user "Do you want to install $1?"; then
            sudo apt-get update
            sudo apt-get install -y "$1"
            if [ $? -eq 0 ]; then
                echo "$1 has been successfully installed."
            else
                print_error "Failed to install $1. Please install it manually."
            fi
        else
            print_error "Skipping installation of $1."
        fi
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
