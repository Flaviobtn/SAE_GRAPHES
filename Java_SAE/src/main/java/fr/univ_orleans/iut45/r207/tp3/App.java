package fr.univ_orleans.iut45.r207.tp3;


import java.util.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
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

        try {
            FileReader lecteur = new FileReader(cheminFichier);

            // On suppose que chaque film est un objet avec une clé "cast" qui contient une liste d’acteurs
            List<Map<String, List<String>>> films = gson.fromJson(
                lecteur,
                new TypeToken<List<Map<String, List<String>>>>() {}.getType()
            );

            for (Map<String, List<String>> film : films) {
                List<String> acteurs = film.get("cast"); // On récupère la liste des acteurs

                // On ajoute tous les acteurs comme sommets
                for (String acteur : acteurs) {
                    graphe.addVertex(acteur);
                }

                // On relie les acteurs du même film entre eux
                for (int i = 0; i < acteurs.size(); i++) {
                    for (int j = i + 1; j < acteurs.size(); j++) {
                        graphe.addEdge(acteurs.get(i), acteurs.get(j));
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier JSON.");
        }

        return graphe;
    }

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

	public static Set<String> collaborateursProches(Graph<String, DefaultEdge> g, String u, int eloignement){
        Set<String> ensembleActeurs = new HashSet<>();
        if(eloignement == 0){
            return ensembleActeurs;
        }
        else{
            for(String voisin:Graphs.neighborListOf(g,u)){
                ensembleActeurs.add(voisin);
                collaborateursProches(g,voisin,eloignement-1);
            }
        }
        return ensembleActeurs;
	}

	public static void main(String[] args) {

        Graph<String, DefaultEdge> graphe = chargerGraphe("C:/Users/tagsm/Desktop/Bureau/SAE_GRAPHES/jeux de données réduits-20250519/data_100.txt");

        System.out.println("Nombre d’acteurs : " + graphe.vertexSet().size());
        System.out.println("Nombre de collaborations : " + graphe.edgeSet().size());
		
		Graph<String, DefaultEdge> graph = GraphTypeBuilder
				.directed()
				.allowingMultipleEdges(true)
				.allowingSelfLoops(true)
				.vertexSupplier(SupplierUtil.createStringSupplier())
				.edgeSupplier(SupplierUtil.createDefaultEdgeSupplier())
				.buildGraph();

		String v0 = graph.addVertex();
		String v1 = graph.addVertex();
		String v2 = graph.addVertex();

		graph.addEdge(v0, v1);
		graph.addEdge(v1, v2);
		graph.addEdge(v0, v2);

		for (String v : graph.vertexSet()) {
			System.out.println("vertex: " + v);
		}

		for (DefaultEdge e : graph.edgeSet()) {
			System.out.println("edge: " + e);
		}
	}
	
}
