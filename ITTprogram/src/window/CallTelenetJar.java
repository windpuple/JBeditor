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

		// 2. 세션 객체를 생성한다 (사용자 이름, 접속할 호스트, 포트를 인자로 준다.)
		try {
			// 1. JSch 객체를 생성한다.
			JSch jsch = new JSch();
			session = jsch.getSession(username, host, port);

			// 3. 패스워드를 설정한다.
			session.setPassword(password);

			// 4. 세션과 관련된 정보를 설정한다.
			java.util.Properties config = new java.util.Properties();
			// 4-1. 호스트 정보를 검사하지 않는다.
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			// 5. 접속한다.
			session.connect();

			// 6. sftp 채널을 연다.
			channel = session.openChannel("exec");

			// 8. 채널을 SSH용 채널 객체로 캐스팅한다
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
