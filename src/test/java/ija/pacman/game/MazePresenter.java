package ija.pacman.game;

import ija.pacman.view.FieldView;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazePresenter {
    private final Maze maze;

    public MazePresenter(Maze maze) {
        this.maze = maze;
    }

    public void open() {
        try {
            SwingUtilities.invokeAndWait(this::initializeInterface);
        } catch (InvocationTargetException | InterruptedException var2) {
            Logger.getLogger(MazePresenter.class.getName()).log(Level.SEVERE, (String)null, var2);
        }

    }

    private void initializeInterface() {
        JFrame frame = new JFrame("Pacman Demo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(350, 400);
        frame.setPreferredSize(new Dimension(350, 400));
        frame.setResizable(false);
        int rows = this.maze.numRows();
        int cols = this.maze.numCols();
        GridLayout layout = new GridLayout(rows, cols);
        JPanel content = new JPanel(layout);

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                FieldView field = new FieldView(this.maze.getField(i, j));
                content.add(field);
            }
        }

        frame.getContentPane().add(content, "Center");
        frame.pack();
        frame.setVisible(true);
    }
}
