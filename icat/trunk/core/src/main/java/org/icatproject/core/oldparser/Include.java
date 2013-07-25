package org.icatproject.core.oldparser;

import java.util.HashSet;
import java.util.Set;

import org.icatproject.core.IcatException;
import org.icatproject.core.entity.EntityBaseBean;
import org.icatproject.core.manager.EntityInfoHandler;

public class Include {

	// Include ::= "INCLUDE" "1" | (Name ("," Name )*)

	private Set<Class<? extends EntityBaseBean>> includes = new HashSet<Class<? extends EntityBaseBean>>();

	private boolean one;

	public Include(Class<? extends EntityBaseBean> bean, OldInput input) throws OldParserException,
			IcatException {

		input.consume(OldToken.Type.INCLUDE);
		OldToken name = input.consume(OldToken.Type.NAME, OldToken.Type.INTEGER);
		String value = name.getValue();
		if (name.getType() == OldToken.Type.NAME) {
			this.includes.add(EntityInfoHandler.getClass(value));
			OldToken t;
			while ((t = input.peek(0)) != null && t.getType() == OldToken.Type.COMMA) {
				input.consume();
				name = input.consume(OldToken.Type.NAME);
				value = name.getValue();
				this.includes.add(EntityInfoHandler.getClass(value));
			}
			DagHandler.checkIncludes(bean, getBeans());
		} else if (value.equals("1")) {
			one = true;
		} else {
			throw new IcatException(IcatException.IcatExceptionType.BAD_PARAMETER,
					"Only integer value allowed in the INCLUDE list is 1");
		}

	}

	@Override
	public String toString() {

		if (one) {
			return " INCLUDE 1";
		}

		if (includes.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		sb.append(" INCLUDE ");
		boolean first = true;
		if (one) {
			sb.append("1 ");
			first = false;
		}
		for (Class<? extends EntityBaseBean> bean : includes) {
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(bean.getSimpleName());
		}
		return sb.toString();
	}

	public Set<Class<? extends EntityBaseBean>> getBeans() {
		return includes;
	}

	public boolean isOne() throws IcatException {
		return one;
	}
}
