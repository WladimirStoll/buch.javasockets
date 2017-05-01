package com.bayern.stoll.wladimir.buch.javasockets;

import java.util.Enumeration;
import java.net.*;

public class InetAddressExample {

	public static void main(String[] args) {

		// Get the network interfaces and associated addresses for this host
		try {
			/**
			 * Get a list of this host’s network interfaces. The static method
			 * getNetworkInterfaces() returns a list containing an instance of
			 * NetworkInterface for each of the host’s interfaces
			 */
			Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces();
			if (interfaceList == null) {
				/**
				 * Check for empty list. The loopback interface is generally
				 * always included, even if the host has no other network
				 * connection, so this check will succeed only if the host has
				 * no networking subsystem at all.
				 */
				System.out.println("--No interfaces found--");
			} else {
				while (interfaceList.hasMoreElements()) {
					/**
					 * Get and print address(es) of each interface in the list:
					 * Print the interface’s name: line 15 The getName() method
					 * returns a local name for the interface. This is usually a
					 * combination of letters and numbers indicating the type
					 * and particular instance of the interface—for example,
					 * “lo0” or “eth0”. Get the addresses associated with the
					 * interface: The getInetAddresses() method returns another
					 * Enumeration, this time containing instances of
					 * InetAddress—one per address associated with the
					 * interface. Depending on how the host is configured, the
					 * list may contain only IPv4, only IPv6, or a mixture of
					 * both types of address. Check for empty list: Iterate
					 * through the list, printing each address: We check each
					 * instance to determine which subtype it is. (At this time
					 * the only subtypes of InetAddress are those listed, but
					 * conceivably there might be others someday.)
					 * getHostAddress() method of InetAddress returns a String
					 * representing the numerical address in the format
					 * appropriate for its specific type: dotted-quad for v4,
					 * colonseparated hex for v6. See the synopsis “String
					 * representations” below for a description of the different
					 * address formats.
					 */
					NetworkInterface iface = interfaceList.nextElement();
					System.out.println("Interface " + iface.getName() + ":");
					Enumeration<InetAddress> addrList = iface.getInetAddresses();
					if (!addrList.hasMoreElements()) {
						System.out.println("\t(No addresses for this interface)");
					}
					while (addrList.hasMoreElements()) {
						InetAddress address = addrList.nextElement();
						System.out.print("\tAddress " + ((address instanceof Inet4Address ? "(v4)"
								: (address instanceof Inet6Address ? "(v6)" : "(?)"))));
						System.out.println(": " + address.getHostAddress());
					}
				}
			}

		} catch (SocketException se) {
			System.out.println("Error getting network interfaces:" + se.getMessage());
		}

		// Get name(s)/address(es) of hosts given on command line
		for (String host : args) {
			try {
				System.out.println(host + ":");
				InetAddress[] addressList = InetAddress.getAllByName(host);
				for (InetAddress address : addressList) {
					System.out.println("\t" + address.getHostName() + "/" + address.getHostAddress());
				}
			} catch (UnknownHostException e) {
				System.out.println("\tUnable to find address for " + host);
			}
		}
	}
}