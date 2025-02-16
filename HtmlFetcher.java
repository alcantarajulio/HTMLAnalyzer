import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HtmlFetcher {
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
            return null;
        }
        return lines;
    }
}
