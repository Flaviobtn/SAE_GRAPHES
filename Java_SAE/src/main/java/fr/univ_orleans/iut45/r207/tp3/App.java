package fr.univ_orleans.iut45.r207.tp3;


import java.util.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultUndirectedGraph;
import java.util.*;
import java.io.*;

public class App {
    public static Graph<String, DefaultEdge> chargerGraphe(String cheminFichier) {
    Graph<String, DefaultEdge> graphe = new DefaultUndirectedGraph<>(DefaultEdge.class);
    Gson gson = new Gson();
    try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
        String ligne;
        while ((ligne = br.readLine()) != null) {
            ligne = ligne.trim();
            if (ligne.isEmpty()) continue; 
            try {
                Map<String, Object> film = gson.fromJson(ligne, new TypeToken<Map<String, Object>>() {}.getType());
                if (film.containsKey("cast")) {
                    List<String> acteursBruts = (List<String>) film.get("cast");
                    List<String> acteurs = new ArrayList<>();
                    for (String acteurBrut : acteursBruts) {
                        // Supprime les crochets [[...]] s'ils existent
                        String acteur = acteurBrut.replaceAll("^\\[\\[(.*)\\]\\]$", "$1");
                        graphe.addVertex(acteur);
                        acteurs.add(acteur);
                    }
                    for (int i = 0; i < acteurs.size(); i++) {
                        for (int j = i + 1; j < acteurs.size(); j++) {
                            graphe.addEdge(acteurs.get(i), acteurs.get(j));
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Erreur JSON sur la ligne ignorée : " + ligne);
            }
        }
    } catch (IOException e) {
        System.err.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
    }
    return graphe;
}

//3.2
	public static Set<String> collaborateursCommuns(Graph<String, DefaultEdge> g, String u, String v){
		Set<String> res = new HashSet<>();
		List<String> vertexU = Graphs.neighborListOf(g,u);
		for(String voisin : Graphs.neighborListOf(g,v)){
			if(vertexU.contains(voisin)){
				res.add(voisin);
			}
		}
		return res;
	}

//3.3
	/*public static Set<String> collaborateursProches(Graph<String, DefaultEdge> g, String u, int k) {
        Set<String> visites = new HashSet<>();
        visites.add(u);
        return collaborateursProchesRecursive(g, u, k, visites);
    }

    private static Set<String> collaborateursProchesRecursive(Graph<String, DefaultEdge> g, String u, int k, Set<String> visites) {
        Set<String> resultat = new HashSet<>();

        if (k == 0) {
            resultat.add(u);
            return resultat;
        }

        for (String voisin : Graphs.neighborListOf(g, u)) {
            if (!visites.contains(voisin)) {
                visites.add(voisin);
                resultat.addAll(collaborateursProchesRecursive(g, voisin, k - 1, visites));
                visites.remove(voisin);
            }
        }
        return resultat;
    }*/

    public static Set<String> collaborateursProches(Graph<String, DefaultEdge> g, String u, int k) {
        Set<String> resultat = new HashSet<>();
        Set<String> visites = new HashSet<>();
        Map<String, Integer> niveaux = new HashMap<>();
        ArrayList<String> file = new ArrayList<>();

        visites.add(u);
        niveaux.put(u, 0);
        file.add(u);

        int index = 0;

        while (index < file.size()) {
            String courant = file.get(index++);
            int niveau = niveaux.get(courant);

            if (niveau >= k) continue;

            for (String voisin : Graphs.neighborListOf(g, courant)) {
                if (!visites.contains(voisin)) {
                    visites.add(voisin);
                    niveaux.put(voisin, niveau + 1);
                    file.add(voisin);
                    resultat.add(voisin);
                }
            }
        }

        return resultat;
    }


    public static int centralite(Graph<String, DefaultEdge> g, String u) {
        List<Integer> l = new ArrayList<>();
        for(String sommet : g.vertexSet()){
            l.add(App.distanceMaxDepuisSommet(g,sommet));
        }
        return Collections.min(l);
    }

//3.4
    public static int distanceMaxDepuisSommet(Graph<String, DefaultEdge> g, String u) {
        int k = 1;
        while (true) {
            Set<String> voisinsAK = collaborateursProches(g, u, k);
            if (voisinsAK.isEmpty()) {
                return k - 1;
            }
            k++;
        }
    }

//3.5
    public static int diametreGraphe(Graph<String, DefaultEdge> g) {
        int diametre = 0;
        for (String sommet : g.vertexSet()) {
            int distMax = distanceMaxDepuisSommet(g, sommet);
            if (distMax > diametre) {
                diametre = distMax;
            }
        }
        return diametre;
    }


	public static void main(String[] args) {

        Graph<String, DefaultEdge> graphe = chargerGraphe("C:/Users/tagsm/Desktop/Bureau/SAE_GRAPHES/Jeux de donnée/test_films_large.txt");
        System.out.println("Nombre d’acteurs : " + graphe.vertexSet().size());
        System.out.println("Nombre de collaborations : " + graphe.edgeSet().size());
        System.out.println(App.collaborateursCommuns(graphe,"Alice Smith","Diana Prince"));
        System.out.println(App.collaborateursProches(graphe,"Alice Smith",1));
        System.out.println(App.centralite(graphe,"Alice Smith"));
        System.out.println(App.distanceMaxDepuisSommet(graphe,"Jack Black"));
        System.out.println(App.diametreGraphe(graphe));

	}
	
}
