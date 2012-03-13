package uk.icat3.parser;

import java.util.ArrayList;
import java.util.List;

import uk.icat3.entity.EntityBaseBean;
import uk.icat3.exceptions.BadParameterException;

public class SearchCondition {

	private List<BooleanTerm> booleanTerms = new ArrayList<BooleanTerm>();

	// SearchCondition ::= BooleanTerm ( "OR" BooleanTerm ) *

	public SearchCondition(Input input) throws ParserException {
		this.booleanTerms.add(new BooleanTerm(input));
		Token t = null;
		while ((t = input.peek(0)) != null) {
			if (t.getType() == Token.Type.OR) {
				input.consume();
				this.booleanTerms.add(new BooleanTerm(input));
			} else {
				return;
			}
		}
	}

	public StringBuilder getWhere(Class<? extends EntityBaseBean> tb) throws BadParameterException {
		StringBuilder sb = new StringBuilder("(");
		sb.append(this.booleanTerms.get(0).getWhere(tb));
		for (int i = 1; i < this.booleanTerms.size(); i++) {
			sb.append("OR ");
			sb.append(this.booleanTerms.get(i).getWhere(tb));
		}
		sb.append(')');
		return sb;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.booleanTerms.get(0));
		for (int i = 1; i < this.booleanTerms.size(); i++) {
			sb.append("OR ");
			sb.append(this.booleanTerms.get(i));
		}
		return sb.toString();
	}

}
