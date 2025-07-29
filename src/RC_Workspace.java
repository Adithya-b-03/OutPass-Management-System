import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RC_Workspace extends JFrame {

    private JLabel jLabel2, jLabel9, jLabel6, jLabel7, jLabel8, jLabel10, jLabel13;
    private JTextField textField1, textField2, textField3, textField4;
    private JButton outpassRequestButton, backButton;
    private JPanel jPanel1;

    private String loggedInUsername; // To store the username of the logged-in user

    public RC_Workspace(String username) {
        this.loggedInUsername = username; // Pass the username of the logged-in user
        initComponents();
        loadUserData(); // Load user data from the database
    }

    private void initComponents() {
        // Set the title and default close operation for the JFrame
        setTitle("RC Workspace");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize labels
        jLabel2 = new JLabel("-RC DET", SwingConstants.CENTER);
        jLabel2.setFont(new Font("Segoe Print", Font.BOLD, 36));

        jLabel9 = new JLabel("RC Desk", SwingConstants.CENTER);
        jLabel9.setFont(new Font("Segoe Print", Font.BOLD, 36));

        jLabel6 = new JLabel("Year");
        jLabel6.setFont(new Font("Segoe Print", Font.PLAIN, 12));

        jLabel7 = new JLabel("Dept");
        jLabel7.setFont(new Font("Segoe Print", Font.PLAIN, 12));

        jLabel8 = new JLabel("Age");
        jLabel8.setFont(new Font("Segoe Print", Font.PLAIN, 12));

        jLabel10 = new JLabel("Name");
        jLabel10.setFont(new Font("Segoe Print", Font.PLAIN, 12));

        jLabel13 = new JLabel("RC Profile");
        jLabel13.setFont(new Font("Segoe Print", Font.BOLD, 12));

        // Initialize text fields
        textField1 = new JTextField();
        textField1.setFont(new Font("Segoe Print", Font.PLAIN, 12));
        textField1.setEditable(false); // Make fields non-editable

        textField2 = new JTextField();
        textField2.setFont(new Font("Segoe Print", Font.PLAIN, 12));
        textField2.setEditable(false);

        textField3 = new JTextField();
        textField3.setFont(new Font("Segoe Print", Font.PLAIN, 12));
        textField3.setEditable(false);

        textField4 = new JTextField();
        textField4.setFont(new Font("Segoe Print", Font.PLAIN, 12));
        textField4.setEditable(false);

        // Initialize buttons
        outpassRequestButton = new JButton("OutPass Requests");
        outpassRequestButton.setFont(new Font("Segoe Print", Font.BOLD, 12));
        outpassRequestButton.addActionListener(e -> openOutpassServer());

        backButton = new JButton("Back"); // New Back button
        backButton.setFont(new Font("Segoe Print", Font.BOLD, 12));
        backButton.addActionListener(e -> navigateToLoginPage()); // Add back button functionality

        // Initialize panel for user input fields
        jPanel1 = new JPanel();
        jPanel1.setBackground(new Color(204, 255, 255));
        GroupLayout panelLayout = new GroupLayout(jPanel1);
        jPanel1.setLayout(panelLayout);

        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel8))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(textField4)
                                        .addComponent(textField1)
                                        .addComponent(textField2)
                                        .addComponent(textField3, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        // Create layout for the main frame
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jLabel9)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addContainerGap(100, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(outpassRequestButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(backButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(40, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel2))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(outpassRequestButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(backButton)))
                                .addContainerGap(50, Short.MAX_VALUE))
        );

        // Set the window size and center it on screen
        pack();
        setLocationRelativeTo(null);
    }

    private void loadUserData() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rc_database", "root", "root");

            String query = "SELECT name, year, department, age FROM users WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, loggedInUsername);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                textField4.setText(rs.getString("name"));
                textField1.setText(rs.getString("year"));
                textField2.setText(rs.getString("department"));
                textField3.setText(rs.getString("age"));
            } else {
                JOptionPane.showMessageDialog(null, "User data not found.");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }

    private void openOutpassServer() {
        new OutpassServer().setVisible(true); // Open the OutpassServer window
    }

    private void navigateToLoginPage() {
        new RC_Login_Page().setVisible(true); // Navigate to the login page
        dispose(); // Close the current window
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new RC_Workspace("MANOJ").setVisible(true));
    }
}
