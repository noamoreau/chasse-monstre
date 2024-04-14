package fr.univlille.iutinfo.J5.model;

import java.util.ArrayList;
import java.util.List;

import fr.univlille.iutinfo.J5.view.IObserver;

/**
 * Classe abstraite représentant un sujet qui peut être observé par d'autres objets, les observateurs.
 * Lorsque l'état du sujet change, il notifie ses observateurs afin qu'ils puissent se mettre à jour.
 *
 */
public abstract class Subject {

    /**
     * Liste des observateurs attachés à ce sujet.
     */
    protected List<IObserver> attached;

    protected Subject() {
        attached = new ArrayList<>();
    }

    /**
     * Attache un observateur à ce sujet.
     *
     * @param obs L'observateur à attacher.
     */
    public void attach(IObserver obs){
      this.attached.add(obs);
    }

    /**
     * Détache un observateur de ce sujet.
     *
     * @param obs L'observateur à détacher.
     */
    public void detach(IObserver obs){
      this.attached.remove(obs);
    }

    /**
     * Notifie tous les observateurs attachés à ce sujet que son état a changé.
     */
    public void notifyObservers(){
        for (IObserver observer : attached) {
			observer.update(this);
		}
    }

    /**
     * Notifie tous les observateurs attachés à ce sujet que son état a changé et leur fournit des données.
     *
     * @param data Les données à fournir aux observateurs.
     */
    public void notifyObservers(Object data){
        for (IObserver observer : attached) {
			observer.update(this, data);
		}
    }
}
