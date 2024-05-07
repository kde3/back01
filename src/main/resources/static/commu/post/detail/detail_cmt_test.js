import Pagination from "/commu/post/detail/Pagination.js";
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

            // console.log(data);
            // console.log(data[0]);
            // console.log(data[0].content);

            //댓글 생성
            data.forEach((cmt, idx) => {
                // console.log(cmt);

                let post = `
                    <div class="card" data-cmtNo=${cmt.cmtNo}>
                        <div class="card-body">
                            <span class="card-username">${cmt.userNo}</span>
                            <textarea disabled>${cmt.content}</textarea>

                            <div class="card-edit-box">
                                <span class="card-date">${cmt.updatedAt}</span>
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