var rhit = rhit || {};

/* Main */
/** function and class syntax examples */
rhit.main = function () {
// Data Picker Initialization
// Date picker only

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

	}else{
		location.href="fail.html";
	}
}

	document.querySelector('#submit').onclick=(event)=>{
		const sel=document.querySelector('#select1');
		const day =document.querySelector('#datepicker');
		const obj={day: `${day.value}`, rest:`${sel.value}` };
		console.log(obj);
		const userSub = async () => {
		const response = await fetch(' http://localhost:8080/v1/select/app', {
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
              <th>Food Items ID	</th>
              <th>Food Name	</th>
              <th>Food Calories	</th>
              <th>Drink ID	</th>
              <th>Drink Name	</th>
              <th>Drink Brand	</th>
              <th>Drink Price	</th>
              <th>Food Date Recieved	</th>
              <th>Quantity	</th>
              <th>Storage Type	</th>
             </tr>`;
            for (let r of Object.values(data)) {
                r.DateRecieved=r.DateRecieved.slice(0,10);
                tab += `<tr>
                <td>${r.RestId} </td>
                <td>${r.RestName}</td>
                <td>${r.RestAddress}</td>
                <td>${r.FoodItemsID}</td>
                <td>${r.Name}</td>
                <td>${r.Calories}</td>
                <td>${r.DrinkID}</td>
                <td>${r.DrinkName}</td>
                <td>${r.DrinkBrand}</td>
                <td>${r.DrinkPrice}</td>
                <td>${r.DateRecieved}</td>
                <td>${r.Quantity}</td>
                <td>${r.StorageType}</td>
            </tr>`;
                    }
            document.querySelector('#restDay').innerHTML=(tab);

        }else{
            // location.href="fail.html";
        }

	    }
	    userSub();
    }


	

	userAction();

    
}


rhit.main();
