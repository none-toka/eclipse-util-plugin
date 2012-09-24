package org.none.toka.util.core.commands.api.open.impl;

import org.eclipse.core.runtime.IPath;
import org.none.toka.util.core.commands.api.open.OpenAPI;
import org.none.toka.util.core.commands.call.CallCommand;
import org.none.toka.util.core.commands.call.impl.CallDosPropmpt;
import org.none.toka.util.core.commands.call.impl.CallNothing;

/**
 * Command for opening explorer
 * 
 * @author none_toka
 *
 */
public final class OpenTerminal implements OpenAPI {
	private static volatile OpenTerminal instance;
	
	/**
	 * open explorer for the given path
	 * 
	 * @param path
	 * @return
	 */
	public static OpenAPI getInstance() {
		synchronized (OpenTerminal.class) {
			if (instance == null) {
				instance = new OpenTerminal();
			}
		}
		
		return instance;
	}
	
	private CallCommand command;
	
	private OpenTerminal() {
		decideOpenMethod();
	}
	
	public synchronized boolean execute(IPath path) {
		open(path);
		return true;
	}
	
	private void decideOpenMethod() {
		command = new CallNothing();
		
		String osName = System.getProperty("os.name");
		if (osName.startsWith("windows") || osName.startsWith("Windows")) {
			command = new CallDosPropmpt();
			return;
		}
	}

	private void open(IPath path) {
		if (path == null || path.isEmpty()) {
			return;
		}
		command.execute(path);
	}
}
