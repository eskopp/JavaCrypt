@echo off

rem Check if Java JRE is installed
where java > nul 2>&1
if %errorlevel% neq 0 (
    echo Java JRE is not installed. Please install Java JRE.
    goto :eof
) else (
    echo Java JRE is installed.
    java -version
)

rem Check if Java JDK is installed
where javac > nul 2>&1
if %errorlevel% neq 0 (
    echo Java JDK is not installed. Please install Java JDK.
    goto :eof
) else (
    echo Java JDK is installed.
    javac -version
)

rem Continue only if Java JRE and JDK are installed

rem Compile the Java code
echo Compiling the Java code...
javac -Xlint:unchecked ./javacrypt/*.java

rem Set variables
echo Setting variables...
set "manifestPath=manifest.mf"
set "classFilesPath=javacrypt/*.class"
set "jarFilePath=JavaCrypt.jar"

rem Create the JAR archive
echo Creating the JAR archive...
jar cfm %jarFilePath% %manifestPath% %classFilesPath%

rem Delete JAVA Class
cd javacrypt
del *.class
cd ..

@echo on
