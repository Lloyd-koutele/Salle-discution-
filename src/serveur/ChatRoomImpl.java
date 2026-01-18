package serveur;

import common.ChatRoom;
import common.ChatUser;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ChatRoomImpl extends UnicastRemoteObject implements ChatRoom {

    private Map<String, ChatUser> users;

    public ChatRoomImpl() throws RemoteException 
    {
        users = new HashMap<>();
        System.out.println("Serveur de chat démarré...");
    }

    @Override
    public synchronized void subscribe(ChatUser user, String pseudo) throws RemoteException 
    {
        users.put(pseudo, user);
        broadcast("🔔 " + pseudo + " a rejoint la discussion.");
    }

    @Override
    public synchronized void unsubscribe(String pseudo) throws RemoteException 
    {
        users.remove(pseudo);
        broadcast("🔕 " + pseudo + " a quitté la discussion.");
    }

    @Override
    public synchronized void postMessage(String pseudo, String message) throws RemoteException 
    {
        broadcast(pseudo + " : " + message);
    }

    private void broadcast(String message) 
    {
        for (ChatUser user : users.values()) 
        {
            try 
            {
                user.displayMessage(message);
            } 
            catch (RemoteException e) 
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) 
    {
        try 
        {
            // Adresse du serveur
            String hostname = "192.168.1.2";
            System.setProperty("java.rmi.server.hostname", hostname);
            
            // Lancement du registre RMI
            Registry registry = LocateRegistry.createRegistry(1099);

            ChatRoomImpl room = new ChatRoomImpl();

            registry.rebind("ChatRoom", room);

            System.out.println("Salle de discussion prête.");
            System.out.println("Serveur RMI accessible sur : " + hostname + ":1099");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
