package gemx.gemx;

public class GemXMain {
	public static void main(String[] args) {
		final WidgetsFactory widgetsFactory = new WidgetsFactory();
		if (args.length >= 1 && args[0].equals("--execution-module-directory-debug")) {
			gemx.utility.ExecutionModuleDirectory.setDebugMode(true);
			String[] args2 = new String[args.length - 1];
			for (int i = 0; i < args2.length; ++i) {
				args2[i] = args[i + 1];
			}
			args = args2;
		}
		gemx.ccfinderx.CCFinderX.theInstance.setModuleDirectory(gemx.utility.ExecutionModuleDirectory.get());
		Main.main(args, widgetsFactory);
	}
}
