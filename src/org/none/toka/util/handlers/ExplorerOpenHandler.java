package org.none.toka.util.handlers;

import java.net.URI;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.none.toka.util.handlers.command.OpenDirectory;

public class ExplorerOpenHandler extends AbstractHandler {
	private static class NotFoundException extends Exception {
		private static final long serialVersionUID = 1L;
		NotFoundException(String message) {
			super(message);
		}
		NotFoundException(String message, Throwable cause) {
			super(message, cause);
		}
	}
	
	/**
	* The constructor.
	*/
	public ExplorerOpenHandler() {
	}
	
	/**
	* the command has been executed, so extract extract the needed information
	* from the application context.
	*/
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			Object type = getSelectedObject(event);
			OpenDirectory.execute(getPathOfObject(type));
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
	
	private IPath getPathOfObject(Object type) throws NotFoundException {
		if (type instanceof IJavaElement) {
			return getLocationPath((IJavaElement) type);
		}
		if (type instanceof IResource) {
			return getLocationPath((IResource) type);
		}
		throw new NotFoundException("Path not found");
	}
	
	private IPath getLocationPath(IJavaElement type) throws NotFoundException {
		IJavaElement targetType = type;
		for (long i = 0; i < Long.MAX_VALUE; i++) {
			if (isTarget(targetType)) { break; }
			targetType = targetType.getParent();
		}
		IResource resource = null;
		try {
			resource = targetType.getCorrespondingResource();
		} catch (JavaModelException e) {
			throw new NotFoundException("Path: " + e.getMessage(), e);
		}
		if (resource != null) {
			return getLocationPath(resource);
		}
		return targetType.getPath();
	}

	private boolean isTarget(IJavaElement type) {
		if (type instanceof IProject) { return true; }
		if (type instanceof IJavaProject) { return true; }
		if (type instanceof IPackageFragmentRoot) { return true; }
		if (type instanceof IPackageFragment) { return true; }
		if (type instanceof ICompilationUnit) { return true; }
		return false;
	}

	private IPath getLocationPath(IResource resource) {
		try {
			URI uri = resource.getLocationURI();
			IFileStore store = EFS.getStore(uri);
			IPath ipath = URIUtil.toPath(store.toURI());
			return ipath;
		} catch (CoreException e) {
			return null;
		}
	}
}