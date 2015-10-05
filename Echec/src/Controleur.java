import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nicolas on 06/07/15.
 */
public class Controleur {
    private Joueur joueur1;
    private Joueur joueur2;

    public Controleur(){
        joueur1 = new Joueur();
        joueur2 = new Joueur();

        //permet de modifier les possibilites de déplacements
        for(int i = 0; i < joueur1.getMesPieces().size(); i++){
            this.deplacerPiece(i, joueur1.getMesPieces().get(i).getX(), joueur1.getMesPieces().get(i).getY());
        }
        this.changerJoueur();

        for(int i = 0; i < joueur2.getMesPieces().size(); i++){
            this.deplacerPiece(i, joueur2.getMesPieces().get(i).getX(), joueur2.getMesPieces().get(i).getY());
        }
        this.changerJoueur();
    }


    /***w
     *
     * Le Roi est en échec si le joueur adverse
     * peut attaquer le roi direcement à son
     * prochain tour
     *
     */
    public boolean joueurActuelEchec(){
        for (int i = 0; i < this.getJoueur(this.getJoueurActuel()).getMesPieces().size(); i++){
            if(this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getClass().getName() == "Roi"){
                for (int j = 0; j < this.getJoueurAdverse().getMesPieces().size(); j++){
                    if(this.getJoueurAdverse().getMesPieces().get(j).deplacementContient(this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getX(),this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getY()))
                        return true;
                }
            }
        }
        return false;
    }


    /***
     *
     * L'échec et mat provoque la fin du jeu.
     * Pour ça, il faut :
     *  - Si le roi soit en échec
     *  - Pour chaque déplacement du Roi
     *    - Etudier le nouveau déplacement de chaque pièce
     *      - Si cette pièce peut accéder au déplacement du Roi
     *        - Une case de plus est attaquable
     *    - Si toutes les cases du Roi sont attaquable, il est en échec et mat
     *
     */
    public boolean joueurActuelEchecEtMat(){
        //si le joueur adverse peut avoir le roi à sa position actuelle
        if(this.joueurActuelEchec()) {
            //choix de base pour le roi, mais on va le modifier
            int choixPossible = 8;
            ArrayList<Case> caseAttaquable = new ArrayList<>();

            for (int i = 0; i < this.getJoueur(this.getJoueurActuel()).getMesPieces().size(); i++) {
                //on recherche le roi parmis les pièces
                if (this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getClass().getName() == "Roi") {
                    choixPossible = this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getChoix().size();

                    ArrayList<Case> deplacements = this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getChoix();

                    int xDebut = this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getX();
                    int yDebut = this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getY();

                    //on parcourt ses déplacements possibles
                    for (int j = 0; j < deplacements.size(); j++) {
                        int xRoi = deplacements.get(j).getX();
                        int yRoi = deplacements.get(j).getY();

                        this.deplacerPiece(i, xRoi, yRoi);

                        System.out.println("Déplacement " + j + " : " + xRoi + "-" + yRoi);
                        //on parcourt les pièces du joueur adverse
                        for(int k = 0; k < this.getJoueurAdverse().getMesPieces().size(); k++){
                            if(this.getJoueurAdverse().getMesPieces().get(k).deplacementContient(xRoi,yRoi)) {
                                System.out.println("\tAjout : " + xRoi + "-" + yRoi);

                                boolean existeDeja = false;
                                for(int l = 0; l < caseAttaquable.size(); l++) {
                                    if ((caseAttaquable.get(i).getX() == xRoi) && (caseAttaquable.get(i).getX() == yRoi)){
                                        existeDeja = true;
                                        break;
                                    }
                                }
                                if(!existeDeja)
                                    caseAttaquable.add(deplacements.get(j));
                            }
                        }
                    }

                    this.deplacerPiece(i, xDebut, yDebut);
                }
            }

            System.out.println(caseAttaquable.size() + " - " + choixPossible);

            //en théorie il est en échec et mat. Maintenant on va tester si une pièce z
                if(caseAttaquable.size() >= choixPossible)
                return true;
        }

        return false;
    }

    public Joueur getJoueur(int num){
        if(num == 1)
            return joueur1;
        else if(num == 2)
            return joueur2;
        else
            return null;
    }

    public int getJoueurActuel(){
        if(joueur1.sonTour())
            return 1;
        else if(joueur2.sonTour())
            return 2;
        else
            return 0;
    }

