import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cache functionality. The implementation
 * of ICache should be able to look information
 * from the cache file and add/update information
 * in the file.
 * @author Ago
 *
 */
class Cache implements ICache {

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
            try (BufferedReader in = new BufferedReader(new FileReader(file))){
                String s;

                while((s = in.readLine()) != null){

                    String[] data = s.split(",");
                    for(String field: data){
                        if (location.equalsIgnoreCase(field)) {

                            ITwitterQuery query = new TwitterQuery();
                            query.setLocation(data[0]);
                            try {
                                query.setLatitude(Double.parseDouble(data[1]));
                            } catch (Exception e) {
                                // do nothing
                            }
                            try {
                                query.setLongitude(Double.parseDouble(data[2]));
                            } catch (Exception e) {
                                // do nothing
                            }
                            try {
                                query.setRadius(Double.parseDouble(data[3]));
                            } catch (Exception e) {
                                // do nothing
                            }
                            return query;
                        }
                    }
                }
                in.close();
            } catch (IOException e) {
                LocationTweets.out.println("Can't read cache from file " + filename);
            }
        }
        return null;
    }

    /**
     * Updates an existing row in the cache file. Note: in the current set up,
     * this method should add a new line if the location does not exist (e.g.
     * you don't have anything to update). If you find it more convenient to
     * have add and update functionalities separately, implement addLocation
     * method and call them accordingly to the situtaion.
     *
     * @param query Query parameters instances which has all the necessary
     *              values.
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
            try (BufferedReader in = new BufferedReader(new FileReader(file))){
                boolean foundInCache = false;
                String s;
                String d;
                List<String> content = new ArrayList<String>();
                String location = query.getLocation();

                while((s = in.readLine()) != null){

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
                for(String string : content) {
                    f2.println(string);
                }
                f2.close();
            } catch (Exception e) {
                LocationTweets.out.println("Can't use cache in file "+filename);
            }
        }
    }

    /**
     * Sets the filename where the cache is stored.
     *
     * @param filename Filename where the cache is stored
     */
    @Override
    public void setCacheFilename(String filename) {
        this.filename = filename;
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
