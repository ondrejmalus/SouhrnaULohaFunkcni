import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PanelPole extends JFrame {
    private JPanel panel;
    private JTextField txtName;
    private JCheckBox CBOwned;
    private JRadioButton RB1;
    private JRadioButton RB2;
    private JRadioButton RB3;
    private JButton prevBtn;
    private JButton nxtBtn;
    private JButton saveBtn;
    private final List<Pole> bGList = new ArrayList<>();
    private int index = 0;
    private final int[] selectedScore = {1};

    public Pole getBG(int i){
        return bGList.get(i);
    }
    public List<Pole> getbGList(){
        return bGList;
    }
    public PanelPole() {
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(RB1);
        btnGroup.add(RB2);
        btnGroup.add(RB3);
        RB1.addItemListener(e -> handleRadioButtonClick(1));
        RB2.addItemListener(e -> handleRadioButtonClick(2));
        RB3.addItemListener(e -> handleRadioButtonClick(3));

        prevBtn.addActionListener(e -> {
            if (index > 0){
                index--;
                displayBG(getBG(index));
            }
        });
        nxtBtn.addActionListener(e -> {
            if (index < bGList.size() - 1) {
                index++;
                displayBG(getBG(index));
            }
        });
        saveBtn.addActionListener(e -> saveToFile());
        cteniZeSouboru();
        if (!getbGList().isEmpty()){
            displayBG(getBG(index));
        } else {
            JOptionPane.showMessageDialog(this, "There is nothing in the list", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void cteniZeSouboru() {
        try (Scanner sc = new Scanner(new BufferedReader(new FileReader("deskovky.txt")))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(";");
                String name = (parts[0]);
                boolean owned = parts[1].equals("owned");
                int score = Integer.parseInt(parts[2]);
                Pole pl = new Pole(name, owned, score);
                bGList.add(pl);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            System.err.println("Wrongly formatted number: " + e.getLocalizedMessage());
        }
    }
    private void handleRadioButtonClick(int score) {
        selectedScore[0] = score;
    }
    public void saveToFile() {
        Pole selectedBG = bGList.get(index);
        selectedBG.setJmeno(txtName.getText());
        selectedBG.setVlastniHru(CBOwned.isSelected());
        selectedBG.setHodnoceni(selectedScore[0]);

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("deskovky.txt")))) {
            for (Pole bg : bGList) {
                writer.println(bg.getJmeno() + ";" + (bg.isVlastniHru() ? "vlastní hru" : "nevlastní hru") + ";" + bg.getHodnoceni());
            }
            JOptionPane.showMessageDialog(this, "Změna uložena do souboru", "Zpráva uložena", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.err.println("Nastala chyba při psaní do souboru: " + e.getLocalizedMessage());
        }
    }
    public void displayBG(Pole bg){
        txtName.setText(bg.getJmeno());
        CBOwned.setSelected(bg.isVlastniHru());
        switch (bg.getHodnoceni()){
            case 1 -> RB1.setSelected(true);
            case 2 -> RB2.setSelected(true);
            case 3 -> RB3.setSelected(true);
        }
    }
    public static void main(String[] args) {
        PanelPole PnPl = new PanelPole();
        PnPl.setContentPane(PnPl.panel);
        PnPl.setBounds(600, 260, 300, 300);
        PnPl.setDefaultCloseOperation(EXIT_ON_CLOSE);
        PnPl.setTitle("Board Games");
        PnPl.setVisible(true);
    }
}
