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

function showError(message) {
    const errorDisplay = document.getElementById('errorMessage');
    errorDisplay.classList.add('shown');
}

window.addEventListener("load", (event) => {
    loadProfile();
});
