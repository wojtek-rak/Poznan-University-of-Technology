var express = require('express');
var app = express();
console.log(__dirname);
app.use(express.static(__dirname ));

app.listen('4200');
console.log('working on 4200');
