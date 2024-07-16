package App;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor {
    private final JFrame frame;
    private final JTextPane textPane;
    private final JComboBox<String> fontSizeComboBox;
    private final JToggleButton boldButton;
    private final JToggleButton italicButton;
    private final JToggleButton underlineButton;
    private final JButton textColorButton;
    private final UndoManager undoManager;

    public TextEditor() {
        frame = new JFrame("Text Editor");
        textPane = new JTextPane();
        textPane.setBackground(new Color(255, 255, 224));
        fontSizeComboBox = new JComboBox<>(new String[]{"12", "14", "16", "18", "20"});
        boldButton = new JToggleButton("Bold");
        italicButton = new JToggleButton("Italic");
        underlineButton = new JToggleButton("Underline");
        textColorButton = new JButton("Text Color");
        undoManager = new UndoManager();

        createUI();
    }

    private void createUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JScrollPane scrollPane = new JScrollPane(textPane);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel toolPanel = new JPanel();
        toolPanel.add(fontSizeComboBox);
        toolPanel.add(boldButton);
        toolPanel.add(italicButton);
        toolPanel.add(underlineButton);
        toolPanel.add(textColorButton);

        frame.add(toolPanel, BorderLayout.NORTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem newFile = new JMenuItem("New");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Exit");

        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPane.setText("");
            }
        });

        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(exit);

        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");

        JMenuItem undoMenuItem = new JMenuItem("Undo");
        JMenuItem redoMenuItem = new JMenuItem("Redo");

        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            }
        });

        redoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo()) {
                    undoManager.redo();
                }
            }
        });

        editMenu.add(undoMenuItem);
        editMenu.add(redoMenuItem);

        menuBar.add(editMenu);

        frame.setJMenuBar(menuBar);

        setupListeners();

        frame.setVisible(true);
    }

    private void setupListeners() {
        fontSizeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyAttributes();
            }
        });

        boldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyAttributes();
            }
        });

        italicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyAttributes();
            }
        });

        underlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyAttributes();
            }
        });

        textColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseTextColor();
                applyAttributes();
            }
        });

        textPane.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });

        InputMap im = textPane.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = textPane.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "Undo");
        am.put("Undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), "Redo");
        am.put("Redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo()) {
                    undoManager.redo();
                }
            }
        });
    }

    private void applyAttributes() {
        StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("CurrentStyle", null);

        String selectedSize = (String) fontSizeComboBox.getSelectedItem();
        int size = Integer.parseInt(selectedSize);
        StyleConstants.setFontSize(style, size);

        StyleConstants.setBold(style, boldButton.isSelected());
        StyleConstants.setItalic(style, italicButton.isSelected());
        StyleConstants.setUnderline(style, underlineButton.isSelected());

        StyleConstants.setForeground(style, textPane.getForeground());  // Apply current text color

        textPane.setCharacterAttributes(style, false);
    }

    private void chooseTextColor() {
        Color selectedColor = JColorChooser.showDialog(frame, "Choose Text Color", textPane.getForeground());
        if (selectedColor != null) {
            textPane.setForeground(selectedColor);
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile())) {
                writer.write(textPane.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                textPane.read(reader, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TextEditor();
            }
        });
    }
}
