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
            System.out.print("Enter the address of the machine: ");
            String hostAddress = ConsoleInput.getString();

            System.out.print("Enter the port of the machine: ");
            int hostPort = ConsoleInput.getPositive_Integer();

            System.out.print("Enter username: ");
            String hostUsername = ConsoleInput.getString();

            session = new JSch().getSession(hostUsername, hostAddress, hostPort);

            System.out.print("Enter password: ");
            String hostPassword = ConsoleInput.getString();
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
            String fileName = ConsoleInput.getString();

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
