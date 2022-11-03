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
          getTable();
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
          getTable();
    }

    document.querySelector("#apply").onclick = (event)=>{
            getTable();
        }

        const getTable = async () => {
            const obj={col: document.querySelector('#select').value};
            console.log(obj);
            const response = await fetch(' http://localhost:8080/v1/select/order', {
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
                  <th>RestId	</th>
                  <th>FoodItemsID	</th>
                  <th>DrinkID	</th>
                  <th>DateRecieved	</th>
                  <th>Quantity	</th>
                  <th>StorageType	</th>
                 </tr>`;
                for (let r of Object.values(data)) {
                    tab += `<tr>
                    <td>${r.RestId} </td>
                    <td>${r.FoodItemsID}</td>
                    <td>${r.DrinkID}</td>
                    <td>${r.DateRecieved}</td>
                    <td>${r.Quantity}</td>
                    <td>${r.StorageType}</td>
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
