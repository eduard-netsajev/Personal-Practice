import java.util.Comparator;

class AuthorComparator implements Comparator<ITweet> {

    @Override
    public int compare(ITweet tw1, ITweet tw2) {
        return tw1.getUser().compareTo(tw2.getUser());
    }
}

class DateComparator implements Comparator<ITweet> {

    @Override
    public int compare(ITweet tw1, ITweet tw2) {
        return tw1.getTimestamp().compareTo(tw2.getTimestamp());
    }
}

class ContentComparator implements Comparator<ITweet> {

    @Override
    public int compare(ITweet tw1, ITweet tw2) {
        return tw1.getText().compareTo(tw2.getText());
    }
}