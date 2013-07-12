import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.icatproject.Group;
import org.icatproject.ICAT;
import org.icatproject.ICATService;
import org.icatproject.IcatExceptionType;
import org.icatproject.IcatException_Exception;
import org.icatproject.Login.Credentials;
import org.icatproject.Login.Credentials.Entry;
import org.icatproject.Rule;
import org.icatproject.User;
import org.icatproject.UserGroup;

public class Setup {

	private static ICAT icatEP;
	private static String sessionId;
	private static int WIDTH = 60;

	enum TableType {
		Rule, Group, User
	};

	private static String[] tables = { "Application", "Datafile", "DatafileFormat",
			"DatafileParameter", "Dataset", "DatasetParameter", "DatasetType", "Facility",
			"FacilityCycle", "Group", "InputDatafile", "InputDataset", "Instrument",
			"InstrumentScientist", "Investigation", "InvestigationParameter", "InvestigationType",
			"InvestigationUser", "Job", "Keyword", "OutputDatafile", "OutputDataset",
			"ParameterType", "PermissibleStringValue", "Publication", "RelatedDatafile", "Rule",
			"Sample", "SampleParameter", "SampleType", "Shift", "Study", "StudyInvestigation",
			"User", "UserGroup" };

	public static void main(String[] args) {

		Options options = new Options();

		options.addOption("h", "help", false, "This message");
		options.addOption("f", "file", true, "File with commands - by default stdin is used");
		CommandLine cmd = null;
		CommandLineParser parser = new GnuParser();
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			abort(e.getMessage());
		}

		if (cmd.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("Setup", options);

			Para p = new Para(WIDTH);
			p.add("Arguments are 'url authenticator credentials'.");
			p.add("The url must just have the scheme, the hostname and the port.");
			System.out.println(p);

			p = new Para(WIDTH);
			p.add("Credentials must be provided in the form 'username root password secret'");
			p.add("There must be an even number of words. It is preferable to specify the");
			p.add("password value as '-' then you will be prompted for it.");
			p.add("For example: 'icat-setup https://example.com:8181 ldap username fisher password -'");
			System.out.println(p);

			p = new Para(WIDTH);
			p.add("It will accept commands from a file or from stdin. In interactive mode it will");
			p.add("report errors and continue. However when reading from a file it will abort");
			p.add("at the first error.");
			System.out.println(p);

			printHelp();

			System.exit(0);
		}

		args = cmd.getArgs();
		if (args.length < 2) {
			abort("Must have arguments url, authenticator, credentials");
		}
		String urlString = args[0];
		String authenticator = args[1];

