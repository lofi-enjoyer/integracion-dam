function uploadProfileImage() {
    var file = document.getElementById("profileImgInput").files[0];
    console.log(file);
    var form = new FormData();
    form.append("file", file);
    console.log(form);
    fetch('/api/media/uploadprofile', {
        method: "POST", 
        body: form
    });
}