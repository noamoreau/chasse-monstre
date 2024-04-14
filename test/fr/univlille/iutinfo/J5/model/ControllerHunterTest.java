package fr.univlille.iutinfo.J5.model;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univlille.iutinfo.J5.controller.ControllerHunter;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;


public class ControllerHunterTest {
    private static final String FILESEPARATOR = File.separator;
    private ControllerHunter model;
    private Maze maze;

    @BeforeEach
    public void init(){
        maze = new Maze("." + FILESEPARATOR + "res" + FILESEPARATOR + "labyrinthe.csv");
        maze.move(new Coordinate(0, 2));
        model = new ControllerHunter(maze);
    }

    @Test
    void test_getState_empty(){
        assertEquals(model.getState(new Coordinate(0, 3)).getState(),CellInfo.EMPTY);
        assertEquals(model.getState(new Coordinate(0, 4)).getState(),CellInfo.EMPTY);
        assertEquals(model.getState(new Coordinate(1, 4)).getState(),CellInfo.EMPTY);
    }

    
    @Test
    void test_getState_wall(){
        assertEquals(model.getState(new Coordinate(0, 1)).getState(),CellInfo.WALL);
        assertEquals(model.getState(new Coordinate(1, 1)).getState(),CellInfo.WALL);
        assertEquals(model.getState(new Coordinate(2, 2)).getState(),CellInfo.WALL);  
    }
    
    
    @Test
    void test_getState_monster(){
        assertEquals(model.getState(new Coordinate(0, 2)).getState(),CellInfo.MONSTER);
    }
    
}
