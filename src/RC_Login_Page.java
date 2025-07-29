import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * RC_Login_Page is a GUI login page for the RC system integrated with a MySQL database.
 */
public class RC_Login_Page extends JFrame {

    // Declare components
    private JLabel jLabel1, jLabel2, jLabel3;
    private JButton loginbutton;
    private JTextField iusername;
    private JPasswordField ipassword;

    /**
     * Creates new form RC_Login_Page
     */
    public RC_Login_Page() {
        initComponents();
    }

    /**
     * This method initializes the form's components.
     */
    private void initComponents() {
        // Initialize components
        jLabel1 = new JLabel("RC Login Application");
        jLabel1.setFont(new Font("Segoe Print", Font.BOLD, 36));
        jLabel1.setForeground(Color.WHITE);

        jLabel2 = new JLabel("Password");
        jLabel2.setFont(new Font("Segoe Print", Font.PLAIN, 12));
        jLabel2.setForeground(Color.WHITE);

        jLabel3 = new JLabel("UserName");
        jLabel3.setFont(new Font("Segoe Print", Font.PLAIN, 12));
        jLabel3.setForeground(Color.WHITE);

        iusername = new JTextField();
        iusername.setFont(new Font("Segoe Print", Font.BOLD, 12));

        ipassword = new JPasswordField();
        ipassword.setFont(new Font("Segoe Print", Font.BOLD, 12));

        loginbutton = new JButton("Login");
        loginbutton.setFont(new Font("Segoe Print", Font.BOLD, 12));
        loginbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loginbuttonActionPerformed(evt);
            }
        });

        // Set layout manager to GroupLayout for better control over alignment
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Horizontal group
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel1)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(iusername, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ipassword, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(loginbutton)))
        );

        // Vertical group
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(40)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(iusername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(20)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(ipassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(20)
                        .addComponent(loginbutton)
        );

        // Configure JFrame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("RC Login Page");
        setSize(600, 400);
        setLocationRelativeTo(null); // Center on screen
        getContentPane().setBackground(new Color(0, 0, 0)); // Set background color to black
    }

    private void loginbuttonActionPerformed(ActionEvent evt) {
        String username = iusername.getText();
        String password = new String(ipassword.getPassword()); // Convert password to string

        if (username.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter UserName");
        } else if (password.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter Password");
        } else {
            try {
                // Connect to MySQL database
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rc_database", "root", "root");

                // Prepare and execute query
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login Successful");
                    RC_Workspace workspace = new RC_Workspace(username);
                    workspace.setVisible(true); // Show workspace
                    dispose(); // Close login page
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password");
                }

                // Close resources
                rs.close();
                ps.close();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
            }
        }
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(() -> new RC_Login_Page().setVisible(true));
    }
}
