package dota;

import dota.judge.MapReader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ali on 1/6/2016.
 */

public class Network {
    public NetType netType;
    public Socket socket;
    public ServerSocket serverSocket;
    public ObjectOutputStream output;
    public ObjectInputStream input;
    GameEngine engine;

    public Network(NetType netType, String serverIP, int port, GameEngine engine) {
        this.netType = netType;
        this.engine = engine;
        try {
            switch (netType) {
                case SERVER:
                    engine.map = new MapReader();
                    serverSocket = new ServerSocket(port);
                    socket = serverSocket.accept();
                    output= new ObjectOutputStream(socket.getOutputStream());
                    output.writeInt(engine.enemyID);
                    output.flush();
                    output.close();
                    socket.close();
                    socket = serverSocket.accept();
                    output = new ObjectOutputStream(socket.getOutputStream());
                    input = new ObjectInputStream(socket.getInputStream());
                    sendMap();
                    break;
                case CLIENT:
                    socket = new Socket(serverIP, port);
                    output = new ObjectOutputStream(socket.getOutputStream());
                    input = new ObjectInputStream(socket.getInputStream());
                    getMap();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMap() {
        try {
            output.writeObject(engine.map);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getMap() {
        try {
            Object obj=input.readObject();
            engine.map = (MapReader)obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void send(Sender runnable) {
        try {
            output.writeObject(runnable);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sender receive() {
        Sender sender=null;
        try {
            sender=(Sender) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return sender;
    }
}
