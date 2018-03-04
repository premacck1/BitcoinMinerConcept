import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.xml.bind.DatatypeConverter;

class Miner implements Runnable {
	private int port;
	private String ipAdd;
	private Socket socket;
	private DataOutputStream os;
	private DataInputStream is;

	public Miner(int port, String ip) {
		this.port = port;
		this.ipAdd = ip;
	}

	void start() {
		try {
			socket = new Socket(ipAdd, port);
			System.out.println("Socket is Connected: " + socket.isConnected() + " IP Add: " + ipAdd + " port: " + port);
			os = new DataOutputStream(socket.getOutputStream());
			is = new DataInputStream(socket.getInputStream());

			int numOfLeadZeros = is.readInt();
			System.out.println("Finding number of leading zeros "+ numOfLeadZeros);
			
			if (numOfLeadZeros != -1) {
				String out = mine(numOfLeadZeros);
				if (out != null) {
					String sentString = "Success, I found a solution " + Thread.currentThread().getName() + out
							+ " Over";
					os.write(sentString.getBytes());
				}
			}
			os.close();
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String mine(int numOfLeadZeros) throws IOException {
		Random rand = new Random();
		String prev = "prev";
		String nounce = String.valueOf(rand.nextInt());
		String inp = prev + nounce;
		boolean out = false;
		while (!out) {
			if (is.available()>0 && is.read() == -1) {
				return null;
			}
			nounce = String.valueOf(rand.nextInt());
			inp = prev + nounce;
			out = mineHelper(inp, numOfLeadZeros);
		}
		return inp;
	}

	private boolean mineHelper(String inp, int numZeros) {
		MessageDigest d;
		String outStr = "";
		try {
			System.out.println(Thread.currentThread().getName()+" Mining with input "+inp);
			d = MessageDigest.getInstance("SHA-256");
			byte[] out = d.digest(inp.getBytes());
			outStr = DatatypeConverter.printHexBinary(out);
			System.out.println(Thread.currentThread().getName()+" Mining with output "+ outStr);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count = 0;
		for (char c : outStr.toCharArray()) {
			if (c == '0')
				count++;
			else
				break;
		}
		return count == numZeros;
	}

	@Override
	public void run() {
		start();
	}
}
