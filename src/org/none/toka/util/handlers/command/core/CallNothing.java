package org.none.toka.util.handlers.command.core;

import org.eclipse.core.runtime.IPath;
import org.none.toka.util.handlers.command.OpenCommand;

public final class CallNothing implements OpenCommand {
	public CallNothing() {
	}

	@Override
	public boolean execute(IPath path) {
		return true;
	}
}
