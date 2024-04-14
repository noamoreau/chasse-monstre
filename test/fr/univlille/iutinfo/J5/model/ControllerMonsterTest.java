package fr.univlille.iutinfo.J5.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univlille.iutinfo.J5.controller.ControllerMonster;

public class ControllerMonsterTest {
    private static final String FILESEPARATOR = File.separator;
    private ControllerMonster model;
    private Maze maze;

    @BeforeEach
    public void init(){
        maze = new Maze("." + FILESEPARATOR + "res" + FILESEPARATOR + "labyrinthe.csv");
        maze.move(new Coordinate(0, 2));
        model = new ControllerMonster(maze);
    }

    
    @Test
    void test_voisinMonster(){
        List<Coordinate> list = new ArrayList<Coordinate>();
        list = model.voisinMonster(); 
        assertTrue(list.contains(new Coordinate(1, 2)));
        assertTrue(list.contains(new Coordinate(0, 1)));
        assertFalse(list.contains(new Coordinate(0, 2)));
        assertFalse(list.contains(new Coordinate(1, 1)));
    }
    

    @Test
    void test_estEnDehors(){
        assertTrue(model.estEnDehors(new Coordinate(0, -1)));
        assertFalse(model.estEnDehors(new Coordinate(0, 0)));
    }
}
