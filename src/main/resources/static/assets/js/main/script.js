var mainScriptJS = {

    logoutFunction: function () {
        const {header, token} = Http.getCookieInfo()
        const form = document.createElement("form")
        const csrfInput = document.createElement("input")
        form.method = "POST"
        form.action = "/logout"
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