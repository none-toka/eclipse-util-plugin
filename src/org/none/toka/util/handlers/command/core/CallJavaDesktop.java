package org.none.toka.util.handlers.command.core;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.none.toka.util.handlers.command.OpenCommand;

public final class CallJavaDesktop implements OpenCommand {
	public CallJavaDesktop() {
	}

	@Override
	public boolean execute(IPath path) {
		try {
			Desktop desktop = Desktop.getDesktop();
			String targetPath = getTargetPathString(path);
			desktop.open(new File(targetPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private String getTargetPathString(IPath ipath) {
		if (ipath.toFile().isFile()) {
			String path = ipath.makeAbsolute().toFile().getParent();
			return path;
		}
		return ipath.toFile().getAbsolutePath();
	}
}
