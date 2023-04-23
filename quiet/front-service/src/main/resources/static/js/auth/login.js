const emailInput = document.getElementById('emailInput');
const passwordInput = document.getElementById('passwordInput');
const loginButton = document.getElementById('loginButton');

loginButton.onclick = sendLoginRequest;

function sendLoginRequest() {
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
    .then((response) => response.json())
    .then((json) => {
        if (json.success) {
            window.location.href = '/'
        }
    });
}