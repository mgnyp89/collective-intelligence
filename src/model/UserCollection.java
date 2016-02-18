package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by mon on 17/02/16.
 */
public class UserCollection {

    /**
     * Represents internally mapping of users to collections of movies they rated
     * <userId, Map<movieId, movieRating>> where movieRating contains rating and timestamp
     */
    private Map<String, RecordCollection> userCollection;
    private Map<String, List<Rating>> movieCollection;
    private int[] ratingClasses = null;

    public UserCollection() {
        this.userCollection = new HashMap<>();
        this.movieCollection = new HashMap<>();
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

        if (movieCollection.containsKey(movieId)) {
            movieCollection.get(movieId).add(rating);
        }
        else {
            List<Rating> ratings = new ArrayList<>();
            ratings.add(rating);
            movieCollection.put(movieId, ratings);
        }

        RecordCollection recordCollection = new RecordCollection();
        recordCollection.addRating(movieId, rating);

        userCollection.merge(userId, recordCollection, (stringRatingMap, stringRatingMap2) -> {
            stringRatingMap.addRating(movieId, rating);
            return stringRatingMap;
        });
    }

    /**
     *
     * @param userId
     * @return all ratings for the user
     */
    public RecordCollection getRatingsForUser(String userId) {
        return userCollection.get(userId);
    }

    /**
     *
     * @return number of users with present records in the collection
     */
    public int getUsersCount() {
        return userCollection.size();
    }

    public int getTotalMoviesCount() {
        return movieCollection.size();
    }

    public int getTotalRatingsCount() {
        int totalRatingsCount = 0;

        for(List<Rating> movieRatings : movieCollection.values()) {
            totalRatingsCount += movieRatings.size();
        }

        return totalRatingsCount;
    }

    public int getRatingCountForRatingClass(int ratingClass) {
        if (ratingClasses == null) {
            ratingClasses = new int[6];

            for (Map.Entry<String, List<Rating>> entry : movieCollection.entrySet()) {
                for (Rating rating : entry.getValue()) {
                    ratingClasses[rating.getRating()]++;
                }
            }
        }

        return ratingClasses[ratingClass];
    }

    public List<Rating> getRatingCollectionForMovie(String movieId) {
        return movieCollection.get(movieId);
    }
}
