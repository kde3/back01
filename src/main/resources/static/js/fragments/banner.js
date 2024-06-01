import MainBanner from "./MainBanner.js";

const banner = new MainBanner(
    $(".banner--box"),
    $(".banner--frame"),
    $(".banner ul"),
    $(".banner li"),
    $(".banner .arrow-box-left"),
    $(".banner .arrow-box-right")
);

banner.autoSlide();