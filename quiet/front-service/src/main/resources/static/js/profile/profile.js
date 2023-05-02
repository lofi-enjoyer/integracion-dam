const urlParts = window.location.href.split("/");
const lastPart = urlParts[urlParts.length - 1];
const username = lastPart.split("?")[0];

function loadProfile() {
  const profileImg = document.getElementById("profileImg");
  const nameElement = document.getElementById("name");
  const usernameElement = document.getElementById("username");
  const descElement = document.getElementById("description");
  const followersCountElement = document.getElementById("followersCount");

  fetch("/api/profiles/" + username, {
    method: "GET",
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  })
    .then((response) => response.json())
    .then((json) => {
      profileImg.src = "/api/media/profile/" + json.username;
      nameElement.textContent = json.name;
      usernameElement.textContent = "@" + json.username;
      descElement.textContent = json.description;
      followersCountElement.textContent = json.followersCount + " followers";
    });
}

window.addEventListener("load", (event) => {
    loadProfile();
  });