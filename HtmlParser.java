import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * The HtmlParser class is responsible for parsing HTML content and extracting the deepest text.
 */
public class HtmlParser {

    /**
     * Parses the given list of HTML lines and extracts the deepest text.
     *
     * @param lines the list of HTML lines to parse
     * @return the deepest text found in the HTML content
     * @throws MalformedHtmlException if the HTML content is malformed
     */

    public String getDeepestText(List<String> lines) throws MalformedHtmlException {
        Deque<String> tagDeque = new LinkedList<>();
        int maxDepth = -1;
        String deepestText = "";

        for (String rawLine : lines) {
            String line = rawLine.trim();

            if (line.isEmpty()) continue;

            if (isTag(line)){
                processTagLine(line, tagDeque);
            } else {
                deepestText = updateDeepestText(line, tagDeque, deepestText, maxDepth);
                maxDepth = Math.max(maxDepth, tagDeque.size());
            }
        }
        if(!tagDeque.isEmpty()) throw new MalformedHtmlException();
        return deepestText;
    }

        if (!tagDeque.isEmpty()) {
            // If there are unclosed tags, the HTML is malformed
            throw new MalformedHtmlException();
        }

    private void processTagLine(String line, Deque<String> tagDeque) throws MalformedHtmlException {
        if (isClosingTag(line)) processClosingTag(line, tagDeque);
        else processOpeningTag(line, tagDeque);
    }

    private void processClosingTag(String line, Deque<String> tagDeque) throws MalformedHtmlException {
        String tagName = extractTagName(line, true);
        if (tagDeque.isEmpty() || !tagDeque.peek().equals(tagName)) throw new MalformedHtmlException();
        tagDeque.pop();
    }

    private void processOpeningTag(String line, Deque<String> tagDeque) throws MalformedHtmlException {
        String tagName = extractTagName(line, false);
        if (tagName.contains(" ")) throw new MalformedHtmlException();
        tagDeque.push(tagName);
    }

    private String updateDeepestText(String text, Deque<String> tagDeque, String currentDeepestText, int currentMaxDepth) {
        int currentDepth = tagDeque.size();
        if (currentDepth > currentMaxDepth) return text;
        return currentDeepestText;
    }

    private boolean isTag(String line) {
        return line.startsWith("<") && line.endsWith(">");
    }

    private boolean isClosingTag(String line) {
        return line.startsWith("</");
    }

    private String extractTagName(String line, boolean closing) {
        return closing
                ?  line.substring(2, line.length() - 1).trim()
                :  line.substring(1, line.length() - 1).trim();
    }
}