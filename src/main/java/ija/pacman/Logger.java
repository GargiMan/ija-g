package ija.pacman;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Logger {

    private File file;

    private int currentMove = 0;
    private final List<String> moves = new ArrayList<>();

    public Logger(String filename) {
        String filePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + "logs" + File.separator;
        file = new File(filePath+filename);

        try {
            if (file.createNewFile()) {
                System.getLogger(Logger.class.getName()).log(System.Logger.Level.INFO, "Created new log file: "+file.getName());
            }
        } catch (Exception e) {
            System.getLogger(Logger.class.getName()).log(System.Logger.Level.ERROR, "Failed to create log file: "+filename+"\n"+e.getMessage());
        }
    }

    public Logger(File log) {
        this.file = log;
        System.getLogger(Logger.class.getName()).log(System.Logger.Level.INFO, "Replaying log file: "+file.getName());
    }

    public void log(String str) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            pw.print(str);
        } catch (Exception e) {
            System.getLogger(Logger.class.getName()).log(System.Logger.Level.ERROR, "Failed to write to log file: "+file.getName()+"\n"+e.getMessage());
        }
    }

    public void loadMoves() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            //skip maze
            while ((line = br.readLine()) != null) {
                if (line.equals("~GAME~")) {
                    break;
                }
            }
            //load moves
            while ((line = br.readLine()) != null) {
                moves.add(line);
            }
        } catch (Exception e) {
            System.getLogger(Game.class.getName()).log(System.Logger.Level.ERROR, "Failed to load game log: "+file.getName()+"\n"+e.getMessage());
        }
    }
}
