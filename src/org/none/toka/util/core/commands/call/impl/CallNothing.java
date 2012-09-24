package org.none.toka.util.core.commands.call.impl;

import org.eclipse.core.runtime.IPath;
import org.none.toka.util.core.commands.call.CallCommand;

public final class CallNothing implements CallCommand {
	public CallNothing() {
	}

	@Override
	public boolean execute(IPath path) {
		return true;
	}
}
