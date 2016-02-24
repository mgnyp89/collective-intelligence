package evaluation;

import math.StatisticsPresenter;

import model.UserCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleEvaluator provides bundles of information about the data
 */
public class SimpleEvaluator {

    private final UserCollection USER_COLLECTION;
    private final StatisticsPresenter statisticsPresenter;
    private List<Double> leaveOneOutTestTimings;

    public SimpleEvaluator(UserCollection userCollection) {
        USER_COLLECTION = userCollection;
        this.statisticsPresenter = new StatisticsPresenter();
        leaveOneOutTestTimings = new ArrayList<>();
    }

    public void performBasicEvaluation() {
        System.out.println("Starting basic evaluation of the data");

        long start = System.currentTimeMillis();

        System.out.println("Number of users: " + USER_COLLECTION.getUsersCount());
        System.out.println("Total number of movies: " + USER_COLLECTION.getTotalMoviesCount());
        System.out.println("Total number of movie ratings: " + USER_COLLECTION.getTotalRatingsCount());

        for (int i = 1 ; i <= 5 ; i++) {
            System.out.println("Number of ratings for rating class " + i + " : " +
                    USER_COLLECTION.getRatingCountForRatingClass(i));
        }

        long end = System.currentTimeMillis() - start;

        System.out.println("SimpleEvaluator completed in " + end + " miliseconds");
        System.out.println("*******************************");

    }

    /**
     * Full statistical evaluation of the dataset for users
     */
    public void performFullStatisticalEvaluationForUsers() {
        System.out.println("Starting statistical evaluation");

        long start = System.currentTimeMillis();
        System.out.println("Minimum ratings count for users: "
                + statisticsPresenter.getMininumRatingCountForUsers(USER_COLLECTION));

        System.out.println("Maximum ratings count for users: "
                + statisticsPresenter.getMaxinumRatingCountForUsers(USER_COLLECTION));

        System.out.println("Mean of ratings count for users: "
                + statisticsPresenter.getMeanRatingCountForUsers(USER_COLLECTION));

        System.out.println("Median of ratings count for users: "
                + statisticsPresenter.getMedianRatingCountForUsers(USER_COLLECTION));

        System.out.println("Standard deviation of ratings count for users: "
                + statisticsPresenter.getStandardDeviationRatingCountForUsers(USER_COLLECTION));

        long end = System.currentTimeMillis() - start;

        System.out.println("SimpleEvaluator completed in " + end + " miliseconds");
        System.out.println("*******************************");

    }

    /**
     * Full statistical evaluation of the dataset for items
     */
    public void performFullStatisticalEvaluationForItems() {
        System.out.println("Starting evaluation of the data for items");

        long start = System.currentTimeMillis();
        System.out.println("Minimum ratings count for items: "
                + statisticsPresenter.getMinimumRatingCountForItems(USER_COLLECTION));

        System.out.println("Maximum ratings count for items: "
                + statisticsPresenter.getMaximumRatingCountForItems(USER_COLLECTION));

        System.out.println("Mean of ratings count for items: "
                + statisticsPresenter.getMeanRatingCountForItems(USER_COLLECTION));

        System.out.println("Median of ratings count for items: "
                + statisticsPresenter.getMedianRatingCountForItems(USER_COLLECTION));

        System.out.println("Standard deviation of ratings count for items: "
                + statisticsPresenter.getStandardDeviationRatingCountForItems(USER_COLLECTION));

        long end = System.currentTimeMillis() - start;

        System.out.println("SimpleEvaluator completed in " + end + " miliseconds");
        System.out.println("*******************************");

    }

    public void displayDensityValueForDataset() {
        System.out.println("Starting evaluation of the data");

        long start = System.currentTimeMillis();

        System.out.println("The density value is " + statisticsPresenter.getRatingsDensityMetric(USER_COLLECTION));

        long end = System.currentTimeMillis() - start;

        System.out.println("SimpleEvaluator completed in " + end + " miliseconds");
        System.out.println("*******************************");
    }

}
