
//Updates a user using POST
function update() {
	alert("update called");
	myJSON = getPersonFromHTML();
	$.ajax({
		url : "cargostock/users/",
		type : 'PUT',
		data : JSON.stringify(myJSON),
		contentType : 'application/json',
		success: function(data) {
			alert("update succesful");
			toUpdate();
		}, failure: function(){
			alert("fail");
		}
	});
	document.getElementById("myForm").reset();	//Clear the form
	return false; //For at undgå at knappen poster data (default behavior).
}

/**
 * Creates a user using PUT, uses the cargostock/user/create path
 */
function submit() { //Formen kalder denne function, sikre at alle felter er udfyldt
	myJSON = getPersonFromHTML(); //myJSON is an object just like "bruger"
	$.ajax({ //Indleder et asynkront ajax kald
		url : "cargostock/users/", //specificerer endpointet
		type : 'POST', //Typen af HTTP requestet
		data : 	JSON.stringify(myJSON),
		contentType : 'application/json',
		//Nedenstående bliver ikke kørt
		success : function(data) {//Funktion der skal udføres når data er hentet
			alert("success"); //Manipulerer #mydiv.
		}, failure: function(){
			alert("fail");
		}
	});
	document.getElementById("myForm").reset();	//Clear the form
	return false; //For at undgå at knappen poster data (default behavior).
}

/**
 * Loads users from list using GET and displays them in table
 * @returns
 */
function loadUsers(){
	$(function() {
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'cargostock/users', //specificerer endpointet
			type : 'GET', //Typen af HTTP requestet (GET er default)
			contentType : 'application/json',
			//Nedenstående bliver ikke kørt
			success : function(data)
			{//Funktion der skal udføres når data er hentet
				iterate(data);
				//alert("data");
			}, failure: function()
			{
				alert("fail");
			}
		});
	});
}

/**
 * Removes user from list using DELETE
 * @returns
 */
function removeUser() {
	var id = document.getElementById("ID").value;
//	s	var myObj = null;
	$.ajax({
		url : "cargostock/users",
		type : 'DELETE',
		data : JSON.stringify(id),
		contentType: 'application/json',
		success : function(data)
		{
			alert("successful delete");
			toDelete(); //currently called as there is no direct method for emptying a table, 
			//and as such we reload the html
		}, error: function(message) {
			alert(message.responseText);
		}
	});
	document.getElementById("deleteForm").reset();	//Clear the form
}

//draws person information from html into a "bruger" variable
function getPersonFromHTML() {
	var id = document.getElementById("ID").value;
	var navn = document.getElementById("Brugernavn").value
	var ini = document.getElementById("ini").value
	var cpr = document.getElementById("CPR").value
	var passwd = document.getElementById("password").value
	var rolle = document.getElementById("rolle").value

	var bruger = {
		"userId" : id,
		"userName" : navn,
		"ini" : ini,
		"cpr" : cpr,
		"password" : passwd,
		"roles" : rolle
	}
	return bruger;
}

/**
 * Iterates throuch each data instance, first stringifying it into JSON and then parsing it into JSO
 * calls insert and adds all to the html table
 * @param data
 */
function iterate(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function() {  
		insert(this.userId, this.userName, this.ini, this.cpr, this.password, this.roles);
	});
}

/**
 * Adds each JSO to the html table
 * @param id
 * @param userName
 * @param ini
 * @param cpr
 * @param passwd
 * @param role
 * @returns
 */
function insert(id, userName, ini, cpr, passwd, role) {
	var table = document.getElementById("userTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	var cell4 = row.insertCell(3);
	var cell5 = row.insertCell(4);
	var cell6 = row.insertCell(5);

	cell1.innerHTML = id;
	cell2.innerHTML = userName;
	cell3.innerHTML = ini;
	cell4.innerHTML = cpr;
	cell5.innerHTML = passwd;
	cell6.innerHTML = role;
}


/**
 * loads the page that allows you to create a user
 * @returns
 */
function toCreate(){
	$(function(){
		$("#transform").load("userContext/opret.html");
	})
}

/**
 * Loads the page that allows you to delete a user
 * @returns
 */
function toDelete(){
	$(function() {
		$("#transform").load("userContext/deleteUser.html",null,function() {
			loadUsers();
		});
	});
}

/**
 * Loads the page that allows you to simply view users.
 * @returns
 */
function toView()
{
	$(function loadViewUsers(){
		$("#transform").load("userContext/ViewUsers.html");
		loadUsers(); //now not automatically executed once front page loads.
	});
}

function toUpdate()
{
	$(function() {
		$("#transform").load("userContext/UpdateUser.html",null,function() {
			loadUsers();
		});
	});
}