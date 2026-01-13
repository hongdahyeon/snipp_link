var indexJS = {

    shortAlertMessageMap: {
        "ACCESS_NO_PERMISSION": "접근 권한이 없습니다.",
        "ACCESS_NOT_FOUND": "해당되는 URL이 없습니다.",
        "ACCESS_TIME_EXPIRED": "유효 기간이 만료되었습니다.",
        "ACCESS_ERROR": "알 수 없는 오류가 발생했습니다."
    },

    copyShortLink: function () {
        const textToCopy = $("#shortUrlText").text();
        if(!textToCopy) {
            Sweet.alert('복사할 주속값이 없습니다!')
            return;
        }
        Util.copyToClipboard(textToCopy);
    },

    makeShortLink: function () {
        const longLinkURL = $("#long-link").val();
        if (!longLinkURL) {
            Sweet.alert('링크를 입력해주세요.');
            return;
        }
        const urlPattern = /^(https?:\/\/)?[a-z0-9-]+(\.[a-z0-9-]+)+([/?].*)?$/i;
        if (!urlPattern.test(longLinkURL)) {
            Sweet.alert('올바른 URL 형식이 아닙니다.');
            return;
        }
        const obj = {
            originUrl: longLinkURL,
            isPublic: 'Y'
        }
        Http.post(`/snipp/api/short-url/create`, obj).then((res) => {
            $("#shortUrlText").text(res);
        }).fail((e) => {
            console.error(e)
            console.error(e.responseText)
        })
    }
}