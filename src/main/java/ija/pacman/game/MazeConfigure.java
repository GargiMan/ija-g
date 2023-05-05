package ija.pacman.game;

import ija.pacman.game.field.Field;
import ija.pacman.game.field.PathField;
import ija.pacman.game.field.WallField;
import ija.pacman.game.object.GhostObject;
import ija.pacman.game.object.KeyObject;
import ija.pacman.game.object.PacmanObject;
import ija.pacman.game.object.TargetObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class MazeConfigure {

    public static final int BORDER_SIZE = 1;
    private int current_row = BORDER_SIZE;
    private Maze maze;
    private final Random random = new Random();

    public MazeConfigure load(File map) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(map));

        //load map size header
        String line = br.readLine();
        String[] lineParts = line.split(" ");
        if (lineParts.length != 2) {
            throw new IllegalArgumentException("Invalid map header: " + Arrays.toString(lineParts));
        }
        maze = new Maze(Integer.parseInt(lineParts[0]), Integer.parseInt(lineParts[1]));

        //load map
        while ((line = br.readLine()) != null) {
            if (line.equals("~GAME~")) {
                break;
            }
            processLine(line);
        }

        if (current_row != maze.numRows() - BORDER_SIZE) {
            throw new IllegalArgumentException("Map has less rows than defined in header");
        } else if (maze.getPacman() == null) {
            throw new IllegalArgumentException("Map has not set pacman spawn");
        } else if (maze.getTarget() == null) {
            throw new IllegalArgumentException("Map has not set target field");
        }

        return this;
    }

    private void processLine(String line) {

        if (line.length() != maze.numCols() - 2 * BORDER_SIZE) {
            throw new IllegalArgumentException("Map row is longer/shorter than defined in header: " + line);
        } else if (current_row == maze.numRows() - BORDER_SIZE) {
            throw new IllegalArgumentException("Map has more rows than defined in header");
        }

        char[] char_line = line.toCharArray();

        for (int current_col = BORDER_SIZE; current_col < maze.numCols()-BORDER_SIZE; current_col++) {

            Field current_field;

            switch (char_line[current_col-BORDER_SIZE]) {
                case 'S' -> {
                    if (maze.getPacman() != null) {
                        throw new RuntimeException("Not supported more than 1 pacman");
                    }
                    current_field = new PathField(current_row, current_col);
                    PacmanObject pacman = new PacmanObject(current_field);
                    current_field.addObject(pacman);
                    maze.setPacman(pacman);
                }
                case 'G' -> {
                    current_field = new PathField(current_row, current_col);
                    GhostObject ghost = new GhostObject(current_field);
                    current_field.addObject(ghost);
                    maze.addGhost(ghost);
                }
                case 'K' -> {
                    current_field = new PathField(current_row, current_col);
                    KeyObject key = new KeyObject(current_field);
                    current_field.addObject(key);
                    maze.addKey(key);
                }
                case 'T' -> {
                    if (maze.getTarget() != null) {
                        throw new IllegalArgumentException("Not supported more than 1 target fields");
                    }
                    current_field = new PathField(current_row, current_col);
                    TargetObject target = new TargetObject(current_field, maze.getKeys());
                    current_field.addObject(target);
                    maze.setTarget(target);
                }
                case '.' -> {
                    current_field = new PathField(current_row, current_col);
                }
                case 'X' -> {
                    current_field = new WallField(current_row, current_col);
                }
                default -> {
                    throw new IllegalArgumentException("Invalid map character: " + char_line[current_col-BORDER_SIZE] + " in line " + line);
                }
            }

            maze.setField(current_row, current_col, current_field);
        }

        current_row++;
    }

    public Maze createMaze() {

        if (maze == null) {
            throw new IllegalArgumentException("Map was not loaded, call load method before");
        }

        //set borders
        for (int i = 0; i < BORDER_SIZE; i++) {

            int col_L = i;
            int col_R = maze.numCols() - BORDER_SIZE - i;
            int row_U = i;
            int row_D = maze.numRows() - BORDER_SIZE - i;

            for (int row = 0; row <= row_D; row++) {
                maze.setField(row, col_L, new WallField(row, col_L));
                maze.setField(row, col_R, new WallField(row, col_R));
            }

            for (int col = BORDER_SIZE; col < col_R; col++) {
                maze.setField(row_U, col, new WallField(row_U, col));
                maze.setField(row_D, col, new WallField(row_D, col));
            }
        }

        return maze;
    }
}
