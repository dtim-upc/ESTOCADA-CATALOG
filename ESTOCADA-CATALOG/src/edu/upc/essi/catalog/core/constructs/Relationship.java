package edu.upc.essi.catalog.core.constructs;

import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGPlainLink;

public class Relationship extends HGPlainLink {

	private String IRI;

	public Relationship(String iRI, HGHandle... targets) throws Exception {
		super(targets);
		assertBinary();
		this.IRI = iRI;
	}

	private void assertBinary() throws Exception {
		if (this.getArity() > 2)
			throw new Exception("Relationship is between two Atoms");
	}

	public String getIRI() {
		return IRI;
	}

	public void setIRI(String iRI) {
		IRI = iRI;
	}
}