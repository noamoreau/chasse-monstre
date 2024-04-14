package fr.univlille.iutinfo.J5.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;

public class DirectionTest {
    
    @Test
    void test_haut_direction_x_and_y(){
        Direction haut = Direction.HAUT;
        assertNotEquals(1,haut.x);
        assertEquals(0,haut.x);
        assertNotEquals(1,haut.x);
        assertNotEquals(1,haut.y);
        assertNotEquals(0,haut.y);
        assertEquals(-1,haut.y); 
    }

    @Test
    void test_bas_direction_x_and_y(){
        Direction bas = Direction.BAS;
        assertNotEquals(1,bas.x);
        assertEquals(0,bas.x);
        assertNotEquals(-1,bas.x);
        assertEquals(1,bas.y);
        assertNotEquals(0,bas.y);
        assertNotEquals(-1,bas.y);
    }

    @Test
    void test_gauche_direction_x_and_y(){
        Direction gauche = Direction.GAUCHE;
        assertNotEquals(1,gauche.x);
        assertNotEquals(0,gauche.x);
        assertEquals(-1,gauche.x);
        assertNotEquals(1,gauche.y);
        assertEquals(0,gauche.y);
        assertNotEquals(-1,gauche.y);
    }

    @Test
    void test_droite_direction_x_and_y(){
        Direction droite = Direction.DROITE;
        assertEquals(1,droite.x);
        assertNotEquals(0,droite.x);
        assertNotEquals(-1,droite.x);
        assertNotEquals(1,droite.y);
        assertEquals(0,droite.y);
        assertNotEquals(-1,droite.y); 
    }
}
