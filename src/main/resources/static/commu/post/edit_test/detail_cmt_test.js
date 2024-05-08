let postNo = 4;
let userNo = "uuidtest1";

const content = document.getElementById("comment-content");
const pagination = document.getElementById('pagination');
const addBtn = document.getElementById('submit');

const BTN_NUM = 5;                    // 페이지당 보여질 페이지네이션 버튼 수
const ITEM_NUM = 10;                  // 페이지당 항목 수

const items = [];                       // 페이지네이션될 항목 배열
let currentPage = 1;                  // 현재 페이지 번호


function displayItems() {
    const startIndex = (currentPage - 1) * ITEM_NUM;
    const endIndex = startIndex + ITEM_NUM;
    const displayedItems = items.slice(startIndex, endIndex);

    content.innerHTML = displayedItems.join('');

    //수정, 삭제 버튼에 이벤트리스너 등록
    //수정 -> 요청만 보냄
    //삭제 -> 요청보내고 다시 화면 뿌림(댓글 개수 세는 것 때문.)

    // 댓글 수정 이벤트 추가
    document.querySelectorAll(".card-update-btn").forEach((updateBtn) => {
        updateBtn.addEventListener("click", function() {
            let textarea = this.parentNode.parentNode.parentNode.querySelector("textarea");
            textarea.removeAttribute("disabled");
            textarea.focus();                           // textarea 활성화
            let len = textarea.textContent.length;
            textarea.setSelectionRange(len, len);       // 커서 맨 뒤

            // 수정 삭제 버튼 안보이게
            this.setAttribute("style", "display:none;");
            this.parentNode.querySelector(".card-delete-btn").setAttribute("style", "display:none;");

            //수정 완료 버튼 보이도록
            this.parentNode.querySelector(".card-complete_update-btn").setAttribute("style", "display:block;");
        });

    });

    // 수정 완료 버튼 이벤트 추가
    document.querySelectorAll(".card-complete_update-btn").forEach((completeUpdateBtn) => {
        completeUpdateBtn.addEventListener("click", function() {
            let textarea = this.parentNode.parentNode.parentNode.querySelector("textarea");
            textarea.setAttribute("disabled", true);

            // 수정, 삭제 버튼 보이기
            this.parentNode.querySelector(".card-update-btn").setAttribute("style", "display: inline-block;");
            this.parentNode.querySelector(".card-delete-btn").setAttribute("style", "display: inline-block;");

            // 수정 완료 버튼 숨기기
            this.setAttribute("style", "display:none;");

            //서버에 수정 내용 보내기
            const cmtNo = Number(completeUpdateBtn.parentNode.parentNode.querySelector(".edit-btn").getAttribute('data-no_cmt'));
            fetch(`/community/${postNo}?cmtNo=${cmtNo}`, {
                method: 'put',
                headers: {
                    'content-type': 'application/json'
                },
                body : JSON.stringify({
                    content : `${textarea.value}`
                })
            })
            .then(response => {
                if (response.ok) console.log("댓글 수정 완료");
            });
        });
    });

    // 댓글 삭제 버튼에 이벤트 등록
    const deleteBtns = document.querySelectorAll(".card-delete-btn");
    deleteBtns.forEach((btn)=> {
        btn.addEventListener("click", () => {
            console.log("눌렀다");
            const cmtNo = Number(btn.parentNode.parentNode.querySelector(".edit-btn").getAttribute('data-no_cmt'));   // (data-no_cmt 값 가져오기
            console.log(cmtNo);

            fetch(`/community/${postNo}?cmtNo=${cmtNo}`, {
                method: 'delete'
            })
            .then(response => {
                if (response.ok) console.log("댓글 삭제 완료");
                window.location.href = `/community/UpdateCmt/${postNo}`;        //화면 다시 뿌림
            });

        });
    });
}

