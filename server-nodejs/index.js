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
    socket.emit("message", "world");

    socket.on("message", (arg1, arg2) => {
        console.log("Message from client: ", arg1, " ", arg2);
    });
});

server.listen(3000, () => {
    console.log('Server running at http://localhost:3000');
});
