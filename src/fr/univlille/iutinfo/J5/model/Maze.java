package fr.univlille.iutinfo.J5.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

/**
 * La classe qui gére ce qu'est un labyrinthe et permet d'en générer
 */
public class Maze extends Subject {
    /**
     * Map contenant tous les tirs effectuer avec le tour où le tir à était
     * effectuer
     */
    public Map<Integer, ICoordinate> allShoot;
    /**
     * Map contenant tous les coordonnées où le monstre à était avec le tour de
     * quand il y était
     */
    public Map<Integer, ICoordinate> monsterPath;
    /**
     * Les coordonnées de la sortie
     */
    public ICoordinate exit;
    /**
     * Le labyrinthe sous forme de tableau boolean mur/chemin
     */
    public boolean wall[][];
    /**
     * Le tour courant
     */
    public int tourCourant;
    /**
     * Si c'est le tour du monstre, tour du chasseur le cas inverse
     */
    public boolean monsterPlay;
    /**
     * Fichier de texture
     */
    private File textureWall;
    /**
     * Fichier de texture
     */
    private File textureGround;
    /**
     * Fichier de texture
     */
    private File textureMonster;
    /**
     * Fichier de texture
     */
    private File textureDoor;
    /**
     * Fichier de texture
     */
    private File textureCloud;
    /**
     * Fileseparator pour la compatibilité avec les différents OS
     */
    private static final String FILESEPARATOR = File.separator;

    /**
     * Le maximum d'iteration pour essayer de générer un chemin pour quand les
     * probabilité WAY_CHANCE sont trop basse
     */
    public static final int MAX_ITERATION = 100; // Pour quand trop peu de chance de mur peu boucler infini à la fin
    /**
     * La chance inverse 1-(ONE_WAY_CHANCE) de générer un chemin si la case a 1
     * chemin voisin (d'où il viens)
     */
    public static double ONE_WAY_CHANCE = 0.1; // chance inverse de générer chemin sachant déjà 1 voisin (le minimum) ->
                                               // ex : 0.2 = 80%
    /**
     * La chance inverse 1-(TWO_WAY_CHANCE) de générer un chemin si la case a 2
     * chemin voisin (d'où il viens + 1 autre)
     */
    public static double TWO_WAY_CHANCE = 0.9; // chance inverse de générer chemin sachant déjà 2 voisin
    /**
     * La chance inverse 1-(THREE_WAY_CHANCE) de générer un chemin si la case a 3
     * chemin voisin (d'où il viens + 2 autres)
     */
    public static double THREE_WAY_CHANCE = 1; // chance inverse de générer chemin sachant déjà 3 voisin