function createPaginationButtons() {
    let nextBtn = null;
    let prevBtn = null;

    const numPages = Math.ceil(items.length / ITEM_NUM);
    let currentBtnGroup = Math.ceil(currentPage / BTN_NUM);
    let startGroupIdx = (currentBtnGroup - 1) * BTN_NUM + 1;
    let endGroupIdx = currentBtnGroup * BTN_NUM;

    if(endGroupIdx >= numPages) {
        endGroupIdx = numPages;
    }
    else {
        // 다음 버튼 생성(페이지 그룹이 하나밖에 없을 때, 마지막 페이지 그룹일 때 다음 버튼 X)
        nextBtn = document.createElement("button");
        nextBtn.className = "next-btn";
        nextBtn.textContent = "다음";

        // 다음 버튼에 이벤트 추가
        nextBtn.addEventListener("click", function() {
            currentPage = startGroupIdx + BTN_NUM;
            displayItems();
            createPaginationButtons();
            document.querySelectorAll('.page-btn')[0].setAttribute("style", "background-color: #8976FD; color:white;");
        });
    }

// 이전 버튼 생성(페이지그룹이 1이 아니라면 이전 버튼 필요)
    if(currentBtnGroup > 1) {
        prevBtn = document.createElement("button");
        prevBtn.textContent = "이전";
        prevBtn.className = "prev-btn";

        // 이전 버튼에 이벤트 추가
        prevBtn.addEventListener("click", function() {
            currentPage = startGroupIdx - BTN_NUM;
            displayItems();
            createPaginationButtons();
            document.querySelectorAll('.page-btn')[0].setAttribute("style", "background-color: #8976FD; color:white;");
        });
    }

    let buttonsHtml = '';
    for (let i = startGroupIdx; i <= endGroupIdx; i++) buttonsHtml += `<div class="page-btn">${i}</div>`;
    pagination.innerHTML = buttonsHtml;

    if(prevBtn) {
        pagination.insertBefore(prevBtn, pagination.firstChild);
    }
    if(nextBtn) {
        pagination.appendChild(nextBtn);
    }

// 버튼 활성화시키고 아이템 다시 뿌려야.
    let pageBtns = document.querySelectorAll('.page-btn');
    pageBtns.forEach((btn) => {
        btn.addEventListener("click", function() {
            currentPage = btn.textContent;
            displayItems();

            // 나머지 버튼 비활성화
            pageBtns.forEach((btn) => btn.setAttribute("style", "background-color: white; color: #332C5C;") );

            // 버튼 활성화(색상변경)
            btn.setAttribute("style", "background-color: #8976FD; color:white;");

        });
    });
}

// 처음에 게시글 상세 페이지로 이동됐을 때 댓글이 보여져야 함...(나중에 수정해야)
fetch(`/community/${postNo}/cmt`, {
    method: 'get',
    headers: {
        'content-type': 'application/json'
    }
})
.then(response => response.json())
.then(data => {
    data.forEach((cmt, idx) => {
        let post = `
                <div class="card" data-cmtNo=${cmt.cmtNo}>
                    <div class="card-body">
                        <span class="card-username">${cmt.userNo}</span>
                        <textarea disabled>${cmt.content}</textarea>

                    <div class="card-edit-box">
                        <span class="card-date">${cmt.updatedAt}</span>
                        <div class="edit-btn" data-no_cmt="${cmt.cmtNo}">
                            <button class="card-update-btn">수정하기</button>
                            <button class="card-delete-btn">삭제하기</button>
                            <button class="card-complete_update-btn">수정완료</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

    items.push(post);
    });

    displayItems();
    createPaginationButtons();
    document.querySelectorAll('.page-btn')[0].setAttribute("style", "background-color: #8976FD; color:white;");
})



//------------------------ main ---------------------------------

//일단 만들어 둔 페이지네이션 코드 그대로 사용
// 요청 보내서 DB에 저장하고 서버에서 댓글 목록 반환 -> 프론트에서 댓글 뿌림.
//( DB에 저장 -> view에 바로 뿌리기? )
addBtn.addEventListener("click", ()=> {
    const commentInput = document.getElementById('input_cmt');
    // commentInput.value = `${itemNum}`;           //(테스트용.)

    if(commentInput.value) {
        fetch(`/community/${postNo}/cmt`, {
            method: 'post',
            headers: {
                'content-type': 'application/json'
            },
            body : JSON.stringify({
                userNo : `${userNo}`,
                content : `${commentInput.value}`
            })
        })
        .then(response => response.json())
        .then(data => {
            items.splice(0);        //배열 비우기(배열을 비우고 새로 채워야 함.)

            //댓글 생성
            data.forEach((cmt, idx) => {
                let post = `
                    <div class="card" data-cmtNo=${cmt.cmtNo}>
                        <div class="card-body">
                            <span class="card-username">${cmt.userNo}</span>
                            <textarea disabled>${cmt.content}</textarea>

                            <div class="card-edit-box">
                                <span class="card-date">${cmt.updatedAt}</span>
                                <div class="edit-btn" data-no_cmt="${cmt.cmtNo}">
                                    <button class="card-update-btn">수정하기</button>
                                    <button class="card-delete-btn">삭제하기</button>
                                    <button class="card-complete_update-btn">수정완료</button>
                                </div>
                            </div>
                        </div>
                    </div>
                `;

                items.push(post);
            });

            displayItems();
            createPaginationButtons();
            document.querySelectorAll('.page-btn')[0].setAttribute("style", "background-color: #8976FD; color:white;");

            commentInput.value = "";
        });
    }
    else {
        alert("댓글을 입력해주세요");
    }
});