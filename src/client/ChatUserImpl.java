package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.rmi.*;
import java.rmi.server.*;

import common.ChatRoom;
import common.ChatUser;

public class ChatUserImpl extends UnicastRemoteObject implements ChatUser {
    private String title = "Logiciel de discussion en ligne";
    private String pseudo = null;

    private JFrame window = new JFrame(this.title);
    private JTextArea txtOutput = new JTextArea();
    private JTextField txtMessage = new JTextField();
    private JButton btnSend = new JButton("Envoyer");
    private ChatRoom chatRoom;

    public ChatUserImpl(ChatRoom chatRoom) throws RemoteException {
        this.chatRoom = chatRoom;
        this.createIHM();
        this.requestPseudo();
        this.chatRoom.subscribe(this, this.pseudo);
    }

    public void createIHM() {
        // Assemblage des composants
        JPanel panel = (JPanel) this.window.getContentPane();
        JScrollPane sclPane = new JScrollPane(txtOutput);

        panel.add(sclPane, BorderLayout.CENTER);
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(this.txtMessage, BorderLayout.CENTER);
        southPanel.add(this.btnSend, BorderLayout.EAST);
        panel.add(southPanel, BorderLayout.SOUTH);

        // Gestion des évènements
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                window_windowClosing(e);
            }
        });

        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSend_actionPerformed(e);
            }
        });

        txtMessage.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent event) {
                if (event.getKeyChar() == '\n')
                    btnSend_actionPerformed(null);
            }
        });

        // Initialisation des attributs
        this.txtOutput.setBackground(new Color(220, 220, 220));
        this.txtOutput.setEditable(false);
        this.window.setSize(500, 400);
        this.window.setVisible(true);
        this.txtMessage.requestFocus();
    }

    public void requestPseudo() {
        this.pseudo = JOptionPane.showInputDialog(
                this.window, "Entrez votre pseudo : ",
                this.title, JOptionPane.OK_OPTION);

        if (this.pseudo == null)
            System.exit(0);
    }

    public void sendMessage() {
        try {
            String text = this.txtMessage.getText();
            if (!text.trim().isEmpty()) {
                this.chatRoom.postMessage(this.pseudo, text);
                this.txtMessage.setText("");
                this.txtMessage.requestFocus();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.window, "Erreur d'envoi au serveur");
        }
    }

    public void displayMessage(String message) {
        this.txtOutput.append(message + "\n");
    }

    public void window_windowClosing(WindowEvent e) {
        try {
            if (this.chatRoom != null) {
                this.chatRoom.unsubscribe(this.pseudo);
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }

    public void btnSend_actionPerformed(ActionEvent e) {
        this.sendMessage();
    }

    public static void main(String[] args) {
        try {
            // DÉTECTION AUTOMATIQUE DE L'IP DU CLIENT
            String clientIP = "";
            try (final java.net.DatagramSocket socket = new java.net.DatagramSocket()) {
                socket.connect(java.net.InetAddress.getByName("8.8.8.8"), 10002);
                clientIP = socket.getLocalAddress().getHostAddress();
            }

            System.out.println("--> Mon IP Client (pour le callback) est : " + clientIP);

            System.setProperty("java.rmi.server.hostname", clientIP);

            String serverAddress = "192.168.1.7";

            String rmiUrl = "rmi://" + serverAddress + ":1099/ChatRoom";

            System.out.println("--> Tentative de connexion au serveur : " + rmiUrl);
            ChatRoom chatRoom = (ChatRoom) Naming.lookup(rmiUrl);

            new ChatUserImpl(chatRoom);
            System.out.println("--> Client connecté et prêt !");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erreur critique :\n" + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
