# BitcoinMinerConcept
A bitcoin miner implementation, where a server gives a target and miners try to achieve that target by Cryptographic Hashing (SHA-256) using Random nonce. It supports multiple Miners.

# Follow these steps to run the program:
- [x] Open terminal or cmd. Navigate to "src" folder and run "javac MinerServer.java". After successful compilation run "java MinerServer".
- [x] Open another terminal or cmd. Navigate to "src" folder and run "javac Miner_alone.java". After successful compilation run "java Miner_alone". If you want to run only 1 Miner
- [x] If you want to simulate running multiple clients, Skip step 2 and run "javac MinerHandler.java" and  "javac Miner.java". Then run "java MinerHandler".
- [x] You can specify target number of leading zeros in MinerServer.java. Default is 4.
