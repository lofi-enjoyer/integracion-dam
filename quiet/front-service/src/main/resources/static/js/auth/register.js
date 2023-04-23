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
    .then((response) => response.json())
    .then((json) => {
      console.log(json);
      if (json.username == undefined) {
        errorMessage.textContent = "That email is being used by another user.";
        errorMessage.classList.remove("hidden");
      }

      window.location.href = "http://localhost:8080/login";
    })
    .catch((reason) => {
    });
}
