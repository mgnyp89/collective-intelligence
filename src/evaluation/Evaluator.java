package evaluation;

import math.StatisticsHelper;
import model.UserCollection;

/**
 * Evalutor provides bundles of information about the data
 */
public class Evaluator {

    private final UserCollection USER_COLLECTION;

    public Evaluator(UserCollection userCollection) {
        USER_COLLECTION = userCollection;
    }

    public void performFullEvaluation() {
        System.out.println("*******************************");
        System.out.println("Starting evaluation of the data");

        long start = System.currentTimeMillis();

        System.out.println("Number of users: " + USER_COLLECTION.getUsersCount());
        System.out.println("Total number of movies: " + USER_COLLECTION.getTotalMoviesCount());
        System.out.println("Total number of movie ratings: " + USER_COLLECTION.getTotalRatingsCount());

        for (int i = 1 ; i <= 5 ; i++) {
            System.out.println("Number of ratings for rating class " + i + " : " +
                    USER_COLLECTION.getRatingCountForRatingClass(i));
        }

        long end = System.currentTimeMillis() - start;

        System.out.println("Evaluator completed in " + end + " seconds");
        System.out.println("*******************************");

    }

    /**
     * Full statistical evaluation of the dataset for a given user
     * @param userId
     */
    public void performFullEvaluationForUser(String userId) {
        System.out.println("*******************************");
        System.out.println("Starting evaluation of the data for user  " + userId);

        long start = System.currentTimeMillis();
        System.out.println("Minimum rating user " + userId + " gave: "
                + StatisticsHelper.minRatingPerUser(userId, USER_COLLECTION));

        System.out.println("Maximum rating user " + userId + " gave: "
                + StatisticsHelper.maxRatingPerUser(userId, USER_COLLECTION));

        System.out.println("Mean of ratings given by user " + userId + " : "
                + StatisticsHelper.meanOfRatingsPerUser(userId, USER_COLLECTION));

        System.out.println("Median of ratings given by user " + userId + " : "
                + StatisticsHelper.medianOfRatingsPerUser(userId, USER_COLLECTION));

        System.out.println("Standard deviation of ratings given by user " + userId + " : "
                + StatisticsHelper.standardDeviationOfRatingsPerUser(userId, USER_COLLECTION));

        long end = System.currentTimeMillis() - start;

        System.out.println("Evaluator completed in " + end + " seconds");
        System.out.println("*******************************");

    }

    /**
     * Full statistical evaluation of the dataset for a given movie
     * @param movieId
     */
    public void performFullEvaluationForItem(String movieId) {
        System.out.println("*******************************");
        System.out.println("Starting evaluation of the data for user  "+movieId);

        long start = System.currentTimeMillis();
        System.out.println("Minimum rating for movie " + movieId + " : "
                + StatisticsHelper.minRatingPerMovie(movieId, USER_COLLECTION));

        System.out.println("Maximum rating for movie " + movieId + " : "
                + StatisticsHelper.maxRatingPerMovie(movieId, USER_COLLECTION));

        System.out.println("Mean of ratings given by users for " + movieId + " : "
                + StatisticsHelper.meanOfRatingsPerItem(movieId, USER_COLLECTION));

        System.out.println("Median of ratings given by users for " + movieId + " : "
                + StatisticsHelper.medianfRatingsPerItem(movieId, USER_COLLECTION));

        System.out.println("Standard deviation of ratings given by users for " + movieId + " : "
                + StatisticsHelper.standardDeviationOfRatingsPerItem(movieId, USER_COLLECTION));

        long end = System.currentTimeMillis() - start;

        System.out.println("Evaluator completed in " + end + " seconds");
        System.out.println("*******************************");

    }

    public void displayDensityValueForDataset() {
        System.out.println("*******************************");
        System.out.println("Starting evaluation of the data");

        long start = System.currentTimeMillis();

        System.out.println("The denstity value is " + StatisticsHelper.ratingsDensityMetric(USER_COLLECTION));

        long end = System.currentTimeMillis() - start;

        System.out.println("Evaluator completed in " + end + " seconds");
        System.out.println("*******************************");
    }

}
