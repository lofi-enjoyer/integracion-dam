function uploadProfileImage() {
    var fileInput = document.getElementById("profileImgInput");
    
    if (fileInput.value == null)
        return;

    var form = new FormData();
    form.append("file", fileInput.files[0]);
    fetch('/api/media/uploadprofile', {
        method: "POST", 
        body: form
    })
    .then(response => {
        fileInput.value = null;
    });
}

function updateProfile() {
    const name = document.getElementById('nameInput');
    const username = document.getElementById('usernameInput');
    const description = document.getElementById('descriptionInput');
    fetch('/api/profiles/edit', {
        method: "POST",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        }, 
        body: JSON.stringify({
            name: name.value,
            username: username.value,
            description: description.value
        })
    })
    .then(response => {
        console.log(response);
        console.log(response.ok);
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text) });
        }
        return response.json();
    })
    .catch(error => {
        showError(error.message);
        console.log(error.message);
    });
}

function loadProfile() {
    const nameElement = document.getElementById("nameInput");
    const usernameElement = document.getElementById("usernameInput");
    const descElement = document.getElementById("descriptionInput");
  
    fetch("/api/profiles/me", {
      method: "GET",
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    })
    .then((response) => response.json())
    .then((json) => {
        nameElement.value = json.name;
        usernameElement.value = json.username;
        descElement.value = json.description;
    });
}

function loadTags() {
  const optionsContainer = document.getElementById('optionsContainer');
  fetch("/api/posts/mytags", {
    method: "GET"
  })
    .then((response) => response.json())
    .then((json) => {
      json.forEach(tag => {
        const tagContainer = document.createElement('div');
        tagContainer.classList.add("post-tag");
        tagContainer.classList.add("create-post-tag");
        tagContainer.style.backgroundColor = "#" + tag.hexColor;
        
        const label = document.createElement('label');
        label.textContent = tag.name;
        label.htmlFor = 'tag' + tag.id;

        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.id = 'tag' + tag.id;
        checkbox.value = tag.id;
        checkbox.checked = tag.checked;

        tagContainer.appendChild(checkbox);
        tagContainer.appendChild(label);

        optionsContainer.appendChild(tagContainer);
      })
    });
}

function saveTags() {
  fetch("/api/posts/savetags", {
        method: "POST",
        headers: {
          "Content-type": "application/json; charset=UTF-8",
        }, 
        body: JSON.stringify({
            tagsIds: getSelectedTags()
        })
  });
}


function getSelectedTags() {
  var array = [];
  var checkboxes = document.getElementById('optionsContainer').querySelectorAll('input[type=checkbox]:checked');

  for (var i = 0; i < checkboxes.length; i++) {
    array.push(checkboxes[i].value);
  }

  return array;
}

function showError(message) {
    const errorDisplay = document.getElementById('errorMessage');
    errorDisplay.classList.add('shown');
    errorDisplay.classList.remove('hidden');
    errorDisplay.textContent = message;
}

window.addEventListener("load", (event) => {
    loadProfile();
    loadTags();
});
