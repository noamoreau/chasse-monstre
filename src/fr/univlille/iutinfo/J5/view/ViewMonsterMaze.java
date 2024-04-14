package fr.univlille.iutinfo.J5.view;

import java.util.ArrayList;

import fr.univlille.iutinfo.J5.ai.MonsterStrategyRandom;
import fr.univlille.iutinfo.J5.controller.ControllerMonster;
import fr.univlille.iutinfo.J5.model.Coordinate;
import fr.univlille.iutinfo.J5.model.Main;
import fr.univlille.iutinfo.J5.model.Maze;
import fr.univlille.iutinfo.J5.model.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * La vue du monstre
 */
public class ViewMonsterMaze extends Stage implements IObserver {

    /**
     * Le modèle du labyrinthe pour le monstre
     */
    private Maze model;
    /**
     * Le controlleur du monstre
     */
    private ControllerMonster controller;
    /**
     * Les informations de la dernière case, initialisé à null
     * Pour JavaFx
     */
    private Rectangle lastCase = null;
    /**
     * Les informations de la dernière coordonnée, initialisé à null
     */
    private Coordinate lastCoordinate = null;
    /**
     * VBox pour JavaFx
     */
    private VBox root = new VBox();
    /**
     * GridPane pour JavaFx
     */
    private GridPane gridpane = new GridPane();

    Label l;

    public boolean brouillard;

    private boolean[][] vision;

    /**
     * Constructeur de la vue du monstre
     * 
     * @param model  Le model de la vue du monstre
     * @param cm     Le controlleur de la vue du monstre
     * @param vision La taille de visibilité du monstre
     */

    public static boolean bot = false;

    public ViewMonsterMaze(Maze model, ControllerMonster cm, int vision) {
        this.model = model;
        this.vision = new boolean[this.model.getLongueur()][this.model.getLargeur()];
        for (boolean[] bs : this.vision) {
            for (boolean b : bs) {
                b = false;
            }
        }
        this.controller = cm;
        this.model.attach(this);
        this.start();
    }

