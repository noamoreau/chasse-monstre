package fr.univlille.iutinfo.J5.controller;

import fr.univlille.iutinfo.J5.model.CellEvent;
import fr.univlille.iutinfo.J5.model.Coordinate;
import fr.univlille.iutinfo.J5.model.Maze;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

import java.util.Map;

/**
 * ControllerHunter est la classe qui permet de controller ViewHunterMaze
 */
public class ControllerHunter implements IController{
    /**
     * Permet de savoir quel model update 
     */
    private Maze model;

    public ControllerHunter(Maze model) {
        this.model = model;
    }

    /**
     * Permet d'avoir les informations sur la case tiré
     * 
     * @param coord la coordonnée du tir
     * @return l'etat de la cellule visé (Monster si le monstre est touché, EMPTY et un tour si le monstre est déja passer par la case 0 sinon, WALL si c'est aucun de ces cas la) 
     */
    public ICellEvent getState(Coordinate coord) {
        if (this.model.monsterPath.get(model.tourCourant).equals(coord)) {
            return new CellEvent(CellInfo.MONSTER, coord);
        }
        if (!this.model.wall[coord.col][coord.row]) {
            for (Map.Entry<Integer,ICoordinate> set : this.model.monsterPath.entrySet()) {
                if (set.getValue().equals(coord)) {
                    return new CellEvent(CellInfo.EMPTY, set.getKey(), coord);
                }
            }
            return new CellEvent(CellInfo.EMPTY, 0, coord);
        }
        return new CellEvent(CellInfo.WALL, coord);
    }

    /**
     * Permet de convertir des coordonnées en Coordinate
     * 
     * @param x la coordonnées x du point voulu
     * @param y la coordonnées y du point voulu
     * @return L'object Coordinate coorespondant aux coordonnées données en paramètres 
     */
    @Override
    public Coordinate xyToCoordinate(int x, int y) {
        return new Coordinate((int)((Math.ceil(x/20.0)*20)-20)/20,(int)((Math.ceil(y/20.0)*20)-20)/20-1);
    }

    /**
     * Permet de passer de coordordonnées à l'état de la case
     * 
     * @param x la coordonnées x du point voulu
     * @param y la coordonnées y du point voulu
     * @return l'état de la case en fonction de coordonnées
     */
    public ICellEvent shoot(int x, int y){
        return this.getState(this.xyToCoordinate(x, y));
    }
}
