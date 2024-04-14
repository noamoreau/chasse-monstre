package fr.univlille.iutinfo.J5.view;

import fr.univlille.iutinfo.J5.model.Subject;

/**
 * Interface représentant un observateur, il est notifié par un sujet lorsqu'il change d'état.
 *
 */
public interface IObserver {

    /**
     * Méthode appelée lorsque l'état du sujet change.
     *
     * @param subj Le sujet qui a changé d'état.
     */
    public void update(Subject subj);

    /**
     * Méthode appelée lorsque l'état du sujet change et que des données sont fournies.
     *
     * @param subj Le sujet qui a changé d'état.
     * @param data Les données fournies par le sujet.
     */
    public void update(Subject subj, Object data);
}
