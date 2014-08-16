package pl.whipsoft.kindleturn;

import java.io.InputStream;

import android.os.AsyncTask;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SendCommandTask extends AsyncTask<COMMAND, Void, Void> {

	private static String PASSWORD = null;
	private static String IP = null;
	private static String USERNAME = null;

	public static void setPrefs(String ip, String username, String password) {
		PASSWORD = password;
		USERNAME = username;
		IP = ip;
	}

	private void sendCommand(String command) {
		try {
			JSch jsch = new JSch();

			Session session = jsch.getSession(USERNAME, IP, 22);

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			// If two machines have SSH passwordless logins setup, the following
			// line is not needed:
			session.setPassword(PASSWORD);
			session.connect();

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			// channel.setInputStream(System.in);
			channel.setInputStream(null);

			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: "
							+ channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(500);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	protected Void doInBackground(COMMAND... params) {
		sendCommand(params[0].getLinux_command());
		return null;
	}
}
