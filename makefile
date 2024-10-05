.PHONY: all clean test permissions clean_test

# Define the target executable
TARGET = JavaCrypt.jar

# Define the source directory
SRC_DIR = javacrypt

# Define the source files
SRC_FILES = $(wildcard $(SRC_DIR)/*.java)

# Define the compiler and flags
JAVAC = javac
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

# Test rule to compile and run a simple Java file
test: $(SRC_DIR)/TestJava.class
	@echo "Compiling TestJava.java..."
	@$(JAVAC) $(SRC_DIR)/TestJava.java
	@echo "Running Java test..."
	@java javacrypt.TestJava
	@$(MAKE) clean_test

# Rule to compile the test Java file
$(SRC_DIR)/TestJava.class: $(SRC_DIR)/TestJava.java
	@echo "Compiling TestJava.java..."
	@$(JAVAC) $(SRC_DIR)/TestJava.java

# Rule to clean up only the TestJava class files after test
clean_test:
	@echo "Cleaning up test class files..."
	@rm -f $(SRC_DIR)/TestJava.class
	@echo "Test cleanup done!"
