class FormDataToObj {
    static async getParameter(formId) {
        const form = document.getElementById(formId);
        const formData = new FormData(form);
        return Array.from(formData.entries()).reduce((perv, [key, value]) => {
            if (perv[key]) {
                Array.isArray(perv[key]) ? perv[key].push(value) : (perv[key] = [perv[key], value]);
            } else {
                perv[key] = value;
            }
            return perv;
        }, {});
    }
}

class Http {

    static handleError(e) {
        if (e.status === 401) {
            Sweet.alert("다시 로그인 해주세요.").then(() => {
                window.location.href = '/';
            })
        } else {
            console.error('AJAX Error: ', e);
        }
    }

    static get(url, params = '', method = 'GET') {
        return $.ajax({
            type: method,
            url: url,
            data: params,
            beforeSend: function(xhr) {
                const {token, header} = Http.getCookieInfo();
                xhr.setRequestHeader(header, token)
            }
        }).fail(e => Http.handleError(e))
    }

    static syncGet(url, params='', async = false, method = 'GET'){
        return $.ajax({
            type: method,
            url: url,
            data: params,
            async: false,
            beforeSend: function(xhr) {
                const {token, header} = Http.getCookieInfo();
                xhr.setRequestHeader(header, token)
            }
        }).fail(e => Http.handleError(e))
    }

    static post(url, data, method = 'POST') {
        return $.ajax({
            type: method,
            url: url,
            data: JSON.stringify(data),
            /*dataType: 'json',*/
            contentType: 'application/json',
            beforeSend: function(xhr) {
                const {token, header} = Http.getCookieInfo();
                xhr.setRequestHeader(header, token)
            }
        }).fail(e => Http.handleError(e))
    }

    static delete(url, data, method = 'DELETE') {
        return $.ajax({
            type: method,
            url: url,
            data: data,
            beforeSend: function(xhr) {
                const {token, header} = Http.getCookieInfo();
                xhr.setRequestHeader(header, token)
            }
        }).fail(e => Http.handleError(e))
    }

    static put(url, data, method='PUT') {
        return $.ajax({
            type: method,
            url: url,
            data: JSON.stringify(data),
            /*dataType: 'json',*/
            contentType: 'application/json',
            beforeSend: function(xhr) {
                const {token, header} = Http.getCookieInfo();
                xhr.setRequestHeader(header, token)
            }
        }).fail(e => Http.handleError(e))
    }

    static filePost(url, formData, method = 'POST'){
        return $.ajax({
            type: method,
            url: url,
            data: formData,
            contentType: false,
            processData: false,
            beforeSend: function(xhr) {
                const {token, header} = Http.getCookieInfo();
                xhr.setRequestHeader(header, token)
            }
        });
    }

    static fileDownload(id){
        let filename = ''
        return $.ajax({
            url: `/api/downloadFile/${id}`,
            method: 'GET',
            xhrFields: {
                responseType: 'blob'
            },
            beforeSend: function(xhr) {
                const {token, header} = Http.getCookieInfo();
                xhr.setRequestHeader(header, token)
            },
            success: function (blobData, status, xhr) {
                const contentDisposition = xhr.getResponseHeader('Content-Disposition');
                const matches = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/.exec(contentDisposition);
                filename = matches && matches[1] ? matches[1].replace(/['"]/g, '').replace('UTF-8', '') : "";

                if (filename) {
                    const link = $('<a style="display: none;"></a>');
                    link.attr('href', window.URL.createObjectURL(blobData));
                    link.attr('download', decodeURIComponent(filename));
                    $('body').append(link);
                    link[0].click();
                    link.remove();
                }
            }
        });
    }

    static getCookieInfo(){
        const token = $("meta[name='_csrf']").attr("content")
        const header = $("meta[name='_csrf_header']").attr("content");
        return { header : header, token : token }
    }

}

class StringUtil {

    /***
     * (입력) YYYY-MM-DD HH:MM:SS
     * (출력) YYYY-MM-DD HH:MM
     * */
    static dateWithHHMM(dateWithHHMMSS) {
        return dateWithHHMMSS.substring(0, 16);
    }

}