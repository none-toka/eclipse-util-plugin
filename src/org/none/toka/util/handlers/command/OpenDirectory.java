package org.none.toka.util.handlers.command;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IPath;

public final class OpenDirectory {
	private static volatile OpenDirectory instance;
	
	public static boolean execute(IPath path) {
		synchronized (OpenDirectory.class) {
			if (instance == null) {
				instance = new OpenDirectory();
			}
		}
		
		instance.callExplorer(path);
		return true;
	}
	
	private void callExplorer(IPath path) {
		if (path == null || path.isEmpty()) {
			return;
		}
		String targetPath = getTargetPathString(path);
		// TODO delete
		System.out.println("args: " + targetPath);
		
		if (!Desktop.isDesktopSupported()) {
			callProcess(targetPath);
			return;
		}
		
		Desktop desktop = Desktop.getDesktop();
		if (!desktop.isSupported(Desktop.Action.OPEN)) {
			callProcess(targetPath);
			return;
		}
		
		try {
			desktop.open(new File(targetPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void callProcess(String targetPath) {
		ProcessBuilder pb = new ProcessBuilder("explorer", targetPath);
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
	
	private String getTargetPathString(IPath ipath) {
		if ("jar".equals(ipath.getFileExtension())) {
			String path = ipath.makeAbsolute().toFile().getParent();
			return path;
		}
		return ipath.toFile().getAbsolutePath();
	}
}
