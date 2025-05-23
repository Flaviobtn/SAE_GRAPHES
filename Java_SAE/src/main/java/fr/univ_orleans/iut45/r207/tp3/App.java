package fr.univ_orleans.iut45.r207.tp3;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Hello JGraphT!
 */
public class App {
	public static Graph<String, DefaultEdge> collaborationsJsonToGraph(String jsonFilePath) throws IOException {
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
		List<Map<String, Object>> collaborations;
		try (FileReader reader = new FileReader(jsonFilePath)) {
			collaborations = gson.fromJson(reader, listType);
		}

		Graph<String, DefaultEdge> graph = GraphTypeBuilder
				.directed()
				.allowingMultipleEdges(true)
				.allowingSelfLoops(true)
				.vertexSupplier(SupplierUtil.createStringSupplier())
				.edgeSupplier(SupplierUtil.createDefaultEdgeSupplier())
				.buildGraph();

		for (Map<String, Object> collaboration : collaborations) {
			String source = (String) collaboration.get("source");
			String target = (String) collaboration.get("target");
			if (!graph.containsVertex(source)) {
				graph.addVertex(source);
			}
			if (!graph.containsVertex(target)) {
				graph.addVertex(target);
			}
			graph.addEdge(source, target);
		}
		return graph;
	}
	public static void main(String[] args) {
		
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
