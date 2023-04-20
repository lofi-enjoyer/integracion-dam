
const feedContainer = document.getElementById("feed-container");

function loadFeed() {
  fetch("/api/posts/feed", {
    method: "GET",
  })
    .then((response) => response.json())
    .then((json) => {
        json.forEach(element => {
            const postContainer = document.createElement("div");
            postContainer.classList.add("post");

            const postContent = document.createElement("span");
            postContent.textContent = element.content;

            const postDate = document.createElement("span");
            postDate.textContent = element.date;

            const postLikes = document.createElement("span");
            postLikes.textContent = element.likes;

            const postUsername = document.createElement("span");
            postUsername.textContent = element.profileUsername;

            postContainer.appendChild(postContent);
            postContainer.appendChild(postDate);
            postContainer.appendChild(postLikes);
            postContainer.appendChild(postUsername);

            feedContainer.appendChild(postContainer);
        });
    });
}

window.addEventListener("load", (event) => {
    loadFeed();
});
