package Infrastructure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

public class AllureCsvReportGenerator {
    public static void GenerateCsvReport() {
        String jsonString;
        JSONObject jsonObject;

        try {
            var path = getLatestJsonFilePath("D:\\JetBrains\\IdeaProjects\\BookingComTest\\target\\allure-results");
            jsonString = new String(Files.readAllBytes(Path.of(path)));
            jsonObject = new JSONObject(jsonString);

            String uuid = jsonObject.getString("uuid");
            String name = jsonObject.getString("name");
            JSONArray children = jsonObject.getJSONArray("children");
            JSONArray befores = jsonObject.getJSONArray("befores");
            JSONArray afters = jsonObject.getJSONArray("afters");
            long start = jsonObject.getLong("start");
            long stop = jsonObject.getLong("stop");

            File csvOutputFile = new File("latest_report.csv");
            FileWriter fileWriter = new FileWriter(csvOutputFile);
            fileWriter.append("uuid,name,children,befores,afters,start,stop\n");
            fileWriter.append(uuid).append(",").append(name).append(",").append(children.toString()).append(",").append(befores.toString()).append(",").append(afters.toString()).append(",").append(String.valueOf(start)).append(",").append(String.valueOf(stop)).append("\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLatestJsonFilePath(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json") && isJsonFileMatching(dir.getPath() + File.separator + name));
        if (files.length == 0) {
            return null;
        }
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0].getAbsolutePath();
    }

    public static boolean isJsonFileMatching(String filePath) {
        try {
            JSONObject json = new JSONObject(new String(Files.readAllBytes(Paths.get(filePath))));
            if (json.has("uuid") && json.has("name") && json.has("befores") && json.has("afters")) {
                JSONArray befores = json.getJSONArray("befores");
                JSONArray afters = json.getJSONArray("afters");
                if (befores.length() > 0 && afters.length() > 0) {
                    return true;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


}