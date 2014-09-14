import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchWeb {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the seraching phrase: ");
        String phrase = input.nextLine();
        System.out.print("Enter a starting URL: ");
        String url = input.nextLine();

        // Search the web for the phrase from the starting url
        crawler(phrase, url);
    }

    public static void crawler(String target, String startingURL){
        final int URLS_LIMIT = 10000;

        ArrayList<String> listOfPendingURLs = new ArrayList<>();
        ArrayList<String> listOfTraversedURLs = new ArrayList<>();

        listOfPendingURLs.add(startingURL);
        while (!listOfPendingURLs.isEmpty()
                && listOfTraversedURLs.size() <= URLS_LIMIT) {
            String urlString = listOfPendingURLs.remove(0);
            if (!listOfTraversedURLs.contains(urlString)) {
                listOfTraversedURLs.add(urlString);
            }
            System.out.println("Searching in " + urlString);

            for (String s: searchSubURLs(target, urlString)) {
                if (!listOfTraversedURLs.contains(s)) {
                    listOfPendingURLs.add(s);
                }
            }
        }
    }

    public static ArrayList<String> searchSubURLs(String target,
                                                  String urlString) {
        ArrayList<String> list = new ArrayList<>();

        try {
            URL url = new URL(urlString);
            Scanner input = new Scanner(url.openStream());
            int current = 0;
            while(input.hasNext()) {
                String line = input.nextLine();

                if (line.contains(target)) {
                    System.out.println("Target phrase found at " + urlString);
                    System.exit(7);
                }


                current = line.indexOf("http:", current);
                while (current > 0) {
                    int endIndex = line.indexOf("\"", current);
                    int endIndexTwo = line.indexOf("\'", current);
                    if (endIndexTwo > 0 && endIndexTwo < endIndex) {
                        endIndex = endIndexTwo;
                    }
                    if (endIndex > 0) { // Make sure the url is correct
                        list.add(line.substring(current, endIndex));
                        current = line.indexOf("http:", endIndex);
                    } else {
                        current = -1;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    return  list;
    }
}
