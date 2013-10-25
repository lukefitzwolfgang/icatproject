public class Para {

	private StringBuilder text;
	private int width;

	public Para(int width) {
		text = new StringBuilder();
		this.width = width;
	}

	public void add(String string) {
		if (text.length() != 0) {
			text.append(' ');
		}
		text.append(string);
	}

	@Override
	public String toString() {
		StringBuilder formatted = new StringBuilder('\n');
		while (true) {

			if (text.length() > width) {

				int n = text.substring(0, width).lastIndexOf(' ');
				if (n < 0) {
					n = text.indexOf(" ");
				}
				if (n < 0) {
					n = text.length();
				}
				formatted.append("\n" + text.substring(0, n));

				text.delete(0, n + 1);
			} else {
				formatted.append("\n" + text.toString());
				break;
			}
		}

		return formatted.toString();

	}

}
