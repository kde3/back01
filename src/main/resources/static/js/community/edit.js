function submitEdit(event) {
    event.preventDefault();

    var title = document.getElementById('title').value;
    var content = document.getElementById('content').value;
    var fileInput = document.getElementById('edit-file');
    var file = fileInput.files[0];

    if (title.trim() === '' || content.trim() === '') {
        alert('제목과 내용을 입력해주세요.');
        return;
    }

    if (!file) {
        alert('파일을 첨부해주세요.');
        return;
    }

    alert("수정이 완료되었습니다.");

    // 폼 제출
    document.getElementById('edit-form').submit();
}

