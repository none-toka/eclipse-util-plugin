package org.none.toka.util.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.none.toka.util.core.commands.api.open.OpenAPI;
import org.none.toka.util.handlers.path.NotFoundException;
import org.none.toka.util.handlers.path.select.Selector;

class UniversalOpenHandler extends AbstractHandler {
	private final Selector selector;
	private final OpenAPI opener;
	
	/**
	* The constructor.
	*/
	public UniversalOpenHandler(Selector selector, OpenAPI opener) {
		this.selector = selector;
		this.opener = opener;
	}
	
	/**
	* the command has been executed, so extract extract the needed information
	* from the application context.
	*/
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			Object type = getSelectedObject(event);
			if (type == null) { return null; }
			opener.execute(selector.getPath(type));
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Object getSelectedObject(ExecutionEvent event) {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (!(currentSelection instanceof IStructuredSelection)) {
			return null;
		}
		IStructuredSelection sSelection = (IStructuredSelection) currentSelection;
		
		Iterator<?> iterator = sSelection.iterator();
		if (iterator.hasNext()) { return iterator.next(); }
		
		return null;
	}
}
