import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.util.TextRange;

import java.util.List;

public class HowdoiPasteAction extends AnAction {
    public void actionPerformed(AnActionEvent actionEvent) {
        Editor editor = DataKeys.EDITOR.getData(actionEvent.getDataContext());
        Document document = editor.getDocument();
        LogicalPosition position = editor.getCaretModel().getLogicalPosition();
        int start = document.getLineStartOffset(position.line);
        String content = document.getText(new TextRange(start, start + position.column));

        String headSpaces = getHeadSpaces(content);
        List<String> snippet = new HowdoiHelper().getCodeSnippet(content.trim());
        if (snippet == null) {
            // TODO: error handling
        } else {
            document.deleteString(start, start + position.column);
            document.insertString(start, headSpaces + join(snippet, "\n" + headSpaces));
            editor.getCaretModel().moveToLogicalPosition(
                    new LogicalPosition(position.line + snippet.size() - 1, 0));
        }
    }

    private String getHeadSpaces(String content) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) != ' ') {
                break;
            }
            builder.append(' ');
        }
        return builder.toString();
    }

    private String join(List<String> lines, String separator) {
        StringBuilder builder = new StringBuilder();
        boolean firstLine = true;
        for (String line: lines) {
            if (firstLine) {
                firstLine = false;
            } else {
                builder.append(separator);
            }
            builder.append(line);
        }
        return builder.toString();
    }
}
