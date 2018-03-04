import java.io.*;
import java.net.*;

public class MinerServer {
	private ServerSocket server = null;
	private Socket socket = null;
	private int port;
	

	public MinerServer(int port) {
		this.port = port;
	}

	private void start() throws IOException {
		try {
			server = new ServerSocket(port);
			System.out.println("Starting Server on port " + port);
			while (true) {
				socket = server.accept();
				new Thread(new HandleMiners(socket)).start();
			}
		}catch(Exception e) {
			e.printStackTrace();
			server.close();
		} 
	}

	/*private static String mine(int numOfLeadZeros) {
		Random rand = new Random();
		String prev = "prev";
		String nounce = String.valueOf(rand.nextInt());
		String inp = prev + nounce;
		while (!mineHelper(inp, numOfLeadZeros)) {
			nounce = String.valueOf(rand.nextInt());
			inp = prev + nounce;
		}
		return inp;
	}

	private static boolean mineHelper(String inp, int numZeros) {
		MessageDigest d;
		String outStr = "";
		try {
			d = MessageDigest.getInstance("sha256");
			byte[] out = d.digest(inp.getBytes());
			outStr = out.toString();
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
	}*/
	
	public static void main(String[] args) {
		MinerServer server = new MinerServer(5000);
//		int numZeros = 3;
		try {
			/*new Thread(new Runnable() {
				
				@Override
				public void run() {
					String out = mine(numZeros);
					if(out.startsWith("Success")) {
						System.out.println(Thread.currentThread().getName() + " Server reading: " + out.toString());
						suspendAllThreads();
					}
				}
			}).start();*/
			
			server.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected static void suspendAllThreads() {
		// TODO Auto-generated method stub
		
	}
}

class HandleMiners implements Runnable{
	private DataInputStream is = null;
	private DataOutputStream os = null;
	private Socket socket = null;
	private int numZeros = 4;  // specify number of leading zero target

	public HandleMiners(Socket soc) {
		socket = soc;
	}

	public void handle() {
		try {
			
			System.out.println("New Client Connected");
			is = new DataInputStream(socket.getInputStream());
			os = new DataOutputStream(socket.getOutputStream());
			os.writeInt(numZeros);
			
			String nextline = "";
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			while ((nextline = br.readLine()) != null) {
				sb.append(nextline);
			}
			System.out.println(Thread.currentThread().getName() + " Server reading: " + sb.toString());
			if(sb.toString().startsWith("Success")) {
				suspendAllThreads();
			}
			System.out.println("Closing connection...!");
			is.close();
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void suspendAllThreads() {
		try {
			os.writeInt(-1);
			socket.close();
		} catch (IOException e) {
			
		}
	}

	@Override
	public void run() {
		handle();
	}

}
