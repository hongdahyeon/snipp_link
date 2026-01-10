var indexJS = {

    shortAlertMessageMap: {
        "ACCESS_NO_PERMISSION": "접근 권한이 없습니다.",
        "ACCESS_NOT_FOUND": "해당되는 URL이 없습니다.",
        "ACCESS_TIME_EXPIRED": "유효 기간이 만료되었습니다.",
        "ACCESS_ERROR": "알 수 없는 오류가 발생했습니다."
    },

    copyShortLink: function () {
        const textToCopy = $("#shortUrlText").text();
        Util.copyToClipboard(textToCopy);
    },

    makeShortLink: function () {
        console.log("make short link")
    }
}