package org.none.toka.util.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;

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
			callExplorer(getPathOfObject(type));
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
		IResource resource = null;
		try {
			resource = type.getCorrespondingResource();
		} catch (JavaModelException e) {
			throw new NotFoundException("Path: " + e.getMessage(), e);
		}
		if (resource != null) {
			return getLocationPath(resource);
		}
		return type.getPath();
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
	
	private void callExplorer(IPath path) {
		if (path == null || path.isEmpty()) {
			return;
		}
		String args = getTargetArgs(path);
		// TODO delete
		System.out.println("args: " + args);
		ProcessBuilder pb = new ProcessBuilder("explorer", args);
		try {
			pb.redirectErrorStream(true);
			Process process = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String read;
			while (null != (read = reader.readLine())) {
				System.out.println(read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getTargetArgs(IPath ipath) {
		if ("jar".equals(ipath.getFileExtension())) {
			String path = ipath.makeAbsolute().toFile().getParent();
			return path;
		}
		return ipath.toFile().getAbsolutePath();
	}
}