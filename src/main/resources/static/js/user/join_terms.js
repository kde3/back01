const $allAgree = $("#all-agree");
const $terms = $(".term1, .term2, .term3");
const $nextBtn = $("#next_btn");

const btnColorAble = "#8976FD";
const btnColorDisable = "#FFF";

$allAgree.on("click", function() {
    if($(this).prop("checked")) {
        $terms.prop("checked", true);
        $nextBtn.prop("disabled", false);
        $nextBtn.css("background-color", btnColorAble);
    }
    else {
        $terms.prop("checked", false);
        $nextBtn.prop("disabled", true);
        $nextBtn.css("background-color", btnColorDisable);
    }

});

$terms.on("click", function() {
    if($terms.length === $(".term1:checked, .term2:checked, .term3:checked").length) {
        $allAgree.prop("checked", true);
        $nextBtn.prop("disabled", false);
        $nextBtn.css("background-color", btnColorAble);
    }
    else {
        $allAgree.prop("checked", false);
        $nextBtn.prop("disabled", true);
        $nextBtn.css("background-color", btnColorDisable);
    }
});
