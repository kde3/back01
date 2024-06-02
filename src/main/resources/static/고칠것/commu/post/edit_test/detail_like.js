const likeCnt = document.querySelector(".like_cnt");
let thumbUpIcon = document.querySelector(".thumb_up_icon");
let checkLike = Number(thumbUpIcon.getAttribute('data-check_like'));
// console.log("좋아요 체크" + thumbUpIcon.getAttribute('data-check_like'));


// 좋아요 아이콘 색상 변경(좋아요 한 상태 - 보라색 / 좋아요 안한 상태 - 회색)
if(checkLike == 0) {
  thumbUpIcon.textContent = "thumb_up_off_alt";
  thumbUpIcon.setAttribute("style", "color: #CACACA");
}
else if(checkLike == 1) {
  thumbUpIcon.textContent = "thumb_up_alt";
  thumbUpIcon.setAttribute("style", "color: #8976FD");
}
else {
  console.log("잘못된 checkLike");
}


thumbUpIcon.addEventListener("click", () => {
  let postNo = 4;

  if(checkLike == 0) {
    fetch(`/community/${postNo}/like`, {
      method: 'post',
      headers: {
        // 'content-type': 'application/json'
      }
    })
    .then(response => {
      if (response.ok) {
        window.location.href = `/community/${4}`;
      }
    });
  }
  else if(checkLike == 1) {
    fetch(`/community/${postNo}/like`, {
      method: 'delete',
      headers: {
        // 'content-type': 'application/json'
      }
    })
    .then(response => {
      if (response.ok) {
        window.location.href = `/community/${4}`;
      }
    });
  }
  else {
    console.log("잘못된 checkLike");
  }
});