    public Joueur getJoueurAdverse(){
        if(joueur1.sonTour())
            return joueur2;
        else if(joueur2.sonTour())
            return joueur1;
        else
            return null;
    }

    public void changerJoueur(){
        joueur1.tourSuivant();
        joueur2.tourSuivant();
    }

    public ArrayList<Case> afficherPossibilites(int x, int y){

        //on parcourt les pièce du joueur en cours
        for(int i = 0; i < this.getJoueur(this.getJoueurActuel()).getMesPieces().size(); i++) {
            //on regarde s'il y a quelque chose à afficher
            if ((this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getX() == x) && (this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getY() == y)) {
                //la liste à retourner prend l'ensemble des choix du joueur
                return this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getChoix();
            }
        }
        return null;
    }

    public boolean caseVide(int x, int y){
        return !(joueur1.occupeCase(x, y) || joueur2.occupeCase(x,y));
    }


    /**
     * Je créer un algorithme par type de pièce
     * car pour certaines il est plus ou moins facile
     * de retrouver leurs déplacement (boucle ou non, direction, ...).
     * Le pion est traité à part !
     */
    private void modifierPossibilites(int id, int x, int y){
        for(int j  = 0; j < this.getJoueur(this.getJoueurActuel()).getMesPieces().size(); j++){
             for(int i = 0; i < this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().size(); i++){
                if((this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().get(i).getX() == this.getJoueur(this.getJoueurActuel()).getMesPieces().get(j).getX()) && (this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().get(i).getY() == this.getJoueur(this.getJoueurActuel()).getMesPieces().get(j).getY())){
                    this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().remove(i);
                    break;
                }
            }
        }

        String type = this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getClass().getName();

        //on déplace la pièce à l'endroit où elle est.
        //où est la logique ?!
        //la fonction de déplacement appelles une fonction privé qui gère les choix des pièces
        this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).deplacer(x,y);

        //on étudie leur choix un par un
        if(type == "Cavalier"){
            //on test chacun de ses choix ...
            for(int i = 0; i<this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().size(); i++){
                //... avec les autres pièces de son équipe
                for(int j = 0; j<this.getJoueur(this.getJoueurActuel()).getMesPieces().size(); j++){
                    if((this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().get(i).getX() == this.getJoueur(this.getJoueurActuel()).getMesPieces().get(j).getX()) && (this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().get(i).getY() == this.getJoueur(this.getJoueurActuel()).getMesPieces().get(j).getY())){
                        this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().remove(i);
                        i--;
                        j = 0;
                        break;
                    }
                }
            }
        }
        else if(type == "Roi"){
            //on test chacun de ses choix ...
            for(int i = 0; i<this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().size(); i++){
                //... avec les autres pièces de son équipe
                for(int j = 0; j<this.getJoueur(this.getJoueurActuel()).getMesPieces().size(); j++){
                    if((this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().get(i).getX() == this.getJoueur(this.getJoueurActuel()).getMesPieces().get(j).getX()) && (this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().get(i).getY() == this.getJoueur(this.getJoueurActuel()).getMesPieces().get(j).getY())){
                        this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().remove(i);
                        i--;
                        j = 0;
                        break;
                    }
                }
            }

            //ROQUE
            //on test chacun de ses choix ...
            Roi roi = (Roi) this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id);
            if(roi.peutRoque()) {
                //... avec les autres pièces de son équipe
                for (int j = 0; j < this.getJoueur(this.getJoueurActuel()).getMesPieces().size(); j++) {
                    if (this.getJoueur(this.getJoueurActuel()).getMesPieces().get(j).getClass().getName() == "Tour") {
                        Tour tour = (Tour) this.getJoueur(this.getJoueurActuel()).getMesPieces().get(j);
                        if(tour.peutRoque()){
                            switch (tour.getX()){
                                case 0:
                                    if(this.caseVide(roi.getX()-1, roi.getY()) && this.caseVide(roi.getX()-2, roi.getY()) )
                                        this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().add( new Case(roi.getX()-2, roi.getY()) );

                                    break;

                                case 7:
                                    if(this.caseVide(roi.getX() + 1, roi.getY()) && this.caseVide(roi.getX()+2, roi.getY()))
                                        this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().add( new Case(roi.getX()+2, roi.getY()) );
                                    break;
                            }
                        }
                    }
                }
            }


