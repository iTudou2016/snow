//SnowBlossom FrontEnd.
//Created by xxcc.

const express = require('express');
const app = express();
const http = require('http');
const fs=require('fs');

app.set('views','.');
app.set('view engine', 'pug');

app.get('/', function(req, res) {
getShare(res);
});
app.get('/css/default.css', function(req, res) {
res.sendFile(__dirname+'/css/default.css');
});

// POST method route
app.post('/', function (req, res) {

});

var server = app.listen(8081, function () {
var host = server.address().address;
var port = server.address().port;
  console.log('SnowBlossom Pool listening at http://%s:%s', host, port);
});

function getShare(res) {
  var report=JSON.parse(fs.readFileSync( "../source/report.json"));
  report.share.pop();
  report.hashrate.pop();
  report.blockfound.pop();
  res.render('index', {title: 'SnowBlossom Pool', report: report});
}
