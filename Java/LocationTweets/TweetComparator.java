import java.util.Comparator;

/**
 * Comparator class for comparing tweets by their authors.
 */
class AuthorComparator implements Comparator<ITweet> {

    @Override
    public int compare(ITweet tw1, ITweet tw2) {
        return tw1.getUser().compareTo(tw2.getUser());
    }
}

/**
 * Comparator class for comparing tweets by their timestamps.
 */
class DateComparator implements Comparator<ITweet> {

    @Override
    public int compare(ITweet tw1, ITweet tw2) {
        return tw1.getTimestamp().compareTo(tw2.getTimestamp());
    }
}

/**
 * Comparator class for comparing tweets by their contents.
 */
class ContentComparator implements Comparator<ITweet> {

    @Override
    public int compare(ITweet tw1, ITweet tw2) {
        return tw1.getText().compareTo(tw2.getText());
    }
}