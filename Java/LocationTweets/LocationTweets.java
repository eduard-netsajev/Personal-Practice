import java.util.ArrayList;
import java.util.List;

public class LocationTweets implements ITwitterApplication {

    private static final String TWITTER_CUSTOMER_KEY =
            "zpNDfFQlI8sfAvA0e5dIwypU9";
    private static final String TWITTER_CUSTOMER_SECRET =
            "SvSHa19OT1BfHD95DaQI2eJs8zGMMXh1c3XWkgXqEzLLUzszVC";

    private static int tweetsCount = 5;

    public static void main(String[] args){

        LocationTweets lc = new LocationTweets();

        List<IAction> ls;
        ls = lc.getActionsFromInput("query \"Tallinn Raja 4D\" 15 search kala" +
                " " +
                "sort date desc");
        System.out.println();

        /*LocationSearch locSearch = new LocationSearch();
        ITwitterQuery iQuery = locSearch.getQueryFromLocation("Tallinn");

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setApplicationOnlyAuthEnabled(true);
        cb.setOAuthConsumerKey(TWITTER_CUSTOMER_KEY)
                .setOAuthConsumerSecret(TWITTER_CUSTOMER_SECRET);

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter4j.Twitter twitter = tf.getInstance();

        OAuth2Token token;
        try {
            token = twitter.getOAuth2Token();
            System.out.println(token.toString());
        } catch (TwitterException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.exit(-1);
        }

        try {
            Query query = new Query();
            query.setCount(10);
            GeoLocation location = new GeoLocation(iQuery.getLatitude(),
                    iQuery.getLongitude());
            query.setGeoCode(location, iQuery.getRadius(), Query.KILOMETERS);
            query.setResultType(Query.ResultType.recent);

            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    try {
                        GeoLocation ths = tweet.getGeoLocation();

                        try {
                            System.out.println(ths.getLatitude());
                            System.out.println(ths.getLongitude());
                        } catch (Exception e) {
                            System.out.println("problem2");
                        }


                    } catch (Exception e) {
                        System.out.println("problem1");
                    }
                    System.out.println(tweet.getCreatedAt().toString());
                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
                break; // TODO next page
            } while ((query = result.nextQuery()) != null);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }*/

    }


