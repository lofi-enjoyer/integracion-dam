var currentPage = 0;

function loadFeed() {
  const feedContainer = document.getElementById("feed");

  fetch("/api/posts/feed", {
    method: "POST",
    body: JSON.stringify({
      page: currentPage,
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8"
    }
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
        posterName.textContent = element.profileUsername;
        const posterUsername = document.createElement("span");
        posterUsername.classList.add("poster-username");
        posterUsername.textContent = element.date;
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
        postData.textContent = element.likes;

        postBottom.appendChild(postText);
        postBottom.appendChild(postData);

        postContainer.appendChild(postInfoContainer);
        postContainer.appendChild(invSeparator);
        postContainer.appendChild(postBottom);

        feedContainer.appendChild(postContainer);

        const smallInvSeparator = document.createElement("div");
        smallInvSeparator.classList.add("small-inv-separator");
        feedContainer.appendChild(smallInvSeparator);
      });
    });
}

window.addEventListener("load", (event) => {
  loadFeed();
});
