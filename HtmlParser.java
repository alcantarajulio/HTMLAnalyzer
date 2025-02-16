import java.util.List;
import java.util.Stack;

public class HtmlParser {

    public String parse(List<String> lines) throws MalformedHtmlException {
        Stack<String> tagStack = new Stack<>();
        int maxDepth = -1;
        String deepestText = "";

        for (String rawLine : lines) {
            String line = rawLine.trim();
            if (line.isEmpty()) {
                continue; // Ignora linhas em branco
            }

            if (isTag(line)) {
                if (isClosingTag(line)) {
                    String tagName = extractTagName(line, true);
                    if (tagStack.isEmpty() || !tagStack.peek().equals(tagName)) {
                        // Tag de fechamento não corresponde à última abertura
                        throw new MalformedHtmlException("Tag de fechamento não corresponde");
                    }
                    tagStack.pop();
                } else {
                    // Tag de abertura
                    String tagName = extractTagName(line, false);
                    if (tagName.contains(" ")) { // Não permite atributos
                        throw new MalformedHtmlException("Tag de abertura com atributos");
                    }
                    tagStack.push(tagName);
                }
            } else {
                // atualiza se a profundidade for maior que a atual
                int currentDepth = tagStack.size();
                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                    deepestText = line;
                }
            }
        }

        if (!tagStack.isEmpty()) {
            // se restarem tags não fechadas, o HTML está mal-formado
            throw new MalformedHtmlException("Tags não fechadas");
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
