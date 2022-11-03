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
rhit.res = function () {

    const inputEmailEl = document.querySelector("#inputName");
	const inputPasswordEl = document.querySelector("#inputAddr");
	document.querySelector("#Submit").onclick = (event) => {
		console.log(`values: ${inputEmailEl.value}  , ${inputPasswordEl.value}`);
		const userAction = async () => {
			const obj={ name: `${inputEmailEl.value}`,addr: `${inputPasswordEl.value}`};
            console.log(obj);
			const response = await fetch(' http://localhost:8080/v1/insert/res', {
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
          getTable();
		}

    
	
    document.querySelector("#Update").onclick = (event) => {
        const userAction = async () => {
            const obj={id:`${updateID.value}`, name: `${updateName.value}`,addr: `${updateAddr.value}`};
            const response = await fetch(' http://localhost:8080/v1/update/res', {
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
                location.href="fail.html";
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
        const response = await fetch(' http://localhost:8080/v1/select/res', {
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
              <th>RestID	</th>
              <th>RestName	</th>
              <th>RestAddress	</th>
             </tr>`;
            for (let r of Object.values(data)) {
                tab += `<tr>
                <td>${r.RestId} </td>
                <td>${r.RestName}</td>
                <td>${r.RestAddress}</td>
            </tr>`;
                    }
            document.querySelector('#dataTable').innerHTML=(tab);

        }else{
            // location.href="fail.html";
        }

    }
    getTable();

	}


rhit.res();
