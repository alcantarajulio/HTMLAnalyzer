import java.util.List;

public class HtmlAnalyzer {
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
            System.out.println("malformed HTML");
        }

    }
}
