package pl.whipsoft.kindleturn;

public enum COMMAND {
	NEXT_PAGE("echo \"send 191\" > /proc/keypad"), 
	PREVIOUS_PAGE("echo \"send 193\" > /proc/keypad");
	private String linux_command;

	public String getLinux_command() {
		return linux_command;
	}

	private COMMAND(String linux_command) {
		this.linux_command = linux_command;
	}
}
