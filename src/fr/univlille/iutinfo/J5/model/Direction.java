package fr.univlille.iutinfo.J5.model;

public enum Direction {
    HAUT(0,-1),
    GAUCHE(-1,0),
    DROITE(1,0),
    BAS(0,1);

    int x;
    int y;

    private Direction(int x,int y){
        this.x = x;
        this.y = y;
    }
}
