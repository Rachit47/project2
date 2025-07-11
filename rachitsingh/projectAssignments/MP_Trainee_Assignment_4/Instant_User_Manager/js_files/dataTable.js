function addTablerecord(recordEntry) {
  const tableBody = document.querySelector("#dataTable tbody");
  const record = document.createElement("tr");
  record.classList.add("fade-in");

  record.innerHTML = `
    <td>${recordEntry.name}</td>
    <td>${recordEntry.age}</td>
    <td>${recordEntry.email}</td>
    <td>${recordEntry.phone}</td>
    <td>${recordEntry.branch}</td>
    <td>${recordEntry.languages}</td>
    <td>${recordEntry.state}</td>
    <td>${recordEntry.city}</td>
    <td>
      <button class="delete-btn">Delete</button>
      <button class="edit-btn">Edit</button>
    </td>`;

  tableBody.appendChild(record);

  record.querySelector(".delete-btn").addEventListener("click", function () {
    const confirmDelete = confirm("Are you sure you want to delete this record?");
    if (confirmDelete === true) {
      record.classList.add("fade-out");
      setTimeout(() => {
        record.remove();
        updateEntryCount();
        showOrHideNoResults();
      }, 800);
    }
  });

  record.querySelector(".edit-btn").addEventListener("click", function () {
    const cells = record.querySelectorAll("td");

    document.getElementById("name").value = cells[0].textContent;
    document.getElementById("age").value = cells[1].textContent;
    document.getElementById("email").value = cells[2].textContent;
    document.getElementById("phone").value = cells[3].textContent;

    const branchValue = cells[4].textContent;
    const branchInput = document.querySelector(`input[name="branch"][value="${branchValue}"]`);
    if (branchInput) branchInput.checked = true;

    const languageArray = cells[5].textContent.split(",").map(l => l.trim());
    document.querySelectorAll(`input[name="languages"]`).forEach(cb => {
      cb.checked = languageArray.includes(cb.value);
    });

    const stateValue = cells[6].textContent;
    const cityValue = cells[7].textContent;

    document.getElementById("state").value = stateValue;
    captureCitiesData(stateValue, () => {
      document.getElementById("city").value = cityValue;
    });

    record.remove();

    document.getElementById("addButton").textContent = "Update Entry";

    updateEntryCount();
    showOrHideNoResults();
  });

  applyColumnFilters();
  updateEntryCount();
  showOrHideNoResults();
}


function applyColumnFilters(){
  const filters = Array.from(document.querySelectorAll('.search-box input')).map(input =>
    input.value.trim().toLowerCase()
  );

  const records = document.querySelectorAll('#dataTable tbody tr');

  records.forEach(record => {
    const cells = record.querySelectorAll('td');
    let toBeShown = true;

    for(let i = 0; i<filters.length; i++){
      if(filters[i] && !cells[i].textContent.toLowerCase().includes(filters[i])) {
        toBeShown = false;
        break;
      }
    }
    record.style.display = toBeShown ? '': 'none';
  })
}

function updateEntryCount() {
  const filteredRecords = Array.from(
    document.querySelectorAll("#dataTable tbody tr")
  ).filter(row => row.style.display !== "none");
}
function showOrHideNoResults() {
  const records = Array.from(document.querySelectorAll("#dataTable tbody tr"));
  const displayedRecords = records.filter(record => record.style.display !== "none");

  const noResults = document.getElementById("noResults");
  if (noResults) {
    noResults.classList.toggle("hidden", displayedRecords.length !== 0);
  }
}
