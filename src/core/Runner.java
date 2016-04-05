package core;

import data.DatasetReader;
import data.DatasetReaderCSV;
import evaluation.ResnicksEvaluator;
import model.UserCollection;

/**
 * Commandline runner for all evaluation modes
 */
public class Runner {

    private final static String MOVIES_FILE_PATH = "./resources/100k.csv";

    public static void main(String args[]) {
        DatasetReader datasetReaderCSV = new DatasetReaderCSV();
        UserCollection userCollection = null;
        try {
            userCollection = datasetReaderCSV.parseData(MOVIES_FILE_PATH);
        } catch (Exception e) {
            System.out.println("Can't load file " + MOVIES_FILE_PATH + " , double check the file path");
            System.exit(1);
        }

        int[] neighbourhoodSize = new int[] { 5, 10, 30, 50, 100, 150, 200, 300, 400, 1000 };

        ResnicksEvaluator resnicksEvaluator =
                new ResnicksEvaluator(userCollection);

        System.out.println("Starting evaluation using pearson's coefficient based prediction with Resnick's formula");

        for (int i = 0; i < neighbourhoodSize.length ; i++) {
            System.out.println("*******************************");
            System.out.println("Neighbourhood size:  "+neighbourhoodSize[i]);
            resnicksEvaluator.runLeaveOneOutLoopTest(neighbourhoodSize[i]);
            resnicksEvaluator.printAverageRMSE();
            resnicksEvaluator.printCoverage();
        }
        System.out.println("ResnicksEvaluator average run time over " +
                neighbourhoodSize.length  + " runs:");
        resnicksEvaluator.printAverageRunTime();
    }

}
