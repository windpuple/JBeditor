package window;

import javax.swing.JOptionPane;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class CallTelenetJar {

	public CallTelenetJar() {
		String username = "prod";
		String host = "10.10.120.29";
		int port = 22;
		String password = "prod123";

		System.out.println("==> Connecting to" + host);
		Session session = null;
		Channel channel = null;
		Channel channelsftp = null;

		// 2. ���� ��ü�� �����Ѵ� (����� �̸�, ������ ȣ��Ʈ, ��Ʈ�� ���ڷ� �ش�.)
		try {
			// 1. JSch ��ü�� �����Ѵ�.
			JSch jsch = new JSch();
			session = jsch.getSession(username, host, port);

			// 3. �н����带 �����Ѵ�.
			session.setPassword(password);

			// 4. ���ǰ� ���õ� ������ �����Ѵ�.
			java.util.Properties config = new java.util.Properties();
			// 4-1. ȣ��Ʈ ������ �˻����� �ʴ´�.
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			// 5. �����Ѵ�.
			session.connect();

			// 6. sftp ä���� ����.
			channel = session.openChannel("exec");

			// 8. ä���� SSH�� ä�� ��ü�� ĳ�����Ѵ�
			ChannelExec channelExec = (ChannelExec) channel;

			System.out.println("==> Connected to" + host);

			channelExec.setCommand("touch ./jschTest.txt");
			channelExec.connect();

			System.out.println("==> Connected and made jschTest file in " + host);

			channelsftp = session.openChannel("sftp");
			channelsftp.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channelsftp;
			try {
				sftpChannel.get("./jschTest.txt", "localfile.txt");
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("==> Connected and get jschTest file to my PC localfile.txt");
			
			sftpChannel.exit();

		} catch (JSchException e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				channel.disconnect();
				channelsftp.disconnect();
				
			}
			if (session != null) {
				session.disconnect();
			}
		}
	}

}
