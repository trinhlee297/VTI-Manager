const express = require("express")
const app = express();
const cors = require("cors")
app.use(
    cors({
        origin: "*",
        methods: ["GET", "PUT", "POST", "DELETE"],
    })
);

app.listen(3000);