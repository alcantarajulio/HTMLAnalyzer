import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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

//    public String parse(List<String> lines) throws MalformedHtmlException {
//        Stack<String> tagStack = new Stack<>();
//        int maxDepth = -1;
//        String deepestText = "";
//
//        for (String rawLine : lines) {
//            String line = rawLine.trim();
//            if (line.isEmpty()) {
//                continue; // Ignora linhas em branco
//            }
//
//            if (isTag(line)) {
//                if (isClosingTag(line)) {
//                    String tagName = extractTagName(line, true);
//                    if (tagStack.isEmpty() || !tagStack.peek().equals(tagName)) {
//                        // Tag de fechamento não corresponde à última abertura
//                        throw new MalformedHtmlException();
//                    }
//                    tagStack.pop();
//                } else {
//                    // Tag de abertura
//                    String tagName = extractTagName(line, false);
//                    if (tagName.contains(" ")) { // Não permite atributos
//                        throw new MalformedHtmlException();
//                    }
//                    tagStack.push(tagName);
//                }
//            } else {
//                // atualiza se a profundidade for maior que a atual
//                int currentDepth = tagStack.size();
//                if (currentDepth > maxDepth) {
//                    maxDepth = currentDepth;
//                    deepestText = line;
//                }
//            }
//        }
//
//        if (!tagStack.isEmpty()) {
//            // se restarem tags não fechadas, o HTML está mal-formado
//            throw new MalformedHtmlException();
//        }
//
//        return deepestText;
//    }

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