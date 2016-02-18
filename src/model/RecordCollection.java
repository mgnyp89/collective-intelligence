package model;

import java.util.HashMap;
import java.util.Map;

public class RecordCollection {

    private Map<String, Rating> ratingCollection;

    public RecordCollection() {
        ratingCollection = new HashMap<>();
    }

    public void addRating(String movieId, Rating rating) {
        ratingCollection.put(movieId, rating);
    }

    public Map<String, Rating> getRatingCollection() {
        return ratingCollection;
    }

    public int getRecordsCount() {
        return ratingCollection.size();
    }

    public int getRatingsCount() {
        return ratingCollection.entrySet().size();
    }

    public int getRatingForMovie(String movieId) {
        return ratingCollection.get(movieId).getRating();
    }

    public int getTimestampForMovie(String movieId) {
        return ratingCollection.get(movieId).getTimestamp();
    }


}
