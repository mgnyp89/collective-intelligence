package core;

import data.DatasetReader;
import data.DatasetReaderCSV;
import evaluation.Evaluator;
import model.UserCollection;

import java.util.Scanner;

/**
 * Commandline runner for all evaluation modes
 */
public class Runner {

    private final static String MOVIES_FILE_PATH = "./resources/100k.csv";
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) {
        System.out.println("Loading the data");
        DatasetReader datasetReaderCSV = new DatasetReaderCSV();
        UserCollection userCollection = null;
        try {
            userCollection = datasetReaderCSV.parseData(MOVIES_FILE_PATH);
        } catch (Exception e) {
            System.out.println("Can't load file " + MOVIES_FILE_PATH + " , double check the file path");
        }

        System.out.println("Loaded dataset movies");

        Evaluator evaluation = new Evaluator(userCollection);

        System.out.println("At any time: ");
        System.out.println("Type 'exit' to exit.");

        System.out.println("Please choose the desired evaluation method. You can choose from 3 evaluation bundles: ");
        System.out.println("Choose '1' for general evalution: total number of users, items, ratings, " +
                "total number of ratings for each of the 5 ratings classes.");
        System.out.println("Choose '2' for evaluation of a specific user: mean, median, standard deviation, max, " +
                "min ratings per user");
        System.out.println("Choose '3' for evaluation of a specific item: mean, median, standard deviation, max, " +
                "min ratings per item");
        System.out.println("Choose '4' to view the density metric of the dataset: this is total provided number of" +
                " ratings (number of all ratings) divided by\n" +
                " all possible ratings (number of all users * number of all movies)");

        while (!scanner.nextLine().equals("exit")) {
            int chosenEvaluationMode = scanner.nextInt();

            if (chosenEvaluationMode == 1) {
                evaluation.performFullEvaluation();
            } else if (chosenEvaluationMode == 2) {
                System.out.println("Please enter the user id");
                String userId = String.valueOf(scanner.nextInt());
                evaluation.performFullEvaluationForUser(userId);
            } else if (chosenEvaluationMode == 3) {
                System.out.println("Please enter the movie id");
                String movieId = String.valueOf(scanner.nextInt());
                evaluation.performFullEvaluationForItem(movieId);
            } else if (chosenEvaluationMode == 4) {
                evaluation.displayDensityValueForDataset();
            }
            else {
                System.out.println("Unrecognized input. Exiting");
                System.exit(0);
            }

        }
    }

}