    /**
     * Given a command as a String (in interactive mode), this method should
     * parse the input and return a list of IAction instances. Usually one
     * command should create one action, this method gives the opportunity to
     * have combined actions for one command line ("query tallinn search kala").
     * If you don't use combined actions, just return a list with one element in
     * it - the IAction instance to be executed.
     *
     * @param action Command string from interactive mode
     * @return List of actions to be executed
     */
    @Override
    public List<IAction> getActionsFromInput(String action) {

        String[] args = action.split("\\s+");

        List<IAction> actions = new ArrayList<IAction>(2);

        String type = "NoType";

        try {
            for (int i = 0; i < args.length; i++) {

                type = args[i];

                switch (type) {
                    case "query":
                        QueryAction queryAction = new QueryAction();
                        String location = args[++i];

                        String temp = "";
                        if (location.charAt(0) == '"') {
                            while (!temp.endsWith("\"")) {
                                temp = args[++i];
                                location += " " + temp;
                            }
                            location = location.substring(1,
                                    location.length() - 1);
                        }
                        queryAction.setLocation(location);

                        int count;
                        try {
                            count = Integer.parseUnsignedInt(args[++i]);
                            queryAction.setCount(count);
                        } catch (NumberFormatException e) {
                            count = getTweetsCount();
                            i--;
                        }

                        queryAction.setCount(count);
                        actions.add(queryAction);
                        break;
                    case "setcount":
                        try {
                            int tweetsCount = Integer.parseUnsignedInt(args[++i]);
                            actions.add(new SetCountAction(tweetsCount));
                        } catch (NumberFormatException e) {
                            System.out.println("Illegal count argument.");
                        }
                        break;
                    case "sort":
                        SortFilterAction filterAction = new SortFilterAction();
                        String field = args[++i];
                        switch (field) {
                            case "author":
                                filterAction.setSortField(1);
                                break;
                            case "date":
                                filterAction.setSortField(2);
                                break;
                            case "content":
                                filterAction.setSortField(3);
                                break;
                            default:
                                System.out.println("Illegal sort argument.");
                        }

                        if (i+1 < args.length) {

                            if (args[++i].equals("desc")) {
                                filterAction.setSortOrder(2);
                            } else {
                                i--;
                            }
                        }

                        actions.add(filterAction);
                        break;
                    case "print":
                        actions.add(new PrintAction());
                        break;
                    case "search":
                        SortFilterAction searchAction = new SortFilterAction();

                        String phrase = args[++i];
                        String tempToken = "";
                        if (phrase.charAt(0) == '"') {
                            while (!tempToken.endsWith("\"")) {
                                tempToken = args[++i];
                                phrase += tempToken;
                            }
                            phrase = phrase.substring(1, phrase.length() - 2);
                        }
                        searchAction.setSearchKeyword(phrase);
                        actions.add(searchAction);
                        break;
                    case "help":
                        actions.add(new HelpAction());
                        break;
                    default:
                        System.out.println("Illegal arguments detected. See help");
                        /*throw new InputMismatchException("Actions reading failed");*/
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Not enough arguments for action " + type);
        }

        return actions;
    }

    /**
     * Given command line arguments this method parses the arguments and returns
     * a list of IAction instances. As the command line can accept several
     * different actions (for example query, sort and search), this method
     * return a list of all the actions.
     *
     * @param arguments Command line arguments (from main method)
     * @return List of actions to be executed
     */
    @Override
    public List<IAction> getActionsFromArguments(String[] arguments) {

        List<IAction> actions = new ArrayList<IAction>(5);

        String argString = String.join(" ", arguments);
        String[] commands = argString.split("-"); // Note: removes leading '-'

        QueryAction queryAction = new QueryAction();
        queryAction.setLocation(commands[0]);
        queryAction.setCount(getTweetsCount());
        actions.add(queryAction);

        boolean allWasOK = true;

        for (int c = 1; c < commands.length; c++) {
            String command = commands[c];
            command = command.trim();
            String[] args = command.split("\\s+");
            String type = "NoType";
            try {
                for (int i = 0; i < args.length; i++) {
                    type = args[i];
                    switch (type) {
                        case "count":
                            try {
                                int tweetsCount = Integer.parseUnsignedInt(args[++i]);
                                actions.add(new SetCountAction(tweetsCount));
                            } catch (NumberFormatException e) {
                                allWasOK = false;
                                System.out.println("Illegal count argument.");
                            }
                            continue;
                        case "sort":
                            SortFilterAction filterAction = new SortFilterAction();
                            String field = args[++i];
                            switch (field) {
                                case "author":
                                    filterAction.setSortField(1);
                                    break;
                                case "date":
                                    filterAction.setSortField(2);
                                    break;
                                case "content":
                                    filterAction.setSortField(3);
                                    break;
                                default:
                                    allWasOK = false;
                                    System.out.println("Illegal sort argument.");
                            }

                            if (i + 1 < args.length) {
                                if (args[++i].equals("desc")) {
                                    filterAction.setSortOrder(2);
                                }
                            }
                            actions.add(filterAction);
                            continue;
                        case "search":
                            SortFilterAction searchAction = new SortFilterAction();

                            String phrase = args[++i];
                            String tempToken = "";
                            if (phrase.charAt(0) == '"') {
                                while (!tempToken.endsWith("\"")) {
                                    tempToken = args[++i];
                                    phrase += tempToken;
                                }
                                phrase = phrase.substring(1, phrase.length() - 2);
                            }
                            searchAction.setSearchKeyword(phrase);
                            actions.add(searchAction);
                            continue;
                        default:
                            allWasOK = false;
                            System.out.println("Illegal arguments detected. See help");
                        /*throw new InputMismatchException("Actions reading failed");*/
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                allWasOK = false;
                System.out.println("Not enough arguments for action " + type);
            }
        }

        if (allWasOK) {
            actions.add(new PrintAction());
        } else {
            System.out.println("There were some problems with your parameters");
            actions.add(new HelpAction());
        }

        return actions;
    }

    /**
     * Given an instance of IAction, it will be executed.
     *
     * @param action
     */
    @Override
    public void executeAction(IAction action) {

    }

    /**
     * Stores location search object which will be used to make queries to
     * location search API.
     *
     * @param locationSearch Implementation of ILocationSearch, which can find
     *                       information about location (city, country etc.).
     */
    @Override
    public void setLocationSearch(ILocationSearch locationSearch) {

    }

    /**
     * Returns currently stored location search object.
     *
     * @return Implementation of ILocationSeach which will be used for location
     * search.
     */
    @Override
    public ILocationSearch getLocationSearch() {
        return null;
    }

    /**
     * Stores Twitter search object which will be used to query tweets from
     * Twitter API.
     *
     * @param twitterSearch Implementation of ITwitterSearch
     */
    @Override
    public void setTwitterSearch(ITwitterSearch twitterSearch) {

    }

    /**
     * Returns currently stored Twitter search object.
     *
     * @return Implementation of ITwitterSearch which will be used for queries.
     */
    @Override
    public ITwitterSearch getTwitterSearch() {
        return null;
    }

    /**
     * Stores cache object which will be used to cache locations in the file.
     *
     * @param cache Implementation of ICache
     */
    @Override
    public void setCache(ICache cache) {

    }

    /**
     * Returns currently stored cache object.
     *
     * @return Implementation of ICache which will be used for location caching.
     */
    @Override
    public ICache getCache() {
        return null;
    }

    /**
     * Stores the latest state of tweets list. You should store your tweets
     * using this method after querying, sorting, searching.
     *
     * @param tweets A list of tweets
     */
    @Override
    public void setTweets(List<? extends ITweet> tweets) {

    }

    /**
     * Get the latest state of tweets list. This method should be used for
     * printing and when applying sorting or searching.
     *
     * @return A list of tweets
     */
    @Override
    public List<? extends ITweet> getTweets() {
        return null;
    }

    public static int getTweetsCount() {
        return tweetsCount;
    }

    public static void setTweetsCount(int tweetsCount) {
        LocationTweets.tweetsCount = tweetsCount;
    }
}


