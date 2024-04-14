package fr.univlille.iutinfo.J5.view;

import fr.univlille.iutinfo.J5.ai.HunterStrategyRandom;
import fr.univlille.iutinfo.J5.controller.ControllerHunter;
import fr.univlille.iutinfo.J5.model.Coordinate;
import fr.univlille.iutinfo.J5.model.Main;
import fr.univlille.iutinfo.J5.model.Maze;
import fr.univlille.iutinfo.J5.model.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * La vue du chasseur
 */
public class ViewHunterMaze extends Stage implements IObserver {

    /**
     * Le modèle du labyrinthe pour le chasseur.
     */
    protected Maze model;
    /**
     * Le controlleur du chasseur
     */
    protected ControllerHunter controller;
    GridPane gridpane;
    Image wall;
    Image ground;
    Image door;
    Image cloud;

    /**
     * VBox pour JavaFx
     */
    private VBox root = new VBox();

    public static boolean bot = false;
    Label l;

    /**
     * Constructeur de la vue du chasseur
     * 
     * @param model      Le model de la vue du chasseur
     * @param controller Le controlleur de la vue du chasseur
     */
    public ViewHunterMaze(Maze model, ControllerHunter controller) {
        this.model = model;
        this.controller = controller;
        wall = new Image("file:" + this.model.getTextureWall().getPath());
        ground = new Image("file:" + this.model.getTextureGround().getPath());
        door = new Image("file:" + this.model.getTextureDoor().getPath());
        cloud = new Image("file:" + this.model.getTextureCloud().getPath());

        this.model.attach(this);
        this.start();
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
            Boolean monsterturn = (Boolean) data;
            if (!monsterturn && !this.isShowing())
                Main.alert(monsterturn);
        } else if (data instanceof Integer) {
            Integer tour = (Integer) data;
            this.l.setText("Tour courant : " + tour);
        } else if (data instanceof Maze) {
            Maze maze = (Maze) data;
            this.root.getChildren().removeAll(this.root.getChildren());
            gridpane = new GridPane();
            HunterStrategyRandom ia = new HunterStrategyRandom();
            ia.initialize(this.model.getLongueur(), this.model.getLargeur());
            l = new Label("Tour courant : " + maze.getTour());
            for (int i = 0; i < maze.getLongueur(); i++) {
                for (int j = 0; j < maze.getLargeur(); j++) {
                    Rectangle r = new Rectangle(20, 20);
                    r.setFill(new ImagePattern(cloud));
                    r.setOnMouseClicked(e -> {
                        if (!this.model.monsterPlay) {
                            if (bot) {
                                action((Coordinate) ia.play(), r);
                            } else {
                                action(new Coordinate(GridPane.getColumnIndex(r), GridPane.getRowIndex(r)), r);
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
     * Affiche le labyrinthe du point de vue du chasseur.
     */
    public void start() {
        VBox root = new VBox();
        gridpane = new GridPane();
        HunterStrategyRandom ia = new HunterStrategyRandom();
        ia.initialize(this.model.getLongueur(), this.model.getLargeur());
        l = new Label("Tour courant : " + this.model.getTour());

        for (int i = 0; i < this.model.getLongueur(); i++) {
            for (int j = 0; j < this.model.getLargeur(); j++) {
                Rectangle r = new Rectangle(20, 20);
                r.setFill(new ImagePattern(cloud));
                r.setStroke(Color.BLACK);
                r.setOnMouseClicked(e -> {
                    if (!this.model.monsterPlay) {
                        if (bot) {
                            action((Coordinate) ia.play(), r);
                        } else {
                            action(new Coordinate(GridPane.getColumnIndex(r), GridPane.getRowIndex(r)), r);
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
        this.setTitle("Vue du chasseur");
        this.show();
    }

    public void action(Coordinate coord, Rectangle r) {
        ICellEvent cell = controller.getState(coord);
        CellInfo status = cell.getState();

        if (status == CellInfo.MONSTER) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Bravo");
            alert.setHeaderText("Chasseur a gagné");
            alert.setOnCloseRequest(end -> {
                Main.end();
            });
            alert.showAndWait();
        } else if (status == CellInfo.WALL) {
            ImagePattern ip = new ImagePattern(wall);
            r.setFill(ip);
            try {
                gridpane.add(r, cell.getCoord().getCol(), cell.getCoord().getRow());
            } catch (Exception ex) {
            }

        } else if (status == CellInfo.EXIT) {
            ImagePattern ip = new ImagePattern(door);
            r.setFill(ip);
            try {
                gridpane.add(r, cell.getCoord().getCol(), cell.getCoord().getRow());
            } catch (Exception ex) {

            }
        } else if (status == CellInfo.EMPTY) {
            ImagePattern ip = new ImagePattern(ground);
            r.setFill(ip);
            if (cell.getTurn() > 0) {
                StackPane sp = new StackPane();
                Text txt = new Text("" + cell.getTurn());
                sp.getChildren().addAll(r, txt);
                try {
                    gridpane.add(sp, cell.getCoord().getCol(), cell.getCoord().getRow());
                    gridpane.add(sp, cell.getCoord().getCol(), cell.getCoord().getRow());
                } catch (Exception ex) {
                    // TODO: handle exception
                }

            } else {
                try {
                    gridpane.add(r, cell.getCoord().getCol(), cell.getCoord().getRow());
                } catch (Exception ex) {
                    // TODO: handle exception
                }

            }
        }
        this.model.setMonsterPlay(true);
        this.model.shoot(coord);
    }
}
