var currentPage = 0;
var feedLoadIcon;
var postInput;
var feedElement;

function loadFeed() {
  const feedContainer = document.getElementById("feed");
  feedLoadIcon.classList.remove("hidden");

  fetch("/api/posts/feed", {
    method: "POST",
    body: JSON.stringify({
      page: currentPage,
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  })
    .then((response) => response.json())
    .then((json) => {
      currentPage++;
      json.forEach((element) => {
        const postContainer = document.createElement("div");
        postContainer.classList.add("panel");
        postContainer.classList.add("post");

        const postInfoContainer = document.createElement("div");
        postInfoContainer.classList.add("post-info");

        const posterImageContainer = document.createElement("div");
        posterImageContainer.classList.add("poster-image");
        const posterImage = document.createElement("img");
        posterImage.src = "/img/user.png";
        posterImageContainer.appendChild(posterImage);

        const posterInfoContainer = document.createElement("div");
        posterInfoContainer.classList.add("poster-info");
        const posterName = document.createElement("span");
        posterName.classList.add("poster-name");
        posterName.textContent = element.profileName;
        const posterUsername = document.createElement("span");
        posterUsername.classList.add("poster-username");
        posterUsername.textContent = "@" + element.profileUsername;
        posterInfoContainer.appendChild(posterName);
        posterInfoContainer.appendChild(posterUsername);

        const postTagsContainer = document.createElement("div");
        postTagsContainer.classList.add("post-tags");

        element.tags.forEach((tag) => {
          const tagContainer = document.createElement("span");
          tagContainer.classList.add("post-tag");
          tagContainer.textContent = tag.name;
          tagContainer.style.backgroundColor = "#" + tag.hexColor;

          postTagsContainer.appendChild(tagContainer);
        });

        postInfoContainer.appendChild(posterImageContainer);
        postInfoContainer.appendChild(posterInfoContainer);
        postInfoContainer.appendChild(postTagsContainer);

        const invSeparator = document.createElement("div");
        invSeparator.classList.add("inv-separator");

        const postBottom = document.createElement("div");
        postBottom.classList.add("post-bottom");

        const postText = document.createElement("div");
        postText.classList.add("post-text");
        postText.textContent = element.content;

        if (element.blurred) {
          postText.classList.add("blurred-text");
          postText.onclick = (event) => {
            postText.classList.remove("blurred-text");
          };
        }

        const postData = document.createElement("div");
        postData.classList.add("post-data");
        postData.textContent = element.date;

        postBottom.appendChild(postText);
        postBottom.appendChild(postData);

        postContainer.appendChild(postInfoContainer);
        postContainer.appendChild(invSeparator);
        postContainer.appendChild(postBottom);

        feedContainer.insertBefore(postContainer, feedLoadIcon);
      });
    })
    .finally(() => {
      feedLoadIcon.classList.add("hidden");
    });
}

function createPost() {
  const feedContainer = document.getElementById("feed");
  const loadIcon = document.getElementById("loadIcon");
  loadIcon.classList.remove("hidden");

  fetch("/api/posts/new", {
    method: "POST",
    body: JSON.stringify({
      content: postInput.value,
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  })
    .then((response) => response.json())
    .then((element) => {
      postInput.value = "";

      const postContainer = document.createElement("div");
      postContainer.classList.add("panel");
      postContainer.classList.add("post");

      const postInfoContainer = document.createElement("div");
      postInfoContainer.classList.add("post-info");

      const posterImageContainer = document.createElement("div");
      posterImageContainer.classList.add("poster-image");
      const posterImage = document.createElement("img");
      posterImage.src = "/img/user.png";
      posterImageContainer.appendChild(posterImage);

      const posterInfoContainer = document.createElement("div");
      posterInfoContainer.classList.add("poster-info");
      const posterName = document.createElement("span");
      posterName.classList.add("poster-name");
      posterName.textContent = element.profileName;
      const posterUsername = document.createElement("span");
      posterUsername.classList.add("poster-username");
      posterUsername.textContent = "@" + element.profileUsername;
      posterInfoContainer.appendChild(posterName);
      posterInfoContainer.appendChild(posterUsername);

      const postTagsContainer = document.createElement("div");
      postTagsContainer.classList.add("post-tags");

      element.tags.forEach((tag) => {
        const tagContainer = document.createElement("span");
        tagContainer.classList.add("post-tag");
        tagContainer.textContent = tag.name;
        tagContainer.style.backgroundColor = "#" + tag.hexColor;

        postTagsContainer.appendChild(tagContainer);
      });

      postInfoContainer.appendChild(posterImageContainer);
      postInfoContainer.appendChild(posterInfoContainer);
      postInfoContainer.appendChild(postTagsContainer);

      const invSeparator = document.createElement("div");
      invSeparator.classList.add("inv-separator");

      const postBottom = document.createElement("div");
      postBottom.classList.add("post-bottom");

      const postText = document.createElement("div");
      postText.classList.add("post-text");
      postText.textContent = element.content;

      const postData = document.createElement("div");
      postData.classList.add("post-data");
      postData.textContent = element.date;

      postBottom.appendChild(postText);
      postBottom.appendChild(postData);

      postContainer.appendChild(postInfoContainer);
      postContainer.appendChild(invSeparator);
      postContainer.appendChild(postBottom);

      feedContainer.insertBefore(postContainer, feedContainer.firstChild);
    }).catch((reason) => {
      console.log(reason);
    })
    .finally(() => {
      loadIcon.classList.add("hidden");
    });
}
function loadProfile() {
  const nameElement = document.getElementById("user-name");
  const usernameElement = document.getElementById("user-username");
  const descElement = document.getElementById("user-desc");
  const navbarUsernameElement = document.getElementById("navbar-username");

  fetch("/api/profiles/me", {
    method: "GET",
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  })
    .then((response) => response.json())
    .then((json) => {
      nameElement.textContent = json.name;
      usernameElement.textContent = "@" + json.username;
      descElement.textContent = json.description;
      navbarUsernameElement.textContent = "@" + json.username;
    });
}

function loadRecommendations() {
  const recommendationsElement = document.getElementById("recommendations");

  fetch("/api/profiles/recommendations", {
    method: "GET",
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  })
    .then((response) => response.json())
    .then((json) => {
      json.forEach(element => {
        const userElement = document.createElement("div");
        userElement.classList.add("recommended-user");

        const imgContainer = document.createElement("div");
        imgContainer.classList.add("recommended-img");

        const imgElement = document.createElement("img");
        imgElement.src = "/img/user.png";

        imgContainer.appendChild(imgElement);

        const textContainer = document.createElement("div");
        textContainer.classList.add("recommendation-text");

        const nameSpan = document.createElement("span");
        nameSpan.classList.add("recommended-name");
        nameSpan.textContent = element.name;

        const usernameSpan = document.createElement("span");
        usernameSpan.classList.add("recommended-username");
        usernameSpan.textContent = "@" + element.username;

        textContainer.appendChild(nameSpan);
        textContainer.appendChild(usernameSpan);

        const addButton = document.createElement("div");
        addButton.classList.add("recommended-add");
        addButton.textContent = "+";

        userElement.appendChild(imgContainer);
        userElement.appendChild(textContainer);
        userElement.appendChild(addButton);

        recommendationsElement.appendChild(userElement);
      });
    });
}

window.addEventListener("load", (event) => {
  feedLoadIcon = document.getElementById("loadIconContainer");
  postInput = document.getElementById("postInput");
  feedElement = document.getElementById("feed");

  feedElement.addEventListener("scroll", throttle(callback, 1000));

  const adaptPostInputHeightCallback = (event) => {
    postInput.style.height = 'auto';
    postInput.style.height = postInput.scrollHeight+'px';
  };
  postInput.onchange = adaptPostInputHeightCallback;
  postInput.onkeyup = adaptPostInputHeightCallback;

  loadFeed();
  loadProfile();
  loadRecommendations();
});

function throttle(fn, wait) {
  var time = Date.now() - wait;
  return function () {
    if (time + wait - Date.now() < 0) {
      fn();
      time = Date.now();
    }
  };
}

function callback() {
  if (
    feedElement.offsetHeight + feedElement.scrollTop >=
    feedElement.scrollHeight - 1000
  ) {
    loadFeed();
  }
}
