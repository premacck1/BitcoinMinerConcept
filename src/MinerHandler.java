import java.util.concurrent.*;

public class MinerHandler {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		int i = 0;
		while (i < 10) {
			executor.execute(new Miner(5000, "127.0.0.1"));
			i++;
		}
	}
}
