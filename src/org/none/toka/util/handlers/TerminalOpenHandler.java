package org.none.toka.util.handlers;

import org.none.toka.util.core.commands.api.open.impl.OpenTerminal;
import org.none.toka.util.handlers.path.select.ResourceSelector;

public class TerminalOpenHandler extends UniversalOpenHandler {
	/**
	* The constructor.
	*/
	public TerminalOpenHandler() {
		super(ResourceSelector.getInstance(), OpenTerminal.getInstance());
	}
}