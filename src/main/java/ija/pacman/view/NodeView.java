/**
 * @file NodeView.java
 * @brief Interface for painting node in UI
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.view;

import javafx.scene.canvas.GraphicsContext;

public interface NodeView {
    void paintNode(GraphicsContext graphics);
}