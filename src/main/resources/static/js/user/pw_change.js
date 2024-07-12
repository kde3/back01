let flag = false;
const pwdbutton =document.querySelector("#pwdbutton")
const check = document.querySelector(".missmatch-message")
const newPw = document.querySelector("#newPw")
const checkPw = document.querySelector("#checkPw")


function displayMassage(){
    const newPw = document.querySelector("#newPw").value
    const checkPw = document.querySelector("#checkPw").value
    if(checkPw == newPw){
        flag = true;
        check.style.display = 'none';
    } else {

        flag = false;
        check.style.display = 'block';
    }
}

checkPw.addEventListener("input", (e)=>{
    displayMassage();
})

newPw.addEventListener("input", (e)=>{
    displayMassage();
})

pwdbutton.addEventListener("click", ()=> {

    const currentPw = document.querySelector("#currentPw").value
    const newPw = document.querySelector("#newPw").value

    if(flag){
        fetch("/user/pw_change", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                password : currentPw,
                newPassword : newPw
            })
        }).then(response => {
            if (response.ok) {
                alert("비밀번호가 변경되었습니다.");
                location.href="/user/info";
            }
            else {
                alert("비밀번호가 틀렸습니다.");
                location.href="/user/pw_change";
            }
        })
    }else {
        alert("새 비밀번호가 일치하지 않습니다.");

    }

});
