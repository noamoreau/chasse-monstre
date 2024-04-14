package fr.univlille.iutinfo.J5.model;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Classe représentant une coordonnée dans le labyrinthe 2D
 */
public class Coordinate implements ICoordinate{

    /**
     * Coordonnée en X.
     */
    public int col;
    /**
     * Coordonnée en Y.
     */
    public int row;

    /**
     * Constructeur avec paramètres.
     *
     * @param col Coordonnée en X dans le labyrinthe.
     * @param row Coordonnée en Y dans le labyrinthe.
     */
    public Coordinate(int col, int row) {
        this.col = col;
        this.row = row;
    }

    /**
     * Obtient la coordonnée X.
     *
     * @return La coordonnée X.
     */
    @Override
    public int getCol() {
        return col;
    }

    /**
     * Obtient la coordonnée Y.
     *
     * @return La coordonnée Y.
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères de cette coordonnée.
     *
     * @return La représentation sous forme de chaîne de caractères de cette coordonnée.
     */
    public String toString(){
        return "[col : " + this.col + " row : " + this.row + "]";
    }

    /**
     * Teste l'égalité de cette coordonnée avec une autre coordonnée.
     *
     * @param obj L'autre coordonnée à tester.
     * @return `true` si les deux coordonnées sont égales, `false` sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coordinate other = (Coordinate) obj;
        if (col != other.col)
            return false;
        if (row != other.row)
            return false;
        return true;
    }
    
    
}
