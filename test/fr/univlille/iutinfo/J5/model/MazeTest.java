package fr.univlille.iutinfo.J5.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Classe de test pour la classe Maze.
 *
 */
public class MazeTest {
    private static final String FILESEPARATOR = File.separator;
    private Maze maze;
    
    /**
     * Initialisation du labyrinthe avant chaque test.
     */
    @BeforeEach
    public void init(){
        maze = new Maze("." + FILESEPARATOR + "res" + FILESEPARATOR + "labyrinthe.csv");
        maze.move(new Coordinate(0, 2));
    }


    /**
     * Teste que le monstre peut se déplacer vers les coordonnées voisines valides.
     */
    @Test
    void test_neighbor_monster_is_valide() { 
        List<Coordinate> voisins = maze.voisinMonster();
        //assertTrue(voisins.contains(new Coordinate(0, 2)));
        assertTrue(voisins.contains(new Coordinate(1, 2)));
        //assertTrue(voisins.contains(new Coordinate(0, 3)));
    }
    
    /**
     * Teste que le monstre ne peut pas se déplacer vers les coordonnées voisines invalides (murs).
     */
    @Test
    void test_neighbor_monster_is_wall() {
        maze.isWall(new Coordinate(1, 1));
        maze.isWall(new Coordinate(7, 5));
        maze.isWall(new Coordinate(0, 1));
    }

    /**
     * Teste que le monstre ne peut pas se déplacer vers les coordonnées voisines invalides (en dehors du labyrinthe).
     */
    @Test
    void test_is_outside() {
        assertTrue(maze.estEnDehors(new Coordinate(-1, 0)));
        assertTrue(maze.estEnDehors(new Coordinate(16, 0)));
        assertTrue(maze.estEnDehors(new Coordinate(0, -1)));
    }

    /**
     * Teste que la fonction estEnDehors return false quand une coordonnées est dans le labyrinthe
     */
    void test_is_not_outside() {
        assertFalse(maze.estEnDehors(new Coordinate(6, 9)));
        assertFalse(maze.estEnDehors(new Coordinate(4, 12)));
        assertFalse(maze.estEnDehors(new Coordinate(2, 2)));
    }

    /**
     * Teste la génération aléatoire de la case "sortie"
     */
    @Test
    void test_exit_is_not_a_wall(){
        assertFalse(maze.isWall(maze.exit));
        assertFalse(maze.estEnDehors(maze.exit));
    }

    /**
     * Test du toString
     */
    @Test
    void test_toString(){
        assertEquals(   ".X......X......\n" +
                        ".X.X.X..XX...X.\n" +
                        "..XX..X.X.X.X..\n" +
                        "X....X.XX...X.X\n" +
                        "..XXXX....XXX.X\n" +
                        ".XX..XX..XX....\n" +
                        "..XX..X.X.X.X.X\n" +
                        ".X....XX.XX..X.\n" +
                        "...XX.X.XX.XXX.\n" +
                        "X....XX.XX..XX.\n" +
                        "XXX..XX...X..X.\n" +
                        "X..X.X...XX..XX\n" +
                        "..X....XXX.X...\n" +
                        "X.X.....X.....X\n" +
                        "X...XXX...X.X.X\n", maze.toString());
    }

    /**
     * Test shoot
     */
    @Test
    void test_Shoot(){
        // Crée une coordonnées où tiré
        Coordinate coordToShoot = new Coordinate(5, 5);
        // Montre que les valeurs qui ne sont pas censé encore existé n'existe pas
        assertFalse(maze.allShoot.containsKey(maze.tourCourant));
        assertFalse(maze.allShoot.containsValue(coordToShoot));
        // Tire
        maze.shoot(coordToShoot);
        assertTrue(maze.allShoot.containsKey(maze.tourCourant));
        assertTrue(maze.allShoot.containsValue(coordToShoot));
        // Crée une coordonnées où tiré
        Coordinate coordToShoot2 = new Coordinate(6, 5);
        // On simule que le tour courant à augmenter mais en réalité il augmente au déplacement du monstre
        maze.tourCourant ++;
        // Montre que les valeurs qui ne sont pas censé encore existé n'existe pas
        assertFalse(maze.allShoot.containsKey(maze.tourCourant));
        assertFalse(maze.allShoot.containsValue(coordToShoot2));
        // Tire
        maze.shoot(coordToShoot2);
        assertTrue(maze.allShoot.containsKey(maze.tourCourant));
        assertTrue(maze.allShoot.containsValue(coordToShoot2));
        // Montre que les anciennes valeur sont conservé
        assertTrue(maze.allShoot.containsKey(maze.tourCourant-1));
        assertTrue(maze.allShoot.containsValue(coordToShoot));
    }

    @Test
    void test_takeRandomDirection(){
        ArrayList<Direction> listPossible = new ArrayList<>();
        listPossible.add(Direction.HAUT);
        assertEquals(Direction.HAUT,Maze.takeRandomDirection(listPossible));
        listPossible.clear();
        listPossible.add(Direction.BAS);
        assertEquals(Direction.BAS,Maze.takeRandomDirection(listPossible));
        listPossible.clear();
        listPossible.add(Direction.DROITE);
        assertEquals(Direction.DROITE,Maze.takeRandomDirection(listPossible));
        listPossible.clear();
        listPossible.add(Direction.GAUCHE);
        assertEquals(Direction.GAUCHE,Maze.takeRandomDirection(listPossible));
        listPossible.clear();
    }

    @Test
    void test_nbVoisinCheminCalcul(){
        int[][] labyrintheSample = {{1,1,1},{0,0,0},{1,1,1}};
        int[][] result = Maze.nbVoisinCheminCalcul(labyrintheSample,3,3);
        int[][] waited = {{1,1,1},{1,2,1},{1,1,1}};
        assertEquals(result[0][0],waited[0][0]);
        assertEquals(result[0][1],waited[0][1]);
        assertEquals(result[0][2],waited[0][2]);
        assertEquals(result[1][0],waited[1][0]);
        assertEquals(result[1][1],waited[1][1]);
        assertEquals(result[1][2],waited[1][2]);
        assertEquals(result[2][0],waited[2][0]);
        assertEquals(result[2][1],waited[2][1]);
        assertEquals(result[2][2],waited[2][2]);
    }

    @Test
    void test_addAllPossibleDirection(){
        ArrayList<Direction> listPossible = new ArrayList<>();
        listPossible = Maze.addAllPossibleDirection(1, 2, 3, 3);
        assertTrue(listPossible.contains(Direction.HAUT));
        assertFalse(listPossible.contains(Direction.BAS));
        assertTrue(listPossible.contains(Direction.GAUCHE));
        assertTrue(listPossible.contains(Direction.DROITE));
        listPossible.clear();
        listPossible = Maze.addAllPossibleDirection(0, 0, 3, 3);
        assertFalse(listPossible.contains(Direction.HAUT));
        assertTrue(listPossible.contains(Direction.BAS));
        assertFalse(listPossible.contains(Direction.GAUCHE));
        assertTrue(listPossible.contains(Direction.DROITE));
    }

    @Test
    void test_mazeInitialize(){
        int[][] labyrinthe = Maze.mazeInitialize(3,5);
        
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 5; j++){
                if (i != 1 && j != 2){
                    // Les cases autres que celle du centre
                    assertEquals(1,labyrinthe[i][j]);
                } else {
                    // La case du centre qui doit être initialisé en 0
                    assertEquals(0,labyrinthe[1][2]);
                }
            }
        }
    }
}
