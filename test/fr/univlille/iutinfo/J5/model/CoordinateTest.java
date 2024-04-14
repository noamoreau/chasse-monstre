package fr.univlille.iutinfo.J5.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;


/**
 * Classe de test pour la classe Coordinate.
 *
 */
class CoordinateTest {

    /**
     * Teste le constructeur de la classe Coordinate avec tous les paramètres fournis.
     */
    @Test
    void test_constructor_all_parameter() {
        int col = 1;
        int row = 2;
        Coordinate coordinate = new Coordinate(col, row);

        assertEquals(col, coordinate.getCol());
        assertEquals(row, coordinate.getRow());
    }

    /**
     * Teste la méthode getCol() de la classe Coordinate.
     */
    @Test
    void test_getCol() {
        Coordinate coordinate = new Coordinate(1, 2);

        assertEquals(1, coordinate.getCol());
    }

    /**
     * Teste la méthode getRow() de la classe Coordinate.
     */
    @Test
    void test_getRow() {
        Coordinate coordinate = new Coordinate(1, 2);

        assertEquals(2, coordinate.getRow()); 
    }

    /**
     * Teste l'égalité de deux coordonnées.
     */
    @Test
    void test_same_coordinates() {
        Coordinate coordinate1 = new Coordinate(1, 2);
        Coordinate coordinate2 = new Coordinate(1, 2);

        assertEquals(coordinate1.getRow(),coordinate2.getRow());
        assertEquals(coordinate1.getRow(),coordinate2.getRow());     
    }

    /**
     * Teste la non-égalité de deux coordonnées pour row uniqement.
     */
    @Test
    void test_not_same_col_same_raw() {
        Coordinate coordinate1 = new Coordinate(1, 2);
        Coordinate coordinate2 = new Coordinate(3, 2);

        assertNotEquals(coordinate1.getCol(),coordinate2.getCol());
        assertEquals(coordinate1.getRow(),coordinate2.getRow());
    }

    /**
     * Teste la non-égalité de deux coordonnées pour col uniqement.
     */
    @Test
    void test_same_col_not_same_row() {
        Coordinate coordinate1 = new Coordinate(1, 3);
        Coordinate coordinate2 = new Coordinate(1, 2);

        assertEquals(coordinate1.getCol(),coordinate2.getCol());
        assertNotEquals(coordinate1.getRow(),coordinate2.getRow());
    }

    /**
     * Teste la non-égalité de deux coordonnées pour col et row.
     */
    @Test
    void test_not_same_at_all() {
        Coordinate coordinate1 = new Coordinate(1, 3);
        Coordinate coordinate2 = new Coordinate(2, 4);

        assertNotEquals(coordinate1.getCol(),coordinate2.getCol());
        assertNotEquals(coordinate1.getRow(),coordinate2.getRow());
    }
}