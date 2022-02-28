package org.yogusoft;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.yogusoft.consoleUtils.ConsoleInput;

import java.io.ByteArrayOutputStream;

public class App {
    public static void main(String[] args) {
        Session session = null;
        ChannelExec channel = null;

        try {
            System.out.print("Enter the address of the machine (default: 127.0.0.1): ");
            String hostAddress = ConsoleInput.getString("127.0.0.1");

            System.out.print("Enter the port of the machine: ");
            int hostPort = ConsoleInput.getPositive_Integer();

            System.out.print("Enter username (default: logmanager): ");
            String hostUsername = ConsoleInput.getString("logmanager");

            session = new JSch().getSession(hostUsername, hostAddress, hostPort);

            System.out.print("Enter password (default: 1234): ");
            String hostPassword = ConsoleInput.getString("1234");
            session.setPassword(hostPassword);

            session.setConfig("StrictHostKeyChecking", "no");

            System.out.println("---------------------");
            System.out.println("connecting...");
            session.connect();
            System.out.println("-----------------------");
            System.out.println("connected");
            System.out.println("-----------------------");

            channel = (ChannelExec) session.openChannel("exec");

            System.out.print("Enter log file name: ");
            String fileName = ConsoleInput.getString("");

            channel.setCommand("cat /var/log/" + fileName + ".log");

            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);

            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            String responseString = new String(responseStream.toByteArray());

            if (responseString.isEmpty()) System.out.println("No such file or directory");
            else System.out.println(responseString);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null) session.disconnect();
            if (channel != null) channel.disconnect();
        }

    }
}
