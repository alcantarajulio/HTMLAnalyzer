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
            if (line.isEmpty()) continue; // Ignore blank lines

            if (isTag(line)) {
                if (isClosingTag(line)) {
                    String tagName = extractTagName(line, true);
                    if (tagDeque.isEmpty() || !tagDeque.peek().equals(tagName)) {
                        // Closing tag does not match the last opening tag
                        throw new MalformedHtmlException();
                    }
                    tagDeque.pop();
                } else {
                    // Opening tag
                    String tagName = extractTagName(line, false);
                    if (tagName.contains(" ")) { // Do not allow attributes
                        throw new MalformedHtmlException();
                    }
                    tagDeque.push(tagName);
                }
            } else {
                // Update if the depth is greater than the current
                int currentDepth = tagDeque.size();
                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                    deepestText = line;
                }
            }
        }

        if (!tagDeque.isEmpty()) {
            // If there are unclosed tags, the HTML is malformed
            throw new MalformedHtmlException();
        }

        return deepestText;
    }

    // verifica se a linha é uma tag (abertura ou fechamento)
    private boolean isTag(String line) {
        return line.startsWith("<") && line.endsWith(">");
    }

    // verifica se a linha é uma tag de fechamento
    private boolean isClosingTag(String line) {
        return line.startsWith("</");
    }

    // extrai o nome da tag removendo os delimitadores
    private String extractTagName(String line, boolean closing) {
        if (closing) {
            // </div>
            return line.substring(2, line.length() - 1).trim();
        } else {
            // <div>
            return line.substring(1, line.length() - 1).trim();
        }
    }
}