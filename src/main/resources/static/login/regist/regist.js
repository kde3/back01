//아이디입력요소
document.addEventListener("DOMContentLoaded", function() {
  let elInputUsername = document.querySelector('#id'); 
  let elSuccessMessage = document.querySelector('.success-message'); 
  let elFailureMessage = document.querySelector('.failure-message'); 
  let elFailureMessageTwo = document.querySelector('.failure-message2'); 

  //아이디 길이확인
  function idLength(value) {
      return value.length >= 6 && value.length <= 20
    }

    //비밀번호영어만 있는 지 확인
  function onlyNumberAndEnglish(str) {
      return /^[A-Za-z0-9][A-Za-z0-9]*$/.test(str);
    }

    //아이디 입력값에 따라 실행되는 메시지 
  elInputUsername.onkeyup = function () {
      if (elInputUsername.value.length !== 0) {
        if(onlyNumberAndEnglish(elInputUsername.value) === false) {
          elSuccessMessage.classList.add('hide');
          elFailureMessage.classList.add('hide');
          elFailureMessageTwo.classList.remove('hide'); 
        }
        else if(idLength(elInputUsername.value) === false) {
          elSuccessMessage.classList.add('hide'); 
          elFailureMessage.classList.remove('hide'); 
          elFailureMessageTwo.classList.add('hide'); 
        }
        else if(idLength(elInputUsername.value) || onlyNumberAndEnglish(elInputUsername.value)) {
          elSuccessMessage.classList.remove('hide'); 
          elFailureMessage.classList.add('hide'); 
          elFailureMessageTwo.classList.add('hide'); 
        }
      }
      else {
        elSuccessMessage.classList.add('hide');
        elFailureMessage.classList.add('hide');
        elFailureMessageTwo.classList.add('hide');
      }
    }

    //비밀번호 입력요소
  let elInputPassword = document.querySelector('#password'); 
  let elInputPasswordRetype = document.querySelector('#password-retype');
  let elMismatchMessage = document.querySelector('.mismatch-message'); 
  let elStrongPasswordMessage = document.querySelector('.strongPassword-message');

    //강력비밀번호확인
  function strongPassword (str) {
      return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(str);
    }

    //비밀번호 일치 여부확인
  function isMatch (password1, password2) {
      return password1 === password2;
    }

    //비밀번호 입력값에 따라 실행되는 메시지
  elInputPassword.onkeyup = function () {
      if (elInputPassword.value.length !== 0) {
        if(strongPassword(elInputPassword.value)) {
          elStrongPasswordMessage.classList.add('hide'); 
        }
        else {
          elStrongPasswordMessage.classList.remove('hide'); 
        }
      }
      else {
        elStrongPasswordMessage.classList.add('hide');
      }
    };

    //비밀번호 확인 입력값이 변경될때마다 실행되는 메시지
  elInputPasswordRetype.onkeyup = function () {
      if (elInputPasswordRetype.value.length !== 0) {
        if(isMatch(elInputPassword.value, elInputPasswordRetype.value)) {
          elMismatchMessage.classList.add('hide'); 
        }
        else {
          elMismatchMessage.classList.remove('hide'); 
        }
      }
      else {
        elMismatchMessage.classList.add('hide'); 
      }
    };



    const signupButton = document.querySelector("#signup--button");
    signupButton.addEventListener("click", ()=>{
        const id = document.querySelector("#id");
        const password = document.querySelector("#password");
        const name = document.querySelector("#name");
        const phoneNumber = document.querySelector("#phone-number");
        const email = document.querySelector("#email");

        fetch("/user/join", {
            method: 'post',
            headers: {
                'content-type': 'application/json'
            },
            body : JSON.stringify({
                id : `${id.value}`,
                password : `${password.value}`,
                name : `${name.value}`,
                password : `${password.value}`,
                phoneNumber : `${phoneNumber.value}`,
                email : `${email.value}`
            })
        })
        .then(response => {
            if (response.ok) {
                alert("회원가입이 완료되었습니다.");
                window.location.href = "/community/retrieve";
            } else {
                alert("아이디가 중복됩니다.");
            }
        });
    });
});
