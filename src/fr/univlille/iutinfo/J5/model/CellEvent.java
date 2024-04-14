package fr.univlille.iutinfo.J5.model;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Cette classe représente toutes les informations d'une case dans le labyrinthe
 */
public class CellEvent implements ICellEvent{

    /**
     * State de type CellInfo représente l'évenement en cours de la case.
     */
    private CellInfo state;
    /**
     * Turn de type int représente le numéro du tour du moment où le monstre est passé sur la case,
     * 0 si le monstre n'est jamais passé.
     */
    private int turn;
    /**
     * Coord de type ICoordinate représentant les coordonnées de la case par rapport au labyrinthe.
     */
    private ICoordinate coord;

    /**
     * Crée un CellEvent avec tous les paramètres
     * 
     * @param state l'évenement de la case
     * @param turn le tour de la case
     * @param coord la coordonnée de la case
     */
    public CellEvent(CellInfo state, int turn , ICoordinate coord){
        this.state = state;
        this.turn = turn;
        this.coord = coord;
    }

    /**
     * Crée un CellEvent sans le nombre de tour initialiser à 0 par défaut
     * 
     * @param state l'évenement de la case
     * @param turn le tour de la case
     * @param coord la coordonnée de la case
     */
    public CellEvent(CellInfo state, ICoordinate coord){
        this(state,0,coord);
    }

    /**
     * Getter de state
     * 
     * @return l'évenement de la case
     */
    public CellInfo getState() {
        return this.state;
    }

    /**
     * Getter de turn
     * 
     * @return le nombre de tour associé à la case
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     * Getter de coord
     * 
     * @return les coordonnées de la case
     */
    public ICoordinate getCoord() {
        return this.coord;
    }

    /**
     * Setter de state
     * 
     * @param state met à jour le state de la case
     */
    public void setState(CellInfo state) {
        this.state = state;
    }

    /**
     * Setter de turn
     * 
     * @param turn met à jour le turn de la case
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }
    
}