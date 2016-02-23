package math;

import model.UserCollection;
import java.util.Optional;

public class StatisticsPresenter {

    /**
     * Assuming that the data is not corrupted, that is if there is an entry for the user
     * it contains at least one valid rating.
     * @param userCollection
     * @return min of the rating count of all users (the least number of ratings given by a user)
     */
    public Optional<Integer> getMininumRatingCountForUsers(UserCollection userCollection) {
        return StatisticsHelper.calculateMinimum(userCollection.getRatingsDistributionPerUser());
    }

    /**
     * Assuming that the data is not corrupted, that is if there is an entry for the item
     * it contains at least one valid rating.
     * @param userCollection
     * @return min of the rating count of all items (the least number of ratings given for an item)
     */
    public Optional<Integer> getMinimumRatingCountForItems(UserCollection userCollection) {
        return StatisticsHelper.calculateMinimum(userCollection.getRatingsDistributionPerItem());
    }

    /**
     * Assuming that the data is not corrupted, that is if there is an entry for the user
     * it contains at least one valid rating.
     * @param userCollection
     * @return max of the rating count of all users (the most number of ratings given by a user)
     */
    public Optional<Integer> getMaxinumRatingCountForUsers(UserCollection userCollection) {
        return StatisticsHelper.calculateMaximum(userCollection.getRatingsDistributionPerUser());
    }

    /**
     * Assuming that the data is not corrupted, that is if there is an entry for the item
     * it contains at least one valid rating.
     * @param userCollection
     * @return max of the rating count of all items (the most number of ratings given for an item)
     */
    public Optional<Integer> getMaximumRatingCountForItems(UserCollection userCollection) {
        return StatisticsHelper.calculateMinimum(userCollection.getRatingsDistributionPerItem());
    }


    /**
     *
     * @param userCollection
     * @return arithmetic mean of the rating count across all users
     */
    public double getMeanRatingCountForUsers(UserCollection userCollection) {
        return StatisticsHelper.calculateMean(userCollection.getRatingsDistributionPerUser());
    }

    /**
     *
     * @param userCollection
     * @return arithmetic mean of the rating count across all items
     */
    public double getMeanRatingCountForItems(UserCollection userCollection) {
        return StatisticsHelper.calculateMean(userCollection.getRatingsDistributionPerItem());
    }

    /**
     *
     * @param userCollection
     * @return median of the rating count values across all users
     */
    public double getMedianRatingCountForUsers(UserCollection userCollection) {
        return StatisticsHelper.calculateMedian(userCollection.getRatingsDistributionPerUser());
    }

    /**
     *
     * @param userCollection
     * @return median of the rating count values across all items
     */
    public double getMedianRatingCountForItems(UserCollection userCollection) {
        return StatisticsHelper.calculateMedian(userCollection.getRatingsDistributionPerItem());
    }

    /**
     *
     * @param userCollection
     * @return standard deviation of all ratings counts for all users
     */
    public double getStandardDeviationRatingCountForUsers(UserCollection userCollection) {
        return StatisticsHelper.calculateStandardDeviation(userCollection.getRatingsDistributionPerUser());
    }

    /**
     *
     * @param userCollection
     * @return standard deviation of all ratings counts for all items
     */
    public double getStandardDeviationRatingCountForItems(UserCollection userCollection) {
        return StatisticsHelper.calculateStandardDeviation(userCollection.getRatingsDistributionPerItem());
    }

    /**
     * Provided ratings (number of all ratings) divided by
     * All possible ratings (number of all users * number of all movies)
     * @param userCollection
     * @return
     */
    public double getRatingsDensityMetric(UserCollection userCollection) {
        double totalRatings = userCollection.getTotalRatingsCount();
        double allPossibleRatings = userCollection.getUsersCount() * userCollection.getTotalMoviesCount();

        return totalRatings / allPossibleRatings;
    }
}
