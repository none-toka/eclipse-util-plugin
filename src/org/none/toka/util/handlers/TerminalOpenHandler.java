package org.none.toka.util.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class TerminalOpenHandler extends AbstractHandler {
	/**
	* The constructor.
	*/
	public TerminalOpenHandler() {
	}
	
	/**
	* the command has been executed, so extract extract the needed information
	* from the application context.
	*/
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("this is test");
		return null;
	}
}