import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.util.TextRange;

public class HowdoiPasteAction extends AnAction {
    public void actionPerformed(AnActionEvent actionEvent) {
        Editor editor = DataKeys.EDITOR.getData(actionEvent.getDataContext());
        Document document = editor.getDocument();
        LogicalPosition position = editor.getCaretModel().getLogicalPosition();
        int start = document.getLineStartOffset(position.line);
        String content = document.getText(new TextRange(start, start + position.column));
        System.out.println(content);
    }
}
