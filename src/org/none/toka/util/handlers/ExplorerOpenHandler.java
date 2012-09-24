package org.none.toka.util.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.none.toka.util.core.commands.api.OpenExplorer;
import org.none.toka.util.handlers.path.NotFoundException;
import org.none.toka.util.handlers.path.select.ResourceSelector;

public class ExplorerOpenHandler extends AbstractHandler {
	private final ResourceSelector selector;
	
	/**
	* The constructor.
	*/
	public ExplorerOpenHandler() {
		this.selector = new ResourceSelector();
	}
	
	/**
	* the command has been executed, so extract extract the needed information
	* from the application context.
	*/
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			Object type = getSelectedObject(event);
			OpenExplorer.execute(selector.getPath(type));
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Object getSelectedObject(ExecutionEvent event) throws NotFoundException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (!(currentSelection instanceof IStructuredSelection)) {
			throw new NotFoundException("No Object selected");
		}
		IStructuredSelection sSelection = (IStructuredSelection) currentSelection;
		
		Iterator<?> iterator = sSelection.iterator();
		if (iterator.hasNext()) { return iterator.next(); }
		
		throw new NotFoundException("No Object selected");
	}
}