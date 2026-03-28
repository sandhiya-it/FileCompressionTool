import java.util.*;
import java.io.*;

class Node implements Comparable<Node> {
    char ch;
    int freq;
    Node left, right;

    Node(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
    }

    public int compareTo(Node o) {
        return this.freq - o.freq;
    }
}

public class HuffmanLevel1 {

    static Map<Character, String> codes = new HashMap<>();

    static Node buildTree(Map<Character, Integer> freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (char c : freq.keySet())
            pq.add(new Node(c, freq.get(c)));

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();

            Node newNode = new Node('\0', left.freq + right.freq);
            newNode.left = left;
            newNode.right = right;

            pq.add(newNode);
        }

        return pq.poll();
    }

    static void generateCodes(Node root, String code) {
        if (root == null) return;

        if (root.left == null && root.right == null)
            codes.put(root.ch, code);

        generateCodes(root.left, code + "0");
        generateCodes(root.right, code + "1");
    }

    public static void main(String[] args) throws Exception {

        // Read file
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String text = br.readLine();
        br.close();

        // Frequency map
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray())
            freq.put(c, freq.getOrDefault(c, 0) + 1);

        Node root = buildTree(freq);
        generateCodes(root, "");

        // Encode
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray())
            encoded.append(codes.get(c));

        // Save compressed file
        BufferedWriter bw = new BufferedWriter(new FileWriter("compressed.txt"));
        bw.write(encoded.toString());
        bw.close();

        System.out.println("Compression Done!");
    }
}
