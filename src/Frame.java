import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Frame{

    public JFrame frame;
    Passwords passwords = new Passwords();
    private JPanel resultPanel; // Panel for entries
    private final Color DARK_GREEN = new Color(8, 136, 8); // Even darker green

    public Frame() {
        setFrame();
        createSearchBar();
        createButtonPanel();
        createTextPanel();

        loadPasswordsFromDatabase(); // Load saved passwords

        frame.setVisible(true);
    }


    private void setFrame() {
        frame = new JFrame("JFrame Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
    }


    private void createSearchBar() {
        JPanel searchPanel = new JPanel(new FlowLayout());

        JTextField searchField_Place = new JTextField(15);
        JTextField searchField_Password = new JTextField(15);
        JButton addButton = new JButton("Add");

        // Panel for storing entries
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(new Color(230, 230, 230)); // Gray background

        addButton.setBackground(DARK_GREEN); // Green button
        addButton.setForeground(Color.WHITE); // White text

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String place = searchField_Place.getText().trim();
                String password = searchField_Password.getText().trim();

                if (!place.isEmpty() && !password.isEmpty()) {
                    addPasswordEntry(place, password);
                }

                searchField_Place.setText("");
                searchField_Password.setText("");
                passwords.addPassword(Passwords.id_generator, place, password);
            }
        });

        searchPanel.add(new JLabel("Place:"));
        searchPanel.add(searchField_Place);
        searchPanel.add(new JLabel("Password:"));
        searchPanel.add(searchField_Password);
        searchPanel.add(addButton);

        frame.add(searchPanel, BorderLayout.NORTH);

        // Scroll pane for result panel
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        frame.add(scrollPane, BorderLayout.CENTER);
    }

    private void addPasswordEntry(String place, String password) {
        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.X_AXIS)); // Side-by-side layout
        entryPanel.setBackground(new Color(245, 245, 245));
        entryPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // JLabel with bold text (HTML formatting)
        JLabel resultLabel = new JLabel("<html><b>Place:</b> " + place + " | <b>Password:</b> " + password + "</html>");
        resultLabel.setOpaque(true);
        resultLabel.setBackground(new Color(245, 245, 245));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        resultLabel.setPreferredSize(new Dimension(300, 30));

        // Copy button
        JButton copyButton = new JButton("Copy");
        copyButton.setPreferredSize(new Dimension(70, 30));
        copyButton.setMaximumSize(new Dimension(70, 30));
        copyButton.setBackground(DARK_GREEN);  // Green button
        copyButton.setForeground(Color.WHITE);  // White text
        copyButton.addActionListener(e -> copyToClipboard("Place: " + place + " | Password: " + password));

        // Delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(80, 30));
        deleteButton.setMaximumSize(new Dimension(80, 30));
        deleteButton.setBackground(DARK_GREEN);  // Green button
        deleteButton.setForeground(Color.WHITE);  // White text
        deleteButton.addActionListener(e -> {
            passwords.deleteInfo(place); // Delete from database
            resultPanel.remove(entryPanel); // Remove from UI
            resultPanel.revalidate();
            resultPanel.repaint();
        });


        // Add components
        entryPanel.add(resultLabel);
        entryPanel.add(copyButton);
        entryPanel.add(deleteButton);

        // Add to result panel
        resultPanel.add(entryPanel);
        resultPanel.revalidate();
        resultPanel.repaint();
    }

    private void copyToClipboard(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, null);
    }

    private void createButtonPanel() {
        JPanel panel = new JPanel();
        JButton button = new JButton("Click Me");

        button.setBackground(DARK_GREEN);  // Green button
        button.setForeground(Color.WHITE);  // White text

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwords.displayPasswords();
            }
        });

        panel.add(button);
        frame.add(panel, BorderLayout.SOUTH);
    }

    private void createTextPanel() {
        JPanel textPanel = new JPanel();
        JLabel label = new JLabel("");
        textPanel.add(label);
        frame.add(textPanel, BorderLayout.SOUTH);
    }

    private void loadPasswordsFromDatabase() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/login_info",
                    "root",
                    "parola1");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String place = resultSet.getString("place");
                String password = resultSet.getString("password");

                addPasswordEntry(place, password); // Display in UI
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}