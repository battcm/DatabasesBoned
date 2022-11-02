/**
 * @fileoverview
 * Provides the JavaScript interactions for all pages.
 *
 * @author 
 * PUT_YOUR_NAME_HERE
 */

/** namespace. */
var rhit = rhit || {};

/** globals */
rhit.variableName = "";

/** function and class syntax examples */
rhit.functionName = function () {
	/** function body */
};

rhit.ClassName = class {
	constructor() {

	}

	methodName() {

	}
}

/* Main */
/** function and class syntax examples */
rhit.main = function () {
// Data Picker Initialization
// Date picker only

const userAction = async () => {
	console.log('here');
	const obj={ };
	const response = await fetch(' http://localhost:8080/v1/select/RestName', {
	  method: 'POST',
	  body: JSON.stringify(obj),
	  headers: {
		'Content-Type': 'application/json'
	  }
	});
	const data=await response.json();
	console.log(data);
	show(data);

	// do something with myJson
	if( response.status==200){

	}else{
		location.href="fail.html";
	}
}

function show(data) {
	
    let tab = 
        `<tr>
          <th>RestName</th></tr>`;
    
    // Loop to access all rows 
    for (let r of Object.values(data)) {
        tab += `<tr> 
    <td>${r.RestName} </td>         
</tr>`;
$('.dropdown-menu').append('<a class="dropdown-item" href="#">'+`${r.RestName}`+'<a>');
    }
	
}
	

	userAction();
    
	}


rhit.main();
