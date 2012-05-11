package org.icatproject.core.parser;

import java.util.ArrayList;
import java.util.List;

import org.icatproject.core.IcatException;
import org.icatproject.core.entity.EntityBaseBean;


public class BooleanTerm {

	// BooleanTerm ::= Factor ( "AND" Factor ) *

	private List<BooleanFactor> factors = new ArrayList<BooleanFactor>();

	public BooleanTerm(Input input) throws ParserException {
		this.factors.add(new BooleanFactor(input));
		Token t = null;
		while ((t = input.peek(0)) != null) {
			if (t.getType() == Token.Type.AND) {
				input.consume();
				this.factors.add(new BooleanFactor(input));
			} else {
				return;
			}
		}
	}

	public StringBuilder getWhere(Class<? extends EntityBaseBean> tb) throws IcatException  {
		StringBuilder sb = new StringBuilder();
		sb.append(this.factors.get(0).getWhere(tb));
		for (int i = 1; i < this.factors.size(); i++) {
			sb.append(" AND ");
			sb.append(this.factors.get(i).getWhere(tb));
		}
		return sb;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.factors.get(0));
		for (int i = 1; i < this.factors.size(); i++) {
			sb.append(" AND ");
			sb.append(this.factors.get(i));
		}
		return sb.toString();
	}

}
