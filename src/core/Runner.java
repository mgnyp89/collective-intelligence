package core;

import data.DatasetReader;
import data.DatasetReaderCSV;
import evaluation.LeaveOneOutEvaluator;
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

        LeaveOneOutEvaluator leaveOneOutEvaluator = new LeaveOneOutEvaluator(userCollection);

        System.out.println("Starting evaluation");
        System.out.println("Running test No. 1");
        leaveOneOutEvaluator.runLeaveOneOutLoopTest();
        leaveOneOutEvaluator.printAverageRMSE();
        leaveOneOutEvaluator.printCoverage();
        System.out.println("*******************************");
        System.out.println("Starting run time evaluation");
        for (int i = 2; i < 11 ; i++) {
            System.out.println("Running test No. " + i);
            leaveOneOutEvaluator.runLeaveOneOutLoopTest();
        }
        leaveOneOutEvaluator.printAverageRunTime();

    }

}
