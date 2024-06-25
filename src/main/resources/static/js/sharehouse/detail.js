const post = document.querySelector(".sharehouse_detail");
const postNo = post.getAttribute("data-postNo");

const likeIcon = document.querySelector(".like_icon");
let checkLike = Number(likeIcon.getAttribute('data-check_like'));

// 좋아요 아이콘 색상 변경(좋아요 한 상태 - 보라색 / 좋아요 안한 상태 - 회색)
function like() {
    likeIcon.textContent = "favorite";
    likeIcon.setAttribute("style", "color: #8976FD");
}

function unlike() {
    likeIcon.textContent = "favorite_border";
    likeIcon.setAttribute("style", "color: #CACACA");
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
likeIcon.addEventListener("click", function() {
    const isContain = this.classList.contains('yesAuth');
    if(isContain) {
        if(checkLike == 0) {
            fetch(`/sharehouse/${postNo}/like`, {
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
            fetch(`/sharehouse/${postNo}/unlike`, {
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