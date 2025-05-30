var boardJS = {

     table : null
    ,contentEditor: null
    ,contentErrorSpan: $("#content-ckeditor-error")
    ,tree: null

    ,initPage: async function () {
        switch (type) {
            case "faq":
                await drawFaqCards()
                boardJS.initFaqClTree();
                break;
            case "free":
                boardJS.initTable();
                break;
        }
    }

    ,initFaqClTree: async function () {
        boardJS.tree = new Tree("test-tree-div", true, false);
        boardJS.tree.drawTree(`/snipp/api/bbs-cl/tree`, () => {
           return { bbsUid }
        });
    }

    ,initTable: function () {
        boardJS.table = new GridTable("board-table")
            .get(`/snipp/api/board/page?bbsUid=${bbsUid}`)
            .headers("left")
            .setPaging(10, 1, 3, true)
            .useIndex("번호")
            .add(new Column('title', '제목'))
            .add(new Column('useAt', '사용여부').formatter((cell, row, column) => {
                const color = (cell === 'Y') ? 'green' : 'red'
                const text = (cell === 'Y') ? '사용' : '미사용'
                return Grid.draw(
                    `<span style="color: ${color}">${text}</span>`
                )
            }))
            .add(new Column('regUid', '등록자').formatter((cell, row, column) => {
                const rowData = boardJS.table.getRowDataFull(row)
                return Grid.draw(`<span>${rowData['regNm']} (${rowData['regId']})</span>`)
            }))
            .init()
            .rowClick((...args) => {
                const rowData = bbsJS.table.getRowDataFull(args[1])
                window.location.href =`/snipp/board/${type}/${rowData['bbsUid']}`
            });
    }

    ,initContent: async function () {
        boardJS.contentEditor = new Editor("content-ckeditor", '', true);
        boardJS.contentEditor = await boardJS.contentEditor.init();
    }

    ,saveBoardForm: function () {

        const content = boardJS.contentEditor.getEditorData();
        const $form = $("#save-form")
        if (!$form[0].checkValidity() || content.length === 0) {

            $form.addClass("was-validated");
            boardJS.contentErrorSpan.text((content.length === 0) ? '필수 입력값입니다.' : '');
            return

        } else {

            boardJS.contentErrorSpan.text((content.length === 0) ? '필수 입력값입니다.' : '');

            thumbnailModal.chooseThumbNail().then(async () => {
                let obj = {
                    title: $("#title").val(),
                    content: content,
                    useAt: $('input[name="useAt"]:checked').val(),
                    bbsUid: bbsUid
                }
                if(type === 'faq') obj['clUid'] = $("#clUid").val()
                obj['thumbnailSrc'] = thumbnailModal.thumbNailImg

                Http.post('/snipp/api/board', obj).then(() => {
                    Sweet.alert("게시글이 등록되었습니다.").then(() => {
                        window.location.href = `/snipp/board/${type}`
                    })
                })
            });

        }
    }
}