package org.none.toka.util.handlers.command.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IPath;
import org.none.toka.util.handlers.command.OpenCommand;

public final class CallExplorer implements OpenCommand {
	public CallExplorer() {
	}

	@Override
	public boolean execute(IPath path) {
		String targetPath = getTargetPathString(path);
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
		return true;
	}
	
	private String getTargetPathString(IPath ipath) {
		if ("jar".equals(ipath.getFileExtension())) {
			String path = ipath.makeAbsolute().toFile().getParent();
			return path;
		}
		return ipath.toFile().getAbsolutePath();
	}
}
