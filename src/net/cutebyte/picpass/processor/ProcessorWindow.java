package net.cutebyte.picpass.processor;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by hopskocz on 02.05.15.
 */
public class ProcessorWindow implements ActionListener {
    private JFrame frame;
    private ImagePanel imgPanel;
    private JTextField tfImagePath;
    private JTextArea taMessage;
    private boolean mode = true;
    private JButton funcButton;

    public ProcessorWindow() {
        frame = new JFrame();
        frame.setTitle("PicPass demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel modeSetting = new JPanel();
        modeSetting.setBorder(new TitledBorder("Wybierz tryb"));

        ButtonGroup btGroup = new ButtonGroup();

        JRadioButton mode = new JRadioButton("Ukrywanie");
        mode.setActionCommand("hide_mode");
        mode.addActionListener(this);
        modeSetting.add(mode);
        mode.setSelected(true);
        btGroup.add(mode);

        mode = new JRadioButton("Odczytywanie");
        mode.setActionCommand("show_mode");
        mode.addActionListener(this);
        modeSetting.add(mode);
        btGroup.add(mode);

        JPanel pathPanel = new JPanel();
        pathPanel.setBorder(new TitledBorder("Wpisz/wybierz ścieżke obrazu:"));
        tfImagePath = new JTextField();
        tfImagePath.setPreferredSize(new Dimension(200,32));
        JButton pathAccept = new JButton("Wybierz");
        pathAccept.setActionCommand("img_load");
        pathAccept.addActionListener(this);
        JButton pathSelect = new JButton("Załaduj");
        pathSelect.setActionCommand("img_accept");
        pathSelect.addActionListener(this);

        pathPanel.add(tfImagePath);
        pathPanel.add(pathAccept);
        pathPanel.add(pathSelect);

        JPanel centerPanel = new JPanel();
        funcButton = new JButton("Zapisz");
        funcButton.setActionCommand("func_button");
        funcButton.addActionListener(this);
        centerPanel.setLayout(new BorderLayout());
        imgPanel = new ImagePanel();
        imgPanel.setPreferredSize(new Dimension(460,320));
        imgPanel.setBorder(new EtchedBorder());
        taMessage = new JTextArea();
        //scroll.setViewportView(taMessage);
        //taMessage.setPreferredSize(new Dimension(460,120));
        taMessage.setBorder(new TitledBorder("Tutaj wpisz wiadomość do ukrycia"));
        taMessage.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(taMessage);
        scroll.setPreferredSize(new Dimension(460,120));
        centerPanel.add(funcButton, BorderLayout.NORTH);
        centerPanel.add(imgPanel, BorderLayout.CENTER);
        centerPanel.add(scroll, BorderLayout.SOUTH);

        frame.add(modeSetting, BorderLayout.NORTH);
        frame.add(pathPanel, BorderLayout.SOUTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("hide_mode")) {
            ((TitledBorder)taMessage.getBorder()).setTitle("Tutaj wpisz wiadomość do ukrycia");
            taMessage.setEditable(true);
            mode = true;
            funcButton.setText("Zapisz");
            return;
        }
        if (actionEvent.getActionCommand().equals("show_mode")) {
            ((TitledBorder)taMessage.getBorder()).setTitle("Tutaj widać odczytaną wiadomość z obrazu (brak jeśli puste)");
            taMessage.setEditable(false);
            mode = false;
            funcButton.setText("Odczytaj");
            taMessage.setText("");
            return;
        }
        if (actionEvent.getActionCommand().equals("img_load")) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showDialog(frame, "Wybierz");
            if (fileChooser.getSelectedFile() != null) {
                tfImagePath.setText(fileChooser.getSelectedFile().getPath());
                imgPanel.setImage(tfImagePath.getText());
            }
            return;
        }
        if (actionEvent.getActionCommand().equals("img_accept")) {
            if (new File(tfImagePath.getText()).exists())
                imgPanel.setImage(tfImagePath.getText());
            return;
        }
        if (actionEvent.getActionCommand().equals("func_button")) {
            // TODO: lel
            if (mode) {
                if (new File(tfImagePath.getText()).exists()) {
                    imgPanel.getImage().storeMsg(taMessage.getText());
                    imgPanel.repaint();
                    imgPanel.saveImage(tfImagePath.getText());
                } else {
                    JOptionPane.showMessageDialog(frame, "Plik nie istnieje!");
                }
            } else {
                if (new File(tfImagePath.getText()).exists()) {
                    taMessage.setText(imgPanel.getImage().extractMsg());
                    imgPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "Plik nie istnieje!");
                }
            }
        }
    }
}
