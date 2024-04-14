package fr.univlille.iutinfo.J5.controller;

import fr.univlille.iutinfo.J5.model.Coordinate;

/**
 * Interface pour les Controller Monster et Hunter
 */
public interface IController {
    /**
     * Permet de convertir des coordonnées en Coordinate
     * 
     * @param x la coordonnées x du point voulu
     * @param y la coordonnées y du point voulu
     * @return L'object Coordinate coorespondant aux coordonnées données en paramètres 
     */
    public Coordinate xyToCoordinate(int x,int y);
}
