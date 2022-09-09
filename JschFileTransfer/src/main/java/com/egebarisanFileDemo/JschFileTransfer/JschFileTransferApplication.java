package com.egebarisanFileDemo.JschFileTransfer;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JschFileTransferApplication {

	public static void main(String[] args) {
		String localPath = "C:/Users/EGE/Desktop/localFile";
		String fileName =  "/phones.xlsx";
		String sftpPath = "RemoteServerPath";
		String sftpHost = "RemoteServerHostName";
		String sftpPort = "22";
		String sftpUser = "USERNAME";
		String sftpPassword = "PASSWORD";

		try{
			/**
			 * Open session to sftp server
			 */
			JSch jsch = new JSch();
			Session session = jsch.getSession(sftpUser, sftpHost, Integer.valueOf(sftpPort));
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(sftpPassword);
			System.out.println("Connecting------");
			session.connect();
			System.out.println("Established Session");

			Channel channel = session.openChannel("sftp");
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.connect();

			System.out.println("Opened sftp Channel");

			/**
			 * Do everything you need in sftp
			 */
			System.out.println("Copying file to Host");
			sftpChannel.put(localPath+"/"+fileName, sftpPath);
			System.out.println("Copied file to Host");

			System.out.println("Copying file from Host to Local");
			sftpChannel.get(sftpPath+"/"+fileName, localPath);
			System.out.println("Copied file from Host to local");

			sftpChannel.disconnect();
			session.disconnect();

			System.out.println("Disconnected from sftp");

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
