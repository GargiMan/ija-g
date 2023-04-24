package ija.pacman.game;

import ija.pacman.game.field.Field;
import ija.pacman.game.field.PathField;
import ija.pacman.game.field.WallField;
import ija.pacman.game.object.MazeObject;
import ija.pacman.game.object.PacmanObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class Test1 {

    /**
     * Test třídy MazeConfigure. Testuje odolnost vůči chobnému vstupu.
     */
    @Test
    public void test01() {
        String filePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + "maps" + File.separator + "mapaInvalid.txt";
        Assert.assertThrows("Vytvoreni bludiste se nezdarilo", IllegalArgumentException.class, () -> new MazeConfigure().load( new File(filePath)));
    }

    /**
     * Test třídy MazeConfigure. Testuje správné zpracování vstupu a vytvoření bludiště.
     */
    @Test
    public void test02() {
        Maze maze = createTestMaze();

        Assert.assertNotNull("Vytvoreni bludiste se zdarilo", maze);
        Assert.assertEquals("Pocet radku bludiste (vcetne krajnich zdi)", 6, maze.numRows());
        Assert.assertEquals("Pocet sloupcu bludiste (vcetne krajnich zdi)", 5, maze.numCols());
    }

    /**
     * Test implementací rozhraní Field. Testuje správné umístění políček, jejich typů
     * a vzájemných vztahů.
     */
    @Test
    public void test03() {
        Maze maze = createTestMaze();

        // Testy, zda je bludiste ohraniceno zdi.
        // Testuje se pouze prvni radek.
        for (int c = 0; c < 5; c++) {
            Field f1 = new WallField(0, c);
            Field f2 = maze.getField(0, c);
            Assert.assertEquals("Pole reprezentujici zed", f1, f2);
            Assert.assertTrue("Pole reprezentujici zed nema zadny objekt", f2.isEmpty());
            Assert.assertFalse("Na pole reprezentujici zed se nelze presunout", f2.canMove());
        }

        Field f1 = new WallField(2,2);
        Field f2 = maze.getField(2, 2);
        Assert.assertEquals("Pole reprezentujici zed", f1, f2);
        Assert.assertTrue("Pole reprezentujici zed nema zadny objekt", f2.isEmpty());
        Assert.assertFalse("Na pole reprezentujici zed se nelze presunout", f2.canMove());

        Field f3 = new PathField(2,1);
        f1 = new WallField(2,1);
        Field f4 = maze.getField(2, 1);
        Assert.assertEquals("Pole reprezentujici cestu", f3, f4);
        Assert.assertNotEquals("Pole reprezentujici cestu", f1, f4);
        Assert.assertTrue("Pole je zatim prazdne", f4.isEmpty());
        Assert.assertTrue("Na pole reprezentujici cestu se lze presunout", f4.canMove());

        Field f5 = new PathField(4,2);
        Field f6 = maze.getField(4, 2);
        Assert.assertEquals("Pole reprezentujici cestu", f5, f6);
        Assert.assertFalse("Pole neni prazdne", f6.isEmpty());
        Assert.assertTrue("Na pole reprezentujici cestu se lze presunout", f6.canMove());

    }

    /**
     * Test implementací rozhraní Field. Testuje správné chování při práci s objekty (MazeObject),
     * která lze na políčka umístit.
     */
    @Test
    public void test04() {
        Maze maze = createTestMaze();

        Field f1 = maze.getField(4, 2);
        Field f2 = f1.nextField(Direction.R);
        Field f3 = f1.nextField(Direction.D);

        Assert.assertTrue("Pacman se muze posunout na pole f2", f2.canMove());
        Assert.assertFalse("Pacman se nemuze posunout na pole f3", f3.canMove());

        MazeObject obj = f1.get();
        Assert.assertTrue("Objekt je instanci tridy PacmanObject", obj instanceof PacmanObject);

        Assert.assertFalse("Pole f1 neni prazdne", f1.isEmpty());
        Assert.assertTrue("Odebrani pacmana z pole f1", f1.contains(obj));
        f1.removeObject(obj);
        Assert.assertTrue("Pole f1 je prazdne", f1.isEmpty());
        Assert.assertFalse("Pokus od odebrani pacmana z pole f2 (neni tam)", f2.contains(obj));
        f2.removeObject(obj);

        Assert.assertTrue("Pole f2 je prazdne", f2.isEmpty());
        Assert.assertFalse("Vlozeni pacmana na pole f2", f2.contains(obj));
        f2.addObject(obj);
        Assert.assertFalse("Pole f2 neni prazdne", f2.isEmpty());
        Assert.assertEquals("Na novem miste je stejny pacman", obj, f2.get());

        f3.addObject(obj);
        Assert.assertThrows(
                "Na pole typu zed nelze vlozit - hledani generuje vyjimku UnsupportedOperationException",
                UnsupportedOperationException.class,
                () -> { f3.contains(obj); }
        );
    }

    /**
     * Test implementací rozhraní MazeObject. Testuje správné chování při přesunu objektů.
     */
    @Test
    public void test05() {
        Maze maze = createTestMaze();

        Field f = maze.getField(4, 2);
        MazeObject obj = f.get();

        Assert.assertTrue("Pacman se muze posunout doprava", obj.canMove(Direction.R));
        Assert.assertTrue("Pacman se muze posunout doleva", obj.canMove(Direction.L));
        Assert.assertFalse("Pacman se nemuze posunout nahoru", obj.canMove(Direction.U));
        Assert.assertFalse("Pacman se nemuze posunout dolu", obj.canMove(Direction.D));

        Assert.assertFalse("Posun nahoru se nezdari", obj.move(Direction.U));
        Assert.assertFalse("Puvodni misto pacmana je obsazeno", f.isEmpty());
        Assert.assertTrue("Vedlejsi misto je volne", f.nextField(Direction.U).isEmpty());
        Assert.assertFalse("Posun dolu se nezdari", obj.move(Direction.D));
        Assert.assertFalse("Puvodni misto pacmana je obsazeno", f.isEmpty());
        Assert.assertTrue("Vedlejsi misto je volne", f.nextField(Direction.D).isEmpty());

        Assert.assertTrue("Posun doleva se zdari", obj.move(Direction.L));
        Assert.assertTrue("Puvodni misto pacmana je volne", f.isEmpty());
        Assert.assertFalse("Vedlejsi misto je obsazene", f.nextField(Direction.L).isEmpty());

        Assert.assertEquals("Na novem miste je stejny pacman", obj, f.nextField(Direction.L).get());
    }

    /**
     * Pomocná metoda pro vytvoření testovacího bludiště. Využito v testech 03, 04 a 05.
     * @return Vytvoné bludiště.
     */
    private Maze createTestMaze() {
        try {
            String filePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + "maps" + File.separator + "mapaValid.txt";
            return new MazeConfigure().load(new File(filePath)).createMaze();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
