package hr.fer.zemris.java.p10.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

// 127.0.0.1
public class UDPKlijent {

	public static void main(String[] args) throws SocketException, UnknownHostException {

		if (args.length != 4) {
			System.out.println("Očekivao sam host port broj1 broj2");
			return;
		}

		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		short broj1 = Short.parseShort(args[2]);
		short broj2 = Short.parseShort(args[3]);

		// pripremi podatke za upit
		byte[] podatci = new byte[4];
		ShortUtil.shortToBytes(broj1, podatci, 0);
		ShortUtil.shortToBytes(broj2, podatci, 2);

		// stvori pristupnu točku klijenta
		DatagramSocket dSocket = new DatagramSocket();

		// umetni podatke u paket i paketu postavi adresu i port poslužitelja
		DatagramPacket packet = new DatagramPacket(podatci, podatci.length);
		packet.setSocketAddress(new InetSocketAddress(InetAddress.getByName(hostname), port));

		// postavi timeout od 4 sekunde na primitak odgovora
		dSocket.setSoTimeout(4000);

		// šalji upite dok ne dobijem odgovor
		boolean answerReceived = false;
		while (!answerReceived) {
			System.out.println("Šaljem upit...");
			try {
				dSocket.send(packet);
			} catch (IOException e) {
				System.out.println("Ne mogu poslati upit.");
				break;
			}

			// pripremi prazan paket za primitak odgovora
			byte[] recvBuffer = new byte[4];
			DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);

			// čekaj na odgovor
			try {
				dSocket.receive(recvPacket);
			} catch (SocketTimeoutException ste) {
				// ako je isteko timeout, ponovno pošalji upit
				continue;
			} catch (IOException e) {
				// u slučaju drugih pogrešaka....
				continue;
			}

			// analiziraj sadržaj
			if (recvPacket.getLength() != 2) {
				System.out.println("Primljen je odg neočekivane dulj");
				break;
			}

			// ispiši rezultat i prkini slanje retransmisije
			System.out.println("Rezultat je: " + ShortUtil.bytesToShort(recvPacket.getData(), recvPacket.getOffset()));
			answerReceived = true;
		}
		
		// zatvoriti pristupnu točku
		dSocket.close();
	}
}
