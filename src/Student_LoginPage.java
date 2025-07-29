//package OutPassMIT;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Student_LoginPage extends JFrame {

    // Declare components
    private JLabel jLabel1, jLabel2, jLabel3;
    private JButton ibutton;
    private JTextField iusername;
    private JPasswordField ipassword;
    private JPanel jPanel1;

    // Declare a variable for the background image
    private Image backgroundImage;

    public Student_LoginPage() {
        initComponents();
        // Load the background image (ensure the path is correct)
        backgroundImage = new ImageIcon("C:\\Users\\welcome\\IdeaProjects\\Outpass_Management_System\\src\\hostel2.jpg").getImage();
    }

    private void initComponents() {
        // Initialize components
        jPanel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image to fit the panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Create labels, buttons, and text fields
        jLabel1 = new JLabel("Student Username");
        jLabel2 = new JLabel("Student Login Page");
        jLabel3 = new JLabel("Student Password");

        iusername = new JTextField();
        ipassword = new JPasswordField();

        ibutton = new JButton("Login");

        // Set font and color for labels and buttons to ensure visibility
        jLabel1.setFont(new Font("Segoe Print", Font.PLAIN, 12));
        jLabel1.setForeground(Color.WHITE);
        jLabel2.setFont(new Font("Segoe Print", Font.BOLD, 36));
        jLabel2.setForeground(Color.WHITE);
        jLabel3.setFont(new Font("Segoe Print", Font.PLAIN, 12));
        jLabel3.setForeground(Color.WHITE);

        iusername.setFont(new Font("Segoe Print", Font.PLAIN, 12));
        ipassword.setFont(new Font("Segoe Print", Font.PLAIN, 12));
        ibutton.setFont(new Font("Segoe Print", Font.BOLD, 12));

        ibutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ibuttonActionPerformed(evt);
            }
        });

        // Set the layout manager
        GroupLayout layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(layout);

        // Automatically create gaps for the components
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // GroupLayout setup (set horizontal and vertical groupings)
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel2)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(iusername, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ipassword, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                        .addComponent(ibutton)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(iusername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(20)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(ipassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(20)
                        .addComponent(ibutton)
        );

        // Add the panel to the frame
        getContentPane().add(jPanel1);

        // Set JFrame properties
        setTitle("Student Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void ibuttonActionPerformed(ActionEvent evt) {
        // Get username and password entered by the user
        String username = iusername.getText();
        String password = String.valueOf(ipassword.getPassword());

        // Validate if the fields are empty
        if (username.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter Student Username");
        } else if (password.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter Student Password");
        } else {
            // MySQL connection variables
            String url = "jdbc:mysql://localhost:3306/student_login"; // Replace with your database URL
            String dbUsername = "root";  // Database username (default is usually root)
            String dbPassword = "root";  // Database password (replace with your own)

            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs = null;

            try {
                // Establish a connection to the database
                conn = DriverManager.getConnection(url, dbUsername, dbPassword);

                // Prepare the SQL query to fetch user from the database
                String sql = "SELECT * FROM students WHERE username = ? AND password = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, username);
                pst.setString(2, password);

                // Execute the query
                rs = pst.executeQuery();

                // If user is found, show success message and navigate to dashboard
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login Successful");

                    // Navigate to the student dashboard (this is just an example)
                     OutpassClient opc = new OutpassClient();
                     opc.show();
                     dispose();  // Close the login window
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Student Username or Password");
                }

            } catch (SQLException e) {
                // Handle SQL exceptions
                JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            } finally {
                // Close the resources
                try {
                    if (rs != null) rs.close();
                    if (pst != null) pst.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String args[]) {
        // Set the Nimbus look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Student_LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Create and display the form
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Student_LoginPage().setVisible(true);
            }
        });
    }
}
