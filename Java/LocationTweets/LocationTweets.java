import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Collections;

/**
 * Core of the application.
 */
public class LocationTweets implements ITwitterApplication {

    /**
     * My course grade.
     */
    private static final int FIVE = 5;

    /**
     * LocationSearch instance object.
     */
    private ILocationSearch locationSearch;

    /**
     * TwitterSearch instance object.
     */
    private ITwitterSearch twitterSearch;

    /**
     * Cache instance object.
     */
    private ICache cache;

    /**
     * List of Tweet objects.
     */
    private List<? extends ITweet> tweets;

    /**
     * Count of tweets for queries.
     * By default set to 5.
     */
    private static int tweetsCount = FIVE;

    /**
     * Application Output stream.
     */
    static PrintStream out = System.out;

    /**
     * Main method, start point of the application.
     *
     * @param args - console parameters.
     */
    public static void main(String[] args) {

        LocationTweets lc = new LocationTweets();

        lc.setCache(new Cache());
        lc.getCache().setCacheFilename("kohad.csv");

        if (args.length > 0) {
            List<IAction> actions = lc.getActionsFromArguments(args);
            lc.executeActions(actions);

            if (!(actions.get(actions.size() - 1) instanceof HelpAction)) {
                System.exit(0);
            }
        }

        Scanner scanner = new Scanner(System.in);

        String input;
        boolean active = true;
        while (active) {
            out.println("Input your actions > ");
            input = scanner.nextLine();

            switch (input) {
                case "exit":
                    active = false;
                    break;
                case "quit":
                    active = false;
                    break;
                default:
                    lc.executeActions(lc.getActionsFromInput(input));
            }
        }

        scanner.close();
        out.close();
    }

