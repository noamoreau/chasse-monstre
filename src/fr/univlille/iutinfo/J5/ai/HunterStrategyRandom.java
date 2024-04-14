package fr.univlille.iutinfo.J5.ai;

import java.util.Random;

import fr.univlille.iutinfo.J5.model.Coordinate;
import fr.univlille.iutinfo.cam.player.hunter.IHunterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Classe représentant une stratégie pour le chasseur (IA). Basé sur l'aléatoire
 */
public class HunterStrategyRandom implements IHunterStrategy {
    private int nbCol;
    private int nbRow;
    private static final Random RAND = new Random();

    @Override
    public ICoordinate play() {
        return new Coordinate(RAND.nextInt(nbCol), RAND.nextInt(nbRow));
    }

    @Override
    public void update(ICellEvent arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void initialize(int arg0, int arg1) {
        this.nbCol = arg0;
        this.nbRow = arg1;
    }

}
