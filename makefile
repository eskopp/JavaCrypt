.PHONY: all clean permissions

# Define the target executable
TARGET = JavaCrypt.jar

# Define the source directory
SRC_DIR = javacrypt

# Define the source files
SRC_FILES = $(wildcard $(SRC_DIR)/*.java)

# Define the compiler and flags
JAVAC = /usr/bin/javac
JAR = jar
JAVAC_FLAGS = -Xlint:unchecked

# Define the default target
all: $(TARGET)

# Rule to build the executable JAR
$(TARGET): $(SRC_FILES)
	@echo "Compiling the Java code..."
	@$(JAVAC) $(JAVAC_FLAGS) $(SRC_FILES)
	@echo "Creating the JAR archive..."
	@$(JAR) cfm $(TARGET) manifest.mf $(SRC_DIR)/*.class

# Rule to clean up the build artifacts
clean:
	@echo "Cleaning up..."
	@rm -rf $(TARGET) $(SRC_DIR)/*.class
	@echo "Done!"

# Rule to set execution permissions for the JAR archive
permissions:
	@echo "Setting execution permissions for the JAR archive..."
	@chmod +x $(TARGET)
