package org.none.toka.util.core.commands.call.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.none.toka.util.core.commands.call.CallCommand;

public final class CallExplorer implements CallCommand {
	private static final List<String> EXPLORER = new ArrayList<String>();
	static {
		EXPLORER.add("explorer");
		EXPLORER.add("/e,");
	}

	public CallExplorer() {
	}

	@Override
	public boolean execute(IPath path) {
		List<String> command = new LinkedList<String>(EXPLORER);
		List<String> targetArgs = getTargetArgs(path);
		command.addAll(targetArgs);
		ProcessBuilder pb = new ProcessBuilder(command);
		try {
			pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private List<String> getTargetArgs(IPath ipath) {
		List<String> ret = new ArrayList<String>();
		if (ipath.toFile().isFile()) {
			ret.add("/select,");
			ret.add(ipath.toFile().getAbsolutePath());
			return ret;
		}
		ret.add(ipath.toFile().getAbsolutePath());
		return ret;
	}
}
