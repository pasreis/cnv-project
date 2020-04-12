package cnv.g33;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class URIArgumentParser {
	private String parseRequestBody(InputStream is) throws IOException {
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);

		int b;
		StringBuilder buf = new StringBuilder(512);

		while ((b = br.read()) != -1) {
			buf.append((char) b);
		}

		br.close();
		isr.close();

		return buf.toString();
	}

	private ArrayList<String> parseRequestHeader(HttpExchange t) {
		// Get the query
		final String query = t.getRequestURI().getQuery();
		System.out.println("> Query:\t" + query);

		// Break it down into String[]
		final String[] params = query.split("&");

		// Store as if it was a direct call to SolverMain
		final ArrayList<String> newArgs = new ArrayList<String>();
		for (final String p : params) {
			final String[] splitParam = p.split("=");
			newArgs.add("-" + splitParam[0]);
			newArgs.add(splitParam[1]);
		}

		return newArgs;
	}

	private String[] convertArrayListToArray(ArrayList<String> arrayList) {
		// Store from array list to regular String[]
		final String[] array = new String[arrayList.size()];
		int i = 0;

		for (String arg : arrayList) {
			array[i] = arg;
			++i;
		}

		return array;
	}

	public String[] parseCompleteQuery(HttpExchange t) throws IOException {
		ArrayList<String> newArgs = parseRequestHeader(t);

		newArgs.add("-b");
		newArgs.add(parseRequestBody(t.getRequestBody()));

		newArgs.add("-d");

		return convertArrayListToArray(newArgs);
	}

	public String[] parseQueryHeader(HttpExchange t) {
		ArrayList<String> args = parseRequestHeader(t);
		return convertArrayListToArray(args);
	}
}
