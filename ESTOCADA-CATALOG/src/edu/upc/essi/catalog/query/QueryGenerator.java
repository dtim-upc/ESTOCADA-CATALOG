package edu.upc.essi.catalog.query;

import java.util.Iterator;

import org.hypergraphdb.HGHandle;

import edu.upc.essi.catalog.core.constructs.Atom;
import edu.upc.essi.catalog.core.constructs.Element;
import edu.upc.essi.catalog.core.constructs.Hyperedge;
import edu.upc.essi.catalog.core.constructs.Triple;
import edu.upc.essi.catalog.ops.Graphoperations;

public class QueryGenerator {

	public QueryGenerator() {
		// TODO Auto-generated constructor stub
	}

	// Relational
	public Triple GetprefixSuffix(Element node, String path) {
		Triple t = new Triple();
		String prefix = "";
		String suffix = "";
		String p = "";

		if (node instanceof Atom) {
			prefix = node.getName();
			suffix = ",";
		} else if (node instanceof Hyperedge) {
			switch (((Hyperedge) node).getType()) {
			case FirstLevel:
				suffix = " FROM " + node.getName();
				break;

			case SecondLevel:
				prefix = "SELECT ";
				suffix = "~";
				break;

			default:
				break;

			}
		}

		t.setPrefix(prefix);
		t.setSuffix(suffix);
		t.setPath(p);
		return t;
	}

	public String CreateQuery(Element node, String path) {
		String Q = "";

		Triple pair = GetprefixSuffix(node, path);
		Q = Q + pair.getPrefix();

		if (node instanceof Hyperedge) {
			Iterator<HGHandle> x = ((Hyperedge) node).iterator();
			while (x.hasNext()) {
				Q = Q + CreateQuery(Graphoperations.getElementbyHandle(x.next()), pair.getPath());
			}
		}
		// for (Node n : node.getChildren()) {
		// Q = Q + CreateQuery(n, pair.path);
		// }
		Q = Q + pair.getSuffix();

		Q = Q.replaceAll(",~", "");
		return Q;
	}
}