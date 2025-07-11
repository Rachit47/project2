window.addEventListener("DOMContentLoaded", function () {
  initializeFormEvents();
  loadStates();
  clearSearchInputData();
  attachSearchListeners();
});

function emailExists(email, excludeRow = null) {
  const rows = document.querySelectorAll("#dataTable tbody tr");
  email = email.trim().toLowerCase();

  for (let row of rows) {
    const emailCell = row.cells[2];
    if (!emailCell) continue;

    const rowEmail = emailCell.textContent.trim().toLowerCase();
    if (rowEmail === email && row !== excludeRow) {
      return true;
    }
  }
  return false;
}

let currentlyEditingRecord = null;

function initializeFormEvents() {
  const form = document.getElementById("dataForm");
  const addButton = document.getElementById("addButton");

  form.addEventListener("input", () => {
    const isValidInput = validateInputForm();
    addButton.disabled = !isValidInput;
  });

form.addEventListener("submit", function (event) {
  event.preventDefault();
  const isValidSubmission = validateInputForm();
  if (!isValidSubmission) return;

  const formData = collectFormData();

  if (emailExists(formData.email, currentlyEditingRecord)) {
    alert("This email already exists. Please enter a unique email.");
    return;
  }

  if (currentlyEditingRecord) {
    const cells = currentlyEditingRecord.querySelectorAll("td");
    cells[0].textContent = formData.name;
    cells[1].textContent = formData.age;
    cells[2].textContent = formData.email;
    cells[3].textContent = formData.phone;
    cells[4].textContent = formData.branch;
    cells[5].textContent = formData.languages;
    cells[6].textContent = formData.state;
    cells[7].textContent = formData.city;

    currentlyEditingRecord = null;
    addButton.textContent = "Add Entry";
  } else {
    addTablerecord(formData);
  }

  clearFormFields();
  addButton.disabled = true;
});
}

function clearSearchInputData(){
  const searchInputs = document.querySelectorAll(".search-box input ");
  searchInputs.forEach(input => input.value ="");
  
}

function attachSearchListeners() {
  const searchInputs = document.querySelectorAll('.search-box input');
  searchInputs.forEach(input => {
    input.addEventListener("input", applyColumnFilters);
  });
}