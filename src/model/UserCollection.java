package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserCollection {

    /**
     * Represents internally mapping of users to collections of movies they rated
     * <userId, Map<movieId, movieRating>> where movieRating contains rating and timestamp
     */
    private Map<String, RecordCollection> userCollection;
    // <movie_id, List<Rating> associated with the movie>
    private Map<String, List<Rating>> movieRatings;
    // <movie_id, Map<userId, Rating>>
    private Map<String, RecordCollection> movieCollection;
    // <number of ratings given by the user>
    private List<Integer> ratingsDistributionPerUser;
    // <number of ratings given to the item>
    private List<Integer> ratingsDistributionPerItem;
    private int[] ratingClasses = null;

    private int maxRating = Integer.MIN_VALUE;
    private int minRating = Integer.MAX_VALUE;

    public UserCollection() {
        this.userCollection = new HashMap<>();
        this.movieCollection = new HashMap<>();
        this.movieRatings = new HashMap<>();
        this.ratingsDistributionPerUser = null;
        this.ratingsDistributionPerItem = null;
    }

    public Map<String, RecordCollection> getUserCollection() {
        return userCollection;
    }

    /**
     * If the userId is present in the collection retrieve the movie rating map and add a new entry
     * Otherwise create a new rating map and insert it into the collection under the userId key
     *
     * Create movie collection -> mapping of movie id to ratings from all users.
     * Same principle: if movie id exists, add the rating to the list of ratings, otherwise create it.
     * @param userId record collection key
     * @param movieId movie collection key
     * @param rating movie rating (numeric rating + timestamp)
     */
    public void addRecord(String userId, String movieId, Rating rating) {

        if (rating.getRating() > maxRating) maxRating = rating.getRating();
        if (rating.getRating() < minRating) minRating = rating.getRating();

        if (movieRatings.containsKey(movieId)) {
            movieRatings.get(movieId).add(rating);
        }
        else {
            List<Rating> ratings = new ArrayList<>();
            ratings.add(rating);
            movieRatings.put(movieId, ratings);
        }

        RecordCollection recordCollectionByUser = new RecordCollection();
        recordCollectionByUser.addRating(userId, rating);

        if (movieCollection.containsKey(movieId)) {
            movieCollection.get(movieId).addRating(userId, rating);
        }
        else {
            movieCollection.put(movieId, recordCollectionByUser);
        }
        RecordCollection recordCollectionByItem = new RecordCollection();
        recordCollectionByItem.addRating(movieId, rating);

        userCollection.merge(userId, recordCollectionByItem, (stringRatingMap, stringRatingMap2) -> {
            stringRatingMap.addRating(movieId, rating);
            return stringRatingMap;
        });
    }

    public int getRecordRating(String userId, String itemId) {
        if (movieCollection.containsKey(itemId) && movieCollection.get(itemId).getRatingCollection().containsKey(userId)) {
            return movieCollection.get(itemId).getRatingCollection().get(userId).getRating();
        }
        return -1;
    }

    /**
     *
     * @return number of users with present records in the collection
     */
    public int getUsersCount() {
        return userCollection.size();
    }

    public int getTotalMoviesCount() {
        return movieRatings.size();
    }

    public int getTotalRatingsCount() {
        return movieRatings.values().stream().mapToInt(value -> value.size()).sum();
    }

    public double calculateMaxDiff() {
        return Math.pow((double) maxRating - minRating, 2);
    }


    public int getRatingCountForRatingClass(int ratingClass) {
        if (ratingClasses == null) {
            ratingClasses = new int[6];

            for (Map.Entry<String, List<Rating>> entry : movieRatings.entrySet()) {
                for (Rating rating : entry.getValue()) {
                    ratingClasses[rating.getRating()]++;
                }
            }
        }

        return ratingClasses[ratingClass];
    }

    public List<Integer> getRatingCollectionForItemLeavingOneOut(String movieId, String userId)
            throws IllegalStateException {
        // shallow copy so we can safely remove unnecessary userId without influencing the original data
        Map<String, Rating> ratings = new HashMap<>(movieCollection.get(movieId).getRatingCollection());
        ratings.remove(userId);
        if (ratings.size() == 0) {
            throw new IllegalStateException("No ratings founds to compare against");
        }
        return ratings.values().stream().map(Rating::getRating).collect(Collectors.toList());
    }

    public List<Integer> getRatingsDistributionPerUser() {
        if (ratingsDistributionPerUser == null) {
            ratingsDistributionPerUser = userCollection.values().stream().map(RecordCollection::getRatingsCount)
                    .collect(Collectors.toList());
        }
        return ratingsDistributionPerUser;
    }

    public List<Integer> getRatingsDistributionPerItem() {
        if (ratingsDistributionPerItem == null) {
            ratingsDistributionPerItem = movieRatings.values().stream().map(Collection::size)
                    .collect(Collectors.toList());
        }
        return ratingsDistributionPerItem;
    }
}