		InputStream is = null;
		boolean interactive = false;
		if (cmd.hasOption("f")) {
			try {
				is = new FileInputStream(cmd.getOptionValue("f"));
			} catch (FileNotFoundException e) {
				abort(e.getMessage());
			}
		} else {
			is = System.in;
			interactive = true;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		URL icatUrl = null;
		try {
			icatUrl = new URL(urlString + "/ICATService/ICAT?wsdl");
		} catch (MalformedURLException e) {
			abort(e.getMessage());
		}
		ICATService icatService = new ICATService(icatUrl, new QName("http://icatproject.org",
				"ICATService"));
		Setup.icatEP = icatService.getICATPort();

		if (args.length % 2 != 0) {
			abort("Credential list must of even length with a series of name value e.g. 'username root password secret'");
		}

		Credentials credentials = new Credentials();
		List<Entry> entries = credentials.getEntry();
		for (int i = 2; i < args.length; i += 2) {

			Entry entry = new Entry();
			entry.setKey(args[i]);
			if (args[i + 1].equals("-")) {
				Console console = System.console();
				if (console == null) {
					abort("Application has no console");
				}
				entry.setValue(new String(console.readPassword("Please enter the value for "
						+ args[i] + " ")));
			} else {
				entry.setValue(args[i + 1]);
			}
			entries.add(entry);
		}

		try {
			sessionId = icatEP.login(authenticator, credentials);
		} catch (IcatException_Exception e) {
			abort(e.getMessage());
		}

		if (interactive) {
			System.out.println("No input file provided - will read from stdin");
		}

		String line;

		try {
			while ((line = br.readLine()) != null) {
				try {
					if (line.startsWith("adduser")) {
						String[] tokens = line.split("\\s+");
						if (tokens.length != 3) {
							throw new ApplicationException(
									"adduser requires 2 parameters - user and group '" + line + "'");
						}
						addUserGroupMember(tokens[1], tokens[2]);
					} else if (line.startsWith("listuser")) {
						String[] tokens = line.split("\\s+");
						if (tokens.length == 2) {
							search("User INCLUDE UserGroup, Group [name like '" + tokens[1] + "']",
									TableType.User);
						} else {
							search("User INCLUDE UserGroup, Group", TableType.User);
						}
					} else if (line.startsWith("deluser")) {
						String[] tokens = line.split("\\s+");
						if (tokens.length != 2) {
							throw new ApplicationException("deluser requires 1 parameters - id '"
									+ line + "'");
						}
						User u = new User();
						u.setId(Long.parseLong(tokens[1]));
						icatEP.delete(sessionId, u);
					} else if (line.startsWith("addrule")) {
						String[] tokens = line.split("\\s+");
						if (tokens.length < 4) {
							throw new ApplicationException(
									"addrule requires 3 parameters - group, what and crudflags '"
											+ line + "'");
						}
						String groupName = tokens[1].equals("null") ? null : tokens[1];
						if (tokens[2].equalsIgnoreCase("all")) {
							for (String table : tables) {
								addRule(groupName, table, tokens[3]);
							}
						} else {
							StringBuilder s = new StringBuilder(tokens[2]);
							for (int i = 3; i < tokens.length - 1; i++) {
								s.append(' ').append(tokens[i]);
							}
							addRule(groupName, s.toString(), tokens[tokens.length - 1]);
						}
					} else if (line.startsWith("listrule")) {
						String[] tokens = line.split("\\s+");
						if (tokens.length == 2) {
							search("Rule INCLUDE Group <-> Group[name like '" + tokens[1] + "']",
									TableType.Rule);
						} else {
							search("Rule INCLUDE Group", TableType.Rule);
						}
					} else if (line.startsWith("delrule")) {
						String[] tokens = line.split("\\s+");
						if (tokens.length != 2) {
							throw new ApplicationException("delrule requires 1 parameters - id '"
									+ line + "'");
						}
						Rule r = new Rule();
						r.setId(Long.parseLong(tokens[1]));
						icatEP.delete(sessionId, r);
					} else if (line.startsWith("listgroup")) {
						String[] tokens = line.split("\\s+");
						if (tokens.length == 2) {
							search("Group INCLUDE UserGroup, User [name like '" + tokens[1] + "']",
									TableType.Group);
						} else {
							search("Group INCLUDE UserGroup, User", TableType.Group);
						}
					} else if (line.startsWith("delgroup")) {
						String[] tokens = line.split("\\s+");
						if (tokens.length != 2) {
							throw new ApplicationException("delgroup requires 1 parameters - id '"
									+ line + "'");
						}
						Group g = new Group();
						g.setId(Long.parseLong(tokens[1]));
						icatEP.delete(sessionId, g);
					} else if (line.trim().isEmpty() || line.startsWith("#")) {
						// Skip empty lines or lines starting with #}
					} else if (line.startsWith("help")) {
						printHelp();
					} else {
						throw new ApplicationException(
								"Valid commands are help, adduser, addrule, listuser, listrule, listgroup, deluser, delrule, delgroup");
					}
				} catch (Exception e) {
					if (interactive) {
						System.err.println(e.getMessage());
					} else {
						abort(e.getMessage());
					}
				}
			}
		} catch (IOException e) {
			abort(e.getMessage());
		}

	}

