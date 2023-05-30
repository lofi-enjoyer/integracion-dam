const urlParts = window.location.href.split("/");
const lastPart = urlParts[urlParts.length - 1];
const username = lastPart.split("?")[0];

function loadProfile() {
  const profileImg = document.getElementById("profileImg");
  const nameElement = document.getElementById("name");
  const usernameElement = document.getElementById("username");
  const descElement = document.getElementById("description");
  const followersCountElement = document.getElementById("followersCount");
  const followButton = document.getElementById("followButton");

  fetch("/api/profiles/" + username, {
    method: "GET",
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  })
    .then((response) => {
      if (!response.ok)
          throw new Error("Profile not found");
      return response.json();
    })
    .then((json) => {
      profileImg.src = "/api/media/profile/" + json.username;
      nameElement.textContent = json.name;
      usernameElement.textContent = "@" + json.username;
      descElement.textContent = json.description;
      followersCountElement.textContent = json.followersCount + " followers";
    })
    .catch((error) => {
      nameElement.textContent = error.message;
    });

  fetch("/api/profiles/isfollowing", {
    method: "POST",
    body: JSON.stringify({
      username: username
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    }
  }).then((response) => {
    if (!response.ok)
        throw new Error("Profile not found");
    return response.json();
  })
  .then((json) => {
    if (!json) {
      followButton.textContent = "Follow";
      followButton.classList.add("unknown-button");
      followButton.onclick = () => {
        followProfile(username).then(response => {
          window.location.href = "/profile/" + username;
        });
      }
    } else {
      followButton.textContent = "Unfollow";
      followButton.classList.add("following-button");
      followButton.onclick = () => {
        unfollowProfile(username).then(response => {
          window.location.href = "/profile/" + username;
        });
      }
    }
  })
  .catch((error) => {
    console.log(error.message);
  });
}

window.addEventListener("load", (event) => {
    loadProfile();
});

function followProfile(profileToFollow) {
  return fetch("/api/profiles/follow", {
    method: "POST",
    body: JSON.stringify({
      username: profileToFollow,
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  });
}

function unfollowProfile(profileToUnfollow) {
  return fetch("/api/profiles/unfollow", {
    method: "POST",
    body: JSON.stringify({
      username: profileToUnfollow,
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  });
}
