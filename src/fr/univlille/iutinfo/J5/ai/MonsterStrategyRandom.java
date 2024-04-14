package fr.univlille.iutinfo.J5.ai;

import java.util.Random;

import fr.univlille.iutinfo.J5.model.Coordinate;
import fr.univlille.iutinfo.J5.model.Maze;
import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * Classe représentant une stratégie pour le monstre (IA). Basé sur l'aléatoire
 *
 */
public class MonsterStrategyRandom implements IMonsterStrategy {
    private boolean[][] maze;
    private ICoordinate coordinate;
    private static final Random RAND = new Random();

    @Override
    public ICoordinate play() {
        Coordinate nextCord = new Coordinate(coordinate.getCol(), coordinate.getRow());
        do {
            nextCord.col = this.coordinate.getCol() + MonsterStrategyRandom.RAND.nextInt(-1, 2);
            nextCord.row = this.coordinate.getRow() + MonsterStrategyRandom.RAND.nextInt(-1, 2);
        } while ((nextCord.col < 0 || nextCord.row < 0) || maze[nextCord.getCol()][nextCord.getRow()]
                || coordinate.equals(nextCord));
        this.coordinate = nextCord;
        return nextCord;
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

}
