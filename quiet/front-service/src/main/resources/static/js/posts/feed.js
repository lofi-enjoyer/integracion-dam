var currentPage = 0;
var feedLoadIcon;
var postInput;
var feedElement;
var searchInput;

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

        const anchorElement = document.createElement("a");
        anchorElement.href = "/profile/" + element.profileUsername;

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

        anchorElement.appendChild(posterInfoContainer);

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
        postInfoContainer.appendChild(anchorElement);
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

function createPost() {
  if (postInput.value.trim().length == 0)
    return;

  const feedContainer = document.getElementById("feed");
  const loadIcon = document.getElementById("loadIcon");
  loadIcon.classList.remove("hidden");

  fetch("/api/posts/new", {
    method: "POST",
    body: JSON.stringify({
      content: postInput.value,
      tagIds: getSelectedTags()
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  })
    .then((response) => response.json())
    .then((element) => {
      clearSelectedTags();
      postInput.value = "";

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

      const anchorElement = document.createElement("a");
      anchorElement.href = "/profile/" + element.profileUsername;

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

      anchorElement.appendChild(posterInfoContainer);

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
      postInfoContainer.appendChild(anchorElement);
      postInfoContainer.appendChild(postTagsContainer);

      const invSeparator = document.createElement("div");
      invSeparator.classList.add("inv-separator");

      const postBottom1 = document.createElement("div");
      postBottom1.classList.add("post-bottom");

      const postText = document.createElement("div");
      postText.classList.add("post-text");
      postText.textContent = element.content;

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

      feedContainer.insertBefore(postContainer, feedContainer.firstChild);
    })
    .catch((reason) => {
      console.log(reason);
    })
    .finally(() => {
      loadIcon.classList.add("hidden");
    });
}

function loadProfile() {
  const profileImg = document.getElementById("userImg");
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
      profileImg.src = "/api/media/profile/" + json.username;
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
      json.forEach((element) => {
        const anchorElement = document.createElement("a");
        anchorElement.href = "/profile/" + element.username;

        const userElement = document.createElement("div");
        userElement.classList.add("recommended-user");

        const imgContainer = document.createElement("div");
        imgContainer.classList.add("recommended-img");

        const imgElement = document.createElement("img");
        imgElement.src = "/api/media/profile/" + element.username;

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
        addButton.onclick = () => {
          followProfile(element.username);
          recommendationsElement.removeChild(userElement);
        }

        anchorElement.appendChild(imgContainer);
        anchorElement.appendChild(textContainer);
        userElement.appendChild(anchorElement);
        userElement.appendChild(addButton);

        recommendationsElement.appendChild(userElement);
      });
    });
}

function likePost(id, countElement) {
  fetch("/api/posts/like", {
    method: "POST",
    body: JSON.stringify({
      postId: id,
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  })
    .then((response) => response.json())
    .then((json) => {
      countElement.textContent = "💕 " + json;
      countElement.onclick = () => unlikePost(id, countElement);
      countElement.classList.add("liked");
    });
}

function unlikePost(id, countElement) {
  fetch("/api/posts/unlike", {
    method: "POST",
    body: JSON.stringify({
      postId: id,
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  })
    .then((response) => response.json())
    .then((json) => {
      countElement.textContent = "💕 " + json;
      countElement.onclick = () => likePost(id, countElement);
      countElement.classList.remove("liked");
    });
}

function followProfile(profileToFollow) {
  fetch("/api/profiles/follow", {
    method: "POST",
    body: JSON.stringify({
      username: profileToFollow,
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  })
    .then((response) => response.json())
    .then((json) => {

    });
}

function unfollowProfile(profileToUnfollow) {
  fetch("/api/profiles/unfollow", {
    method: "POST",
    body: JSON.stringify({
      username: profileToUnfollow,
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  })
    .then((response) => response.json())
    .then((json) => {

    });
}

function loadTags() {
  const optionsContainer = document.getElementById('optionsContainer');
  fetch("/api/posts/alltags", {
    method: "GET"
  })
    .then((response) => response.json())
    .then((json) => {
      json.forEach(tag => {
        const tagContainer = document.createElement('div');
        tagContainer.classList.add("post-tag");
        tagContainer.classList.add("create-post-tag");
        tagContainer.style.backgroundColor = "#" + tag.hexColor;
        
        const label = document.createElement('label');
        label.textContent = tag.name;
        label.htmlFor = 'tag' + tag.id;

        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.id = 'tag' + tag.id;
        checkbox.value = tag.id;

        tagContainer.appendChild(checkbox);
        tagContainer.appendChild(label);

        optionsContainer.appendChild(tagContainer);
      })
    });
}

function searchPosts() {
  window.location.href = '/search/' + searchInput.value;
}

function getSelectedTags() {
  var array = [];
  var checkboxes = document.getElementById('optionsContainer').querySelectorAll('input[type=checkbox]:checked');

  for (var i = 0; i < checkboxes.length; i++) {
    array.push(checkboxes[i].value);
  }

  return array;
}

function clearSelectedTags() {
  var checkboxes = document.getElementById('optionsContainer').querySelectorAll('input[type=checkbox]:checked');

  for (var i = 0; i < checkboxes.length; i++) {
    checkboxes[i].checked = false;
  }
}

window.addEventListener("load", (event) => {
  feedLoadIcon = document.getElementById("loadIconContainer");
  postInput = document.getElementById("postInput");
  postInput.addEventListener("keypress", (event) => {
    if (event.key == 'Enter')
      createPost();
  });

  feedElement = document.getElementById("feed");
  searchInput = document.getElementById("searchInput");
  searchInput.addEventListener("keypress", (event) => {
    if (event.key == 'Enter') {
      event.preventDefault();
      searchPosts();
    }
  });

  feedElement.addEventListener("scroll", throttle(callback, 1000));

  const adaptPostInputHeightCallback = (event) => {
    postInput.style.height = "auto";
    postInput.style.height = postInput.scrollHeight + "px";
  };
  postInput.onchange = adaptPostInputHeightCallback;
  postInput.onkeyup = adaptPostInputHeightCallback;

  loadFeed();
  loadProfile();
  loadRecommendations();
  loadTags();
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
