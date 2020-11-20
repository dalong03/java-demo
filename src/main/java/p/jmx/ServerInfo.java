package p.jmx;

public class ServerInfo implements ServerInfoMBean {
	public int getExecutedSqlCmdCount() {
		int result = Double.valueOf(Math.random() * 100).intValue();
		return result;
	}

	public void printString(String fromJConsole) {
		System.out.println(fromJConsole);
	}
}
