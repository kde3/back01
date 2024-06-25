document.addEventListener('DOMContentLoaded', function () {
    const createForm = document.getElementById('createFrm');
    const titleInput = document.getElementById('create-textarea-title');
    const contentInput = document.getElementById('create-textarea-content');
    const submitButton = document.getElementById('create-submit-button');

    // 등록하기 버튼 클릭 시 동작
    submitButton.addEventListener('click', function () {

        if (titleInput.value.trim() === '' || contentInput.value.trim() === '') {
            alert('제목과 내용을 입력해주세요.');
            return;
        }

        alert("작성이 완료되었습니다.")

        // 폼 제출
        document.getElementById('createFrm').submit();
        // window.location.href = `/`;
    });
});

// function submitForm() {
//     const formData = new FormData(document.getElementById('createFrm'));
//
//     fetch('/community', {
//         method: 'POST',
//         body: JSON.stringify(Object.fromEntries(formData)),
//         headers: {
//             'Content-Type': 'application/json'
//         }
//     })
//         .then(response => response.json())
//         .then(postNo => {
//             window.location.href = `/community/${postDetailDTO.post}`;
//         })
//         .catch(error => {
//             console.error('Error:', error);
//         });
// }

// document.addEventListener('DOMContentLoaded', function () {
//     const createForm = document.getElementById('createFrm');
//     const titleInput = document.getElementById('create-textarea-title');
//     const contentInput = document.getElementById('create-textarea-content');
//     const submitButton = document.getElementById('create-submit-button');
//
//     submitButton.addEventListener('click', function (event) {
//         event.preventDefault(); // 기본 폼 제출 동작 방지
//
//         if (titleInput.value.trim() === '' || contentInput.value.trim() === '') {
//             alert('제목과 내용을 입력해주세요.');
//             return;
//         }
//
//         const formData = new FormData(createForm);
//
//         fetch('/community/create', {
//             method: 'POST',
//             body: formData
//         })
//             .then(response => {
//                 console.log(response);
//                 return response.json();
//             })
//             .then(post => {
//                 console.log(post);
//                 if (post.postNo) {
//                     window.location.href = `/community/${postDetailDTO.postNo}`;
//                 } else {
//                     alert('게시물 생성에 실패했습니다.');
//                 }
//             })
//             .catch(error => {
//                 console.error('Error:', error);
//                 alert('서버 오류가 발생했습니다.');
//             });
//     });
// });
//
// function submitEdit(event) {
//     event.preventDefault();
//
//     var title = document.getElementById('create-textarea-title').value;
//     var content = document.getElementById('create-textarea-content').value;
//     var fileInput = document.getElementById('create-file');
//     var submitButton = document.getElementById('create-submit-button')
//     var file = fileInput.files[0];
//
//     if (title.trim() === '' || content.trim() === '') {
//         alert('제목과 내용을 입력해주세요.');
//         return;
//     }
//
//     // alert("작성이 완료되었습니다.");
//
//     // 폼 제출
//     document.getElementById('createFrm').submit();
// }

// document.addEventListener('DOMContentLoaded', function () {
//     const createForm = document.getElementById('createFrm');
//     const titleInput = document.getElementById('create-textarea-title');
//     const contentInput = document.getElementById('create-textarea-content');
//     const submitButton = document.getElementById('create-submit-button');
//
//     submitButton.addEventListener('click', function (event) {
//         event.preventDefault(); // Prevent default form submission
//
//         if (titleInput.value.trim() === '' || contentInput.value.trim() === '') {
//             alert('제목과 내용을 입력해주세요.');
//             return;
//         }
//
//         const formData = new FormData(createForm);
//
//         fetch('/community/create', {
//             method: 'POST',
//             body: formData
//         })
//             .then(response => response.json())
//             .then(post => {
//                 if (post.postNo) {
//                     window.location.href = `/community/${post.postNo}`;
//                 } else {
//                     alert('게시물 생성에 실패했습니다.');
//                 }
//             })
//             .catch(error => {
//                 console.error('Error:', error);
//                 alert('서버 오류가 발생했습니다.');
//             });
//     });
// });

// let index = {
//     init: function(){
//         $("#create-submit-button").on("click",()=>{
//             this.save();
//         });
//     },
//
//     save: function(){
//         let formData = new FormData();
//         formData.append("title", $("#create-textarea-title").val());
//         formData.append("content", $("#create-textarea-content").val());
//
//         let fileInput = document.querySelector('input[type="file"]');
//         if (fileInput.files.length > 0) {
//             formData.append("files", fileInput.files[0]);
//         }
//
//         $.ajax({
//             type: "POST",
//             url:"/community/create",
//             data: formData,
//             processData: false,
//             contentType: false,
//         }).done(function(data){
//             alert("글쓰기가 완료되었습니다.");
//             console.log(data);
//             location.href= "/";
//         }).fail(function(error){
//             alert(JSON.stringify(error));
//         });
//     },
// }
// index.init();

// function savePost() {
//     const form = document.getElementById('crateFrm');
//     const fields = [form.title, form.content];
//     const fieldNames = ['title', 'content'];
//
//     for (let i = 0, len = fields.length; i < len; i++) {
//         isValid(fields[i], fieldNames[i]);
//     }
//
//     document.getElementById('create-submit-button').disabled = true;
//     form.noticeYn.value = form.isNotice.checked;
//     form.action = [[ ${post == null} ]] ? '/community/create' : '/community/edit';
//     form.submit();
// }
