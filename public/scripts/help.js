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
const userActed = async () => {
    const sel=document.querySelector('#select1');
	const obj={rest:`${sel.value}` };
	const response = await fetch(' http://localhost:8080/v1/select/meal', {
	  method: 'POST',
	  body: JSON.stringify(obj),
	  headers: {
		'Content-Type': 'application/json'
	  }
	});
    if(response.status==200){
        const data=await response.json();
        console.log(data);
        for (let r of Object.values(data)) {
            $('#select2').append(`<option value="${r.Name}">`+`${r.Name}`+'</option>');
        }
    }else{
        location.href='fail.html';
    }
    
}

const userAction = async () => {
	const obj={ };
	const response = await fetch(' http://localhost:8080/v1/select/RestName', {
	  method: 'POST',
	  body: JSON.stringify(obj),
	  headers: {
		'Content-Type': 'application/json'
	  }
	});
		
	// do something with myJson
	if( response.status==200){
		const data=await response.json();
		console.log(data);
		for (let r of Object.values(data)) {
			$('#select1').append(`<option value="${r.RestName}">`+`${r.RestName}`+'</option>');
				}
                document.querySelector('#select1').onchange=(event)=>{
                    userActed();
                }
                  

	}else{
		location.href="fail.html";
	}
}

    document.querySelector('#submit').onclick=(event)=>{
        const userNO = async (response)=>{
            const data= await response.json();
            console.log(data);
            let tab = 
            `<tr>
              <th>IngreName	</th>
              <th>IngreType	</th>
              <th>Supplier	</th>
              <th>Ingredient ID	</th>
              <th>Food Name	</th>
             </tr>`;
            for (let r of Object.values(data)) {
                tab += `<tr> 
                <td>${r.IngreName} </td>
                <td>${r.IngreType} </td>
                <td>${r.Supplier} </td>
                <td>${r.IngredientID} </td>
                <td>${r.Name} </td>
            </tr>`;
                    }
                document.querySelector('#restDay').innerHTML=(tab);
        }
        const userCame = async () => {
            const day =document.querySelector('#select2');
            const obj={rest:`${day.value}` };
            const response = await fetch(' http://localhost:8080/v1/select/mealing', {
                method: 'POST',
                body: JSON.stringify(obj),
                headers: {
                  'Content-Type': 'application/json'
                }
              });
            
            if(response.status==200){
                userNO(response);
            }else{
                location.href="fail.html"
            }
        }



        userCame();
    }

	

	userAction();
    
    
	}


rhit.main();
