import express from 'express';
import { createServer } from 'node:http';
import { fileURLToPath } from 'node:url';
import { dirname, join } from 'node:path';
import { Server } from 'socket.io';

const app = express();
const server = createServer(app);
const io = new Server(server);

const __dirname = dirname(fileURLToPath(import.meta.url));

app.get('/', (req, res) => {
    res.sendFile(join(__dirname, 'index.html'));
});

io.on("connection", (socket) => {
    console.log('Client connected');
    socket.emit("hello", "world");

    socket.on("update item", (arg1, arg2, callback) => {
        console.log(arg1); // 1
        console.log(arg2); // { name: "updated" }
        callback({
            status: "ok"
        });
    });
});

server.listen(3000, () => {
    console.log('Server running at http://localhost:3000');
});
