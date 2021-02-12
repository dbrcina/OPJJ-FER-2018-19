package hr.fer.zemris.java.p10.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * Klijent koji šalje poruku sa charsetom UTF-8
 * 
 * @author dbrcina
 *
 */
public class UDPKlijent2 {

	public static void main(String[] args) throws SocketException, UnknownHostException {

		if (args.length != 3) {
			System.out.println("Očekivao sam host port poruka");
			return;
		}

		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		String poruka = args[2];

		// pripremi podatke za upit
		byte[] bajtovi = poruka.getBytes(StandardCharsets.UTF_8);
		byte[] podatci = new byte[2 + bajtovi.length];
		ShortUtil.shortToBytes((short) bajtovi.length, podatci, 0);
		int i = 2;
		for (byte b : bajtovi) {
			podatci[i++] = b;
		}

		// stvori pristupnu točku klijenta
		DatagramSocket dSocket = new DatagramSocket();

		// umetni podatke u paket i paketu postavi adresu i port poslužitelja
		DatagramPacket packet = new DatagramPacket(podatci, podatci.length);
		packet.setSocketAddress(new InetSocketAddress(InetAddress.getByName(hostname), port));

		try {
			dSocket.send(packet);
		} catch (IOException e) {
			System.out.println("Ne mogu poslati upit.");
		}

		// zatvoriti pristupnu točku
		dSocket.close();
	}
}
