package org.none.toka.util.handlers.path.select;

import java.net.URI;

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
import org.none.toka.util.handlers.path.NotFoundException;

public class ResourceSelector implements Selector {
	private static final ResourceSelector INSTANCE = new ResourceSelector();
	public static Selector getInstance() {
		return INSTANCE;
	}
	
	private ResourceSelector() {
	}
	
	/* (non-Javadoc)
	 * @see org.none.toka.util.handlers.path.select.Selector#getPath(java.lang.Object)
	 */
	public IPath getPath(Object type) throws NotFoundException {
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
