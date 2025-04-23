var noticeJS = {
    ckeditor1: null,
    ckeditor2: null,
    initial: function () {

        editorConfig1.extraPlugins = [
            function (editor) { mainScriptJS.editorUploadAdapter(editor); }
        ];

        // CKEditor 1 초기화
        ClassicEditor
            .create(document.querySelector("#ckeditor1"), editorConfig1)
            .then(editor => {
                noticeJS.ckeditor1 = editor;
            })
            .catch(error => {
                console.error("CKEditor 1 초기화 오류:", error);
            });

        // CKEditor 2 초기화 (editorConfig2가 있다고 가정)
        ClassicEditor
            .create(document.querySelector("#ckeditor2"), editorConfig2)
            .then(editor => {
                noticeJS.ckeditor2 = editor;
            })
            .catch(error => {
                console.error("CKEditor 2 초기화 오류:", error);
            });
    }
};

