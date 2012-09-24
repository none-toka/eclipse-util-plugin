package org.none.toka.util.handlers;

import org.none.toka.util.core.commands.api.open.impl.OpenExplorer;
import org.none.toka.util.handlers.path.select.ResourceSelector;

public class ExplorerOpenHandler extends UniversalOpenHandler {
	/**
	* The constructor.
	*/
	public ExplorerOpenHandler() {
		super(ResourceSelector.getInstance(), OpenExplorer.getInstance());
	}
}