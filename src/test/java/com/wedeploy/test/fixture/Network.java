package com.wedeploy.test.fixture;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;

import java.util.Enumeration;

/**
 * @author Ciro Costa
 */
public class Network {

	public static String getHostIp() {
		try {
			Enumeration<NetworkInterface> itNetwork =
				NetworkInterface.getNetworkInterfaces();

			while (itNetwork.hasMoreElements()) {
				NetworkInterface network = itNetwork.nextElement();

				if (!network.isUp()) {
					continue;
				}

				if (network.isLoopback()) {
					continue;
				}

				if (network.isPointToPoint()) {
					continue;
				}

				Enumeration<InetAddress> itAddress = network.getInetAddresses();

				while (itAddress.hasMoreElements()) {
					InetAddress address = itAddress.nextElement();

					if (!(address instanceof Inet4Address)) {
						continue;
					}

					if (address.isAnyLocalAddress()) {
						continue;
					}

					if (address.isLoopbackAddress()) {
						continue;
					}

					if (address.isMulticastAddress()) {
						continue;
					}

					return address.getHostAddress();
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Couldn't get host ip address.");
		}

		return null;
	}

	public static boolean isTcpPortOpen(String ip, int port, int timeout) {
		try {
			Socket socket = new Socket();

			socket.connect(new InetSocketAddress(ip, port), timeout);
			socket.close();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

}

