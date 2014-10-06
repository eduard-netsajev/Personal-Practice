import org.json.JSONObject;
import org.json.JSONArray;

import java.net.URL;

public class LocationTweets {

    private static final String TWITTER_CUSTOMER_KEY =
            "zpNDfFQlI8sfAvA0e5dIwypU9";
    private static final String TWITTER_CUSTOMER_SECRET =
            "SvSHa19OT1BfHD95DaQI2eJs8zGMMXh1c3XWkgXqEzLLUzszVC";

    public static void main(String[] args){






        String s;
        StringBuilder response = new StringBuilder();
        try {

            URL url = new URL("http://nominatim.openstreetmap.org/search/Unter%20den%20Linden%201%20Berlin?format=json&addressdetails=1&limit=1");
            Reader.init(url.openStream());
            while (true) {
                s = Reader.nextLine();
                if (s == null) {
                    break;
                } else {
                    response.append(s);
                }
            }

            JSONObject res = new JSONArray(response.toString()).getJSONObject(0);
            System.out.println(res.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

