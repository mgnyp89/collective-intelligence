package core;

import data.DatasetReader;
import data.DatasetReaderCSV;
import evaluation.Evaluator;
import model.UserCollection;

/**
 * Commandline runner for all evaluation modes
 */
public class Runner {

    private final static String MOVIES_FILE_PATH = "./resources/100k.csv";

    public static void main(String args[]) {
        System.out.println("Loading the data");
        DatasetReader datasetReaderCSV = new DatasetReaderCSV();
        UserCollection userCollection = null;
        try {
            userCollection = datasetReaderCSV.parseData(MOVIES_FILE_PATH);
        } catch (Exception e) {
            System.out.println("Can't load file " + MOVIES_FILE_PATH + " , double check the file path");
            System.exit(1);
        }

        System.out.println("Loaded dataset movies");
        System.out.println("*******************************");

        Evaluator evaluation = new Evaluator(userCollection);

        evaluation.performBasicEvaluation();
        evaluation.performFullStatisticalEvaluationForUsers();
        evaluation.performFullStatisticalEvaluationForItems();
        evaluation.displayDensityValueForDataset();

    }

}
