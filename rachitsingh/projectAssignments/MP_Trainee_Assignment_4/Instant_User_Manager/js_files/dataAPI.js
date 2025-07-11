let stateWiseCityMap = {};

function loadStates() {
  fetch("./APIData.json")
    .then((response) => response.json())
    .then((jsonArrayObject) => {
      stateWiseCityMap = jsonArrayObject;
      statesDistributionIntoCities();
    })
    .catch((errorMessage) => {
      alert("Error Loading APIData.json");
      console.error(errorMessage);
    });

  document.getElementById("state").addEventListener("change", (event) => {
    const selectedState = event.target.value;
    captureCitiesData(selectedState);
  });
}

function statesDistributionIntoCities() {
    const stateDropdownMenu = document.getElementById("state");
    // Here I have created a state drop down menu, with first child as the placeholder option => Select State
    stateDropdownMenu.innerHTML = '<option value="">-- Select State --</option>';

    // this states is an array which stores only states' names which are keys in stateWiseCityMap
    const states = Object.keys(stateWiseCityMap);

    // now all options are created for the dropdown menu using each state element stored in the array.
    states.forEach((state)=>{
        const option = document.createElement("option");
        option.value = state;
        option.textContent = state;
        stateDropdownMenu.appendChild(option)
    })
}

function captureCitiesData(state){
    const cityDropdownMenu = document.getElementById("city");
    cityDropdownMenu.innerHTML = '<option>-- Select City --</option>';
    const cities = stateWiseCityMap[state] || [];

    cities.forEach((city) => {
        const option = document.createElement("option");
        option.value = city;
        option.textContent = city;
        cityDropdownMenu.appendChild(option);
    });
    if (typeof callback === "function") {
      callback();
    }
    
}