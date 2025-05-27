/*
* ## How To Use ##
* (1) init & setData
*   const editor = new Editor("div_id", "content_html", true/false);
*   editor = await editor.init();
*
* (2) getData
*   editor.getEditorData()
*
* (3) setData
*   editor.setEditorData("<b>hi?</b>")
*
* */

class Editor {

    constructor(id, content = '', use_uploader = true) {
        this.id = id;
        this.content = content;
        this.use_uploader = use_uploader;
        this.ckeditor = null;
    }

    async init(isViewMode = false) {
        const config = this.use_uploader ? editorConfig1 : editorConfig2;

        if (this.use_uploader) {
            config.extraPlugins = [
                function (editor) { mainScriptJS.editorUploadAdapter(editor); }
            ];
        }

        try {

            // 공통 CKEditor 초기화
            this.ckeditor = await ClassicEditor.create(document.querySelector(`#${this.id}`), config);

            // 에디터 내용 설정
            this.ckeditor.setData(this.content);

            // 뷰 모드일 경우 처리
            if (isViewMode) {
                const toolbarElement = this.ckeditor.ui.view.toolbar.element;
                if (toolbarElement) {
                    toolbarElement.style.display = 'none';
                }
                this.ckeditor.enableReadOnlyMode(this.id);
            }

            return this; // Editor 인스턴스 리턴

        }  catch (error) {

            console.error(`CKEditor(${this.id}) 초기화 오류:`, error);
            return null;

        }
    }

    getEditorData() {
        return this.ckeditor?.getData?.() || "";
    }

    setEditorData(data) {
        if (this.ckeditor) {
            this.ckeditor.setData(data);
        }
    }
}