package data;

import model.UserCollection;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Intended to be implemented by different types of readers: depending on the data input format
 */
public interface DatasetReader {

    UserCollection parseData(String filePath) throws FileNotFoundException, IOException;

}
