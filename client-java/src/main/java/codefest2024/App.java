package codefest2024;

import java.net.URI;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class App {
    final static URI uri = URI.create("http://localhost:3000");

    public static void main(String[] args) throws JSONException {
        IO.Options options = IO.Options.builder().build();

        Socket socket = IO.socket(uri, options);

        // Log connection events
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Connected to server");
            }
        });

        socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Connection error: " + args[0]);
            }
        });

        // Listen for "hello" event
        socket.on("hello", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Received message: " + args[0]);
            }
        });

        socket.emit("update item", 1, new JSONObject(Collections.singletonMap("name", "updated")), new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject response = (JSONObject) args[0];
                try {
                    System.out.println(response.getString("status"));
                } catch (JSONException e) {
                    e.printStackTrace();
                } // "ok"
            }
        });

        socket.connect();
    }
}
