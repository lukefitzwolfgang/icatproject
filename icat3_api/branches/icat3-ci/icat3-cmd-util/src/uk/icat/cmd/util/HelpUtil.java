// $Id: HelpUtil.java 934 2011-08-09 13:16:35Z nab24562@FED.CCLRC.AC.UK $
package uk.icat.cmd.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import uk.icat.cmd.entity.Parameter;

public class HelpUtil {

	public static void printMethods(Method[] methods) {
		System.out.println("To get detailed method description please use following syntax: ");
		System.out.println("\ticat_cmd.sh [method] -h");
		System.out.println("Available methods: ");
		Arrays.sort(methods, methodComparator);
		for (Method m : methods) {
			System.out.println(methodToString(m));
		}
	}

	private static String methodToString(Method m) {
		StringBuffer sb = new StringBuffer();

		sb.append('\t');
		sb.append(m.getName());
		sb.append('(');

		List<Parameter> params = ParameterUtil.extractParameters(m);
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			if (i < params.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append(')');

		return sb.toString();
	}

	public static void printDetailedHelp(Method method, Options options) {
		new HelpFormatter().printHelp("icat_cmd.sh " + method.getName() + " [parameters]", options);
	}

	private static Comparator<Method> methodComparator = new Comparator<Method>() {
		@Override
		public int compare(Method o1, Method o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};

	public static void printMethodSignature(Method method) {
		System.err.println("Method signature: " + methodToString(method));
	}

	public static void printHelp() {
		System.err.println("Usage: icat_cmd.sh [method] [parameters]");
		System.err.println("\t-l returns list of available methods");
		System.err.println("\t-h prints this help");
		System.err.println("To get detailed method description please use following syntax: ");
		System.err.println("\ticat_cmd.sh [method] -h");
		System.err.println();
		System.err.println("Web Service address, username and password are stored in icat.properties file");
	}
}
