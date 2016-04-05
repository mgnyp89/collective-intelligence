package math;

import model.Rating;
import model.RecordCollection;
import model.UserCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

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

    /**
     *
     * @param userCollection
     * @param userId
     * @param itemId
     * @return the average rating for this item calculated across all ratings for this item, other that the rating
     * given by the specific user_id.
     */
    public double getMeanItemRatingLeaveOneOut(String userId, String itemId, UserCollection userCollection) throws
        IllegalStateException {
        // get a collection of all ratings excluding the one given by userId
        return StatisticsHelper.calculateMean(userCollection.getRatingCollectionForItemLeavingOneOut(itemId, userId));
    }

    /**
     *
     * @param userId
     * @param userCollection
     * @return
     */
    public TreeMap<Double, String> getListOfSimilarUsers(String userId, UserCollection userCollection) {
        // we need these sorted
        TreeMap<Double, String> similarUsers = new TreeMap<>();

        // calculate similarity between userId and every other user in the collection
        Map<String, RecordCollection> users = userCollection.getUserCollection();

        // get all items rated by userId
        RecordCollection userItemRatings = users.get(userId);

        for (Map.Entry<String, RecordCollection> entry : users.entrySet()) {
            String otherUser = entry.getKey();
            // don't compare the user to himself
            if (!userId.equals(otherUser)) {
                double similarity = getSimpleDistanceSimilarityOfUsers(userItemRatings.getRatingCollection(),
                        entry.getValue().getRatingCollection());
                similarUsers.put(similarity, otherUser);
            }
        }

        return similarUsers;
    }

    /**
     *
     * @param userId
     * @param userCollection
     * @return
     */
    public TreeMap<Double, String> getListOfSimilarUsersUsingCosine(String userId, UserCollection userCollection) {
        // we need these sorted
        TreeMap<Double, String> similarUsers = new TreeMap<>();

        // calculate similarity between userId and every other user in the collection
        Map<String, RecordCollection> users = userCollection.getUserCollection();

        // get all items rated by userId
        RecordCollection userItemRatings = users.get(userId);

        for (Map.Entry<String, RecordCollection> entry : users.entrySet()) {
            String otherUser = entry.getKey();
            // don't compare the user to himself
            if (!userId.equals(otherUser)) {
                double similarity = getCosineDistanceSimilarityOfUsers(userItemRatings.getRatingCollection(),
                        entry.getValue().getRatingCollection());
                similarUsers.put(similarity, otherUser);
            }
        }

        return similarUsers;
    }

    /**
     *
     * @param itemId item id we wish to predict for
     * @param neighbourhoodSize desired neighbourhood size
     * @param similarUsers <similarity, userID>
     * @param userCollection original dataset
     * @return
     */
    public double getDistanceBasedPrediction(String itemId, int neighbourhoodSize,
                                             TreeMap<Double, String> similarUsers, UserCollection userCollection) {

        double distance;
        double distanceSum = 0.0;
        double distanceByRatingSum = 0.0;

        double maxDiff = userCollection.calculateMaxDiff();

       // get the desired neighbourhood
        for (Map.Entry<Double, String> entry : similarUsers.descendingMap().entrySet()) {
            // get simple distance between the users
            distance = getDistance(entry.getKey(), maxDiff);

            // check if the user we are calculating distance to has rated the item we are interested in
            int itemRating = userCollection.getRecordRating(entry.getValue(), itemId);
            if (itemRating > -1) {

                distanceSum += distance;
                distance *= (double) itemRating;
                distanceByRatingSum += distance;
            }

            if (--neighbourhoodSize == 0 ) break;
        }

        if (distanceSum == 0.0 || distanceByRatingSum == 0.0) {
            throw new IllegalStateException("Can't compare!");
        }
        return distanceByRatingSum / distanceSum;
    }

    public double getCosineSimilarityBasedPrediction(String itemId, int neighbourhoodSize,
                                             TreeMap<Double, String> similarUsers, UserCollection userCollection) {
        double distance;
        double distanceSum = 0.0;
        double distanceByRatingSum = 0.0;

        double maxDiff = userCollection.calculateMaxDiff();

        // get the desired neighbourhood
        for (Map.Entry<Double, String> entry : similarUsers.descendingMap().entrySet()) {
            // get simple distance between the users
            distance = getDistance(entry.getKey(), maxDiff);

            // check if the user we are calculating distance to has rated the item we are interested in
            int itemRating = userCollection.getRecordRating(entry.getValue(), itemId);
            if (itemRating > -1) {

                distanceSum += distance;
                distance *= (double) itemRating;
                distanceByRatingSum += distance;
            }

            if (--neighbourhoodSize == 0 ) break;
        }

        if (distanceSum == 0.0 || distanceByRatingSum == 0.0) {
            throw new IllegalStateException("Can't compare!");
        }
        return distanceByRatingSum / distanceSum;
    }

    public double getResnicksPrediction(String userId, String itemId, int neighbourhoodSize,
                                                     TreeMap<Double, String> similarUsers, UserCollection userCollection) {
        double summedRatingsForTargetItem = 0.0;
        double similarity = 0.0;
        double absoluteSimilarirySumOfUsers = 0.0;

        double meanOfRatingsForUser = meanRatingForUser(userCollection, userId);

        // get the desired neighbourhood
        for (Map.Entry<Double, String> entry : similarUsers.descendingMap().entrySet()) {
            String user2 = entry.getValue();

            similarity = getPearsonsCoeffient(userId, user2, userCollection);
            absoluteSimilarirySumOfUsers += Math.abs(similarity);

            // check if the user we are calculating the similarity to has rated the item
            int itemRating = userCollection.getRecordRating(user2, itemId);
            if (itemRating > -1) {
                summedRatingsForTargetItem += ( userCollection.getRecordRating(user2, itemId) - meanRatingForUser(userCollection, user2)) * similarity;
            }

            if (--neighbourhoodSize == 0 ) break;
        }

        return meanOfRatingsForUser + (summedRatingsForTargetItem / absoluteSimilarirySumOfUsers);
    }

    public double getPearsonsCoeffient(String user1, String user2, UserCollection userCollection) {
        double sumOfCoratedItemPredictionsForBothUsers = 0.0;
        double itemRatingMinusMeanForUser1 = 0.0;
        double itemRatingMinusMeanForUser2 = 0.0;

        double sumOfRatingsForUSer1Squared = 0.0;
        double sumOfRatingsForUSer2Squared = 0.0;

        double meanRatingUser1 = meanRatingForUser(userCollection, user1);
        double meanRatingUser2 = meanRatingForUser(userCollection, user2);

        Map<String, Rating> ratingsCollectionUser1 = userCollection.getUserCollection().get(user1).getRatingCollection();
        Map<String, Rating> ratingsCollectionUser2 = userCollection.getUserCollection().get(user2).getRatingCollection();

        // get ratings for items that both users have rated
        for (Map.Entry<String, Rating> entry : ratingsCollectionUser1.entrySet()) {
            String item = entry.getKey();
            // only compare if the key is found in both maps
            if (ratingsCollectionUser2.containsKey(item)) {
                // rating of item k for user1 - mean of the ratings for that user
                itemRatingMinusMeanForUser1 = entry.getValue().getRating() - meanRatingUser1;
                // rating of item k for user1 - mean of the ratings for that user
                itemRatingMinusMeanForUser2 = ratingsCollectionUser2.get(item).getRating() - meanRatingUser2;

                sumOfCoratedItemPredictionsForBothUsers += itemRatingMinusMeanForUser1 * itemRatingMinusMeanForUser2;

                sumOfRatingsForUSer1Squared += Math.pow(itemRatingMinusMeanForUser1, 2);
                sumOfRatingsForUSer2Squared += Math.pow(itemRatingMinusMeanForUser2, 2);

            }
        }

        double sqrtOfSummedRatings = Math.sqrt(sumOfRatingsForUSer1Squared) * Math.sqrt(sumOfRatingsForUSer2Squared);
    //    System.out.println("Done perasons");
        return sumOfCoratedItemPredictionsForBothUsers / sqrtOfSummedRatings;
    }

    private double getSimpleDistanceSimilarityOfUsers(Map<String, Rating> itemRatings1, Map<String, Rating> itemRatings2) {
        // get lists of ratings for items that both users have rated (items in common)

        List<Integer> commonRatingsUser1 = new ArrayList<>();
        List<Integer> commonRatingsUser2 = new ArrayList<>();

        // get ratings for items that both users have rated
        for (Map.Entry<String, Rating> entry : itemRatings1.entrySet()) {
            String otherUser = entry.getKey();
            // only compare if the key is found in both maps
            if (itemRatings2.containsKey(otherUser)) {
                // rating of item k for user1
                commonRatingsUser1.add(entry.getValue().getRating());
                // rating of item k for user2
                commonRatingsUser2.add(itemRatings2.get(otherUser).getRating());
            }
        }
        // get the similarity measure based on mean square differences
        return StatisticsHelper.calculateMeanSquareDifferences(commonRatingsUser1, commonRatingsUser2);
    }

    private double getCosineDistanceSimilarityOfUsers(Map<String, Rating> itemRatings1, Map<String, Rating> itemRatings2) {
        // get lists of ratings for items that both users have rated (items in common)

        List<Integer> commonRatingsUser1 = new ArrayList<>();
        List<Integer> commonRatingsUser2 = new ArrayList<>();

        // get ratings for items that both users have rated
        for (Map.Entry<String, Rating> entry : itemRatings1.entrySet()) {
            String otherUser = entry.getKey();
            // only compare if the key is found in both maps
            if (itemRatings2.containsKey(otherUser)) {
                // rating of item k for user1
                commonRatingsUser1.add(entry.getValue().getRating());
                // rating of item k for user2
                commonRatingsUser2.add(itemRatings2.get(otherUser).getRating());
            }
        }
        // get the similarity measure based on mean square differences
        return StatisticsHelper.calculateMeanSquareDifferences(commonRatingsUser1, commonRatingsUser2);
    }

    private double getDistance(double similarity, double maxDiff) {
        return (1 - similarity) / maxDiff;
    }

    private double meanRatingForUser(UserCollection userCollection, String userId) {
        Collection<Rating> ratings = userCollection.getUserCollection().get(userId).getRatingCollection().values();
        List<Integer> ratingValues = new ArrayList<>();
        for (Rating rating: ratings) {
            ratingValues.add(rating.getRating());
        }
        return StatisticsHelper.calculateMean(ratingValues);
    }
}
