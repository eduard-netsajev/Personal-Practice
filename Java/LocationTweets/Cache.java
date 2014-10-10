import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Cache functionality which is able to
 * look information from the cache file
 * and add/update information in the file.
 */
class Cache implements ICache {

    /**
     * Cache file name.
     */
    String filename;

    /**
     * Finds query parameters from cache file. If location is not found in
     * cache, the method returns null. If location is found, but latitude,
     * longitude or radius is not found, then the given parameters will be set
     * to null.
     *
     * @param location Location to be searched for
     * @return Twitter query object where location information is filled if it
     * is present in the cache. If the location is not found, null is returned.
     */
    @Override
    public ITwitterQuery getQueryFromCache(String location) {
        if (filename == null) {
            return null;
        }
        File file = new File(filename);
        if (file.canRead()) {
            try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                String s;

                while ((s = in.readLine()) != null) {

                    String[] data = s.split(",");
                    for (String field: data) {
                        if (location.equalsIgnoreCase(field)) {
                            return getQueryFromData(data);
                        }
                    }
                }
                in.close();
            } catch (IOException e) {
                LocationTweets.out.print("Can't read cache from file: ");
                LocationTweets.out.println(filename);
            }
        }
        return null;
    }

    /**
     * Given array of strings with the data,
     * creates an instance of TwitterQuery.
     * @param data String array of data about the query
     * @return TwitterQuery instance with filled fields
     */
    private ITwitterQuery getQueryFromData(String[] data) {
        ITwitterQuery query = new TwitterQuery();
        query.setLocation(data[0]);
        try {
            query.setLatitude(Double.parseDouble(data[1]));
        } catch (Exception e) {
            query.setLatitude(0.0);
        }
        try {
            query.setLongitude(Double.parseDouble(data[2]));
        } catch (Exception e) {
            query.setLongitude(0.0);
        }
        try {
            query.setRadius(Double.parseDouble(data[3]));
        } catch (Exception e) {
            query.setRadius(0.0);
        }
        return query;
    }

    /**
     * Updates an existing row in the cache file.
     * If the location doesn't exist in the file,
     * add it as a new row at the end of the file.
     *
     * @param query ITwitterQuery instance which
     *              has all the necessary values.
     */
    @Override
    public void updateLocation(ITwitterQuery query) {
        if (filename == null) {
            return;
        }
        File file = new File(filename);

        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile(); // creates if it doesn't exist only
        } catch (Exception e) { // can't create cache file
            return;
        }

        if (file.canRead() && file.canWrite()) {
            try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                boolean foundInCache = false;
                String s;
                String d;
                List<String> content = new ArrayList<String>();
                String location = query.getLocation();

                while ((s = in.readLine()) != null) {

                    String[] data = s.split(",");

                    if (data[0].equalsIgnoreCase(location)) {
                        foundInCache = true;
                        data[1] = String.valueOf(query.getLatitude());
                        data[2] = String.valueOf(query.getLongitude());
                        data[3] = String.valueOf(query.getRadius());
                        d = String.join(",", data);
                        content.add(d);
                    } else {
                        content.add(s);
                    }
                }
                if (!foundInCache) {
                    String[] data = new String[4];
                    data[0] = query.getLocation();
                    data[1] = String.valueOf(query.getLatitude());
                    data[2] = String.valueOf(query.getLongitude());
                    data[3] = String.valueOf(query.getRadius());
                    d = String.join(",", data);
                    content.add(d);
                }

                in.close();

                PrintWriter f2 = new PrintWriter(new FileWriter(file, false));
                for (String string : content) {
                    f2.println(string);
                }
                f2.close();
            } catch (Exception e) {
                LocationTweets.out.print("Can't use cache in file ");
                LocationTweets.out.println(filename);
            }
        }
    }

    /**
     * Sets the filename where the cache is stored.
     *
     * @param newFilename Filename where the cache is stored
     */
    @Override
    public void setCacheFilename(String newFilename) {
        filename = newFilename;
    }

    /**
     * Returns the filename where the cache is stored.
     *
     * @return Filename where the cache is stored
     */
    @Override
    public String getCacheFilename() {
        return filename;
    }
}
