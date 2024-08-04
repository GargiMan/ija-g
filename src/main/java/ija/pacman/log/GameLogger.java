/**
 * @file GameLogger.java
 * @brief Class for logging game moves and replaying them later
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.log;

import ija.pacman.App;
import ija.pacman.game.object.MazeObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class GameLogger {

    private final File file;

    private final List<List<Move>> moves = new ArrayList<>();
    private GameMovesIterator<List<Move>> iterator = null;

    public GameLogger(String filename) {
        String filePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + "logs" + File.separator;
        file = new File(filePath+filename);

        try {
            if (file.createNewFile()) {
                System.getLogger(GameLogger.class.getName()).log(System.Logger.Level.INFO, "Created new log file: "+file.getName());
            }
        } catch (Exception e) {
            System.getLogger(GameLogger.class.getName()).log(System.Logger.Level.ERROR, "Failed to create log file: "+filename+"\n"+e.getMessage());
        }
    }

    public GameLogger(File log) {
        this.file = log;
        System.getLogger(GameLogger.class.getName()).log(System.Logger.Level.INFO, "Replaying log file: "+file.getName());
    }

    public void log(String str) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            pw.print(str);
        } catch (Exception e) {
            System.getLogger(GameLogger.class.getName()).log(System.Logger.Level.ERROR, "Failed to write to log file: "+file.getName()+"\n"+e.getMessage());
        }
    }

    public boolean loadGame() {
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
                List<Move> lineMoves = new ArrayList<>();
                String[] lineMoveS = line.split("\t");

                int ghostIndex = 0;
                int keyIndex = 0;

                for (String moveS : lineMoveS) {
                    int row = Integer.parseInt(moveS.split("-")[1].split(":")[0]);
                    int col = Integer.parseInt(moveS.split("-")[1].split(":")[1]);

                    MazeObject mazeObject = switch (moveS.split("-")[0]) {
                        case "S" -> App.getGame().getMaze().getPacman();
                        case "G" -> App.getGame().getMaze().getGhosts().get(ghostIndex++);
                        case "K" -> App.getGame().getMaze().getKeys().get(keyIndex++);
                        default -> throw new RuntimeException("Invalid log maze object");
                    };

                    Move previousMove;
                    if (moves.isEmpty()) {
                        previousMove = null;
                    } else {
                        previousMove = moves.get(moves.size()-1).stream().filter(m -> m.getObject() == mazeObject).findFirst().orElse(null);
                    }

                    Move currentMove = new Move(mazeObject, previousMove, row, col);
                    if (previousMove != null) {
                        previousMove.setNext(currentMove);
                    }
                    lineMoves.add(currentMove);
                }
                moves.add(lineMoves);
            }
            iterator = new GameMovesIterator<>(moves);
        } catch (Exception e) {
            System.getLogger(GameLogger.class.getName()).log(System.Logger.Level.ERROR, "Failed to load game log: "+file.getName()+"\n"+e.getMessage());
            return false;
        }
        return true;
    }

    public GameMovesIterator<List<Move>> getMovesIterator() {
        return iterator;
    }

    public static class GameMovesIterator<E> {
        private final List<E> list;
        private int currentIndex = 0;

        public GameMovesIterator(List<E> list) {
            this.list = list;
        }

        public boolean hasNext() {
            return currentIndex < list.size() - 1;
        }

        public boolean hasPrevious() {
            return currentIndex > 0;
        }

        public E current() {
            return list.get(currentIndex);
        }

        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            currentIndex--;
            return list.get(currentIndex);
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            currentIndex++;
            return list.get(currentIndex);
        }
    }
}
