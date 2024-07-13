package codefest2024;

import java.net.URI;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;

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

        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Message from server: " + args[0]);
            }
        });

        socket.emit("message", 1, new JSONObject(Collections.singletonMap("name", "updated")));

        socket.connect();
    }
}
