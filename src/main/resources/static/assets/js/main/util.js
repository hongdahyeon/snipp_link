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

    // {{ JWT 사용 추가 : 주석
    // static handleError(e) {
    //     if (e.status === 401) {
    //         Sweet.alert("다시 로그인 해주세요.").then(() => {
    //             window.location.href = '/';
    //         })
    //     } else {
    //         console.error('AJAX Error: ', e);
    //     }
    // }
    // }}

    // {{ JWT 사용 추가
    static handleError(originalRequest, e) {
        if (e.status === 401) {
            // 중복 로그인 혹은 단순 만료인지 확인하기 위해 재발급 시도
            console.warn("인증 만료 혹은 중복 로그인 감지. 재발급 시도...");
            return $.ajax({
                url: '/api/auth/refresh',
                method: 'POST',
                // 재발급 요청 시에는 헤더에 만료된 토큰을 담지 않거나 별도 처리
            }).done((data, textStatus, xhr) => {
                // 1. 새 토큰 저장 (헤더에서 꺼냄)
                const newAccessToken = xhr.getResponseHeader("Authorization");
                if (newAccessToken) {
                    const token = newAccessToken.replace("Bearer ", "");
                    localStorage.setItem("accessToken", token);

                    // 2. [핵심] 실패했던 원래 요청을 다시 실행 (Retry)
                    // 원래 요청의 Authorization 헤더만 교체해서 다시 보냅니다.
                    originalRequest.headers = originalRequest.headers || {};
                    originalRequest.headers["Authorization"] = newAccessToken;
                    $.ajax(originalRequest);

                    // 페이지 새로고침 없이 데이터만 슥 업데이트됩니다.
                }
            }).fail(() => {
                // 재발급도 실패했다면? -> 이건 진짜 중복 로그인이거나 완전 만료
                Sweet.alert("다른 곳에서 로그인했거나 인증이 만료되었습니다.")
                    .then(() => window.location.href = '/');
            });
        }
    }
    // }}

    // 모든 메서드에서 공통으로 호출할 헤더 설정 함수 (중복 제거)
    static setAuthHeaders(xhr) {
        // 1. CSRF 정보 (기존 유지)
        const {token, header} = Http.getCookieInfo();
        if (header && token) {
            xhr.setRequestHeader(header, token);
        }
        // {{ JWT 사용 추가
        // 2. JWT 토큰 추가 [핵심 추가]
        const jwt = localStorage.getItem("accessToken");
        if (jwt) {
            xhr.setRequestHeader("Authorization", "Bearer " + jwt);
        }
        // }}
    }

    static get(url, params = '', method = 'GET') {
        // {{ JWT 사용 추가 : 주석
        // return $.ajax({
        //     type: method,
        //     url: url,
        //     data: params,
        //     beforeSend: function(xhr) {
        //         Http.setAuthHeaders(xhr); // 수정한 함수 호출
        //     }
        // }).fail(e => Http.handleError(e))
        // }}

        // {{ JWT 사용 추가
        const settings = {
            type: method,
            url: url,
            data: params,
            beforeSend: function(xhr) { Http.setAuthHeaders(xhr); }
        };
        return $.ajax(settings).fail(e => Http.handleError(settings, e));
        // }}
    }

    static syncGet(url, params='', async = false, method = 'GET'){
        // {{ JWT 사용 추가 : 주석
        // return $.ajax({
        //     type: method,
        //     url: url,
        //     data: params,
        //     async: false,
        //     beforeSend: function(xhr) {
        //         Http.setAuthHeaders(xhr); // 수정한 함수 호출
        //     }
        // }).fail(e => Http.handleError(e))
        // }}

        // {{ JWT 사용 추가
        const settings = {
            type: method,
            url: url,
            data: params,
            async: false,
            beforeSend: function(xhr) { Http.setAuthHeaders(xhr); }
        };
        return $.ajax(settings).fail(e => Http.handleError(settings, e));
        // }}
    }

    static post(url, data, method = 'POST') {
        // {{ JWT 사용 추가 : 주석
        // return $.ajax({
        //     type: method,
        //     url: url,
        //     data: JSON.stringify(data),
        //     /*dataType: 'json',*/
        //     contentType: 'application/json',
        //     beforeSend: function(xhr) {
        //         Http.setAuthHeaders(xhr); // 수정한 함수 호출
        //     }
        // }).fail(e => Http.handleError(e))
        // }}

        // {{ JWT 사용 추가
        const settings = {
            type: method,
            url: url,
            data: JSON.stringify(data),
            /*dataType: 'json',*/
            contentType: 'application/json',
            beforeSend: function(xhr) { Http.setAuthHeaders(xhr); }
        };
        return $.ajax(settings).fail(e => Http.handleError(settings, e));
        // }}
    }

    static delete(url, data, method = 'DELETE') {
        // {{ JWT 사용 추가 : 주석
        // return $.ajax({
        //     type: method,
        //     url: url,
        //     data: data,
        //     beforeSend: function(xhr) {
        //         Http.setAuthHeaders(xhr); // 수정한 함수 호출
        //     }
        // }).fail(e => Http.handleError(e))
        // }}

        // {{ JWT 사용 추가
        const settings = {
            type: method,
            url: url,
            data: data,
            beforeSend: function(xhr) { Http.setAuthHeaders(xhr); }
        };
        return $.ajax(settings).fail(e => Http.handleError(settings, e));
        // }}
    }

    static put(url, data, method='PUT') {
        // {{ JWT 사용 추가 : 주석
        // return $.ajax({
        //     type: method,
        //     url: url,
        //     data: JSON.stringify(data),
        //     /*dataType: 'json',*/
        //     contentType: 'application/json',
        //     beforeSend: function(xhr) {
        //         Http.setAuthHeaders(xhr); // 수정한 함수 호출
        //     }
        // }).fail(e => Http.handleError(e))
        // }}

        // {{ JWT 사용 추가
        const settings = {
            type: method,
            url: url,
            data: JSON.stringify(data),
            /*dataType: 'json',*/
            contentType: 'application/json',
            beforeSend: function(xhr) { Http.setAuthHeaders(xhr); }
        };
        return $.ajax(settings).fail(e => Http.handleError(settings, e));
        // }}
    }

    static filePost(url, formData, method = 'POST'){
        // {{ JWT 사용 추가 : 주석
        // return $.ajax({
        //     type: method,
        //     url: url,
        //     data: formData,
        //     contentType: false,
        //     processData: false,
        //     beforeSend: function(xhr) {
        //         Http.setAuthHeaders(xhr); // 수정한 함수 호출
        //     }
        // });
        // }}

        // {{ JWT 사용 추가
        const settings = {
            type: method,
            url: url,
            data: formData,
            contentType: false,
            processData: false,
            beforeSend: function(xhr) { Http.setAuthHeaders(xhr); }
        };
        return $.ajax(settings).fail(e => Http.handleError(settings, e));
        // }}
    }

    static fileDownload(id){
        // {{ JWT 사용 추가 : 주석
        // let filename = ''
        // return $.ajax({
        //     url: `/api/downloadFile/${id}`,
        //     method: 'GET',
        //     xhrFields: {
        //         responseType: 'blob'
        //     },
        //     beforeSend: function(xhr) {
        //         Http.setAuthHeaders(xhr); // 수정한 함수 호출
        //     },
        //     success: function (blobData, status, xhr) {
        //         const contentDisposition = xhr.getResponseHeader('Content-Disposition');
        //         const matches = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/.exec(contentDisposition);
        //         filename = matches && matches[1] ? matches[1].replace(/['"]/g, '').replace('UTF-8', '') : "";
        //
        //         if (filename) {
        //             const link = $('<a style="display: none;"></a>');
        //             link.attr('href', window.URL.createObjectURL(blobData));
        //             link.attr('download', decodeURIComponent(filename));
        //             $('body').append(link);
        //             link[0].click();
        //             link.remove();
        //         }
        //     }
        // });
        // }}

        // {{ JWT 사용 추가 : 주석
        let filename = '';
        const settings = {
            url: `/api/downloadFile/${id}`,
            method: 'GET',
            xhrFields: {
                responseType: 'blob'
            },
            beforeSend: function(xhr) {
                Http.setAuthHeaders(xhr); // 토큰과 CSRF 헤더 주입
            },
            success: function (blobData, status, xhr) {
                // 파일명 추출 로직 (기존 유지)
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
        };
        return $.ajax(settings).fail(e => Http.handleError(settings, e));
        // }}
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

    /***
     * {str} 문자열에 대해서 {n} 길이마다 <br> 추가
     * */
    static insertBrEveryNChars(str, n) {
        if (!str) return '';

        let result = '';
        for (let i = 0; i < str.length; i += n) {
            result += str.substring(i, i + n) + '<br>';
        }
        return result;
    }

}

class Util {

    static copyToClipboard(message) {
        if (navigator.clipboard && window.isSecureContext) {
            navigator.clipboard.writeText(message)
                .then(() => {
                    Sweet.alert("주소가 복사되었습니다!");
                })
                .catch(err => {
                    Util.fallbackCopyTextToClipboard(message);
                });
        } else {
            Util.fallbackCopyTextToClipboard(message);
        }
    }

    static fallbackCopyTextToClipboard(text) {
        const textArea = document.createElement("textarea");
        textArea.value = text;
        textArea.style.position = "fixed";
        textArea.style.left = "-9999px";
        document.body.appendChild(textArea);
        textArea.focus();
        textArea.select();
        try {
            const successful = document.execCommand('copy');
            const message = (successful) ? "주소가 복사되었습니다!" : "복사에 실패했습니다."
            Sweet.alert(message);
        } catch (err) {
            console.error('Fallback 복사 실패:', err);
            Sweet.alert("복사에 실패했습니다.");
        }
        document.body.removeChild(textArea);
    }

}

class CookieUtil {

    static getCookie(name) {
        let value = "; " + document.cookie;
        let parts = value.split("; " + name + "=");
        if (parts.length === 2) return parts.pop().split(";").shift();
    }

    static deleteCookie(name) {
        document.cookie = name + '=; Max-Age=-99999999; path=/;';
    }

    static setCookie(name, value, seconds) {
        let expires = "";
        if (seconds) {
            let date = new Date();
            date.setTime(date.getTime() + (seconds * 1000));
            expires = "; expires=" + date.toUTCString();
        }
        document.cookie = name + "=" + (value || "") + expires + "; path=/";
    }
}