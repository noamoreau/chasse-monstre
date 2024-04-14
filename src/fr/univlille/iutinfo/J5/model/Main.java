package fr.univlille.iutinfo.J5.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import fr.univlille.iutinfo.J5.controller.ControllerHunter;
import fr.univlille.iutinfo.J5.controller.ControllerMonster;
import fr.univlille.iutinfo.J5.view.ViewHunterMaze;
import fr.univlille.iutinfo.J5.view.ViewMonsterMaze;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Classe principale de l'application "Chasse aux monstres"
 *
 */
public class Main extends Application {

    /**
     * Le labyrinthe dans lequel se déroule la chasse.
     */
    Maze maze;
    /**
     * La vue pour le chasseur
     */
    static ViewHunterMaze vh;
    /**
     * La vue pour le monstre
     */
    static ViewMonsterMaze vm;
    /**
     * Permet l'affichage
     */
    static Stage st;
    /**
     * Fileseparator pour la compatibilité avec les différents OS
     */
    private static final String FILESEPARATOR = File.separator;

    private static final CheckBox cb = new CheckBox("Voir tout le plateau");

    final FileChooser fileChooser = new FileChooser();

    static public File file = null;

    /**
     * Crée, instantie et gére l'intérieur de l'application.
     *
     * @param stage La scène principale de l'application.
     */
    @Override
    public void start(Stage stage) throws Exception {
        ArrayList<Double> params = loadSettings();

        // Initialisations
        TabPane tp = new TabPane();
        Tab t1 = new Tab();
        Tab t2 = new Tab();

        AnchorPane ap1 = new AnchorPane();
        AnchorPane ap2 = new AnchorPane();

        VBox v1 = new VBox();

        Label l = new Label("Chasse aux monstres");

        Button b1 = new Button("Jouer 1v1");
        Button b2 = new Button("Jouer le monstre contre l'ordinateur");

        Button b3 = new Button("Jouer le chasseur contre l'ordinateur");

        Button b4 = new Button("IA v IA");

        Button exit = new Button("Quitter le jeu");

        VBox v2 = new VBox();

        Label l1 = new Label("Largeur du labyrinthe");
        Slider largeur = new Slider(6, 30, params.get(0));
        largeur.setMajorTickUnit(2);
        largeur.setShowTickMarks(true);
        largeur.setShowTickLabels(true);
        Label l2 = new Label("Longueur du labyrinthe");
        Slider longueur = new Slider(6, 30, params.get(1));
        longueur.setMajorTickUnit(2);
        longueur.setShowTickMarks(true);
        longueur.setShowTickLabels(true);
        Label l3 = new Label("Taux de murs");
        Slider murs = new Slider(10, 75, params.get(2));
        murs.setMajorTickUnit(5);
        murs.setShowTickMarks(true);
        murs.setShowTickLabels(true);
        Label l4 = new Label("Vision du monstres (cases)");
        HBox hbox = new HBox();
        Slider vision;
        if (params.get(3) == -1) {
            vision = new Slider(2, 6, 3);
            cb.setSelected(true);
        } else {
            vision = new Slider(2, 6, params.get(3));
            cb.setSelected(false);
        }
        hbox.getChildren().addAll(vision, cb);

        Button b6 = new Button("Choisir fichier");

        Button b5 = new Button("Sauvegarder les paramètres");
        // Actions
        b1.setOnMouseClicked(e -> {
            ViewHunterMaze.bot = false;
            ViewMonsterMaze.bot = false;
            play();
            stage.hide();
        });
        b2.setOnMouseClicked(e -> {
            ViewHunterMaze.bot = true;
            play();
            stage.hide();
        });
        b3.setOnMouseClicked(e -> {
            ViewMonsterMaze.bot = true;
            play();
            stage.hide();
        });
        b4.setOnMouseClicked(e -> {
            ViewHunterMaze.bot = true;
            ViewMonsterMaze.bot = true;
            play();
            stage.hide();
        });
        b5.setOnMouseClicked(e -> {
            saveSettings(largeur.getValue(), longueur.getValue(), murs.getValue(), vision.getValue(), cb.isSelected());
        });

        b6.setOnMouseClicked(e -> {
            file = fileChooser.showOpenDialog(stage);

        });

        exit.setOnMouseClicked(e -> {
            System.exit(0);
        });

        // Styles des objets
        l.setStyle("-fx-font-size: 35px;");
        v1.setPadding(new Insets(25));
        v1.setSpacing(15);
        v2.setPadding(new Insets(25));
        v2.setSpacing(15);
        v1.setAlignment(Pos.CENTER);
        v2.setAlignment(Pos.CENTER);
        // Ajout sur la page
        v1.getChildren().addAll(l, b1, b2, b3, b4, exit);
        v2.getChildren().addAll(l1, largeur, l2, longueur, l3, murs, l4, hbox, b5, b6);

        ap1.getChildren().add(v1);
        t1.setText("Menu Principal");
        t1.setClosable(false);
        t1.setContent(ap1);

        ap2.getChildren().add(v2);
        t2.setText("Paramètres");
        t2.setClosable(false);
        t2.setContent(ap2);

        tp.getTabs().addAll(t1, t2);
        Scene scene = new Scene(tp);
        stage.setScene(scene);
        stage.setTitle("Chasse aux monstres - Menu Principal");
        stage.show();
        st = stage;
    }

