class MyUploadAdapter {

    constructor(loader) {
        // CKEditor 5의 데이터 로더 인스턴스
        this.loader = loader;
        // 이미지 업로드 API 경로
        this.url = "/snipp/image/api/uploadCkImageFile";
        // 요청 취소를 위한 컨트롤러
        this.controller = new AbortController();
    }

    // 업로드 프로세스 시작
    upload() {
        return this.loader.file.then(
            (file) =>
                new Promise((resolve, reject) => {
                    this._sendRequest(file, resolve, reject);
                })
        );
    }

    // 업로드 중단
    abort() {
        if (this.controller) {
            this.controller.abort();
        }
    }

    // 실제 서버 요청 전송
    _sendRequest(file, resolve, reject) {
        const data = new FormData();
        data.append("file", file);

        const headers = new Headers();
        headers.append("Accept", "application/json");

        // 1. CSRF 토큰 (보안 유지용)
        const {header, token} = Http.getCookieInfo();
        if (header && token) {
            headers.append(header, token);
        }

        fetch(this.url, {
            method: "POST",
            body: data,
            headers: headers,
            signal: this.controller.signal,
        })
        .then((response) => this._handleResponse(response, resolve, reject, file.name))
        .catch((error) => {
            if (error.name === "AbortError") return;
            reject(`Couldn't upload file: ${file.name}.`);
        });
    }

    // 서버 응답 처리 로직
    _handleResponse(response, resolve, reject, fileName) {
        const genericErrorText = `Couldn't upload file: ${fileName}.`;

        // 1. HTTP 상태 코드 확인 (401은 중복 로그인 혹은 만료)
        if (!response.ok) {
            if (response.status === 401) {
                return reject("세션이 만료되었거나 다른 곳에서 로그인되었습니다.");
            }
            return reject(genericErrorText);
        }

        // 2. JSON 응답 파싱
        const contentType = response.headers.get("content-type");
        if (contentType && contentType.includes("application/json")) {
            return response.json().then((json) => {
                if (!json || json.error || !json.url) {
                    return reject(json && json.error ? json.error.message : genericErrorText);
                }
                // 성공 시 서버에서 받은 이미지 URL 반환
                resolve({
                    default: json.url,
                });
            });
        }

        return reject(genericErrorText);
    }
}