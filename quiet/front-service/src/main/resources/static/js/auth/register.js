const usernameInput = document.getElementById("usernameInput");
const emailInput = document.getElementById("emailInput");
const passwordInput = document.getElementById("passwordInput");
const confirmPasswordInput = document.getElementById("confirmPasswordInput");
const registerButton = document.getElementById("registerButton");
const errorMessage = document.getElementById("errorMessage");

registerButton.onclick = sendLoginRequest;

function sendLoginRequest() {
  errorMessage.classList.add("hidden");
  if (passwordInput.value !== confirmPasswordInput.value) {
    errorMessage.textContent = "Please confirm your password.";
    errorMessage.classList.remove("hidden");
  }

  fetch("/api/users/register", {
    method: "POST",
    body: JSON.stringify({
      email: emailInput.value,
      password: passwordInput.value,
      username: usernameInput.value,
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
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