    /**
     * Mets fin à l'application
     */
    public static void end() {
        vm.close();
        vh.close();
        st.show();
    }

    /**
     * Affiche une alerte pour annoncer la fin du tour
     * 
     * @param boolean monsterplay
     */
    public static void alert(boolean monsterplay) {
        vm.hide();
        vh.hide();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Monstre");
        alert.setHeaderText("Vous avez joué. Cliquez sur OK pour afficher le monstre");
        alert.setOnCloseRequest(e -> {
            if (monsterplay) {
                vm.show();
            } else {
                vh.show();
            }
        });
        alert.showAndWait();
    }

    /**
     * Sauvegarde dans un fichier les paramètres
     * 
     * @param largeur
     * @param longueur
     * @param murs
     * @param vision
     * @param checkbox
     */
    private static void saveSettings(double largeur, double longueur, double murs, double vision, boolean checkbox) {
        try {
            File f = new File("." + FILESEPARATOR + "res" + FILESEPARATOR + "settings");
            FileWriter writer = new FileWriter(f);
            int intlongueur = (int) (longueur - 1);
            int intlargeur = (int) (largeur - 1);
            int intvision = -1;
            if (!checkbox) {
                intvision = (int) (vision - 1);
            }
            writer.append(intlargeur + " " + intlongueur + " " + murs + " " + intvision);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Charge les paramètres
     * 
     * @return une liste de doubles
     */
    private static ArrayList<Double> loadSettings() {
        ArrayList<Double> result = new ArrayList<Double>();
        try {
            Scanner sc = new Scanner(new File("." + FILESEPARATOR + "res" + FILESEPARATOR + "settings"));
            String[] line = sc.nextLine().split(" ");
            sc.close();
            for (int i = 0; i < line.length; i++) {
                result.add(Double.parseDouble(line[i]));
            }
        } catch (FileNotFoundException e) {
            ArrayList<Double> standard = new ArrayList<>();
            standard.add(15.0);
            standard.add(15.0);
            standard.add(50.0);
            standard.add(3.0);
            return standard;
        }
        return result;
    }

    /**
     * Fonction qui lance le jeu joueur contre joueur
     * 
     * @param longueur
     * @param largeur
     * @param murs
     * @param vision
     */
    public static void play() {
        // Récuperer les paramètres
        ArrayList<Double> params = loadSettings();
        int largeurint = params.get(0).intValue();
        int longueurint = params.get(1).intValue();
        double mursvalue = params.get(2);
        int visionint = params.get(3).intValue();
        // 5 5 30 -> 30 30 90
        Maze model = null;
        if (file == null) {
            Maze.generateMaze(longueurint, largeurint, mursvalue);
            model = new Maze("." + FILESEPARATOR + "res" + FILESEPARATOR + "LabyrintheTmp.csv");
        } else {
            model = new Maze(file.getPath());
        }
        // Maze.generateMaze(30,30,30);
        // Labyrinthe aléatoire

        // Labyrinthe prédéfinie
        // Maze model = new Maze("." + FILESEPARATOR + "res" + FILESEPARATOR +
        // "labyrinthe.csv");

        ControllerHunter ch = new ControllerHunter(model);
        ControllerMonster cm = new ControllerMonster(model);
        Screen screen = Screen.getPrimary();
        Rectangle2D r = screen.getBounds();

        double sclargeur = r.getWidth();
        double sclongeur = r.getHeight();
        vh = new ViewHunterMaze(model, ch);
        vh.setX(sclargeur / 2 - vh.getWidth());
        vh.setY(sclongeur / 2);
        vh.setResizable(false);
        vm = new ViewMonsterMaze(model, cm, visionint);
        vm.setResizable(false);
        vm.setX(sclargeur / 2 + vm.getWidth());
        vm.setY(sclongeur / 2);

        if (cb.isSelected()) {
            vm.brouillard = false;
        } else {
            vm.brouillard = true;
        }

        model.attach(vh);
        model.attach(vm);

    }

    /**
     * Point de départ de l'application.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        launch();
    }
}
