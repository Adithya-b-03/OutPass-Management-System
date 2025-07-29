import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.sql.*;

public class OutpassClient extends javax.swing.JFrame {
    private boolean isRequestSent = false; // Track if a request has already been sent
    private Socket socket; // Socket for communication with server
    private BufferedReader in; // Input stream from server
    private PrintWriter out; // Output stream to server

    // GUI components declaration
    private javax.swing.JButton backButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JButton submitButton;
    private javax.swing.JTextArea textArea1;
    private javax.swing.JTextField submitRequestField;

    public OutpassClient() {
        initComponents();
        try {
            // Connect to the server (assuming server details are known)
            socket = new Socket("localhost", 12345);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            // Start a thread to listen for server responses
            new Thread(this::listenForServerResponse).start();
        } catch (IOException e) {
            e.printStackTrace(); // Handle connection error
        }
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        textArea1 = new javax.swing.JTextArea();
        submitButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        submitRequestField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Outpass Client");

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 36));
        jLabel1.setText("OutPass Request Client");

        jLabel2.setFont(new java.awt.Font("Segoe Print", 0, 12));
        jLabel2.setText("Student Name");

        jLabel3.setFont(new java.awt.Font("Segoe Print", 0, 12));
        jLabel3.setText("Request Message");

        submitButton.setFont(new java.awt.Font("Segoe Print", 1, 12));
        submitButton.setText("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        clearButton.setFont(new java.awt.Font("Segoe Print", 1, 12));
        clearButton.setText("Clear");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        backButton.setFont(new java.awt.Font("Segoe Print", 1, 12));
        backButton.setText("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        submitRequestField.setFont(new java.awt.Font("Segoe Print", 0, 12));
        submitRequestField.setEditable(false); // Disable editing since it's for status

        // Layout configurations
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(59, 59, 59)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel3))
                                                .addGap(54, 54, 54)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                                        .addComponent(jTextField1))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addContainerGap(112, Short.MAX_VALUE)
                                                .addComponent(backButton)
                                                .addGap(46, 46, 46)
                                                .addComponent(submitButton)
                                                .addGap(52, 52, 52)
                                                .addComponent(clearButton)
                                                .addGap(53, 53, 53)))
                                .addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(135, 135, 135)
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(135, 135, 135)
                                .addComponent(submitRequestField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel1)
                                .addGap(37, 37, 37)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(textArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(submitButton)
                                                        .addComponent(clearButton)
                                                        .addComponent(backButton))))
                                .addGap(18, 18, 18)
                                .addComponent(submitRequestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (isRequestSent) {
            submitRequestField.setText("Request already submitted!");
            return;
        }

        String studentName = jTextField1.getText().trim();
        String requestMessage = jTextField2.getText().trim();

        if (studentName.isEmpty() || requestMessage.isEmpty()) {
            submitRequestField.setText("Please fill all fields.");
            return;
        }

        // Background task to insert the request into the database
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                Connection conn = null;
                PreparedStatement pstmt = null;
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/OutpassDB", "root", "root");
                    String sql = "INSERT INTO OutpassRequests (student_name, request_message, status) VALUES (?, ?, 'Pending')";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, studentName);
                    pstmt.setString(2, requestMessage);
                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        SwingUtilities.invokeLater(() -> {
                            textArea1.setText("Outpass request submitted successfully!");
                            submitRequestField.setText("Request submitted.");
                            isRequestSent = true;
                            // Send the request message to the server
                            out.println("New Request from " + studentName + ": " + requestMessage);
                        });
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            textArea1.setText("Failed to submit the request.");
                            submitRequestField.setText("Submission failed.");
                        });
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        textArea1.setText("Database error!");
                        submitRequestField.setText("Database error.");
                    });
                } finally {
                    try {
                        if (pstmt != null) pstmt.close();
                        if (conn != null) conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        worker.execute();
    }

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {
        jTextField1.setText("");
        jTextField2.setText("");
        submitRequestField.setText("");
        isRequestSent = false;
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // Optionally handle "Back" button action here, e.g., go to the login screen.
    }

    private void listenForServerResponse() {
        try {
            String response;
            while ((response = in.readLine()) != null) {
                // Display the response in a popup dialog box
                JOptionPane.showMessageDialog(this, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new OutpassClient().setVisible(true));
    }
}
