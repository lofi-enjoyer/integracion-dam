var feedLoadIcon;
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
          posterImage.src = "/api/media/profile/" + element.profileUsername;
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
  
          const postBottom1 = document.createElement("div");
          postBottom1.classList.add("post-bottom");
  
          const postText = document.createElement("div");
          postText.classList.add("post-text");
          postText.textContent = element.content;
  
          if (element.blurred) {
            postText.classList.add("blurred-text");
            postText.onclick = (event) => {
              postText.classList.remove("blurred-text");
            };
          }
  
          const postBottom2 = document.createElement("div");
          postBottom2.classList.add("post-bottom");
  
          const postData = document.createElement("div");
          postData.classList.add("post-data");
          postData.textContent = element.date;
  
          const postLikes = document.createElement("div");
          postLikes.classList.add("post-likes");
          postLikes.textContent = "💕 " + element.likes;
          if (element.likedByUser) {
            postLikes.classList.add("liked");
            postLikes.onclick = () => unlikePost(element.id, postLikes);
          } else {
            postLikes.onclick = () => likePost(element.id, postLikes);
          }
  
          postBottom1.appendChild(postText);
  
          postBottom2.appendChild(postLikes);
          postBottom2.appendChild(postData);
  
          postContainer.appendChild(postInfoContainer);
          postContainer.appendChild(invSeparator);
          postContainer.appendChild(postBottom1);
          postContainer.appendChild(postBottom2);
  
          feedContainer.insertBefore(postContainer, feedLoadIcon);
        });
      })
      .finally(() => {
        feedLoadIcon.classList.add("hidden");
      });
  }

window.addEventListener("load", (event) => {
  feedLoadIcon = document.getElementById("loadIconContainer");
  feedElement = document.getElementById("feed");
 
  loadFeed();
});
  