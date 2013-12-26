package jp.gr.java_conf.daisy.intellij_howdoi_plugin;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HowdoiHelper {
    /**
     * @return code snippet from howdoi.py script or null if something wrong happens.
     */
    public List<String> getCodeSnippet(String query) {
        try {
            Runtime runtime = Runtime.getRuntime();
            File tmpFile = createTmpFileFromResource("/howdoi.py");
            Process process = runtime.exec("python " + tmpFile.getAbsolutePath() + " " + query);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            List<String> lines = new ArrayList<String>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
            tmpFile.delete();
            return lines;
        } catch (IOException e) {
            Notifications.Bus.notify(
                    new Notification("HowdoiHelper", "HowdoiAction",
                            "Unexpected error while getting snippet", NotificationType.ERROR)
            );
            return null;
        }
    }

    private File createTmpFileFromResource(String resourcePath) throws IOException {
        File tmpFile = new File("howdoi_helper_tmp.py");
        InputStream stream = getClass().getResourceAsStream(resourcePath);
        FileOutputStream out = new FileOutputStream(tmpFile);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = stream.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        stream.close();
        out.close();
        return tmpFile;
    }
}
