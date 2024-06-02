// document.querySelector(".signup--button").onclick = function() {
//     // 회원가입 페이지로 이동
//     window.location.href = "../terms/terms.html";
// };
//
// document.querySelector(".find-pw--button").onclick = function() {
//     // 비밀번호 찾기 페이지로 이동
//     window.location.href = "../find_pw/find_pw.html";
// };

const id = document.querySelector('#id');
const password = document.querySelector('#password');

const loginButton = document.querySelector(".login--button");
loginButton.addEventListener("click", ()=> {
    console.log("클릭됨");

    const idResult = document.querySelector('#idResult');
    const pwResult = document.querySelector('#pwResult');

    if(id.value == "" || password.value == "") {
        idResult.innerText = "아이디를 입력하세요";
        pwResult.innerText = "비밀번호를 입력하세요";
        idResult.style.color = "#8976FD";
        pwResult.style.color = "#8976FD";
        console.error("아이디 또는 비밀번호가 입력되지 않았습니다.");
    }
    else {
        fetch("/user/login", {
            method: 'post',
            headers: {
                'content-type': 'application/json'
            },
            body : JSON.stringify({
                id : `${id.value}`,
                password : `${password.value}`
            })
        })
        .then(response => {
            if (response.ok) {
                console.log("로그인 성공");
                window.location.href = "/community/";

            } else if (response.status === 401) {
                const checkIdAndPw = document.querySelector("#checkIdAndPw");
                checkIdAndPw.textContent = "아이디(로그인 전용 아이디) 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.";
            }
        });
    }
});
