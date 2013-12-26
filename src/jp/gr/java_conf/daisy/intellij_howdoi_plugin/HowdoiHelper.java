package jp.gr.java_conf.daisy.intellij_howdoi_plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HowdoiHelper {
    /**
     * @return code snippet from howdoi.py script or null if something wrong happens.
     */
    public List<String> getCodeSnippet(String query) {
        Runtime runtime = Runtime.getRuntime();
        String path = this.getClass().getResource("howdoi.py").getPath();
        try {
            Process process = runtime.exec("python " + path + " " + query);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            List<String> lines = new ArrayList<String>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
            return lines;
        } catch (IOException e) {
            return null;
        }
    }
}