	private static void printHelp() {
		Para p = new Para(WIDTH);
		p.add("The program has the ability to add, list and delete users, groups and rules.");
		p.add("Valid commands are help, adduser, addrule, listuser, listrule, listgroup, deluser, delrule and delgroup.");
		System.out.println(p);

		p = new Para(WIDTH);
		p.add("adduser syntax is: adduser <username> <groupname> where the user and group are created as necessary and linked");
		System.out.println(p);

		p = new Para(WIDTH);
		p.add("addrule syntax is: addrule <groupname> <what> <crud> where 'crud' is letters from the set C,R,U,D and 'what' is the rule. 'What' may have the special value 'ALL' to apply the specified 'crud' to all tables for that group.");
		System.out.println(p);

		p = new Para(WIDTH);
		p.add("All the list commands will take an extra parameter to match against the");
		p.add("name of a user, the name of a group, or the name of the group to which a rule");
		p.add("applies. The parameter may contain SQL style wild cards.");
		System.out.println(p);

		p = new Para(WIDTH);
		p.add("The del commands all expect the id of the object to be deleted. This can");
		p.add(" be obtained from the corresponding list command");
		System.out.println(p);
	}

	private static void search(String string, TableType tt) throws IcatException_Exception {

		List<Object> os = icatEP.search(sessionId, string);
		for (Object o : os) {
			if (tt == TableType.User) {
				User u = (User) o;
				System.out.print(u.getId() + " : " + u.getName() + " [");
				boolean first = true;
				for (UserGroup ug : u.getUserGroups()) {
					if (first) {
						first = false;
					} else {
						System.out.print(", ");
					}
					System.out.print(ug.getGroup().getName());
				}
				System.out.println("]");
			} else if (tt == TableType.Rule) {
				Rule r = (Rule) o;
				Group g = r.getGroup();
				String name = g == null ? "NULL" : g.getName();
				System.out.println(r.getId() + " : " + name + " " + r.getWhat() + " "
						+ r.getCrudFlags());
			} else if (tt == TableType.Group) {
				Group g = (Group) o;
				System.out.print(g.getId() + " : " + g.getName() + " [");
				boolean first = true;
				for (UserGroup ug : g.getUserGroups()) {
					if (first) {
						first = false;
					} else {
						System.out.print(", ");
					}
					System.out.print(ug.getUser().getName());
				}
				System.out.println("]");
			}
		}

	}

	private static void abort(String msg) {
		System.err.println(msg);
		System.exit(1);
	}

	private static void addRule(String groupName, String what, String crudFlags)
			throws IcatException_Exception, ApplicationException {
		Rule rule = new Rule();
		if (groupName != null) {
			List<Object> os = icatEP.search(sessionId, "Group[name ='" + groupName + "']");
			if (os.size() != 1) {
				throw new ApplicationException("Group " + groupName + " does not exist");
			}
			Group g = (Group) os.get(0);
			rule.setGroup(g);
		}
		rule.setWhat(what);
		rule.setCrudFlags(crudFlags);
		icatEP.create(sessionId, rule);
	}

	private static void addUserGroupMember(String userName, String groupName)
			throws IcatException_Exception {
		Group group = null;

		List<Object> os = icatEP.search(sessionId, "Group[name ='" + groupName + "']");
		if (os.size() == 0) {
			group = new Group();
			group.setName(groupName);
			group.setId(icatEP.create(sessionId, group));
		} else if (os.size() == 1) {
			group = (Group) os.get(0);
		}

		User user = null;
		os = icatEP.search(sessionId, "User[name ='" + userName + "']");

		if (os.size() == 0) {
			user = new User();
			user.setName(userName);
			user.setId(icatEP.create(sessionId, user));
		} else if (os.size() == 1) {
			user = (User) os.get(0);
		}

		UserGroup userGroup = new UserGroup();
		userGroup.setUser(user);
		userGroup.setGroup(group);

		try {
			icatEP.create(sessionId, userGroup);
		} catch (IcatException_Exception e) {
			if (e.getFaultInfo().getType() == IcatExceptionType.OBJECT_ALREADY_EXISTS) {
				System.out.println("Ignore exception " + e.getMessage());
			} else {
				throw e;
			}
		}

	}

}
