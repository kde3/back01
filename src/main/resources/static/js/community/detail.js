
const post = document.querySelector(".detail-container");
const postNo = post.getAttribute("data-postNo");

const thumbUpIcon = document.querySelector(".thumb_up_icon");
let checkLike = Number(thumbUpIcon.getAttribute('data-check_like'));
// console.log("좋아요 체크" + thumbUpIcon.getAttribute('data-check_like'));


// 좋아요 아이콘 색상 변경(좋아요 한 상태 - 보라색 / 좋아요 안한 상태 - 회색)
function like() {
    thumbUpIcon.textContent = "thumb_up_alt";
    thumbUpIcon.setAttribute("style", "color: #8976FD");
}

function unlike() {
    thumbUpIcon.textContent = "thumb_up_off_alt";
    thumbUpIcon.setAttribute("style", "color: #CACACA");
}


if(checkLike == 0) {
    unlike()
}
else if(checkLike == 1) {
    like();
}
else {
    console.log("잘못된 checkLike");
}

// 좋아요 하기
thumbUpIcon.addEventListener("click", function() {
    const isContain = this.classList.contains('yesAuth');
    if(isContain) {
        if(checkLike == 0) {
            fetch(`/community/${postNo}/like`, {
                method: 'post'
            })
            .then((response)=>{
                like();
                checkLike = 1;
                const likeCnts = document.querySelectorAll(".like_cnt");
                likeCnts.forEach(likeCnt => {
                    likeCnt.textContent = Number(likeCnt.textContent) + 1;
                });
            })

        }
        else if(checkLike == 1) {
            fetch(`/community/${postNo}/unlike`, {
                method: 'post'
            })
            .then((response) => {
                unlike();
                checkLike = 0;
                const likeCnts = document.querySelectorAll(".like_cnt");
                likeCnts.forEach(likeCnt => {
                    likeCnt.textContent = Number(likeCnt.textContent) - 1;
                });
            })
        }
        else {
            console.log("잘못된 checkLike");
        }
    }
    else {
        location.href = "/user/login";
    }
});

//------------------------------------------------------- 댓글 이벤트

let textareaContent;

// 댓글 수정 이벤트 추가
document.querySelectorAll(".card-update-btn")
    .forEach((updateBtn) => {

    updateBtn.addEventListener("click", function() {
        let textarea = this.parentNode.parentNode.parentNode.querySelector("textarea");
        textareaContent = textarea.value;
        console.log(textareaContent);

        textarea.removeAttribute("readonly");
        textarea.setAttribute("style", "outline:solid;");
        textarea.focus();                           // textarea 활성화
        let len = textarea.textContent.length;
        textarea.setSelectionRange(len, len);       // 커서 맨 뒤

        // 수정, 삭제 버튼 안보이게
        this.setAttribute("style", "display:none;");
        this.parentNode.querySelector(".card-delete-btn").setAttribute("style", "display:none;");

        //수정 완료 버튼 보이도록
        this.parentNode.querySelector(".card-complete_update-btn").setAttribute("style", "display:block;");
        this.parentNode.querySelector(".card-cancel_update-btn").setAttribute("style", "display:block;");
    });

});


// 수정 완료 버튼 이벤트 추가
document.querySelectorAll(".card-complete_update-btn")
    .forEach((completeUpdateBtn) => {

    completeUpdateBtn.addEventListener("click", function() {
        let textarea = this.parentNode.parentNode.parentNode.querySelector("textarea");
        textarea.setAttribute("readonly", true);
        textarea.setAttribute("style", "outline:none;");

        // 수정, 삭제 버튼 보이기
        this.parentNode.querySelector(".card-update-btn").setAttribute("style", "display: inline-block;");
        this.parentNode.querySelector(".card-delete-btn").setAttribute("style", "display: inline-block;");

        // 수정완료, 수정취소 버튼 숨기기
        this.setAttribute("style", "display:none;");
        this.parentNode.querySelector(".card-cancel_update-btn").setAttribute("style", "display:none;");
    });

});

// 수정 취소 버튼 이벤트 추가
document.querySelectorAll(".card-cancel_update-btn")
    .forEach((completeUpdateBtn) => {

        completeUpdateBtn.addEventListener("click", function() {
            let textarea = this.parentNode.parentNode.parentNode.querySelector("textarea");
            textarea.value = textareaContent;     //원래 있던 텍스트 복구 시킴.
            textarea.setAttribute("readonly", true);
            textarea.setAttribute("style", "outline:none;");


            // 수정, 삭제 버튼 보이기
            this.parentNode.querySelector(".card-update-btn").setAttribute("style", "display: inline-block;");
            this.parentNode.querySelector(".card-delete-btn").setAttribute("style", "display: inline-block;");

            // 수정완료, 수정취소 버튼 숨기기
            this.setAttribute("style", "display:none;");
            this.parentNode.querySelector(".card-complete_update-btn").setAttribute("style", "display:none;");
        });

    });