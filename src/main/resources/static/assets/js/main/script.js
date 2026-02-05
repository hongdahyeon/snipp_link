var mainScriptJS = {

    logoutFunction: function () {
        // {{ JWT 사용 추가
        localStorage.removeItem("accessToken");
        // }}
        const form = document.createElement("form")
        form.method = "POST"
        form.action = "/logout"
        document.body.appendChild(form)
        form.submit()
    }

    ,editorUploadAdapter: function (editor) {
        editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
            return new MyUploadAdapter(loader)
        }
    }

}