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




    const inputEmailEl = document.querySelector("#inputResId");
	const inputPasswordEl = document.querySelector("#inputFoodId");
    const inputEmailname = document.querySelector("#inputDrinkId");
	const inputPasswordAddr = document.querySelector("#inputQuan");
    const inputEmailElll = document.querySelector("#inputStor");
	const inputPasswordElll = document.querySelector("#inputDate");
	document.querySelector("#Submit").onclick = (event) => {
		console.log(`values: ${inputEmailEl.value}  , ${inputPasswordEl.value}`);
		const userAction = async () => {
            console.log('here');
			const obj={ resid: `${inputEmailEl.value}`,foodid: `${inputPasswordEl.value}`, drinkid: `${inputEmailname.value}`,quan: `${inputPasswordAddr.value}`,stor: `${inputEmailElll.value}`,day: `${inputPasswordElll.value}`};
			const response = await fetch(' http://localhost:8080/v1/insert/order', {
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
                inputPasswordAddr.innerHTML='';
                inputEmailname.innerHTML='';
			}else{
				location.href="fail.html";
			}
		}
			)};
			
		  userAction();
		}
	

    document.querySelector("#Update").onclick = (event) => {
         const userAction = async () => {
            const obj={ resId: `${updateResId.value}`,ingreId: `${updateFoodId.value}`, drinkId: `${updateDrinkId.value}`,quantity: `${updateQuan.value}`,type: `${updateType.value}`,date: `${updateDate.value}`};
            const response = await fetch(' http://localhost:8080/v1/update/order', {
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
