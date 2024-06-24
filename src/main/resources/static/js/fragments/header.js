
// 네비게이션 색상 표시
const currentUrl = location.pathname;
const category = currentUrl.split('/', 2)[1];   // url에서 카테고리 얻기

// console.log("currentUrl : " + currentUrl);
// console.log(currentUrl.split('/', 2)[1]);

if(category == "community") {
    document.querySelector(".header--left-community").classList.add('active');
}
else if(category == "barter") {
    document.querySelector(".header--left-barter").classList.add('active');
}
else if(category == "sharehouse") {
    document.querySelector(".header--left-sharehouse").classList.add('active');
}
