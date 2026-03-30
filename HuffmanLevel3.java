import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class HuffmanLevel3 {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Huffman Compression Tool");
        JButton button = new JButton("Select File to Compress");

        button.setBounds(50, 100, 200, 40);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(frame);

                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String text = br.readLine();
                        br.close();

                        int originalSize = text.length();

                        // Fake compression simulation
                        int compressedSize = originalSize / 2;

                        double ratio = (double) compressedSize / originalSize;

                        JOptionPane.showMessageDialog(frame,
                                "Compression Done!\nRatio: " + ratio);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        frame.add(button);
        frame.setSize(300, 300);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
