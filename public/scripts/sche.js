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




    const inputEmailEl = document.querySelector("#inputId");
	const inputPasswordEl = document.querySelector("#inputDay");
    const inputEmailname = document.querySelector("#inputOpen");
	const inputPasswordAddr = document.querySelector("#inputClose");
	document.querySelector("#Submit").onclick = (event) => {
		console.log(`values: ${inputEmailEl.value}  , ${inputPasswordEl.value}`);
		const userAction = async () => {
			const obj={ id: `${inputEmailEl.value}`,day: `${inputPasswordEl.value}`, open: `${inputEmailname.value}`,close: `${inputPasswordAddr.value}`};
			const response = await fetch(' http://localhost:8080/v1/insert/schedule', {
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
            const obj={ id: `${updateId.value}`,day: `${updateDay.value}`, open: `${updateOpen.value}`,close: `${updateClose.value}`};
            const response = await fetch(' http://localhost:8080/v1/update/schedule', {
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
            const response = await fetch(' http://localhost:8080/v1/select/schedule', {
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
                  <th>DayName	</th>
                  <th>OpenTime	</th>
                  <th>CloseTime	</th>
                 </tr>`;
                for (let r of Object.values(data)) {
                    tab += `<tr>
                    <td>${r.RestId} </td>
                    <td>${r.DayName}</td>
                    <td>${r.OpenTime}</td>
                    <td>${r.CloseTime}</td>
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
