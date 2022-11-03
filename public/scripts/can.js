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

    const inputEmailEl = document.querySelector("#inputName");
	const inputPasswordEl = document.querySelector("#inputAddr");
	document.querySelector("#Submit").onclick = (event) => {
		console.log(`values: ${inputEmailEl.value}  , ${inputPasswordEl.value}`);
		const userAction = async () => {
			const obj={ a: `${inputEmailEl.value}`,b: `${inputPasswordEl.value}`};
			const response = await fetch(' http://localhost:8080/v1/insert/can', {
			  method: 'POST',
			  body: JSON.stringify(obj),
			  headers: {
				'Content-Type': 'application/json'
			  }
			}).then(function(value){
			console.log(value.status);
			// do something with myJson
			if( value.status==200){
				inputEmailEl.innerHTML='';
                inputPasswordEl.innerHTML='';
			}else{
				//location.href="fail.html";
			}
		}
			)};
			
		  userAction();
		  getTable();
		}

		

const getTable = async () => {
        const obj={col: "IngredientAID"};
        console.log(obj);
        const response = await fetch(' http://localhost:8080/v1/select/can', {
            method: 'POST',
            body: JSON.stringify(obj),
            headers: {
              'Content-Type': 'application/json'
            }
         });
        if( response.status==200){
            const data=await response.json();
            console.log(data);
            let tab =
            `<tr>
              <th>IngredientAID	</th>
              <th>IngredientBID	</th>
             </tr>`;
            for (let r of Object.values(data)) {
                tab += `<tr>
                <td>${r.IngredientAID} </td>
                <td>${r.IngredientBID}</td>
            </tr>`;
                    }
            document.querySelector('#dataTable').innerHTML=(tab);

        }else{
            // location.href="fail.html";
        }

    }
    getTable();
	
		 
	}


rhit.main();
