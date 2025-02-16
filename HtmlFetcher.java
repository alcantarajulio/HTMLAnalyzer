import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The HtmlFetcher class is responsible for fetching HTML content from a given URL.
 * It reads the HTML content line by line and returns it as a list of strings.
 */
public class HtmlFetcher {
    /**
     * Fetches HTML content from the specified URL.
     *
     * @param urlString the URL to fetch HTML content from
     * @return a list of strings, each representing a line of HTML content
     */
    public List<String> fetchHtmlLines(String urlString) {
        List<String> lines = new ArrayList<>();
        try {
            URL url = new URL(urlString);
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream(), "UTF-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }

        } catch (Exception e) {
            System.out.println("Url connection error while fetching html lines");
        }
        return lines;
    }
}
