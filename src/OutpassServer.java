import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OutpassServer extends javax.swing.JFrame {
    private Connection conn;
    private PreparedStatement selectStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;
    private ServerSocket serverSocket;
    private Map<Socket, PrintWriter> clients;

    private DefaultTableModel tableModel;

    public OutpassServer() {
        initComponents();
        connectToDatabase();
        loadPendingRequests();
        startServer();
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/OutpassDb";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, user, password);
            logArea.append("Connected to database.\n");

            selectStmt = conn.prepareStatement("SELECT * FROM OutpassRequests WHERE status = 'Pending'");
            updateStmt = conn.prepareStatement("UPDATE OutpassRequests SET status = ? WHERE request_id = ?");
            deleteStmt = conn.prepareStatement("DELETE FROM OutpassRequests WHERE request_id = ?");
        } catch (SQLException ex) {
            logArea.append("Database connection failed.\n");
            ex.printStackTrace();
        }
    }

    private void loadPendingRequests() {
        logArea.append("Loading pending requests...\n");
        tableModel.setRowCount(0); // Clear existing rows
        try (ResultSet rs = selectStmt.executeQuery()) {
            while (rs.next()) {
                int requestId = rs.getInt("request_id");
                String studentName = rs.getString("student_name");
                String requestMessage = rs.getString("request_message");

                tableModel.addRow(new Object[]{requestId, studentName, requestMessage});
            }
            if (tableModel.getRowCount() == 0) {
                logArea.append("No pending requests.\n");
            }
        } catch (SQLException ex) {
            logArea.append("Error loading pending requests.\n");
            ex.printStackTrace();
        }
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(12345);
            logArea.append("Server started on port 12345...\n");
            clients = new HashMap<>();
            new Thread(this::handleClients).start();
        } catch (IOException e) {
            logArea.append("Error starting server: " + e.getMessage() + "\n");
        }
    }

    private void handleClients() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                clients.put(clientSocket, out);
                logArea.append("Client connected...\n");

                new Thread(() -> handleClient(clientSocket)).start();
            } catch (IOException e) {
                logArea.append("Error accepting client connection: " + e.getMessage() + "\n");
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message;
            while ((message = in.readLine()) != null) {
                logArea.append("Received message from client: " + message + "\n");
            }
        } catch (IOException e) {
            logArea.append("Error handling client connection: " + e.getMessage() + "\n");
        } finally {
            clients.remove(clientSocket);
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleAccept() {
        updateRequestStatus("Accepted");
        sendResponseToAllClients("Your request has been accepted.");
    }

    private void handleReject() {
        updateRequestStatus("Rejected");
        sendResponseToAllClients("Your request has been rejected.");
    }

    private void updateRequestStatus(String status) {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow != -1) {
            int requestId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                updateStmt.setString(1, status);
                updateStmt.setInt(2, requestId);
                updateStmt.executeUpdate();
                logArea.append("Request ID " + requestId + " marked as " + status + ".\n");
                tableModel.removeRow(selectedRow);
            } catch (SQLException ex) {
                logArea.append("Error updating request status.\n");
                ex.printStackTrace();
            }
        } else {
            logArea.append("No request selected.\n");
        }
    }

    private void sendResponseToAllClients(String response) {
        for (PrintWriter clientOut : clients.values()) {
            clientOut.println(response);
        }
    }

    private void initComponents() {
        setTitle("Outpass Server");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        jPanel = new JPanel();
        jPanel.setBackground(new java.awt.Color(255, 204, 204));

        jLabel = new JLabel("Outpass Server");
        jLabel.setFont(new java.awt.Font("Segoe Print", 1, 36));

        tableModel = new DefaultTableModel(new Object[]{"Request ID", "Student Name", "Request Message"}, 0);
        requestTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(requestTable);

        logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);

        acceptButton = new JButton("Accept");
        acceptButton.addActionListener(evt -> handleAccept());

        rejectButton = new JButton("Reject");
        rejectButton.addActionListener(evt -> handleReject());

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(evt -> loadPendingRequests());

        backButton = new JButton("Back");
        backButton.addActionListener(evt -> dispose());

        GroupLayout layout = new GroupLayout(jPanel);
        jPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel)
                                        .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(logScrollPane)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(acceptButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(rejectButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(refreshButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(backButton)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(acceptButton)
                                .addComponent(rejectButton)
                                .addComponent(refreshButton)
                                .addComponent(backButton))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(logScrollPane)
                        .addContainerGap()
        );

        add(jPanel);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OutpassServer().setVisible(true));
    }

    private JPanel jPanel;
    private JLabel jLabel;
    private JTable requestTable;
    private JTextArea logArea;
    private JButton acceptButton, rejectButton, refreshButton, backButton;
}
