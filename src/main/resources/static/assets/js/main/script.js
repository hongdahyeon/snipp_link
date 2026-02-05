var mainScriptJS = {

    logoutFunction: function () {
        // {{ JWT 사용 추가
        localStorage.removeItem("accessToken");
        // }}

        const {header, token} = Http.getCookieInfo()
        const form = document.createElement("form")
        form.method = "POST"
        form.action = "/logout"

        const csrfInput = document.createElement("input")
        csrfInput.type = 'hidden'
        csrfInput.name = "_csrf";
        csrfInput.value = token

        form.appendChild(csrfInput)
        document.body.appendChild(form)
        form.submit()
    }

    ,editorUploadAdapter: function (editor) {
        editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
            return new MyUploadAdapter(loader)
        }
    }

}