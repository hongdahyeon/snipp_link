var noticeJS = {
    ckeditor1: null,
    ckeditor2: null,
    table: null,

    initial: async function () {
        const content = '<p>hello world</p>';

        noticeJS.ckeditor1 = new Editor("ckeditor1", content, true);
        noticeJS.ckeditor1 = await noticeJS.ckeditor1.init();

        noticeJS.ckeditor2 = new Editor("ckeditor2", '', false);
        noticeJS.ckeditor2 = await noticeJS.ckeditor2.init();
        noticeJS.ckeditor2.setEditorData("<b>hi?</b>")

        noticeJS.table = new GridTable("table")
                                .search("userNm")
                                .get('/snipp/api/user/page')
                                .add(new Column('uid', '유저UID'))
                                .add(new Column('userNm', '유저 이름'))
                                .add(new Column('userEmail', '유저 이메일'))
                                .init()

    }

    ,getCkeditorData: function () {
        console.log(">> content1 : ", noticeJS.ckeditor1.getEditorData())
        console.log(">> content2 : ", noticeJS.ckeditor2.getEditorData())
    }
};

