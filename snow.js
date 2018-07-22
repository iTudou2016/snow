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
//  var share=JSON.parse(fs.readFileSync( "../source/share.json"));
  var report=JSON.parse(fs.readFileSync( "../source/report.json"));
//  var nodestate=JSON.parse(fs.readFileSync( "../source/nodestate.json"));
  report.share.pop();
  report.hashrate.pop();
//  share.miners.pop();
  res.render('index', {title: 'SnowBlossom Pool', report: report});
}
