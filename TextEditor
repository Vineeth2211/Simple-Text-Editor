import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class TextEditor {
    private JFrame frame;
    private JTextPane textPane;
    private JComboBox<String> fontSizeComboBox;
    private JToggleButton boldButton;
    private JToggleButton italicButton;
    private JToggleButton underlineButton;
    private JButton textColorButton;

    public TextEditor() {
        frame = new JFrame("Text Editor");
        textPane = new JTextPane();
        fontSizeComboBox = new JComboBox<>(new String[]{"12", "14", "16", "18", "20"});
        boldButton = new JToggleButton("Bold");
        italicButton = new JToggleButton("Italic");
        underlineButton = new JToggleButton("Underline");
        textColorButton = new JButton("Text Color");

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

        setupListeners();

        frame.setVisible(true);
    }

    private void setupListeners() {
        fontSizeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSize = (String) fontSizeComboBox.getSelectedItem();
                int size = Integer.parseInt(selectedSize);
                setFontSize(size);
            }
        });

        boldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBold(boldButton.isSelected());
            }
        });

        italicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setItalic(italicButton.isSelected());
            }
        });

        underlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUnderline(underlineButton.isSelected());
            }
        });

        textColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseTextColor();
            }
        });

        textPane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {}

            @Override
            public void removeUpdate(DocumentEvent e) {}

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
    }

    private void setFontSize(int size) {
        StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("FontStyle", null);
        StyleConstants.setFontSize(style, size);
        doc.setCharacterAttributes(textPane.getSelectionStart(), textPane.getSelectionEnd() - textPane.getSelectionStart(), style, false);
    }

    private void setBold(boolean bold) {
        StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("BoldStyle", null);
        StyleConstants.setBold(style, bold);
        doc.setCharacterAttributes(textPane.getSelectionStart(), textPane.getSelectionEnd() - textPane.getSelectionStart(), style, false);
    }

    private void setItalic(boolean italic) {
        StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("ItalicStyle", null);
        StyleConstants.setItalic(style, italic);
        doc.setCharacterAttributes(textPane.getSelectionStart(), textPane.getSelectionEnd() - textPane.getSelectionStart(), style, false);
    }

    private void setUnderline(boolean underline) {
        StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("UnderlineStyle", null);
        StyleConstants.setUnderline(style, underline);
        doc.setCharacterAttributes(textPane.getSelectionStart(), textPane.getSelectionEnd() - textPane.getSelectionStart(), style, false);
    }

    private void chooseTextColor() {
        Color selectedColor = JColorChooser.showDialog(frame, "Choose Text Color", textPane.getForeground());
        if (selectedColor != null) {
            StyledDocument doc = textPane.getStyledDocument();
            Style style = textPane.addStyle("TextColorStyle", null);
            StyleConstants.setForeground(style, selectedColor);
            doc.setCharacterAttributes(textPane.getSelectionStart(), textPane.getSelectionEnd() - textPane.getSelectionStart(), style, false);
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
