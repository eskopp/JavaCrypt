/*
 * File: RunnableKeyValueFactory.java
 * Package: javacrypt
 * Author: Erik Skopp
 * Created: 10.06.2018
 * Modified: 18.11.2023
 * Version: 1.0.4
 */

package javacrypt;

import java.util.HashMap;
import java.util.Map;

/**
 * A factory class that maps keywords to class names and provides methods for creating instances based on keywords.
 * <p>
 * This class extends the RunnableFactory class.
 * <p>
 * Usage:
 * - Instantiate the RunnableKeyValueFactory with a mapping list of keywords to class names.
 * - Use the getInstanceFromKey() method to get an instance of a class based on the provided keyword.
 * - Use the getClassNameOfKey() method to get the class name associated with a keyword.
 * - Use the containsKey() method to check if a keyword exists in the mapping.
 * <p>
 * Example usage:
 * <p>
 * // Create a mapping list of keywords to class names
 * String[][] mapping = {
 *     {"keyword1", "com.example.Class1"},
 *     {"keyword2", "com.example.Class2"},
 *     {"keyword3", "com.example.Class3"}
 * };
 * <p>
 * // Instantiate the RunnableKeyValueFactory with the mapping list
 * RunnableKeyValueFactory factory = new RunnableKeyValueFactory(mapping);
 * <p>
 * // Get an instance of a class based on a keyword
 * RunnableInterface instance = factory.getInstanceFromKey("keyword1");
 * <p>
 * // Get the class name associated with a keyword
 * String className = factory.getClassNameOfKey("keyword2");
 * <p>
 * // Check if a keyword exists in the mapping
 * boolean containsKey = factory.containsKey("keyword3");
 */
public class RunnableKeyValueFactory extends RunnableFactory {

    // Map for mapping keywords to class names
    private final Map<String, String> key2ClassNameMap;

    /**
     * Constructs a RunnableKeyValueFactory with a mapping list of keywords to class names.
     *
     * @param args The mapping list of keywords to class names.
     */
    public RunnableKeyValueFactory(String[][] args) {
        // Instantiating the Map
        key2ClassNameMap = new HashMap<>();

        if (args != null) {
            // Iterating through the provided mapping list
            for (String[] keywordToClassName : args) {
                String keyword = keywordToClassName[0];
                String className = keywordToClassName[1];

                // Adding the keyword and class name to the Map
                key2ClassNameMap.put(keyword, className);
            }
        }
    }

    /**
     * Gets an instance of a class based on the provided keyword.
     *
     * @param keyword The keyword used to retrieve the class name.
     * @return An instance of the class associated with the keyword, or null if not found.
     * @throws Exception If an error occurs during instance creation.
     */
    public RunnableInterface getInstanceFromKey(String keyword) throws Exception {
        RunnableInterface runnableInterface = null;

        // Getting the class name associated with the keyword
        String className = getClassNameOfKey(keyword);
        if (className != null) {
            // Instantiating a class based on the class name
            runnableInterface = getInstance(className);
        }
        return runnableInterface;
    }

    /**
     * Gets the class name associated with the provided keyword.
     *
     * @param keyword The keyword for which to retrieve the class name.
     * @return The class name associated with the keyword, or null if not found.
     */
    public String getClassNameOfKey(String keyword) {
        // Checking if the keyword exists in the Map
        if (containsKey(keyword)) {
            // Retrieving the class name for the keyword from the Map
            return key2ClassNameMap.get(keyword);
        }
        return null;
    }

    /**
     * Checks if the provided keyword exists in the mapping.
     *
     * @param keyword The keyword to check.
     * @return True if the keyword exists in the mapping, false otherwise.
     */
    public boolean containsKey(String keyword) {
        // Checking if the keyword exists in the Map
        return keyword != null && key2ClassNameMap.containsKey(keyword);
    }
}
