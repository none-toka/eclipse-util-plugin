package org.none.toka.util.core.commands.call.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.none.toka.util.core.commands.call.CallCommand;
import org.none.toka.util.core.commands.call.util.TinyProcessExecuter;

public final class CallDosPropmpt implements CallCommand {
	private static final List<String> COMMAND_BASE = new ArrayList<String>();
	static {
		COMMAND_BASE.add("cmd");
		COMMAND_BASE.add("/c");
		COMMAND_BASE.add("start");
		COMMAND_BASE.add("/d");
	}

	public CallDosPropmpt() {
	}

	@Override
	public boolean execute(IPath path) {
		List<String> command = new LinkedList<String>(COMMAND_BASE);
		command.addAll(getTargetArgs(path));
		TinyProcessExecuter.execute(command);
		return true;
	}
	
	private List<String> getTargetArgs(IPath ipath) {
		List<String> ret = new ArrayList<String>();
		if (ipath.toFile().isFile()) {
			ret.add(ipath.makeAbsolute().toFile().getParent());
			return ret;
		}
		ret.add(ipath.toFile().getAbsolutePath());
		return ret;
	}
}
