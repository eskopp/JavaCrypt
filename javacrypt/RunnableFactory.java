/*
 * File: RunnableFactory.java
 * Package: javacrypt
 * Author: Erik Skopp
 * Created: 10.06.2018
 * Modified: 18.11.2023
 * Version: 1.0.4
 */

package javacrypt;

import java.lang.reflect.Constructor;

/**
 * The RunnableFactory class is responsible for creating instances of classes that implement the RunnableInterface.
 * It uses reflection to dynamically instantiate the classes based on the provided class name.
 * <p>
 * This class follows the Factory design pattern, providing a centralized way to create objects without exposing the instantiation logic.
 * <p>
 * The RunnableFactory expects the class name of the desired RunnableInterface implementation as input and returns an instance of that class.
 * <p>
 * Example usage:
 * <p>
 * RunnableFactory factory = new RunnableFactory();
 * RunnableInterface runnable = factory.getInstance("javacrypt.RunDecrypt");
 * runnable.run();
 * <p>
 * In the example above, the RunnableFactory creates an instance of the RunDecrypt class, which implements the RunnableInterface,
 * and then invokes the run() method on the created instance.
 * <p>
 * Note: The class name provided to the getInstance() method should be the fully qualified class name (including the package).
 * 
 * @author Erik Skopp
 */
public class RunnableFactory {

    /**
     * Creates an instance of a class that implements the RunnableInterface based on the provided class name.
     *
     * @param className The fully qualified name of the class to instantiate.
     * @return An instance of the class that implements the RunnableInterface.
     * @throws Exception If an error occurs during instantiation.
     */
    public RunnableInterface getInstance(String className) throws Exception {
        // Load the class based on the provided class name
        Class<?> cls = Class.forName(className);
        
        // Get the default constructor of the class
        Constructor<?> constructor = cls.getDeclaredConstructor();
        
        // Instantiate the class using the constructor
        return (RunnableInterface) constructor.newInstance();
    }
}
