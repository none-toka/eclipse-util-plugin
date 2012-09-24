package org.none.toka.util.core.commands.call.util;

import java.io.IOException;
import java.util.List;

public final class TinyProcessExecuter {
	private TinyProcessExecuter() {
	}
	
	public static void execute(List<String> command) {
		// TODO: delete
		System.err.println(command);
		
		ProcessBuilder pb = new ProcessBuilder(command);
		try {
			pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
