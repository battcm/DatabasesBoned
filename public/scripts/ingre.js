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
// rhit.renderTable = function () {
// 	const url = ' http://localhost:8080/v1/tableify/ingredient';
// 	let request = new Request(url,{
// 		method: 'POST',
// 		body: JSON.stringify(data),
// 		headers: new Headers({
// 			'Content-Type': 'application/json; charset=UTF-8'
// 		})
// 	});

// 	fetch(request)
// 		.then$(function () {
		
// 				let newRow = $("<tr>");
// 				let cols = '';
		
// 				// Table columns
// 				cols += '<th scrope="row">' + counter + '</th>';
// 				cols += '<td><input class="form-control rounded-0" type="text" name="firstname" placeholder="First name"></td>';
// 				cols += '<td> + + </td>';
// 				cols += '<td><input class="form-control rounded-0" type="text" name="handle" placeholder="Handle"></td>';
// 				cols += '<td><button class="btn btn-danger rounded-0" id ="deleteRow"><i class="fa fa-trash"></i></button</td>';
		
// 				// Insert the columns inside a row
// 				newRow.append(cols);
// 				$("table").append(newRow);
// 			});
		
// 			// Remove row when delete btn is clicked
// 			$("table").on("click", "#deleteRow", function (event) {
// 				$(this).closest("tr").remove();
// 				counter -= 1
// 			});
// 		});

// }

rhit.main = function () {


    const inputEmailEl = document.querySelector("#inputName");
	const inputPasswordEl = document.querySelector("#inputAddr");
	document.querySelector("#Submit").onclick = (event) => {
		console.log(`values: ${inputEmailEl.value}  , ${inputPasswordEl.value}`);
		const userAction = async () => {
			const obj={ name: `${inputEmailEl.value}`,type: `${inputPasswordEl.value}`};
			const response = await fetch(' http://localhost:8080/v1/insert/ingred', {
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
        console.log(`values: ${updateID.value}, ${updateName.value}, ${updateType.value}`);
        const userAction = async () => {
            const obj={ id: `${updateID.value}`,name: `${updateName.value}`, type:`${updateType.value}`};
            const response = await fetch(' http://localhost:8080/v1/update/ingred', {
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
            const response = await fetch(' http://localhost:8080/v1/select/ingre', {
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
                  <th>IngredientID	</th>
                  <th>IngreName	</th>
                  <th>IngreType	</th>
                  <th>Supplier	</th>
                 </tr>`;
                for (let r of Object.values(data)) {
                    tab += `<tr>
                    <td>${r.IngredientID} </td>
                    <td>${r.IngreName}</td>
                    <td>${r.IngreType}</td>
                    <td>${r.Supplier}</td>
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
