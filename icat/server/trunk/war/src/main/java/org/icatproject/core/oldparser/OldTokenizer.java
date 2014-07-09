package org.icatproject.core.oldparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OldTokenizer {

	enum State {
		RESET, NONE, INQUOTES, CLOSEDQUOTES, LT, LTMINUS, NAME, INTEGER, REAL, NOT, PARAMETER, TIMESTAMP, GT
	}

	private final static Pattern tsRegExp = Pattern
			.compile("\\{\\s*ts\\s+(\\d{4}-\\d{2}-\\d{2})\\s+(\\d{2}:\\d{2}:\\d{2})\\s*\\}");

	private final static Set<String> boolops = new HashSet<String>(
			Arrays.asList("AND", "OR", "NOT"));
	private final static Set<String> keyWords = new HashSet<String>(Arrays.asList("DISTINCT",
			"ORDER", "BY", "ASC", "DESC", "IN", "BETWEEN", "INCLUDE"));
	private final static Set<String> functions = new HashSet<String>(Arrays.asList("MIN", "MAX",
			"AVG", "COUNT", "SUM"));

	public static List<OldToken> getTokens(String input) throws OldLexerException {
		List<OldToken> tokens = new ArrayList<OldToken>();
		State state = State.NONE;
		int start = 0;
		char ch = ' ';
		for (int i = 0; i < input.length() + 1; i++) {
			if (state == State.RESET) {
				i--;
				state = State.NONE;
			} else if (i < input.length()) {
				ch = input.charAt(i);
			} else {
				ch = 0;
			}
			if (state == State.NONE) {
				if (ch == ' ' || ch == 0) {
					// Ignore
				} else if (Character.isLetter(ch)) {
					state = State.NAME;
					start = i;
				} else if (Character.isDigit(ch) || ch == '-') {
					state = State.INTEGER;
					start = i;
				} else if (ch == ':') {
					state = State.PARAMETER;
					start = i;
				} else if (ch == '\'') {
					state = State.INQUOTES;
					start = i;
				} else if (ch == '[') {
					tokens.add(new OldToken(OldToken.Type.BRA, ch));
				} else if (ch == ']') {
					tokens.add(new OldToken(OldToken.Type.KET, ch));
				} else if (ch == '(') {
					tokens.add(new OldToken(OldToken.Type.OPENPAREN, ch));
				} else if (ch == ')') {
					tokens.add(new OldToken(OldToken.Type.CLOSEPAREN, ch));
				} else if (ch == ',') {
					tokens.add(new OldToken(OldToken.Type.COMMA, ch));
				} else if (ch == '<') {
					state = State.LT;
				} else if (ch == '=') {
					tokens.add(new OldToken(OldToken.Type.COMPOP, ch));
				} else if (ch == '>') {
					state = State.GT;
				} else if (ch == '!') {
					state = State.NOT;
				} else if (ch == '{') {
					state = State.TIMESTAMP;
					start = i;
				} else {
					reportError(ch, state, i, input);
				}
			} else if (state == State.INQUOTES) {
				if (ch == '\'') {
					state = State.CLOSEDQUOTES;
				} else if (ch == 0) {
					reportError(ch, state, i, input);
				}
			} else if (state == State.CLOSEDQUOTES) {
				if (ch == '\'') {
					state = State.INQUOTES;
				} else {
					tokens.add(new OldToken(OldToken.Type.STRING, input.substring(start + 1, i - 1)
							.replace("''", "'")));
					state = State.RESET;
				}
			} else if (state == State.GT) {
				if (ch == '=') {
					tokens.add(new OldToken(OldToken.Type.COMPOP, ">="));
					state = State.NONE;
				} else {
					tokens.add(new OldToken(OldToken.Type.COMPOP, ">"));
					state = State.RESET;
				}
			} else if (state == State.LT) {
				if (ch == '-') {
					state = State.LTMINUS;
				} else if (ch == '>') {
					tokens.add(new OldToken(OldToken.Type.COMPOP, "<>"));
					state = State.NONE;
				} else if (ch == '=') {
					tokens.add(new OldToken(OldToken.Type.COMPOP, "<="));
					state = State.NONE;
				} else {
					tokens.add(new OldToken(OldToken.Type.COMPOP, "<"));
					state = State.RESET;
				}
			} else if (state == State.LTMINUS) {
				if (ch == '>') {
					tokens.add(new OldToken(OldToken.Type.ENTSEP, "<->"));
					state = State.NONE;
				} else {
					reportError(ch, state, i, input);
				}
			} else if (state == State.NAME) {
				if (!Character.isLetterOrDigit(ch) && ch != '_' && ch != '.') {
					String name = input.substring(start, i);
					String nameUp = name.toUpperCase();
					if (boolops.contains(nameUp) || keyWords.contains(nameUp)
							|| functions.contains(nameUp)) {
						tokens.add(new OldToken(OldToken.Type.valueOf(nameUp), nameUp));
					} else if (nameUp.equals("LIKE")) {
						tokens.add(new OldToken(OldToken.Type.COMPOP, "LIKE"));
					} else {
						tokens.add(new OldToken(OldToken.Type.NAME, name));
					}
					state = State.RESET;
				}
			} else if (state == State.INTEGER) {
				if (ch == 'e' || ch == 'E' || ch == '.') {
					state = State.REAL;
				} else if (!Character.isDigit(ch)) {
					tokens.add(new OldToken(OldToken.Type.INTEGER, input.substring(start, i)));
					state = State.RESET;
				}
			} else if (state == State.REAL) {
				if (!Character.isDigit(ch) && ch != 'e' && ch != 'E' && ch != '.' && ch != '+'
						&& ch != '-') {
					Double d = null;
					try {
						d = Double.parseDouble(input.substring(start, i));
					} catch (NumberFormatException e) {
						reportError(ch, state, i, input);
					}
					tokens.add(new OldToken(OldToken.Type.REAL, d.toString()));
					state = State.RESET;
				}
			} else if (state == State.NOT) {
				if (ch == '=') {
					tokens.add(new OldToken(OldToken.Type.COMPOP, "!="));
					state = State.NONE;
				} else {
					reportError(ch, state, i, input);
				}
			} else if (state == State.PARAMETER) {
				if (!Character.isLetterOrDigit(ch) && ch != '_') {
					tokens.add(new OldToken(OldToken.Type.PARAMETER, input.substring(start, i)));
					state = State.RESET;
				}
			} else if (state == State.TIMESTAMP) {
				if (ch == '}') {
					String ts = input.substring(start, i) + "}";
					Matcher matcher = tsRegExp.matcher(ts);
					if (!matcher.matches()) {
						throw new OldLexerException("Timestamp " + ts
								+ " is not of form {ts yyyy-mm-dd hh:mm:ss}.");
					}
					tokens.add(new OldToken(OldToken.Type.TIMESTAMP, "{ts " + matcher.group(1) + " "
							+ matcher.group(2) + "}"));
					state = State.NONE;
				}
			}
		}

		return tokens;
	}

	private static void reportError(char ch, State state, int i, String input)
			throws OldLexerException {
		int i1 = Math.max(0, i - 4);
		int i2 = Math.min(i + 5, input.length());
		if (ch != 0) {
			throw new OldLexerException("Unexpected character '" + ch + "' near \""
					+ input.substring(i1, i2) + "\" in state " + state + " for string: " + input);
		} else {
			throw new OldLexerException("Unexpected end of string in state " + state + " for string: "
					+ input);
		}
	}

	public static String getTypeToPrint(OldToken.Type type) {
		if (type == OldToken.Type.COMPOP) {
			return ">, <, !=, =, <>, >=, <=";
		} else if (type == OldToken.Type.OPENPAREN) {
			return "(";
		} else if (type == OldToken.Type.CLOSEPAREN) {
			return ")";
		}
		return type.name();
	}

}