    /**
     * Génére un csv labyrinthe aléatoire qui respect de nombreuses régles
     * Les paramètres minimum conseillé sont x=5, y=5, ratio=30
     * Les paramètres maximum conseillé sont x=30, y=30, ratio=90
     * 
     * @param x    nombre de colonne du labyrinthe généré
     * @param y    nombre de ligne du labyrinthe généré
     * @param taux taux de mur dans le labyrinthe
     */
    public static void generateMaze(int x, int y, double taux) {

        ///// Initialisation : le labyrinthe plein de mur et un chemin au centre
        int[][] labyrinthe = mazeInitialize(x, y);
        ///// Génére la première version du nombre de chemn voisin
        int nbVoisinChemin[][] = nbVoisinCheminCalcul(labyrinthe, x, y);

        // Initialise différente variable tels que le nombres de mur actuelle
        // le nombre de mur nécessaire en fonction du taux
        // et le compteur. Tout pour la boucle de condition while
        int nbWall = (int) (x * y * taux / 100);
        int initNbWall = (x * y) - 1;
        int cpt = 0;

        // Initialise une HashSet de coordonnée pour ne produire que 1 chemin pour
        // chaque coordonnée
        HashSet<Integer[]> coord = new HashSet<Integer[]>();

        // Initialise la ArrayList pour les Direction possible de à chaque point
        ArrayList<Direction> listPossible = new ArrayList<>();

        ///// Parti principale : boucle tant que le nombre de mur voulu est plus petit
        ///// que le nombre de mur
        ///// ou que l'on dépasse MAX_ITERATION (uniquement pour les petits ratio de
        ///// mur)
        while (initNbWall > nbWall && cpt < MAX_ITERATION) {
            cpt++;
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    nbVoisinChemin = nbVoisinCheminCalcul(labyrinthe, x, y);

                    // On ne refait pas une coordonnées déjà fait
                    if (labyrinthe[j][i] == 0 && !(coord.contains(new Integer[] { j, i }))) {
                        coord.add(new Integer[] { j, i });

                        // Ajoute à cette liste toute les directions possible pour ce point
                        listPossible = addAllPossibleDirection(j, i, x, y);
                        // Prend une direction random parmis celle de la liste
                        Direction now = takeRandomDirection(listPossible);

                        // Essaye aléatoirement de contruire un chemin
                        if (check(nbVoisinChemin, j + now.x, i + now.y)) {
                            labyrinthe[j + now.x][i + now.y] = 0;
                            initNbWall--;
                        }

                        // Vide la liste pour le prochain point
                        listPossible.clear();
                    }
                }
            }
        }
        // écrit le labyrinthe en csv
        CSVWrite(labyrinthe);
    }

    /**
     * écrit dans un CSV le labyrinthe utile à l'import/export
     * 
     * @param labyrinthe
     */
    public static void CSVWrite(int[][] labyrinthe) {
        FileWriter writer;

        try {
            writer = new FileWriter("." + FILESEPARATOR + "res" + FILESEPARATOR + "LabyrintheTmp.csv");
            for (int[] ligne : labyrinthe) {
                for (int i = 0; i < ligne.length; i++) {
                    if (i < ligne.length - 1) {
                        writer.write("" + ligne[i] + ",");
                    } else {
                        writer.write("" + ligne[i]);
                    }
                }
                writer.write("\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée un labyrinthe de la taille voulue, le remplie de 1 et initialise la case
     * au centre à 0
     * 
     * @param x La taille total du labyrinthe en x
     * @param y La taille total du labyrinthe en y
     * @return Le labyrinthe généré et remplit de 1
     */
    public static int[][] mazeInitialize(int x, int y) {
        int[][] labyrinthe = new int[x][y];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                labyrinthe[j][i] = 1;
            }
        }
        labyrinthe[x / 2][y / 2] = 0;
        return labyrinthe;
    }

    /**
     * addAllPossibleDirection renvoie une liste de direction possible pour une
     * coordonnée donnée du labyrinthe
     * 
     * @param j La coordonnée x concernée
     * @param i La coordonnée y concernée
     * @param x La coordonnée x maximum
     * @param y La coordonnée y maximum
     * @return Une liste des directions possibles
     */
    public static ArrayList<Direction> addAllPossibleDirection(int j, int i, int x, int y) {
        ArrayList<Direction> newListPossible = new ArrayList<>();
        if (i != 0) {
            newListPossible.add(Direction.HAUT);
        }
        if (i != y - 1) {
            newListPossible.add(Direction.BAS);
        }
        if (j != 0) {
            newListPossible.add(Direction.GAUCHE);
        }
        if (j != x - 1) {
            newListPossible.add(Direction.DROITE);
        }
        return newListPossible;
    }

    /**
     * La fonction renvoie une Direction aléatoire parmi toutes celle donner dans la
     * liste en paramètres
     * 
     * @param listPossible La liste des direction possible
     * @return Une direction Random parmi la liste des possibles
     */
    public static Direction takeRandomDirection(ArrayList<Direction> listPossible) {
        Random random = new Random();
        int maxZ = listPossible.size();
        int z = (int) (random.nextDouble() * maxZ) + 1;
        return listPossible.get(z - 1);
    }

    /**
     * Génére un tableau[][] similaire au labyrinthe où chaque valeur représente
     * le nombres de chemin collé pour chaque coordonées x,y
     * 
     * @param labyrinthe l'état du labyrinthe pendant la génération
     * @param x          la taille du labyrinthe x
     * @param y          la taille du labyrinthe y
     * @return un labyrinthe où chaque case représente le nombre de chemin voisin
     */
    public static int[][] nbVoisinCheminCalcul(int[][] labyrinthe, int x, int y) {

        int[][] nbVoisinChemin = new int[x][y];

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                nbVoisinChemin[j][i] = 0;

                if (j != 0 && labyrinthe[j - 1][i] == 0) {
                    nbVoisinChemin[j][i]++;
                }
                ;

                if (i != y - 1 && labyrinthe[j][i + 1] == 0) {
                    nbVoisinChemin[j][i]++;

                }

                if (i != 0 && labyrinthe[j][i - 1] == 0) {
                    nbVoisinChemin[j][i]++;
                }

                if (j != x - 1 && labyrinthe[j + 1][i] == 0) {
                    nbVoisinChemin[j][i]++;
                }
            }
        }
        return nbVoisinChemin;
    }

    // Défini en fonction du nombre de voisin et de la chance si un chemin doit être
    // généré
    /**
     * Tente en fonction des WAY_CHANCE et du nombre de voisin chemin
     * pour la coordonnée passé en paramètres de la transformer en chemin
     * 
     * @param nbVoisinChemin le labyrinthe de nombre de voisin chemin
     * @param x              la coordonnée x à tester
     * @param y              la coordonnée y à tester
     * @return un boolean qui autorise où non à changer la case mur en chemin
     */
    public static boolean check(int[][] nbVoisinChemin, int x, int y) {
        Random random = new Random();
        double nb = random.nextDouble();
        if (nbVoisinChemin[x][y] == 0) {
            // génére si aucun chemin autour (impossible puisque case d'origine adjacente)
            return true; // 100%
        } else if (nbVoisinChemin[x][y] == 1) {
            if (nb > ONE_WAY_CHANCE)
                return true; // chance de généré le chemin
            else
                return false;
        } else if (nbVoisinChemin[x][y] == 2) {
            if (nb > TWO_WAY_CHANCE)
                return true; // chance de généré le chemin
            else
                return false;
        } else if (nbVoisinChemin[x][y] == 3) {
            if (nb > THREE_WAY_CHANCE)
                return true; // chance de généré le chemin
            else
                return false;
        } else if (nbVoisinChemin[x][y] == 4) {
            // ne génére pas si y'a déjà 4 (peut mener à boucle infini d'où MAX_ITERATION)
            return false;
        } else {
            // Aucun cas
            return false;
        }
    }

    /**
     * Le constructeur de labyrinthe depuis un fichier csv générer par generateMaze
     * ou importer
     * 
     * @param String Le chemin du fichier
     */
    public Maze(String file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<boolean[]> tabs = new ArrayList<>();
            while (br.ready()) {
                String[] tmp = br.readLine().split(",");
                boolean[] tab = new boolean[tmp.length];
                for (int i = 0; i < tmp.length; i++) {
                    tab[i] = Integer.parseInt(tmp[i]) == 1;
                }
                tabs.add(tab);
            }
            int largeur = tabs.get(0).length;
            int longueur = tabs.size();

            boolean[][] tmp = new boolean[longueur][largeur];
            for (int i = 0; i < tabs.size(); i++) {
                tmp[i] = tabs.get(i);
            }

            br.close();

            this.wall = tmp;
            this.exit = this.generateCoord();
            this.tourCourant = 0;
            this.allShoot = new HashMap<Integer, ICoordinate>();
            this.monsterPath = new HashMap<Integer, ICoordinate>();
            Coordinate coord;
            do {
                coord = (Coordinate) this.generateCoord();
            } while (coord.equals(exit));
            this.monsterPath.put(0, (ICoordinate) coord);
            this.monsterPlay = false;
            String location = System.getProperty("user.dir");
            if (location.substring(location.length() - 8, location.length()).equals("J5_SAE3A")) {
                File path = new File("./res/textures/murs");
                if (path.isDirectory()) {
                    File[] dir = path.listFiles();
                    Random rd = new Random();
                    int choice = rd.nextInt(dir.length);
                    this.textureWall = dir[choice];
                }
                path = new File("./res/textures/sols");
                if (path.isDirectory()) {
                    this.textureGround = new File(path.getPath() + "/herbe.png");
                    this.textureMonster = new File(path.getPath() + "/monster.png");
                    this.textureDoor = new File(path.getPath() + "/porte.png");
                    this.textureCloud = new File(path.getPath() + "/cloud.png");
                }
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Renvoie le fichier de la texture du mur
     * 
     * @return le fichier de texture
     */
    public File getTextureWall() {
        return this.textureWall;
    }

    /**
     * Renvoie le fichier de la texture du sol
     * 
     * @return le fichier de texture
     */
    public File getTextureGround() {
        return this.textureGround;
    }

    /**
     * Renvoie le fichier de la texture du monstre
     * 
     * @return le fichier de texture
     */
    public File getTextureMonster() {
        return this.textureMonster;
    }

    /**
     * Renvoie le fichier de la texture de la porte
     * 
     * @return le fichier de texture
     */
    public File getTextureDoor() {
        return this.textureDoor;
    }

    /**
     * Renvoie le fichier de la texture du nuage
     * 
     * @return le fichier de texture
     */
    public File getTextureCloud() {
        return this.textureCloud;
    }

    /**
     * Renvoie une liste de Coordinate où le monstre peut aller
     * 
     * @return une liste de Coordinate voisines
     */
    public List<Coordinate> voisinMonster() {
        ArrayList<Coordinate> result = new ArrayList<Coordinate>();
        ICoordinate current = this.monsterPath.get(this.monsterPath.size() - 1);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Coordinate tmp = new Coordinate(current.getCol() + i, current.getRow() + j);
                if (!this.estEnDehors(tmp) && !this.isWall(tmp) && !tmp.equals(current))
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
     * @return un boolean
     */
    boolean estEnDehors(ICoordinate ic) {
        int longueur = this.wall.length;
        int largeur = this.wall[0].length;
        boolean result = true;
        if ((0 <= ic.getRow() && ic.getRow() < longueur) && (0 <= ic.getCol() && ic.getCol() < largeur)) {
            result = false;
        }
        return result;
    }

    public boolean isWall(ICoordinate ic) {
        return this.wall[ic.getRow()][ic.getCol()];
    }

    /**
     * Bouge le monstre à la coordonnée indiquée
     * 
     * @param coord
     */
    public void move(Coordinate coord) {
        this.monsterPath.put(this.monsterPath.size(), coord);
        this.tourCourant++;
        this.monsterPlay = false;
        this.notifyObservers(this.isMonsterPlay());
        // this.notifyObservers(this);
    }

    public void shoot(Coordinate coord) {
        this.allShoot.put(this.tourCourant, coord);
        this.monsterPlay = true;
        this.notifyObservers(this.isMonsterPlay());
        // this.notifyObservers(this);
    }

    /**
     * Génére une coordonnée qui n'est pas un mur
     * 
     * @return une coordonnée
     */
    private ICoordinate generateCoord() {
        ICoordinate id;
        do {
            Random rd = new Random();
            int x = rd.nextInt(wall.length);
            int y = rd.nextInt(wall[0].length);
            id = new Coordinate(x, y);
        } while (this.isWall(id));
        return id;
    }

    /**
     * Getter pour l'attribut wall
     * 
     * @return l'attribut wall
     */
    public boolean[][] getWalls() {
        return this.wall;
    }

    /**
     * Getter pour l'attribut tour
     * 
     * @return l'attribut tour
     */
    public int getTour() {
        return this.tourCourant;
    }

    /**
     * Getter pour l'attribut largeur
     * 
     * @return l'attribut largeur
     */
    public int getLargeur() {
        return this.wall.length;
    }

    /**
     * Getter pour l'attribut longueur
     * 
     * @return l'attribut longueur
     */
    public int getLongueur() {
        return this.wall[0].length;
    }

    /**
     * Getter pour l'attribut monsterPlay
     * 
     * @return l'attribut monsterPlay
     */
    protected boolean isMonsterPlay() {
        return this.monsterPlay;
    }

    /**
     * Setter pour l'attribut monsterPLay
     * 
     * @param b la nouvelle valeur
     */
    public void setMonsterPlay(boolean b) {
        this.monsterPlay = b;
    }

    /**
     * ToString du labyrinthe
     * 
     * @return Une chaîne de caractère qui représente le labyrinthe
     */
    public String toString() {
        String result = "";
        for (int i = 0; i < wall.length; i++) {
            for (int j = 0; j < wall[i].length; j++) {
                if (this.wall[i][j]) {
                    result += "X";
                } else {
                    result += ".";
                }
            }
            result += "\n";
        }
        return result;
    }
}