package fr.univlille.iutinfo.J5.ai;

import fr.univlille.iutinfo.J5.model.Coordinate;
import fr.univlille.iutinfo.J5.model.Maze;
import fr.univlille.iutinfo.cam.player.monster.IMonsterStrategy;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

public class MonsterStragyMainDroite implements IMonsterStrategy {
    private static DirectionEnum direction = DirectionEnum.EST;
    private boolean[][] maze;
    private ICoordinate coordinate;

    @Override
    public ICoordinate play() {
        if (direction==DirectionEnum.EST) {
            if (this.maze[0].length > coordinate.getCol()+1 && !this.maze[coordinate.getRow()][coordinate.getCol()+1]) {
                this.coordinate = new Coordinate(this.coordinate.getRow(),this.coordinate.getCol()+1);
                return this.coordinate;
            }
            tournerADroite();
        }
        if (direction==DirectionEnum.SUD) {
            if (this.maze.length > coordinate.getRow()+1 && !this.maze[coordinate.getRow()+1][coordinate.getCol()]) {
                this.coordinate = new Coordinate(this.coordinate.getRow()+1,this.coordinate.getCol());
                return this.coordinate;
            }
            tournerADroite();
        }
        if (direction==DirectionEnum.OUEST) {
            if (0 < coordinate.getCol()-1 && !this.maze[coordinate.getRow()][coordinate.getCol()-1]) {
                this.coordinate = new Coordinate(this.coordinate.getRow(),this.coordinate.getCol()-1);
                return this.coordinate;
            }
            tournerADroite();
        }
        if (direction==DirectionEnum.SUD) {
            if (0 < coordinate.getRow()+1 && !this.maze[coordinate.getRow()-1][coordinate.getCol()]) {
                this.coordinate = new Coordinate(this.coordinate.getRow()-1,this.coordinate.getCol());
                return this.coordinate;
            }
        }
        return null; //n'arrive jamais
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

    private static void tournerADroite() {
        switch (MonsterStragyMainDroite.direction.name()) {
            case "EST":
                MonsterStragyMainDroite.direction = DirectionEnum.SUD;
                break;
            case "SUD":
                MonsterStragyMainDroite.direction = DirectionEnum.OUEST;
                break;
            case "OUEST":
                MonsterStragyMainDroite.direction = DirectionEnum.NORD;
                break;
            case "NORD":
                MonsterStragyMainDroite.direction = DirectionEnum.EST;
                break;
        }
    }



    public void getEntryMonster(Maze model) {
        this.coordinate = model.monsterPath.get(0);
    }
}
