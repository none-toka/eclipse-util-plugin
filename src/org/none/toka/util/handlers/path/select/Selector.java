package org.none.toka.util.handlers.path.select;

import org.eclipse.core.runtime.IPath;
import org.none.toka.util.handlers.path.NotFoundException;

public interface Selector {

	IPath getPath(Object type) throws NotFoundException;

}