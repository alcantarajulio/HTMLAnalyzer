import java.util.List;

public class HtmlAnalyzer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HtmlAnalyzer <url>");
        }
        HtmlFetcher fetcher = new HtmlFetcher();
        List<String> htmlLines = fetcher.fetchHtmlLines(args[0]);
        if (htmlLines == null) {
            System.out.println("Url connection error");
        } else {
            System.out.println("Url connection success");
        }

    }
}
