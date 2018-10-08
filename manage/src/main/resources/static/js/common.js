$(function () {

    var pageJumpFlag = false;

    function pageJump(linkstr) {
        if (pageJumpFlag == true) {
            setTimeout(function () {
                pageJumpFlag = false
            }, 2000);
            return false;
        }
        if (linkstr == null || linkstr == "null" || linkstr == "") {
            return false;
        } else {
            window.location.replace(linkstr);
        }
    }
})