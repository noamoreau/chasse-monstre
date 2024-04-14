package fr.univlille.iutinfo.J5.controller;

import java.util.ArrayList;
import java.util.List;

import fr.univlille.iutinfo.J5.model.Coordinate;
import fr.univlille.iutinfo.J5.model.Maze;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * ControllerMonster est la classe qui permet de controller ViewMonsterMaze
 */
public class ControllerMonster implements IController {

    /**
     * Permet de savoir quel model update
     */
    private Maze model;

    public ControllerMonster(Maze model) {
        this.model = model;
    }

    /**
     * Permet de connaître les cases voisines du monstre
     * 
     * @return une liste de Coordinate où le monstre peut aller
     */
    public List<Coordinate> voisinMonster() {

        ArrayList<Coordinate> result = new ArrayList<Coordinate>();
        ICoordinate current = model.monsterPath.get(model.monsterPath.size() - 1);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Coordinate tmp = new Coordinate(current.getCol() + i, current.getRow() + j);
                if (!this.estEnDehors(tmp) && !model.isWall(tmp) && !tmp.equals(current))
                    result.add(tmp);
            }
        }
        return result;
    }

    /**
     * Vérifie à partir de ICoordinate en paramètre si la coordonnée est en dehors
     * du labyrinthe
     * 
     * @param ICoordinate
     * 
     * @return true si la coord est en dehors false sinon
     */
    public boolean estEnDehors(ICoordinate ic) {
        int longueur = model.wall.length;
        int largeur = model.wall[0].length;
        boolean result = true;
        if ((0 <= ic.getRow() && ic.getRow() < longueur) && (0 <= ic.getCol() && ic.getCol() < largeur)) {
            result = false;
        }
        return result;
    }

    /**
     * Permet de mettre a jour le model
     * 
     * @param coord
     *              nouvelle coordonnée du monster
     */
    public void move(Coordinate coord) {
        this.model.monsterPath.put(this.model.monsterPath.size(), coord);
        this.model.tourCourant++;
    }

    /**
     * Permet de setTurn sur la coordonnées passé en paramètre
     * 
     * @param coord
     */
    public void setTurn(Coordinate coord) {
        throw new UnsupportedOperationException("Unimplemented method 'setTurn'");
    }

    /**
     * Permet de convertir des coordonnées en Coordinate
     * 
     * @param x la coordonnées x du point voulu
     * @param y la coordonnées y du point voulu
     * @return L'object Coordinate coorespondant aux coordonnées données en
     *         paramètres
     */
    @Override
    public Coordinate xyToCoordinate(int x, int y) {
        System.out.println(new Coordinate((int) ((Math.ceil(x / 20.0) * 20) - 20) / 20,
                (int) ((Math.ceil(y / 20.0) * 20) - 20) / 20 - 1));
        System.out.println((int) ((Math.ceil(x / 20.0) * 20) - 20) / 20 + "-"
                + (int) ((Math.ceil((y - 16) / 20.0) * 20) - 20) / 20);
        return new Coordinate((int) ((Math.ceil(x / 20.0) * 20) - 20) / 20,
                (int) ((Math.ceil((y - 16) / 20.0) * 20) - 20) / 20 - 1);
    }

}
