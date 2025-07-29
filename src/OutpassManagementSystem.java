import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * OutpassManagementSystem serves as the main menu with navigation to the RC Login Page and Student Login Page.
 */
public class OutpassManagementSystem extends JFrame {

    private JButton rcLoginButton;
    private JButton studentLoginButton;

    /**
     * Creates new form OutpassManagementSystem
     */
    public OutpassManagementSystem() {
        initComponents();
    }

    /**
     * This method initializes the form's components.
     */
    private void initComponents() {
        // Set up the JFrame
        setTitle("Outpass Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        JLabel titleLabel = new JLabel("Outpass Management System");
        titleLabel.setFont(new Font("Segoe Print", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        rcLoginButton = new JButton("RC Login Page");
        rcLoginButton.setFont(new Font("Segoe Print", Font.BOLD, 18));
        rcLoginButton.addActionListener(this::navigateToRCLogin);

        studentLoginButton = new JButton("Student Login Page");
        studentLoginButton.setFont(new Font("Segoe Print", Font.BOLD, 18));
        studentLoginButton.addActionListener(this::navigateToStudentLogin);

        // Layout setup
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Horizontal group
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(titleLabel)
                        .addComponent(rcLoginButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                        .addComponent(studentLoginButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
        );

        // Vertical group
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addGap(30)
                        .addComponent(rcLoginButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
                        .addComponent(studentLoginButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        );

        // JFrame settings
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set background color
        getContentPane().setBackground(Color.GREEN); // Green background
    }

    /**
     * Navigates to the RC Login Page.
     */
    private void navigateToRCLogin(ActionEvent evt) {
       new RC_Login_Page().setVisible(true);
        dispose(); // Close current window
    }

    /**
     * Navigates to the Student Login Page.
     */
    private void navigateToStudentLogin(ActionEvent evt) {
        new Student_LoginPage().setVisible(true); // Ensure Student_Login_Page class exists
         dispose(); // Close current window
    }

    /**
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new OutpassManagementSystem().setVisible(true));
    }
}