    private void updateBrouillard() {
        Coordinate monster = (Coordinate) this.model.monsterPath.get(this.model.monsterPath.size() - 1);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    this.vision[monster.row + i][monster.col + j] = true;
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Mets à jour l'affichage du labyrinthe en fonction de l'état du modèle
     */
    @Override
    public void update(Subject subj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    /**
     * Mets à jour l'affichage du labyrinthe en fonction de l'état du modèle et des
     * données fournies
     */
    @Override
    public void update(Subject subj, Object data) {
        if (data instanceof Boolean) {
            Boolean monsterplay = (Boolean) data;
            if (monsterplay && !this.isShowing()) {
                Main.alert(monsterplay);
            }
        } else if (data instanceof Integer) {
            Integer tour = (Integer) data;
            this.l.setText("Tour courant : " + tour);
        } else if (data instanceof Maze) {
            Maze maze = (Maze) data;
            this.root.getChildren().removeAll(this.root.getChildren());
            Image wall = new Image("file:" + this.model.getTextureWall().getPath());
            Image ground = new Image("file:" + this.model.getTextureGround().getPath());
            Image monster = new Image("file:" + this.model.getTextureMonster().getPath());
            Image door = new Image("file:" + this.model.getTextureDoor().getPath());
            Image cloud = new Image("file:" + this.model.getTextureCloud().getPath());
            MonsterStrategyRandom ia = new MonsterStrategyRandom();
            l = new Label("Tour courrant : " + maze.getTour());
            for (int i = 0; i < maze.getLongueur(); i++) {
                for (int j = 0; j < maze.getLargeur(); j++) {
                    boolean tmp = maze.getWalls()[i][j];
                    Rectangle r = new Rectangle(20, 20);
                    int col = j;
                    int row = i;
                    if (!this.brouillard) {
                        if (tmp) {
                            r.setFill(new ImagePattern(wall));
                        } else if (col == maze.exit.getCol() && row == maze.exit.getRow()) {
                            r.setFill(new ImagePattern(door));
                        } else if (col == maze.monsterPath.get(maze.monsterPath.size() - 1).getCol()
                                && row == maze.monsterPath.get(maze.monsterPath.size() - 1).getRow()) {
                            r.setFill(new ImagePattern(monster));
                            lastCase = r;
                            lastCoordinate = new Coordinate(
                                    maze.monsterPath.get(maze.monsterPath.size() - 1).getCol(),
                                    maze.monsterPath.get(maze.monsterPath.size() - 1).getCol());
                        } else {
                            r.setFill(new ImagePattern(ground));
                        }
                    } else {
                        this.updateBrouillard();
                        if (tmp && this.vision[i][j]) {
                            r.setFill(new ImagePattern(wall));
                        } else if (col == maze.exit.getCol() && row == maze.exit.getRow()
                                && this.vision[i][j]) {
                            r.setFill(new ImagePattern(door));
                        } else if (col == maze.monsterPath.get(maze.monsterPath.size() - 1).getCol()
                                && row == maze.monsterPath.get(maze.monsterPath.size() - 1).getRow()
                                && this.vision[i][j]) {
                            r.setFill(new ImagePattern(monster));
                            lastCase = r;
                            lastCoordinate = new Coordinate(
                                    maze.monsterPath.get(maze.monsterPath.size() - 1).getCol(),
                                    maze.monsterPath.get(maze.monsterPath.size() - 1).getCol());
                        } else if (this.vision[i][j]) {
                            r.setFill(new ImagePattern(ground));
                        } else {
                            r.setFill(new ImagePattern(cloud));
                        }
                    }
                    r.setOnMouseClicked(e -> {
                        if (maze.monsterPlay) {
                            Coordinate coord;
                            if (bot) {
                                coord = (Coordinate) ia.play();
                            } else {
                                coord = new Coordinate(GridPane.getColumnIndex(r), GridPane.getRowIndex(r));
                            }
                            if (maze.voisinMonster().contains(coord)) {
                                if (coord.getCol() == maze.exit.getCol()
                                        && coord.getRow() == maze.exit.getRow()) {
                                    Alert alert = new Alert(AlertType.CONFIRMATION);
                                    alert.setTitle("Bravo");
                                    alert.setHeaderText("Monstre a gagné");
                                    alert.setOnCloseRequest(end -> {
                                        Main.end();
                                    });
                                    alert.showAndWait();
                                }
                                maze.move(coord);
                                r.setFill(new ImagePattern(monster));
                                if (lastCase != null) {
                                    lastCase.setFill(new ImagePattern(ground));
                                    gridpane.getChildren().remove(lastCase);
                                    gridpane.add(lastCase, lastCoordinate.col, lastCoordinate.row);
                                }
                                lastCase = r;
                                lastCoordinate = coord;
                                maze.setMonsterPlay(false);
                            }
                        }
                    });
                    gridpane.add(r, j, i);
                }
            }

            VBox vbox = new VBox();
            Button menu = new Button("Retourner au menu");
            menu.setOnMouseClicked(e -> {
                Main.end();
            });
            Button exit = new Button("Fermer le jeu");
            exit.setOnMouseClicked(e -> {
                System.exit(0);
            });
            vbox.getChildren().addAll(menu, exit);
            root.getChildren().addAll(l, gridpane, vbox);
        }
    }

    /**
     * Renvoie la List des cases autour du monstre en fonction
     * 
     * @param nb La taille de la visibilité du monstre
     * @return une Liste de Rectangle qui sont les cases autour du monstre en
     *         fonction de la visibilité
     */
    public ArrayList<Rectangle> aroundMonster(int nb) {
        ICoordinate coordinateMonster = this.model.monsterPath.get(this.model.monsterPath.size() - 1);

        ArrayList<Rectangle> result = new ArrayList<Rectangle>();
        for (int x = coordinateMonster.getRow() - nb; x < coordinateMonster.getRow() + nb; x++) {
            for (int y = coordinateMonster.getCol() - nb; y < coordinateMonster.getCol() + nb; y++) {
                if (x < this.model.getLongueur() && x >= 0 && y < this.model.getLargeur() && y >= 0) {
                    result.add(getRectangleFromGridPane(x, y));
                }
            }
        }

        return result;
    }

    /**
     * Renvoie une case sous forme de Rectangle en fonction des coordonnées donné
     * 
     * @param col La coordonnée x du labyrinthe
     * @param row La coordonnée y du labyrinthe
     * @return Un Rectangle correspondant aux coordonnées
     */
    public Rectangle getRectangleFromGridPane(int col, int row) {
        for (Node node : gridpane.getChildren()) {
            if (node instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) node;
                if (GridPane.getColumnIndex(rectangle) == col && GridPane.getRowIndex(rectangle) == row) {
                    return rectangle;
                }
            }
        }
        return null; // Return null if no rectangle is found at the specified coordinates
    }

    /**
     * Mets à jour l'apparence en fonction de la liste des Rectangles reçus
     * 
     * @param rectangles L'intégralité des Rectangles représente ce que le monstre
     *                   peux voir
     */
    public void apparenceUpdate(ArrayList<Rectangle> rectangles) {
        Image cloud = new Image("file:" + this.model.getTextureCloud().getPath());
        for (Node node : gridpane.getChildren()) {
            if (node instanceof Rectangle) {
                Rectangle r = (Rectangle) node;
                if (rectangles.contains(r)) {

                } else {
                    r.setFill(new ImagePattern(cloud));
                }

            }
        }
    }

    /**
     * Affiche le labyrinthe du point de vue du chasseur.
     */
    public void start() {
        Image wall = new Image("file:" + this.model.getTextureWall().getPath());
        Image ground = new Image("file:" + this.model.getTextureGround().getPath());
        Image monster = new Image("file:" + this.model.getTextureMonster().getPath());
        Image door = new Image("file:" + this.model.getTextureDoor().getPath());
        Image cloud = new Image("file:" + this.model.getTextureCloud().getPath());
        MonsterStrategyRandom ia = new MonsterStrategyRandom();
        ia.initialize(this.model.getWalls());
        ia.getEntryMonster(model);
        l = new Label("Tour courrant : " + this.model.getTour());
        for (int i = 0; i < this.model.getLongueur(); i++) {
            for (int j = 0; j < this.model.getLargeur(); j++) {
                boolean tmp = this.model.getWalls()[i][j];
                Rectangle r = new Rectangle(20, 20);
                int col = j;
                int row = i;
                if (!this.brouillard) {
                    if (tmp) {
                        r.setFill(new ImagePattern(wall));
                    } else if (col == this.model.exit.getCol() && row == this.model.exit.getRow()) {
                        r.setFill(new ImagePattern(door));
                    } else if (col == this.model.monsterPath.get(this.model.monsterPath.size() - 1).getCol()
                            && row == this.model.monsterPath.get(this.model.monsterPath.size() - 1).getRow()) {
                        r.setFill(new ImagePattern(monster));
                        lastCase = r;
                        lastCoordinate = new Coordinate(
                                this.model.monsterPath.get(this.model.monsterPath.size() - 1).getCol(),
                                this.model.monsterPath.get(this.model.monsterPath.size() - 1).getCol());
                    } else {
                        r.setFill(new ImagePattern(ground));
                    }
                } else {
                    this.updateBrouillard();
                    if (tmp && this.vision[i][j]) {
                        r.setFill(new ImagePattern(wall));
                    } else if (col == this.model.exit.getCol() && row == this.model.exit.getRow()
                            && this.vision[i][j]) {
                        r.setFill(new ImagePattern(door));
                    } else if (col == this.model.monsterPath.get(this.model.monsterPath.size() - 1).getCol()
                            && row == this.model.monsterPath.get(this.model.monsterPath.size() - 1).getRow()
                            && this.vision[i][j]) {
                        r.setFill(new ImagePattern(monster));
                        lastCase = r;
                        lastCoordinate = new Coordinate(
                                this.model.monsterPath.get(this.model.monsterPath.size() - 1).getCol(),
                                this.model.monsterPath.get(this.model.monsterPath.size() - 1).getCol());
                    } else if (this.vision[i][j]) {
                        r.setFill(new ImagePattern(ground));
                    } else {
                        r.setFill(new ImagePattern(cloud));
                    }
                }
                r.setOnMouseClicked(e -> {
                    if (this.model.monsterPlay) {
                        Coordinate coord;
                        if (bot) {
                            coord = (Coordinate) ia.play();
                        } else {
                            coord = new Coordinate(GridPane.getColumnIndex(r), GridPane.getRowIndex(r));
                        }
                        if (this.model.voisinMonster().contains(coord)) {
                            if (coord.getCol() == this.model.exit.getCol()
                                    && coord.getRow() == this.model.exit.getRow()) {
                                Alert alert = new Alert(AlertType.CONFIRMATION);
                                alert.setTitle("Bravo");
                                alert.setHeaderText("Monstre a gagné");
                                alert.setOnCloseRequest(end -> {
                                    Main.end();
                                });
                                alert.showAndWait();
                            }
                            this.model.move(coord);
                            r.setFill(new ImagePattern(monster));
                            if (lastCase != null) {
                                lastCase.setFill(new ImagePattern(ground));
                                gridpane.getChildren().remove(lastCase);
                                gridpane.add(lastCase, lastCoordinate.col, lastCoordinate.row);
                            }
                            lastCase = r;
                            lastCoordinate = coord;
                            this.model.setMonsterPlay(false);
                        }
                    }
                });
                gridpane.add(r, j, i);
            }
        }

        VBox vbox = new VBox();
        Button menu = new Button("Retourner au menu");
        menu.setOnMouseClicked(e -> {
            Main.end();
        });
        Button exit = new Button("Fermer le jeu");
        exit.setOnMouseClicked(e -> {
            System.exit(0);
        });
        vbox.getChildren().addAll(menu, exit);
        root.getChildren().addAll(l, gridpane, vbox);
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("Vue du monstre");
    }
}
