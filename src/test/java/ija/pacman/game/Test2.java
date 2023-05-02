package ija.pacman.game;

import ija.pacman.game.field.Field;
import ija.pacman.game.object.MazeObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class Test2 {

    private Maze maze;

    /**
     * Vytvoří bludiště, nad kterým se provádějí testy.
     */
    @Before
    public void setUp() {
        try {
            String filePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + "maps" + File.separator + "validGhost.txt";
            maze = new MazeConfigure().load(new File(filePath)).createMaze();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ija.pacman.game.Test existence objektu, který reprezentuje ducha.
     * 2 body
     */
    @Test
    public void testGhosts() {
        List<MazeObject> lstGhost = maze.ghosts();
        Assert.assertEquals("Bludiste obsahuje jednoho ducha", 1, lstGhost.size());
        MazeObject obj = lstGhost.remove(0);
        Assert.assertEquals("Bludiste obsahuje jednoho ducha", 1, maze.ghosts().size());
        Assert.assertFalse("Objekt neni pacman", obj.isPacman());
        Assert.assertEquals("Objekt je na spravne pozici",
                maze.getField(1, 3),
                obj.getField());
    }

    /**
     * ija.pacman.game.Test správného pohybu ducha po bludišti.
     * 2 body
     */
    @Test
    public void testGhostMoving() {
        // Ghost na pozici 1,3
        MazeObject obj = maze.ghosts().get(0);
        Assert.assertTrue("Presun na policko se podari.", obj.move(Direction.D));
        Assert.assertTrue("Presun na policko se podari.", obj.move(Direction.D));
        Assert.assertTrue("Presun na policko se podari.", obj.move(Direction.D));
        Assert.assertFalse("Presun na policko se nepodari.", obj.move(Direction.R));
    }

    /**
     * ija.pacman.game.Test správného chování při setkání ducha s pacmanem (sníží se počet životů pacmana).
     * 3 body
     */
    @Test
    public void testGhostMeetsPacman() {
        // Ghost na pozici 1,3
        MazeObject ghost = maze.ghosts().get(0);

        // Pacman na pozici 4,2
        Assert.assertFalse("Policko [4,2] neni prazdne", maze.getField(4, 2).isEmpty());
        MazeObject pacman = maze.getField(4, 2).get();
        Assert.assertTrue("Objekt je pacman", pacman.isPacman());
        Assert.assertEquals("Pocet zivotu pacmana", 1, pacman.getLives());

        Assert.assertTrue("Presun na policko se podari.", ghost.move(Direction.D));
        Assert.assertEquals("Pocet zivotu pacmana", 1, pacman.getLives());
        Assert.assertTrue("Presun na policko se podari.", ghost.move(Direction.D));
        Assert.assertEquals("Pocet zivotu pacmana", 1, pacman.getLives());
        Assert.assertTrue("Presun na policko se podari.", ghost.move(Direction.D));
        Assert.assertEquals("Pocet zivotu pacmana", 1, pacman.getLives());
        Assert.assertTrue("Presun na policko se podari.", ghost.move(Direction.L));
        Assert.assertEquals("Pocet zivotu pacmana", 0, pacman.getLives());
    }

    /**
     * Testování notifikací při přesunu objektu (ducha).
     * 5 bodů
     */
    @Test
    public void testNotificationGhostMoving() {
        MazeTester tester = new MazeTester(maze);

        // Ghost na pozici 1,3
        MazeObject obj = maze.ghosts().get(0);

        /* Testy, kdy se presun podari.
         * Dve prezentace policka (view) budou notifikovana o zmene (odebrani objektu a vlozeni objektu).
         * Kazde takove view bude notifikovano prave jednou.
         * Ostatni notifikovana nebudou.
         */
        testNotificationGhostMoving(tester, true, obj, Direction.L);
        testNotificationGhostMoving(tester, true, obj, Direction.L);
        testNotificationGhostMoving(tester, true, obj, Direction.D);

        /* Testy, kdy se presun nepodari (pokus vstoupit do zdi).
         * Nikdo nebude notifikovan.
         */
        testNotificationGhostMoving(tester, false, obj, Direction.R);
    }

    /**
     * Pomocná metoda pro testování notifikací při přesunu objektu.
     *
     * @param tester  Tester nad bludištěm, který provádí vyhodnocení notifikací.
     * @param success Zda se má přesun podařit nebo ne
     * @param obj     Přesouvaný objekt
     * @param dir     Směr přesunu
     */
    private void testNotificationGhostMoving(MazeTester tester, boolean success, MazeObject obj, Direction dir) {
        StringBuilder msg;
        boolean res;

        // Policko, na kterem byl objekt pred zmenou
        Field previous = obj.getField();
        // Policko, na kterem ma byt objekt po zmene
        Field current = previous.nextField(dir);

        // Zadna notifikace zatim neexistuje
        res = tester.checkEmptyNotification();
        Assert.assertTrue("Zadna notifikace.", res);

        // Pokud se ma presun podarit
        if (success) {
            Assert.assertTrue("Presun na policko se podari.", obj.move(dir));
            msg = new StringBuilder();
            // Overeni spravnych notifikaci
            res = tester.checkNotification(msg, obj, current, previous);
            Assert.assertTrue("ija.pacman.game.Test notifikace: " + msg, res);
        }
        // Pokud se nema presun podarit
        else {
            Assert.assertFalse("Presun na policko se nepodari.", obj.move(dir));
            // Zadne notifikace nebyly zaslany
            res = tester.checkEmptyNotification();
            Assert.assertTrue("Zadna notifikace.", res);
        }
    }

}
