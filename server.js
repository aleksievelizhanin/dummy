// server.js
const express = require('express');
const app = express();
const PORT = 8080;

// simple “health” endpoint used by your test
app.get('/action', (_, res) => res.send('service-is-alive'));

// root endpoint (optional)
app.get('/', (_, res) => res.send('hello from Express on Ubuntu'));

app.listen(PORT, '0.0.0.0', () =>
    console.log(`🚀  Service listening on http://0.0.0.0:${PORT}`)
);