            // VERIFIE QU'IL NE PUISSE PAS SE METTRE EN ECHEC
            //on test chacun de ses choix ...
            for(int i = 0; i<this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().size(); i++){
                //... avec les pièces adverse ...
                for(int j = 0; j<this.getJoueurAdverse().getMesPieces().size(); j++){
                    boolean supprimerChoix = false;
                    // ... pour chacun de leur déplacement
                    for(int k = 0; k < this.getJoueurAdverse().getMesPieces().get(j).getChoix().size(); k++) {
                        //si le roi peut être en échec
                        if((this.getJoueurAdverse().getMesPieces().get(j).getChoix().get(k).getX() == this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().get(i).getX()) && (this.getJoueurAdverse().getMesPieces().get(j).getChoix().get(k).getY() == this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().get(i).getY())){
                            this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().remove(i);
                            supprimerChoix = true;
                            k = 0;
                            break;
                        }
                    }
                    if(supprimerChoix){
                        /***
                         * On a supprimé une pièce
                         * il faut donc selectionner celle qui
                         * l'a remplacé dans la ArrayList,
                         * et re-tester toutes les pièces adverses
                         */
                        i--;
                        j = 0;
                        break;
                    }
                }
            }
        }
        else if(this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getClass().getName() == "Pion"){
            Pion p = (Pion) this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id);

            for(int j = 0; j < p.getChoix().size(); j++){
                //(on vérifie pas manger verticalement)
                //... une pièce adverse ...
                for (int k = 0; k < this.getJoueurAdverse().getMesPieces().size(); k++){
                    //... sont au même endroit
                    if((p.getChoix().get(j).getX() == this.getJoueurAdverse().getMesPieces().get(k).getX()) && (p.getChoix().get(j).getY() == this.getJoueurAdverse().getMesPieces().get(k).getY())) {
                        this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().remove(j);
                        if(j == 0)
                            this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().remove(j);
                        j--;
                        break;
                    }
                }
            }

            //... et que ses choix pour manger ...
            for(int j = 0; j < p.getManger().size(); j++){
                //... une pièce adverse ...
                for(int k = 0; k < this.getJoueurAdverse().getMesPieces().size(); k++){
                    //... sont au même endroit
                    if((p.getManger().get(j).getX() == this.getJoueurAdverse().getMesPieces().get(k).getX()) && (p.getManger().get(j).getY() == this.getJoueurAdverse().getMesPieces().get(k).getY()))
                        this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().add(p.getManger().get(j));
                }
            }
        }
        //on étudie ses diagonales
        else if(type == "Fou"){
            int delta = 1;
            boolean trouverPremierePiece = false;
            //diagonale haut - doite
            while (y - delta>=0 && x + delta<Constantes.CASE_NOMBRE){
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x + delta, y - delta))
                        trouverPremierePiece = true;

                if(trouverPremierePiece)
                    this.supprimerDeplacement(id, x + delta, y - delta);

                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x + delta, y - delta))
                        trouverPremierePiece = true;

                delta++;
            }

            delta = 1;
            trouverPremierePiece = false;
            //diagonale haut - gauche
            while (y - delta>=0 && x - delta>=0){
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x - delta, y - delta ))
                        trouverPremierePiece = true;

                if(trouverPremierePiece)
                    this.supprimerDeplacement(id, x - delta, y - delta);

                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x - delta, y - delta))
                        trouverPremierePiece = true;

                delta++;
            }

            delta = 1;
            trouverPremierePiece = false;
            //diagonale bas - droite
            while (y + delta<Constantes.CASE_NOMBRE && x + delta<Constantes.CASE_NOMBRE){
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x + delta, y + delta))
                        trouverPremierePiece = true;

                if(trouverPremierePiece)
                    this.supprimerDeplacement(id, x + delta, y + delta);

                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x + delta, y + delta))
                        trouverPremierePiece = true;

                delta++;
            }

            delta = 1;
            trouverPremierePiece = false;
            //diagonale bas - gauche
            while (y + delta<Constantes.CASE_NOMBRE && x - delta>=0){
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x - delta, y + delta))
                        trouverPremierePiece = true;

                if(trouverPremierePiece)
                    this.supprimerDeplacement(id, x - delta, y + delta);

                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x - delta, y + delta))
                        trouverPremierePiece = true;

                delta++;
            }
        }
        //on étudie ses alignements
        else if(type == "Tour"){
            int delta = 1;
            boolean trouverPremierePiece = false;
            //vericale bas
            while (y-delta>=0){
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x, y - delta))
                        trouverPremierePiece = true;

                if(trouverPremierePiece)
                    this.supprimerDeplacement(id, x, y - delta);

                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x, y -delta))
                        trouverPremierePiece = true;

                delta++;
            }

            delta = 1;
            trouverPremierePiece = false;
            //verticale haut
            while (y+delta < Constantes.CASE_NOMBRE) {
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x, y + delta))
                        trouverPremierePiece = true;

                if(trouverPremierePiece)
                    this.supprimerDeplacement(id, x, y + delta);


                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x, y + delta))
                        trouverPremierePiece = true;

                delta++;
            }

            delta = 1;
            trouverPremierePiece = false;
            //horizontale droite
            while (x+delta<Constantes.CASE_NOMBRE){
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x + delta, y))
                        trouverPremierePiece = true;

                if(trouverPremierePiece)
                    this.supprimerDeplacement(id, x + delta, y);


                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x + delta, y))
                        trouverPremierePiece = true;

                delta++;
            }

            delta = 1;
            trouverPremierePiece = false;
            //hprizontale gauche
            while (x-delta>=0) {
                if (!trouverPremierePiece)
                    if(this.surUneCaseAllie(x - delta, y))
                        trouverPremierePiece = true;

                if (trouverPremierePiece)
                    this.supprimerDeplacement(id, x - delta, y);


                if (!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x - delta, y))
                        trouverPremierePiece = true;

                delta++;
            }
        }
        //on étudie ses diagonales et ses alignements
        else if(type == "Dame"){
            int delta = 1;
            boolean trouverPremierePiece = false;
            //diagonale haut - doite
            while (y - delta>=0 && x + delta<Constantes.CASE_NOMBRE){
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x + delta, y - delta))
                        trouverPremierePiece = true;

                if(trouverPremierePiece)
                    this.supprimerDeplacement(id, x + delta, y - delta);

                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x + delta, y - delta))
                        trouverPremierePiece = true;

                delta++;
            }

            delta = 1;
            trouverPremierePiece = false;
            //diagonale haut - gauche
            while (y - delta>=0 && x - delta>=0){
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x - delta, y - delta ))
                        trouverPremierePiece = true;

                if(trouverPremierePiece)
                    this.supprimerDeplacement(id, x - delta, y - delta);

                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x - delta, y - delta))
                        trouverPremierePiece = true;

                delta++;
            }

            delta = 1;
            trouverPremierePiece = false;
            //diagonale bas - droite
            while (y + delta<Constantes.CASE_NOMBRE && x + delta<Constantes.CASE_NOMBRE){
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x + delta, y + delta))
                        trouverPremierePiece = true;

                if(trouverPremierePiece)
                    this.supprimerDeplacement(id, x + delta, y + delta);

                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x + delta, y + delta))
                        trouverPremierePiece = true;

                delta++;
            }

            delta = 1;
            trouverPremierePiece = false;
            //diagonale bas - gauche
            while (y + delta<Constantes.CASE_NOMBRE && x - delta>=0){
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x - delta, y + delta))
                        trouverPremierePiece = true;

                if(trouverPremierePiece){
                    this.supprimerDeplacement(id, x - delta, y + delta);

                }
                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x - delta, y + delta))
                        trouverPremierePiece = true;

                delta++;
            }

            delta = 1;
            trouverPremierePiece = false;
            //vericale bas
            while (y-delta>=0){
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x, y - delta))
                        trouverPremierePiece = true;

                if(trouverPremierePiece)
                    this.supprimerDeplacement(id, x, y - delta);

                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x, y -delta))
                        trouverPremierePiece = true;

                delta++;
            }

            delta = 1;
            trouverPremierePiece = false;
            //verticale haut
            while (y+delta < Constantes.CASE_NOMBRE) {
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x, y + delta))
                        trouverPremierePiece = true;

                if(trouverPremierePiece)
                    this.supprimerDeplacement(id, x, y + delta);

                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x, y + delta))
                        trouverPremierePiece = true;

                delta++;
            }

            delta = 1;
            trouverPremierePiece = false;
            //horizontale droite
            while (x+delta<Constantes.CASE_NOMBRE){
                if(!trouverPremierePiece)
                    if(this.surUneCaseAllie(x + delta, y))
                        trouverPremierePiece = true;

                if(trouverPremierePiece)
                    this.supprimerDeplacement(id, x + delta, y);

                if(!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x + delta, y))
                        trouverPremierePiece = true;

                delta++;
            }

            delta = 1;
            trouverPremierePiece = false;
            //hprizontale gauche
            while (x-delta>=0) {
                if (!trouverPremierePiece)
                    if(this.surUneCaseAllie(x - delta, y))
                        trouverPremierePiece = true;

                if (trouverPremierePiece)
                    this.supprimerDeplacement(id, x - delta, y);

                if (!trouverPremierePiece)
                    if(this.surUneCaseAdverse(x - delta, y))
                        trouverPremierePiece = true;

                delta++;
            }
        }
    }

    private boolean surUneCaseAllie(int x, int y){
        for (int i = 0; i < this.getJoueur(this.getJoueurActuel()).getMesPieces().size(); i++) {
            if ((this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getX() == x) && (y == this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getY())){
                return true;
            }
        }
        return false;
    }

    private boolean surUneCaseAdverse(int x, int y){
        for (int i = 0; i < this.getJoueurAdverse().getMesPieces().size(); i++) {
            if ((this.getJoueurAdverse().getMesPieces().get(i).getX() == x) && (y == this.getJoueurAdverse().getMesPieces().get(i).getY())){
                return true;
            }
        }
        return false;
    }

    private void supprimerDeplacement(int id, int x, int y){
        for(int i = 0; i<this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().size(); i++){
            if((y == this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().get(i).getY()) && (x == this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().get(i).getX())){
                this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getChoix().remove(i);
                break;
            }
        }
    }

    /***
     * ATTENTION : pour cette méthode, il faut faire
     * être vigilent avec le joueur actuel
     * car elle en dépend pour gérer les déplacements
     *
     * Liste des actions :
     *  1. Déplace la pièce à sa position finale
     *  2. Ecrase la pièce qui était présente (si c'était le cas)
     *  3. On recréer les déplacements du joueur qui a déplacer la pièce
     *  3. Ainsi que pour le joueur adverse
     */
    public void deplacerPiece(int id, int x, int y){

        //on déplace le joueur
        //si c'est un roi, choix à part pour le rpque
        if(this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getClass().getName() == "Roi"){
            //vers la gauche
            if((this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getX() == x - 2) && (this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getY() == y)){
                for(int i = 0; i < this.getJoueur(this.getJoueurActuel()).getMesPieces().size(); i++){
                    if(this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getClass().getName() == "Tour" && this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getX() == 7) {
                        this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).deplacer(x - 1, y);
                        break;
                    }
                }

                System.out.println("Grand roque");
            }

            //vers la droite
            else if((this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getX() == x + 2) && (this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).getY() == y)){
                for(int i = 0; i < this.getJoueur(this.getJoueurActuel()).getMesPieces().size(); i++){
                    if(this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getClass().getName() == "Tour" && this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getX() == 0) {
                        this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).deplacer(x + 1, y);
                        break;
                    }
                }
                System.out.println("Petit roque");
            }

            this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).deplacer(x, y);

        }
        //si c'est autre qu'un roi
        else
            this.getJoueur(this.getJoueurActuel()).getMesPieces().get(id).deplacer(x,y);

        //on retravaille les choix des pièces de son équipe
        for(int i = 0; i < this.getJoueur(this.getJoueurActuel()).getMesPieces().size(); i++)
            this.modifierPossibilites(i, this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getX(), this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getY());

        //on gère les pièes adverses
        this.changerJoueur();

        for(int i = 0; i < this.getJoueur(this.getJoueurActuel()).getMesPieces().size(); i++)
            this.modifierPossibilites(i, this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getX(), this.getJoueur(this.getJoueurActuel()).getMesPieces().get(i).getY());

        //on a finit de gérer les pièces adverses
        this.changerJoueur();
    }

    public void ecraserPiece(int x, int y){
        //on écrase la pièce adverse sur laquelle il arrive
        for(int i = 0 ; i < this.getJoueurAdverse().getMesPieces().size(); i++){
            //... si il y en a une !
            if(this.getJoueurAdverse().getMesPieces().get(i).getX() == x && this.getJoueurAdverse().getMesPieces().get(i).getY() == y){
                this.getJoueurAdverse().getMesPieces().remove(i);
                break;
            }
        }
    }

    public boolean deplacementPeutManger(int x, int y){
        for(int i = 0; i < this.getJoueurAdverse().getMesPieces().size(); i++){
            if(this.getJoueurAdverse().getMesPieces().get(i).getX() == x && this.getJoueurAdverse().getMesPieces().get(i).getY() == y)
                return true;
        }
        return false;
    }
}
