package fr.univlille.iutinfo.J5.ai;

import java.util.LinkedList;
import java.util.Queue;

import fr.univlille.iutinfo.J5.model.Coordinate;
import fr.univlille.iutinfo.J5.model.Maze;
import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class MonsterStrategyParcourLargeur implements IMonsterStrategy {

    private boolean[][] maze;
    private ICoordinate coordinate;
    private Queue<ICoordinate> queueParcours;
    private ICoordinate exit;
    /* 
    private Coordinate getVoisin(Coordinate coord){
        
    } */

    private void parcours() {
        boolean trouver = false;
        this.queueParcours.offer(coordinate);
        while (!this.queueParcours.isEmpty() && !trouver) {
            Coordinate c = (Coordinate) this.queueParcours.peek();
            if (c.equals(this.exit)) {
                trouver = true;
            } else {
                Coordinate voisin;
            }
        }
    }

    @Override
    public ICoordinate play() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'play'");
    }

    @Override
    public void update(ICellEvent arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void initialize(boolean[][] arg0) {
        this.maze = arg0;
    }

    public void getEntryMonster(Maze model) {
        this.coordinate = model.monsterPath.get(0);
    }

    public void getExit(Maze model) {
        this.exit = model.exit;
    }

}
