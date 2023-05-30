const emailInput = document.getElementById('emailInput');
const passwordInput = document.getElementById('passwordInput');
const loginButton = document.getElementById('loginButton');

loginButton.onclick = sendLoginRequest;

function sendLoginRequest() {
  hideError();

    fetch("/api/auth/login", {
        method: "POST",
        body: JSON.stringify({
          email: emailInput.value,
          password: passwordInput.value
        }),
        headers: {
          "Content-type": "application/json; charset=UTF-8"
        }
    })
    .then((response) => {
      if (!response.ok) {
        return response.text().then(text => { throw new Error(text) });
      }
      return response.json();
    })
    .then((json) => {
        window.location.href = '/home';
    })
    .catch(error => {
        showError(error.message);
    });
}

function showError(message) {
    const errorDisplay = document.getElementById('errorMessage');
    errorDisplay.classList.add('shown');
    errorDisplay.classList.remove('hidden');
    errorDisplay.textContent = message;
}

function hideError() {
    const errorDisplay = document.getElementById('errorMessage');
    errorDisplay.classList.remove('shown');
    errorDisplay.classList.add('hidden');
}
