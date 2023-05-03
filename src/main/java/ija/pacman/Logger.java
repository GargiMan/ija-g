package ija.pacman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Logger {

    private File file;

    public Logger(String filename) {
        String filePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + "logs" + File.separator;
        file = new File(filePath+filename);

        try {
            if (file.createNewFile()) {
                System.getLogger(Logger.class.getName()).log(System.Logger.Level.INFO, "Created new log file: "+file.getName());
            } else {
                System.getLogger(Logger.class.getName()).log(System.Logger.Level.INFO, "Replaying log file: "+file.getName());
            }
        } catch (Exception e) {
            System.getLogger(Logger.class.getName()).log(System.Logger.Level.ERROR, "Failed to create log file: "+filename);
        }
    }

    public void log(String str) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            pw.print(str);
        } catch (Exception e) {
            System.getLogger(Logger.class.getName()).log(System.Logger.Level.ERROR, "Failed to write to log file: "+file.getName());
        }

    }
}
