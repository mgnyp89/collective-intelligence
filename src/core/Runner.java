package core;

import data.DatasetReader;
import data.DatasetReaderCSV;
import evaluation.CosineDistanceBasedPredictionEvaluator;
import evaluation.DistanceBasedPredictionEvaluator;
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

        CosineDistanceBasedPredictionEvaluator cosineDistanceBasedPredictionEvaluator =
                new CosineDistanceBasedPredictionEvaluator(userCollection);

        System.out.println("Starting evaluation");
        int[] neighbourhoodSize = new int[] { 5, 10, 30, 50, 100, 150, 200, 300, 400, 1000 };


        for (int i = 0; i < neighbourhoodSize.length ; i++) {
            System.out.println("Neighbourhood size:  "+neighbourhoodSize[i]);
            cosineDistanceBasedPredictionEvaluator.runLeaveOneOutLoopTest(neighbourhoodSize[i]);
            cosineDistanceBasedPredictionEvaluator.printAverageRMSE();
            cosineDistanceBasedPredictionEvaluator.printCoverage();
            System.out.println("*******************************");
        }
        System.out.println("cosineDistanceBasedPredictionEvaluator:");
        cosineDistanceBasedPredictionEvaluator.printAverageRunTime();
    }

}
