function showChangeName(){
    document.getElementById("change-name-page").style.display = "block"
    document.getElementById("main-options").style.display = "none"
}

function showChangePassword(){
    document.getElementById("change-pass-page").style.display = "block"
    document.getElementById("main-options").style.display = "none"
}

function showpass(){
    var oldpass = document.getElementById("oldpass")
    var newpass = document.getElementById("newpass")

    if (oldpass.type === "password" && newpass.type === "password") {
        oldpass.type = "text";
        newpass.type = "text";
      } else {
        oldpass.type = "password";
        newpass.type = "password";
      }
}

function logout() {
    window.location.href = "/logout";
}