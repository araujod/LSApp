// filename: nodeDB2_http.js

var mysql = require('mysql');
var fs = require("fs");
var express = require('express');
var bodyParser = require('body-parser');

var app = express();
var port = 8000 ;

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(express.static(__dirname));

var server = app.listen(port, () => {
    console.log('server is listening on port', server.address().port);
});

var connection = mysql.createPool({
        connectionLimit: 5,
        host     : 'localhost',
        user     : 'root',
        password : '',
        database : 'LSApp' 
    });

app.get('/', function (req, res) {
    res.send("Hello");
});

 
app.post('/GET_ONE', function (req, res) {
    console.log("Got a GET request for the homepage");
    console.log(req);
   
   
    let qString = "SELECT * from " + req.body.table + " WHERE " + req.body.where_key +" ='" + req.body.where_value + "'";
    connection.query(qString, function(err, rows, fields) {
    if (!err) {
        console.log("Displaying all the rows");
        
        res.send(JSON.stringify(rows));
    }
    else
        console.log('Error while performing Query.');
    });
 });
 
 app.post('/PUT_PRODAV', function (req, res) {
    console.log("Got a GET request for the homepage");
    console.log(req);
    
    
    let qString = "UPDATE StoreProduct SET Available = "+ req.body.new_value +" WHERE ProductID=" + req.body.where_value + " AND  StoreID=" + req.body.where_value2 + "   ";
   
    //let qString = "update StoreProduct set Available=false where ProductID = 1 and StoreID=1;"
    connection.query(qString, function(err, rows, fields) {
    if (!err) {
        console.log("Displaying all the rows");
        
        res.send(JSON.stringify(rows));
    }
    else
        console.log('Error while performing Query.');
    });
 });
 
 app.post('/GET_ORDDET', function (req, res) {
    console.log("Got a GET request for the homepage");
    console.log(req);


    let qString = "select t1.*, t2.*, t3.* from Orders t1 inner join Order_Detail t2 on t1.OrderID = t2.OrderID inner join Product t3 on t2.ProductID = t3.ProductID where t1.OrderID=" + req.body.where_value;

    connection.query(qString, function(err, rows, fields) {
    if (!err) {
        console.log("Displaying all the rows");

        res.send(JSON.stringify(rows));
    }
    else
        console.log('Error while performing Query.');
    });
 });

 
  app.post('/GET_PRODAV', function (req, res) {
    console.log("Got a GET request for the homepage");
    console.log(req);
    
    
    let qString = "select t3.*, t1.StoreID, t1.available from StoreProduct t1 inner join Store t2 on t1.StoreID = t2.StoreID inner join Product t3 on t1.ProductID = t3.ProductID where t2.Email='" + req.body.where_value + "'";
    
    connection.query(qString, function(err, rows, fields) {
    if (!err) {
        console.log("Displaying all the rows");
        
        res.send(JSON.stringify(rows));
    }
    else
        console.log('Error while performing Query.');
    });
 });
 
 app.post('/GET_ALL', function (req, res) {
    console.log("Got a GET request for the homepage");
    console.log(req);
    
    let qString = "SELECT * from " + req.body.table + " ";
    
    connection.query(qString, function(err, rows, fields) {
    if (!err) {
        console.log("Displaying all the rows");
        
        res.send(JSON.stringify(rows));
    }
    else
        console.log('Error while performing Query.');
    });
 });
 
 
 
  app.post('/UPDATE', function (req, res) {
    console.log("Got a GET request for the homepage");
    console.log(req);
   
   
    let qString = "UPDATE " + req.body.table + " SET "+ req.body.column_name + " = '"+ req.body.new_value +"' WHERE " + req.body.where_key +" ='" + req.body.where_value + "'";
    connection.query(qString, function(err, rows, fields) {
    if (!err) {
        console.log("Displaying all the rows");
        
        res.send(JSON.stringify(rows));
    }
    else
        console.log('Error while performing Query.');
    });
    
 });
 
 
   app.post('/DELETE', function (req, res) {
    console.log("Got a GET request for the homepage");
    console.log(req);
   
    let qString = "DELETE from " + req.body.table + " WHERE " + req.body.where_key +" ='" + req.body.where_value + "'";
    
    connection.query(qString, function(err, rows, fields) {
    if (!err) {
        console.log("Displaying all the rows");
        
        res.send(JSON.stringify(rows));
    }
    else
        console.log('Error while performing Query.');
    });
    
 });
 