/**
 * @file MazeTester.java
 * @brief Reused and adjusted test class for Maze from previous projects
 */
package ija.pacman.game;

import ija.pacman.game.field.Field;
import ija.pacman.game.object.MazeObject;
import ija.pacman.view.FieldView;

import java.util.ArrayList;
import java.util.List;

public class MazeTester {
    private final List<FieldView> fields;

    public MazeTester(Maze maze) {
        this.fields = new ArrayList<>();
        int rows = maze.numRows();
        int cols = maze.numCols();

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                FieldView field = new FieldView(maze.getField(i, j));
                this.fields.add(field);
            }
        }
    }

    public boolean checkEmptyNotification() {
        return this.check().isEmpty();
    }

    public boolean checkNotification(StringBuilder msg, MazeObject obj, Field current, Field previous) {
        boolean res = this.privCheckNotification(msg, obj, current, previous);
        this.fields.forEach(FieldView::clearChanged);
        return res;
    }

    private boolean privCheckNotification(StringBuilder msg, MazeObject obj, Field current, Field previous) {
        List<FieldView> changed = this.check();
        int size = changed.size();
        if (size != 2) {
            msg.append("Chyba - nespravny pocet notifikovanych policek!").append(" Ocekava se 2, je ").append(size);
            return false;
        } else {
            FieldView fv1 = (FieldView)changed.get(0);
            FieldView fv2 = (FieldView)changed.get(1);
            Field f1 = fv1.field();
            Field f2 = fv2.field();
            size = fv1.numberUpdates();
            if (size != 1) {
                msg.append("Chyba - nespravny pocet notifikovani policka ").append(" Ocekava se 1, je ").append(size);
                return false;
            } else {
                size = fv2.numberUpdates();
                if (size != 1) {
                    msg.append("Chyba - nespravny pocet notifikovani policka ").append(" Ocekava se 1, je ").append(size);
                    return false;
                } else {
                    Field c;
                    Field p;
                    if (f1.equals(current) && f2.equals(previous)) {
                        c = f1;
                        p = f2;
                    } else {
                        if (!f2.equals(current) || !f1.equals(previous)) {
                            msg.append("Chyba - notifikovana nespravna policka. ").append(" Ocekavaji se ").append(current).append(", ").append(previous).append(". Jsou ").append(f1).append(", ").append(f2);
                            return false;
                        }

                        c = f2;
                        p = f1;
                    }

                    if (!c.contains(obj)) {
                        msg.append("Chyba - cilove policko neobsahuje presouvany objekt!");
                        return false;
                    } else if (p.contains(obj)) {
                        msg.append("Chyba - zdrojove policko obsahuje presouvany objekt!");
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
    }

    private List<FieldView> check() {
        return this.fields.stream().filter(fieldView -> fieldView.numberUpdates() > 0).toList();
    }
}
