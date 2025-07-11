

function validateInputForm() {
  let isValidInput = true;

  document.querySelectorAll(".error").forEach((element) => {
    element.textContent = "";
  });

  const name = document.getElementById("name").value.trim();
if (name === "" || name.length > 100 || !isOnlyLettersAndSpaces(name)) {
  document.getElementById("inputNameError").textContent =
    "Name must contain only letters and spaces";
  isValidInput = false;
}

  const ageInput = document.getElementById("age").value.trim();
  const age = parseInt(ageInput);
  if (!ageInput || isNaN(age) || age <= 0 || age > 110) {
    document.getElementById("inputAgeError").textContent = "Enter a valid age (1-110)";
    isValidInput = false;
  }

  const email = document.getElementById("email").value.trim();
  if (!validateEmailEntry(email)) {
    document.getElementById("inputEmailError").textContent = "Invalid input e-mail found";
    isValidInput = false;
  }

  const phone = document.getElementById("phone").value.trim();
    if (phone.length !== 10 || isNaN(phone) || phone === "0000000000") {
    document.getElementById("inputPhoneError").textContent =
      "Enter a valid 10-digit mobile number";
    isValidInput = false;
  }

  const branchCheckedOrNot = document.querySelector('input[name="branch"]:checked');
  if (!branchCheckedOrNot) {
    document.getElementById("inputBranchError").textContent = "Branch not selected.";
    isValidInput = false;
  }

  const languages = document.querySelectorAll('input[name="languages"]:checked');
  if (languages.length === 0) {
    document.getElementById("inputLanguagesError").textContent = "Select at least one language.";
    isValidInput = false;
  }

  const state = document.getElementById("state").value;
  if (!state) {
    document.getElementById("inputStateError").textContent = "Select a state";
    isValidInput = false;
  }

  const city = document.getElementById("city").value;
  if (!city) {
    document.getElementById("inputCityError").textContent = "Select a city";
    isValidInput = false;
  }

  return isValidInput;
}
function isOnlyLettersAndSpaces(str) {
  for (let i = 0; i < str.length; i++) {
    const char = str[i];
    const code = char.charCodeAt(0);
    const isLetter =
      (code >= 65 && code <= 90) ||
      (code >= 97 && code <= 122); 
    const isSpace = char === " ";

    if (!isLetter && !isSpace) {
      return false;
    }
  }
  return true;
}
function collectFormData() {
  const name = document.getElementById("name").value.trim();
  const age = parseInt(document.getElementById("age").value.trim());
  const email = document.getElementById("email").value.trim();
  const phone = document.getElementById("phone").value.trim();
  const branch = document.querySelector('input[name="branch"]:checked').value;
  const languagesKnownItems = document.querySelectorAll('input[name="languages"]:checked');
  const languages = Array.from(languagesKnownItems).map((lang) => lang.value).join(", ");
  const state = document.getElementById("state").value;
  const city = document.getElementById("city").value;

  return { name, age, email, phone, branch, languages, state, city };
}

function clearFormFields() {
  document.getElementById("dataForm").reset();

  document.querySelectorAll(".error").forEach((element) => {
    element.textContent = "";
  });

  document.getElementById("state").innerHTML = '<option value="">-- Select State --</option>';
  document.getElementById("city").innerHTML = '<option value="">-- Select City --</option>';
  statesDistributionIntoCities();
}

