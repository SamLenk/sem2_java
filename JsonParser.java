import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class JsonParser {
    public static void main(String[] args) {
        String jsonString = "[{\"фамилия\":\"Иванов\",\"оценка\":\"5\",\"предмет\":\"Математика\"},{\"фамилия\":\"Петрова\",\"оценка\":\"4\",\"предмет\":\"Информатика\"},{\"фамилия\":\"Краснов\",\"оценка\":\"5\",\"предмет\":\"Физика\"}]";

        try {
            String result = parseJson(jsonString);
            System.out.println(result);
            writeToFile(result, "output.txt");
        } catch (Exception e) {
            writeToLog(e);
        }
    }

    public static String parseJson(String jsonString) {
        StringBuilder resultBuilder = new StringBuilder();
        jsonString = jsonString.replace("[", "").replace("]", "");
        String[] entries = jsonString.split("\\},\\{");
        for (String entry : entries) {
            String[] fields = entry.replace("{", "").replace("}", "").split(",");
            String surname = null;
            String grade = null;
            String subject = null;
            for (String field : fields) {
                String[] keyValue = field.split(":");
                String key = keyValue[0].replace("\"", "").trim();
                String value = keyValue[1].replace("\"", "").trim();
                if (key.equals("фамилия")) {
                    surname = value;
                } else if (key.equals("оценка")) {
                    grade = value;
                } else if (key.equals("предмет")) {
                    subject = value;
                }
            }
            if (surname != null && grade != null && subject != null) {
                resultBuilder.append("Студент ")
                        .append(surname)
                        .append(" получил ")
                        .append(grade)
                        .append(" по предмету ")
                        .append(subject)
                        .append(".\n");
            }
        }
        return resultBuilder.toString();
    }

    public static void writeToFile(String content, String fileName) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(content);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static void writeToLog(Exception e) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("error.log", true));
            writer.write(LocalDateTime.now() + ": " + e.toString());
            writer.newLine();
            writer.close();
        } catch (IOException ex) {
            System.out.println("Failed to write to log file: " + ex.getMessage());
        }
    }
}
