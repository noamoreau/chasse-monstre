package fr.univlille.iutinfo.J5.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Classe de test pour la classe CellEvent.
 *
 */
public class CellEventTest{


    /**
     * Teste le constructeur de la classe CellEvent avec tous les paramètres fournis.
     */
    @Test
    void test_constructor_all_parameter() {
        CellInfo state = CellInfo.MONSTER;
        int turn = 5;
        ICoordinate coord = new Coordinate(1, 2);
        CellEvent cellEvent = new CellEvent(state, turn, coord);

        assertEquals(state, cellEvent.getState());
        assertEquals(turn, cellEvent.getTurn());
        assertEquals(coord, cellEvent.getCoord());
    }

    /**
     * Teste le constructeur de la classe CellEvent sans le paramètre de nombre de tour.
     */
    @Test
    void test_constructor_no_turn_parameter() {
        CellInfo state = CellInfo.MONSTER;
        ICoordinate coord = new Coordinate(1, 2);

        state = CellInfo.EMPTY;
        CellEvent cellEvent = new CellEvent(state, coord);

        assertEquals(state, cellEvent.getState());
        assertEquals(0, cellEvent.getTurn());
        assertEquals(coord, cellEvent.getCoord());
    }

    /**
     * Teste la méthode setState() de la classe CellEvent.
     */
    @Test
    void test_setState() {
        CellEvent cellEvent = new CellEvent(CellInfo.MONSTER, new Coordinate(1, 2));
        assertEquals(CellInfo.MONSTER, cellEvent.getState());

        cellEvent.setState(CellInfo.EMPTY);
        assertEquals(CellInfo.EMPTY, cellEvent.getState());
    }

    /**
     * Teste la méthode setTurn() de la classe CellEvent.
     */
    @Test
    void test_setTurn() {
        CellEvent cellEvent = new CellEvent(CellInfo.MONSTER, new Coordinate(1, 2));
        assertEquals(0, cellEvent.getTurn());

        cellEvent.setTurn(5);
        assertEquals(5, cellEvent.getTurn());
    }

    /**
     * Teste les getters de la classe CellEvent.
     */
    @Test
    void testGetters() {
        CellInfo state = CellInfo.MONSTER;
        int turn = 5;
        ICoordinate coord = new Coordinate(1, 2);
        CellEvent cellEvent = new CellEvent(state, turn, coord);

        assertEquals(state, cellEvent.getState());
        assertEquals(turn, cellEvent.getTurn());
        assertEquals(coord, cellEvent.getCoord());
    }
    
}
