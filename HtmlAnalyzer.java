import java.util.List;

/**
 * The HtmlAnalyzer class is the main entry point for analyzing HTML content.
 * It fetches HTML lines from a given URL, parses the HTML to extract the deepest text,
 * and prints the result to the console.
 */

public class HtmlAnalyzer {

    /**
     * The main method that serves as the entry point of the application.
     * It expects a single argument which is the URL to fetch HTML content from.
     *
     * @param args the command line arguments, where args[0] should be the URL
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HtmlAnalyzer <url>");
            return;
        }

        HtmlFetcher fetcher = new HtmlFetcher();
        List<String> htmlLines = fetcher.fetchHtmlLines(args[0]);
        if (htmlLines == null) {
            System.out.println("Url connection error");
            return;
        }

        HtmlParser parser = new HtmlParser();
        try {
            String deepestText = parser.parse(htmlLines);
            System.out.println(deepestText);
        } catch (MalformedHtmlException e) {
            System.out.println(e.getMessage());
        }

    }
}
