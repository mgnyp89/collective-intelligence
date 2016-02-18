package model;

public class Rating implements Comparable<Rating> {

    private final int rating;
    private final int timestamp;

    public Rating(int rating, int timestamp) {
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getRating() {
        return rating;
    }

    public int compareTo(Rating other){
        // compareTo should return < 0 if this is supposed to be
        // less than other, > 0 if this is supposed to be greater than
        // other and 0 if they are supposed to be equal
        if (this.rating == other.rating) return 0;
        return (this.rating < other.rating) ? -1 : 1;
    }
}
