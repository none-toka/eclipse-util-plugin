package org.none.toka.util.core.commands.api;

import java.awt.Desktop;

import org.eclipse.core.runtime.IPath;
import org.none.toka.util.core.commands.call.CallCommand;
import org.none.toka.util.core.commands.call.impl.CallExplorer;
import org.none.toka.util.core.commands.call.impl.CallJavaDesktop;
import org.none.toka.util.core.commands.call.impl.CallNothing;

/**
 * Command for opening explorer
 * 
 * @author none_toka
 *
 */
public final class OpenExplorer {
	private static volatile OpenExplorer instance;
	
	/**
	 * open explorer for the given path
	 * 
	 * @param path
	 * @return
	 */
	public static boolean execute(IPath path) {
		synchronized (OpenExplorer.class) {
			if (instance == null) {
				instance = new OpenExplorer();
			}
		}
		
		instance.open(path);
		return true;
	}
	
	private CallCommand command;
	
	private OpenExplorer() {
		decideOpenMethod();
	}
	
	private void decideOpenMethod() {
		String osName = System.getProperty("os.name");
		if (osName.startsWith("windows") || osName.startsWith("Windows")) {
			command = new CallExplorer();
			return;
		}
		
		if (!Desktop.isDesktopSupported()) {
			command = new CallNothing();
			return;
		}
		
		// MINE: 恐らく Desktop.getDesktop は sigleton なので、このクラスで参照を持つ必要はない
		Desktop desktop = Desktop.getDesktop();
		if (!desktop.isSupported(Desktop.Action.OPEN)) {
			command = new CallNothing();
		}
		
		command = new CallJavaDesktop();
	}

	private void open(IPath path) {
		if (path == null || path.isEmpty()) {
			return;
		}
		command.execute(path);
	}
}
