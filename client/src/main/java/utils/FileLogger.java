package utils;
import java.io.FileWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {
    private static FileLogger fileLogger;
    private static String filePath;
    private static int stackTraceSize;



    private FileLogger() {
        //TODO figure out where this is supposed to go
        //filePath = "logs/";
        filePath = "C:/p1_logs/";
        stackTraceSize = 10;
    }

    public static FileLogger getFileLogger() {
        if(fileLogger == null) {
            fileLogger = new FileLogger();
        }
        return fileLogger;
    }

    public static FileLogger getFileLogger(String path) {
        if(fileLogger == null) {
            fileLogger = new FileLogger();
        }
        filePath = path;
        return fileLogger;
    }


    public void log(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(fileLogger.getTimestamp())
                .append(" - ")
                .append(e.getMessage())
                .append("\n")
                .append(fileLogger.formatStackTrace(e))
                .append("\n");
        fileLogger.writeToLog(sb.toString());
    }

    public void log(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(fileLogger.getTimestamp())
                .append(" - ")
                .append(str)
                .append("\n");
        fileLogger.writeToLog(sb.toString());
    }

    private String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[yyyy-MM-dd HH:mm:ss]");
        return formatter.format(LocalDateTime.now());
    }

    private String getFileName() {
        return filePath + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".log";
    }

    private String formatStackTrace(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < stackTraceSize; i++) {
            if(i >= stackTrace.length) {
                break;
            }
            sb.append("\t");
            sb.append(stackTrace[i]);
            sb.append("\n");
        }
        return sb.toString();
    }

    private void writeToLog(String text){
        String fileName = getFileName();
        try(Writer fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(text);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO - figure out what to do if the excepton lgger throws an exception
        }

    }

}