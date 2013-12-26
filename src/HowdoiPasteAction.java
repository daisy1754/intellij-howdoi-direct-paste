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

        List<String> snippet = new HowdoiHelper().getCodeSnippet(content);
        if (snippet == null) {
            // TODO: error handling
        } else {
            document.deleteString(start, start + position.column);
            document.insertString(start, join(snippet, '\n'));
            editor.getCaretModel().moveToLogicalPosition(
                    new LogicalPosition(position.line + snippet.size() - 1, 0));
        }
    }

    private String join(List<String> lines, char separator) {
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
