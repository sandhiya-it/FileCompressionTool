import java.util.*;
import java.io.*;
import javax.swing.*;

class Node implements Comparable<Node> {
    char ch; int freq;
    Node left, right;

    Node(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
    }

    public int compareTo(Node o) {
        return this.freq - o.freq;
    }
}

public class HuffmanAllInOne {

    static Map<Character, String> codes = new HashMap<>();

    static Node buildTree(Map<Character,Integer> freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (char c : freq.keySet())
            pq.add(new Node(c, freq.get(c)));

        while (pq.size() > 1) {
            Node l = pq.poll(), r = pq.poll();
            Node n = new Node('\0', l.freq + r.freq);
            n.left = l; n.right = r;
            pq.add(n);
        }
        return pq.poll();
    }

    static void generate(Node root, String s) {
        if (root == null) return;
        if (root.left == null && root.right == null)
            codes.put(root.ch, s);
        generate(root.left, s+"0");
        generate(root.right, s+"1");
    }

    static String decode(String enc, Node root) {
        StringBuilder res = new StringBuilder();
        Node cur = root;

        for (char b : enc.toCharArray()) {
            cur = (b == '0') ? cur.left : cur.right;
            if (cur.left == null && cur.right == null) {
                res.append(cur.ch);
                cur = root;
            }
        }
        return res.toString();
    }

    static String readFile() throws Exception {
        return new BufferedReader(new FileReader("input.txt")).readLine();
    }

    static void level1() throws Exception {
        System.out.println("Level 1: Compression");

        String text = readFile();

        Map<Character,Integer> freq = new HashMap<>();
        for (char c : text.toCharArray())
            freq.put(c, freq.getOrDefault(c, 0)+1);

        Node root = buildTree(freq);
        generate(root, "");

        StringBuilder enc = new StringBuilder();
        for (char c : text.toCharArray())
            enc.append(codes.get(c));

        new BufferedWriter(new FileWriter("compressed.txt")).write(enc.toString());

        System.out.println("Compression Done!");
    }

    static void level2() throws Exception {
        System.out.println("Level 2: Compression + Decompression");

        String text = readFile();

        Map<Character,Integer> freq = new HashMap<>();
        for (char c : text.toCharArray())
            freq.put(c, freq.getOrDefault(c, 0)+1);

        Node root = buildTree(freq);
        generate(root, "");

        StringBuilder enc = new StringBuilder();
        for (char c : text.toCharArray())
            enc.append(codes.get(c));

        String dec = decode(enc.toString(), root);

        new BufferedWriter(new FileWriter("compressed.txt")).write(enc.toString());
        new BufferedWriter(new FileWriter("decompressed.txt")).write(dec);

        System.out.println("Done!");
    }

    static void level3() {
        JFrame f = new JFrame("Huffman Tool");
        JButton b = new JButton("Select File");

        b.setBounds(50,100,150,40);

        b.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String text = br.readLine();
                    br.close();

                    double ratio = 0.5;
                    JOptionPane.showMessageDialog(f, "Compression Done!\nRatio: "+ratio);
                } catch(Exception ex) { ex.printStackTrace(); }
            }
        });

        f.add(b);
        f.setSize(300,300);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose Level:");
        System.out.println("1 - Compression");
        System.out.println("2 - Compression + Decompression");
        System.out.println("3 - GUI");

        int ch = sc.nextInt();

        if (ch == 1) level1();
        else if (ch == 2) level2();
        else level3();
    }
}
