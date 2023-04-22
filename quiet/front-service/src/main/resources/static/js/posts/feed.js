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
      page: currentPage
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
        posterImage.src = "/test.png";
        posterImageContainer.appendChild(posterImage);

        const posterInfoContainer = document.createElement("div");
        posterInfoContainer.classList.add("poster-info");
        const posterName = document.createElement("span");
        posterName.classList.add("poster-name");
        posterName.textContent = element.profileName;
        const posterUsername = document.createElement("span");
        posterUsername.classList.add("poster-username");
        posterUsername.textContent = element.profileUsername;
        posterInfoContainer.appendChild(posterName);
        posterInfoContainer.appendChild(posterUsername);

        postInfoContainer.appendChild(posterImageContainer);
        postInfoContainer.appendChild(posterInfoContainer);

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

        feedContainer.insertBefore(postContainer, feedLoadIcon);

        const smallInvSeparator = document.createElement("div");
        smallInvSeparator.classList.add("small-inv-separator");
        feedContainer.insertBefore(smallInvSeparator, feedLoadIcon);
      });
    }).finally(() => {
      feedLoadIcon.classList.add("hidden");
    });;
}

function createPost() {
  const loadIcon = document.getElementById("loadIcon");
  loadIcon.classList.remove("hidden");

  fetch("/api/posts/new", {
    method: "POST",
    body: JSON.stringify({
      content: postInput.value
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  })
    .then((response) => response.json())
    .then((json) => {
      postInput.value = "";
    }).finally(() => {
      loadIcon.classList.add("hidden");
    });
}

window.addEventListener("load", (event) => {
  feedLoadIcon = document.getElementById("loadIconContainer");
  postInput = document.getElementById("postInput");
  feedElement = document.getElementById("feed");

  feedElement
    .addEventListener("scroll", throttle(callback, 1000));

  loadFeed();
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
  if (feedElement.offsetHeight + feedElement.scrollTop >= feedElement.scrollHeight - 1000){
    loadFeed();
 }
}