    /**
     * Given a command as a String (in interactive mode), this method should
     * parse the input and return a list of IAction instances. Usually one
     * command should create one action, this method gives the opportunity to
     * have combined actions for one command line ("query tallinn search kala").
     *
     * @param action Command string from interactive mode
     * @return List of actions to be executed
     */
    @Override
    public List<IAction> getActionsFromInput(String action) {

        List<IAction> actions = new ArrayList<>(2);

        if (action.startsWith("?")) {
            CommandInfoAction help = new CommandInfoAction(action.substring(1));
            actions.add(help);
            return actions;
        }

        String[] args = action.split("\\s+");

        String type = "NoType";

        try {
            for (int i = 0; i < args.length; i++) {

                type = args[i];

                switch (type) {
                    case "query":
                        QueryAction queryAction = new QueryAction();
                        String location = args[i + 1];

                        String temp = "";
                        if (location.charAt(0) == '"') {
                            location = "";
                            while (!temp.endsWith("\"")) {
                                temp = args[++i];
                                location += " " + temp;
                            }
                            location = location.substring(2,
                                    location.length() - 1);
                        } else {
                            i++;
                        }
                        queryAction.setLocation(location);

                        if (i + 1 < args.length) {
                            try {
                                int count = Integer.parseUnsignedInt(args[++i]);
                                queryAction.setCount(count);
                            } catch (NumberFormatException e) {
                                i--;
                            }
                        }
                        actions.add(queryAction);
                        break;
                    case "setcount":
                        String countArg = args[++i];
                        try {
                            int twCount = Integer.parseUnsignedInt(countArg);
                            actions.add(new SetCountAction(twCount));
                        } catch (NumberFormatException e) {
                            out.println("Illegal count argument: " + countArg);
                        }
                        break;
                    case "sort":
                        SortFilterAction filterAction = new SortFilterAction();
                        int fieldNum = getField(args[++i]);

                        if (fieldNum == -1) {
                            continue;
                        } else {
                            filterAction.setSortField(fieldNum);
                        }

                        if (i + 1 < args.length) {
                            if (args[i + 1].equals("desc")) {
                                i++;
                                int desc = IFilterAction.ORDER_DESCENDING;
                                filterAction.setSortOrder(desc);
                            } else if (args[i + 1].equals("asc")) {
                                i++;
                            }
                        }

                        actions.add(filterAction);
                        break;
                    case "print":
                        actions.add(new PrintAction());
                        break;
                    case "search":
                        SortFilterAction searchAction = new SortFilterAction();

                        String phrase = args[i + 1];

                        String tempPhrase = "";
                        if (phrase.charAt(0) == '"') {
                            phrase = "";
                            while (!tempPhrase.endsWith("\"")) {
                                tempPhrase = args[++i];
                                phrase += " " + tempPhrase;
                            }
                            phrase = phrase.substring(2,
                                    phrase.length() - 1);
                        } else {
                            i++;
                        }
                        searchAction.setSearchKeyword(phrase);
                        actions.add(searchAction);
                        break;
                    case "help":
                        actions.add(new HelpAction());
                        break;
                    default:
                        actions.add(new HelpAction());
                        out.println("Illegal argument found: " + type);
                        out.println("Input 'help' for a quick guide.");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            out.println("Not enough arguments for action " + type);
        }

        return actions;
    }

    /**
     * Given a string of field name, returns int of the field,
     * as defined in {@link IFilterAction} interface.
     * @param field field name, like author, date or content
     * @return integer linked to the respective field
     */
    private int getField(String field) {
        switch (field) {
            case "author":
                return IFilterAction.FIELD_AUTHOR;
            case "date":
                return IFilterAction.FIELD_DATE;
            case "content":
                return IFilterAction.FIELD_TWEET;
            default:
                out.println("Illegal sort argument: " + field);
                return -1;
        }
    }

    /**
     * Given command line arguments this method parses the arguments and returns
     * a list of IAction instances. As the command line can accept several
     * different actions (for example query, sort and search), this method
     * return a list of all the actions. Depending on success of parsing
     * all the arguments, append either PrintAction or HelpAction to the end
     * of the list. HelpAction if there were some errors. Otherwise PrintAction.
     *
     * @param arguments Command line arguments (from main method)
     * @return List of actions to be executed
     */
    @Override
    public List<IAction> getActionsFromArguments(String[] arguments) {

        List<IAction> actions = new ArrayList<>(FIVE);

        int l = -1;


        QueryAction queryAction = new QueryAction();
        String location = arguments[l + 1];

        String temp = "";
        if (location.charAt(0) == '"') {
            location = "";
            while (!temp.endsWith("\"")) {
                temp = arguments[++l];
                location += " " + temp;
            }
            location = location.substring(2,
                    location.length() - 1);
        }

        queryAction.setLocation(location);

        String argString = String.join(" ", arguments);
        String[] commands = argString.split("-"); // Note: removes leading '-'
        actions.add(queryAction);

        boolean allWasOK = true;

        commands:
        for (int c = 1; c < commands.length; c++) {
            String command = commands[c];
            command = command.trim();
            String[] args = command.split("\\s+");
            String type = "NoType";
            try {
                for (int i = 0; i < args.length; i++) {
                    type = args[i];
                    switch (type) {
                        case "c":
                        case "count":
                            String countArg = args[++i];
                            try {
                                int twCount = Integer.parseUnsignedInt(
                                        countArg);
                                actions.add(new SetCountAction(twCount));
                            } catch (NumberFormatException e) {
                                allWasOK = false;
                                out.println("Illegal count argument: "
                                        + countArg);
                            }
                            continue commands;
                        case "sort":
                            SortFilterAction fAction = new SortFilterAction();
                            int field = getField(args[++i]);
                            if (field > 0) {
                                fAction.setSortField(field);
                            } else {
                                allWasOK = false;
                                continue commands;
                            }

                            if (i + 1 < args.length) {
                                if (args[i + 1].equals("desc")) {
                                    fAction.setSortOrder(2);
                                    i++;
                                } else if (args[i + 1].equals("asc")) {
                                    i++;
                                }
                            }
                            actions.add(fAction);
                        case "search":
                            SortFilterAction sActiong = new SortFilterAction();

                            String phrase = args[i + 1];
                            String tempPhrase = "";
                            if (phrase.charAt(0) == '"') {
                                phrase = "";
                                while (!tempPhrase.endsWith("\"")) {
                                    tempPhrase = args[++i];
                                    phrase += " " + tempPhrase;
                                }
                                phrase = phrase.substring(2,
                                        phrase.length() - 1);
                            }
                            sActiong.setSearchKeyword(phrase);
                            actions.add(sActiong);
                        default:
                            allWasOK = false;
                            out.println("Illegal argument found: '"
                                    + type + "'");
                            continue commands;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                allWasOK = false;
                out.println("Missing arguments for action '" + type + "'");
            }
        }

        if (allWasOK) {
            actions.add(new PrintAction());
        } else {
            out.println("There were some problems with your parameters");
            actions.add(new HelpAction());
        }

        return actions;
    }

    /**
     * Executes all the actions given as a list.
     * Prioritizes SetCountAction type actions first.
     * Filters all HelpAction instances out, executes
     * one after all other actions have been executed.
     *
     * @param actions A list of actions
     */
    @Override
    public void executeActions(List<IAction> actions) {

        boolean needHelp = false;

        Iterator<IAction> iter = actions.iterator();
        while (iter.hasNext()) {
            IAction action = iter.next();
            if (action instanceof SetCountAction) {
                executeAction(action);
                iter.remove();
            }
            if (action instanceof HelpAction) {
                iter.remove();
                needHelp = true;
            }
        }

        for (IAction action : actions) {
            executeAction(action);
        }

        if (needHelp) {
            executeAction(new HelpAction());
        }
    }

    /**
     * Given an instance of IAction, it will be executed.
     *
     * @param action Action to execute
     */
    @Override
    public void executeAction(IAction action) {

        if (action instanceof QueryAction) {
            QueryAction queryAction = (QueryAction) action;
            executeQuery(queryAction);
        } else if (action instanceof PrintAction) {
            printTweets(getTweets());
        } else if (action instanceof SetCountAction) {
            setTweetsCount(((SetCountAction) action).getCount());
        } else if (action instanceof HelpAction) {
            printHelp();
        } else if (action instanceof SortFilterAction) {
            SortFilterAction sfAction = (SortFilterAction) action;
            sortFilterTweets(sfAction);
        } else if (action instanceof CommandInfoAction) {
            printCommandInfo((CommandInfoAction) action);
        }
    }

    /**
     * Returns a query object which holds all the values.
     * First, make a look-up in the Cache file, set with setCache().
     * If location is found in the cache and it has all the needed
     * geographic parameters set, use them to create a query object.
     * Otherwise, execute a location search using LocationSearch.
     *
     * @param location The location which is to be searched for
     * @return Query object which holds all the necessary information about
     * Twitter query
     * @see LocationSearch
     */
    @Override
    public ITwitterQuery getQueryFromLocation(String location) {
        ICache iCache = getCache();
        ITwitterQuery query;

        if (getLocationSearch() == null) {
            setLocationSearch(new LocationSearch());
        }

        if (iCache != null) {
            query = iCache.getQueryFromCache(location);
            if (query != null) { // get the official name
                location = query.getLocation();
            }

            if (query == null || !query.isGeoSet()) {
                ILocationSearch locSearch = getLocationSearch();
                query = locSearch.getQueryFromLocation(location);
                iCache.updateLocation(query);
            }
        } else {
            ILocationSearch locSearch = getLocationSearch();
            query = locSearch.getQueryFromLocation(location);
        }
        return query;
    }

    /**
     * Prints information about the asked command.
     *
     * @param action CommandInfoAction instance with the needed command.
     */
    private void printCommandInfo(CommandInfoAction action) {
        String command = action.getCommand();

        switch (command) {
            case "query":
                out.println(QUERY_INFORMATION);
                break;
            case "setcount":
                out.println(SETCOUNT_INFORMATION);
                break;
            case "print":
                out.println(PRINT_INFORMATION);
                break;
            case "sort":
                out.println(SORT_INFORMATION);
                break;
            case "search":
                out.println(SEARCH_INFORMATION);
                break;
            default:
                out.println("Can't recognize command '" + command + "'");
                out.println("Type 'help' for a list of available commands.");
                break;
        }
    }

    /**
     * Either sorts or filters available tweets,
     * depending on sfAction parameters.
     *
     * @param sfAction {@link SortFilterAction} instance
     */
    private void sortFilterTweets(SortFilterAction sfAction) {
        List<? extends ITweet> avTweets = getTweets();
        if (avTweets == null) {
            out.println("No tweets for sort/filter action.");
            return;
        }
        int field = sfAction.getSortField();
        int order = sfAction.getSortOrder();
        String keyword = sfAction.getSearchKeyword();

        if (field == 0 && keyword != null) {
            List<Tweet> tweetsFiltered = new ArrayList<>();

            for (ITweet tweet: avTweets) {
                if (tweet instanceof Tweet) {
                    if (tweet.toString().contains(keyword)) {
                        tweetsFiltered.add((Tweet) tweet);
                    }
                }
            }
            setTweets(tweetsFiltered);
        } else if (order > 0) {
            if (order == IFilterAction.ORDER_ASCENDING) {
                switch (field) {
                    case IFilterAction.FIELD_AUTHOR:
                        Collections.sort(avTweets, new AuthorComparator());
                        break;
                    case IFilterAction.FIELD_DATE:
                        Collections.sort(avTweets, new DateComparator());
                        break;
                    case IFilterAction.FIELD_TWEET:
                        Collections.sort(avTweets, new ContentComparator());
                        break;
                    default:
                        out.println("Unknown sort field parameter.");
                }
            } else if (order == IFilterAction.ORDER_DESCENDING) { // descending
                switch (field) {
                    case IFilterAction.FIELD_AUTHOR:
                        Collections.sort(avTweets, Collections.reverseOrder(
                                new AuthorComparator()));
                        break;
                    case IFilterAction.FIELD_DATE:
                        Collections.sort(avTweets, Collections.reverseOrder(
                                new DateComparator()));
                        break;
                    case IFilterAction.FIELD_TWEET:
                        Collections.sort(avTweets, Collections.reverseOrder(
                                new ContentComparator()));
                        break;
                    default:
                        out.println("Unknown sort field parameter.");
                }
            }
        }
    }

    /**
     * Simple quick guide printing function.
     */
    private void printHelp() {
        out.println("---------------------------------");
        out.println("Help for Location Tweets program");
        out.println("List of supported commands: ");
        out.println("---------------------------------");
        out.println("query");
        out.println("setcount");
        out.println("print");
        out.println("sort");
        out.println("search");
        out.println("---------------------------------");
        out.println("To get more info about any command, type '?command'");
        out.println("---------------------------------");
    }

    /**
     * Executes TwitterQuery.
     *
     * @param action QueryAction instance.
     */
    private void executeQuery(QueryAction action) {
        String location = action.getLocation();
        int count = action.getCount();

        ITwitterQuery query = getQueryFromLocation(location);
        if (query != null && query.isGeoSet()) {
            if (count > 0) {
                query.setCount(count);
            } else {
                query.setCount(getTweetsCount());
            }
            if (getTwitterSearch() == null) {
                setTwitterSearch(new TwitterSearch());
            }
            setTweets(getTwitterSearch().getTweets(query));
        }
    }

    /**
     * Prints given tweets.
     *
     * @param tweetsList A list of tweets
     */
    private void printTweets(List<? extends ITweet> tweetsList) {
        if (tweetsList == null) {
            return;
        }
        for (ITweet tweet: tweetsList) {
            out.println(tweet.toString());
        }
    }

    /**
     * Stores location search object which will be used to make queries to
     * location search API.
     *
     * @param newLocationSearch Implementation of ILocationSearch, which
     *                          can find information about location.
     */
    @Override
    public void setLocationSearch(ILocationSearch newLocationSearch) {
        locationSearch = newLocationSearch;
    }

    /**
     * Returns currently stored location search object.
     *
     * @return Implementation of ILocationSeach which will be used for location
     * search.
     */
    @Override
    public ILocationSearch getLocationSearch() {
        return locationSearch;
    }

    /**
     * Stores Twitter search object which will be used to query tweets from
     * Twitter API.
     *
     * @param newTwitterSearch Implementation of ITwitterSearch
     */
    @Override
    public void setTwitterSearch(ITwitterSearch newTwitterSearch) {
        twitterSearch = newTwitterSearch;
    }

    /**
     * Returns currently stored Twitter search object.
     *
     * @return Implementation of ITwitterSearch which will be used for queries.
     */
    @Override
    public ITwitterSearch getTwitterSearch() {
        return twitterSearch;
    }

    /**
     * Stores cache object which will be used to cache locations in the file.
     *
     * @param newCache Implementation of ICache
     */
    @Override
    public void setCache(ICache newCache) {
        cache = newCache;
    }

    /**
     * Returns currently stored cache object.
     *
     * @return Implementation of ICache which will be used for location caching.
     */
    @Override
    public ICache getCache() {
        return cache;
    }

    /**
     * Stores the latest state of tweets list. You should store your tweets
     * using this method after querying, sorting, searching.
     *
     * @param tweetsList A list of tweets
     */
    @Override
    public void setTweets(List<? extends ITweet> tweetsList) {
        tweets = tweetsList;
    }

    /**
     * Get the latest state of tweets list. This method should be used for
     * printing and when applying sorting or searching.
     *
     * @return A list of tweets
     */
    @Override
    public List<? extends ITweet> getTweets() {
        return tweets;
    }

    /**
     * Gets current default tweets count value used for queries.
     *
     * @return int representing current default tweets count value
     */
    public static int getTweetsCount() {
        return tweetsCount;
    }

    /**
     * Sets new default value for tweets queries.
     *
     * @param newTweetsCount new value of tweets count default value
     */
    public static void setTweetsCount(int newTweetsCount) {
        LocationTweets.tweetsCount = newTweetsCount;
    }

    /**
     * Information about query command.
     */
    public static final String QUERY_INFORMATION = "Command 'query <location>"
            + " is the main command for getting\ntweets from Twitter API from "
            + "specified in <location> parameter place,\n"
            + "which can be practically anything. If location contains "
            + "spaces,\nit must be enclosed in quote marks. You can specify "
            + "needed tweets count\nby adding a number after location parameter"
            + ".\nExamples:\nquery Tallinn\nquery London 10\n"
            + "query \"Tallinn University of Technology\" 25\n"
            + "\nTo make a query using console parameters you should use next "
            + "patterns:\njava LocationTweets Tallinn\njava LocationTweets "
            + "London -c 10\njava LocationTweets"
            + " \"Tallinn University Of Technology\" -count 25\nNote that you "
            + "can use both '-c' and '-count' for setting tweets number.\nToo "
            + "see results of the query use 'print' command.";

    /**
     * Information about setcount command.
     */
    public static final String SETCOUNT_INFORMATION = "Command 'setcound "
            + "<count>' is intended for changing\nthe default value of tweets "
            + "count given to queries.\nExamples: setcount 15\nTo set the "
            + "default count from the console,\nuse '-c <count>' or '-count "
            + "<count>' parameters.\nNote that setting default count command"
            + " are given the highest priority,\nthus program executes them "
            + "before making a query. So commands like\n'query Tallinn setcount"
            + " 15' or 'java LocationTweets \"Tallinn University of Technology"
            + "\" -c 25'\nwill translate into:\n1) set the default count to the"
            + " respective amount\n2) execute all other commands";

    /**
     * Information about print information.
     */
    public static final String PRINT_INFORMATION = "Command 'print' prints "
            + "currently acquired tweets.\nNote that if you type print before"
            + " sorting the data,\nthis command will print pre-processed "
            + "(unsorted) tweets.\nAutomatically executed if command-line "
            + "query is successful.";

    /**
     * Information about sort information.
     */
    public static final String SORT_INFORMATION = "Command 'sort <field> "
            + "<order>' sorts the available tweets.\nPossible sorting fields:"
            + " author, date, content\nOrder field is voluntary, by default "
            + "sorting is ascending\nExamples:\nsort content\nsort author "
            + "desc\nsort date asc\nConsole counterparts:\njava LocationTweets "
            + "Tallinn -sort author desc\njava LocationTweets \"Tallinn "
            + "University Of Technology\" -c 45 sort content";

    /**
     * Information about search command.
     */
    public static final String SEARCH_INFORMATION = "Command 'search <key>' "
            + "filters available tweets.\nAs result only tweets "
            + "which author, date or content\ncontain <key> remain for "
            + "processing. Examples:\nsearch developer\nsearch \"Tallinn "
            + "University Of Technology\"\njava LocationTweets Tallinn 15 "
            + "-search \"Old Town\"\njava LocationTweets Tartu -search linn";
}