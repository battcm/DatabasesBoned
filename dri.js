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
	const inputPasswordEl = document.querySelector("#inputBrand");
    const inputPasswordKK = document.querySelector("#inputPrice");
	document.querySelector("#Submit").onclick = (event) => {
		console.log(`values: ${inputEmailEl.value}  , ${inputPasswordEl.value},${inputPasswordKK.value}`);
		const userAction = async () => {
			const obj={ name: `${inputEmailEl.value}`,brand: `${inputPasswordEl.value}`, price:`${inputPasswordKK.value}`};
			const response = await fetch(' http://localhost:8080/v1/insert/drink', {
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
				location.href="fail.html";
			}
		}
			)};
			
		  userAction();
	    }

    document.querySelector("#Update").onclick = (event) => {
        console.log(`values: ${updateID.value}, ${updateName.value}, ${updateBrand.value}, ${updatePrice.value}`);
        const userAction = async () => {
            const obj={ id: `${updateID.value}`,name: `${updateName.value}`, brand:`${updateBrand.value}`, price: `${updatePrice.value}`};
            const response = await fetch(' http://localhost:8080/v1/update/drink', {
              method: 'POST',
              body: JSON.stringify(obj),
              headers: {
                'Content-Type': 'application/json'
              }
            }).then(function(value){
            console.log(value.status);
            // do something with myJson
            if( value.status==200){

            }else{
                //location.href="fail.html";
            }
        }
            )};

          userAction();
        }

	}


rhit.main();
