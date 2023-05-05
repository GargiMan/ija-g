package ija.pacman.log;

import ija.pacman.App;
import ija.pacman.game.Direction;
import ija.pacman.game.object.MazeObject;
import ija.pacman.others.Constant;
import javafx.application.Platform;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.Pane;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

public class Logger {

    private static final double REPLAY_SPEED = 2.0;
    private final File file;
    private List<Move> currentMoves = null;
    private final List<List<Move>> moves = new ArrayList<>();
    private Timer timer;

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

    public void loadGame() {
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
                        case "G" -> App.getGame().getMaze().getGhosts().get(ghostIndex);
                        case "K" -> App.getGame().getMaze().getKeys().get(keyIndex);
                        default -> null;
                    };
                    if (mazeObject == null) {
                        throw new RuntimeException("Invalid log maze object");
                    } else if (mazeObject.isGhost()) {
                        ghostIndex++;
                    } else if (mazeObject.isKey()) {
                        keyIndex++;
                    }
                    Move previousMove;
                    if (moves.isEmpty()) {
                        previousMove = null;
                    } else if (mazeObject.isKey()) {
                        previousMove = moves.get(moves.size()-1).stream().filter(m -> Arrays.equals(m.getCoordinates(), new int[]{row, col})).findFirst().orElse(null);
                    } else {
                        previousMove = moves.get(moves.size()-1).get(ghostIndex);
                    }
                    Move currentMove = new Move(mazeObject, previousMove, row, col);
                    if (previousMove != null) {
                        previousMove.setNext(currentMove);
                    }
                    lineMoves.add(currentMove);
                }
                moves.add(lineMoves);
            }
            currentMoves = moves.get(0);
        } catch (Exception e) {
            System.getLogger(Logger.class.getName()).log(System.Logger.Level.ERROR, "Failed to load game log: "+file.getName()+"\n"+e.getMessage());
        }
    }

    public void first() {
        while (moves.indexOf(currentMoves) != 0) {
            previous();
        }
    }

    public void previous() {
        if (moves.indexOf(currentMoves) == 0) {
            return;
        }

        for (Move move : currentMoves) {
            if (move.getObject().isKey()) {
                continue;
            }
            int[] fromCoords = move.getCoordinates();
            int[] toCoords = move.previous().getCoordinates();
            move.getObject().undoMove(Direction.fromPositions(fromCoords[0], fromCoords[1], toCoords[0], toCoords[1]));
        }

        //return key if it was picked up
        List<Move> previousMoves = moves.get(moves.indexOf(currentMoves)-1);
        if (previousMoves.stream().anyMatch(m -> m.next() == null)) {
            App.getGame().getMaze().getPacman().returnKey().place();
        }

        currentMoves = previousMoves;
    }

    public void backward() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (moves.indexOf(currentMoves) == 0) {
                        pause();
                        ButtonBase button = (ButtonBase)((Pane)App.getStage().getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable()
                                .stream().filter(n -> n instanceof ButtonBase nbutton && nbutton.getText().equals(Constant.UI.BUTTON_PAUSE)).findFirst().orElseThrow();
                        button.setText(Constant.UI.BUTTON_BACKWARD);
                        return;
                    }
                    previous();
                });
            }
        }, 0, (long)(1/ REPLAY_SPEED * 1000));
    }

    public void pause() {
        timer.cancel();
    }

    public void forward() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (moves.indexOf(currentMoves) == moves.size()-1) {
                        pause();
                        ButtonBase button = (ButtonBase)((Pane)App.getStage().getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable()
                                .stream().filter(n -> n instanceof ButtonBase nbutton && nbutton.getText().equals(Constant.UI.BUTTON_PAUSE)).findFirst().orElseThrow();
                        button.setText(Constant.UI.BUTTON_FORWARD);
                        return;
                    }
                    next();
                });
            }
        }, 0, (long)(1/ REPLAY_SPEED * 1000));
    }

    public void next() {
        if (moves.indexOf(currentMoves) == moves.size()-1) {
            return;
        }
        for (Move move : currentMoves) {
            if (move.getObject().isKey()) {
                continue;
            }
            int[] fromCoords = move.getCoordinates();
            int[] toCoords = move.next().getCoordinates();
            move.getObject().move(Direction.fromPositions(fromCoords[0], fromCoords[1], toCoords[0], toCoords[1]));
        }
        currentMoves = moves.get(moves.indexOf(currentMoves)+1);
    }

    public void last() {
        while (moves.indexOf(currentMoves) != moves.size()-1) {
            next();
        }
    }
}
