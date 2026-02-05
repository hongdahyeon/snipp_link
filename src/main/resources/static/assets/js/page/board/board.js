var boardJS = {

     table: null
    ,faqClDiv: null
    ,contentEditor: null
    ,contentErrorSpan: $("#content-ckeditor-error")

    ,initPage: function () {
        switch (type) {
            case "faq":
                boardJS.drawFaqCl().then(() => {
                    const $firstLink = $('#board-cl-faq a.text-muted').first();
                    $firstLink.addClass('selected');
                    clUid = $firstLink.data('uid');
                }).then(async () => {
                    await faqCard.drawFaqBoardCards();
                });
                break;
            case "free":
                boardJS.initTable();
                break;
        }
    }

    ,initTable: function () {
        boardJS.table = new GridTable("board-table")
            .get(`/api/snipp/board/page?bbsUid=${bbsUid}`)
            .headers("left")
            .setPaging(10, 1, 3, true)
            .useIndex("번호")
            .add(new Column('title', '제목'))

        if( user != null && (user?.['role'] === 'ROLE_SUPER' || user?.['role'] === 'ROLE_MANAGER')) {
            boardJS.table
                .add(new Column('useAt', '사용여부').formatter((cell, row, column) => {
                    const color = (cell === 'Y') ? 'green' : 'red'
                    const text = (cell === 'Y') ? '사용' : '미사용'
                    return Grid.draw(
                        `<span style="color: ${color}">${text}</span>`
                    )
                }))
        }

        boardJS.table
            .add(new Column('regUid', '등록자').formatter((cell, row, column) => {
                const rowData = boardJS.table.getRowDataFull(row)
                return Grid.draw(`<span>${rowData['regNm']} (${rowData['regId']})</span>`)
            }))
            .init()
            .rowClick((...args) => {
                const rowData = boardJS.table.getRowDataFull(args[1])
                window.location.href =`/snipp/board/${type}/${rowData['bbsUid']}`
            });
    }

    ,drawFaqCl: function () {
         return new Promise(async (resolve) => {
             boardJS.faqClDiv = new FaqCl("board-cl-faq");
             const res = await Http.get('/api/snipp/bbs-cl/tree', {bbsUid});
             boardJS.faqClDiv.data(res).draw();
             resolve();
         });
    }

    ,deleteBoard: async function (uid) {
        const isOk = await Sweet.confirm("게시글을 삭제하시겠습니까?")
        if(isOk) {
            Http.delete(`/api/snipp/board/${uid}`).then(() => {
                Sweet.alert("게시글이 삭제되었습니다.").then(() => {
                    window.location.reload();
                })
            })
        }
    }

    ,initContent: async function (content = '', isViewMode = false) {
        boardJS.contentEditor = new Editor("content-ckeditor", content, true);
        boardJS.contentEditor = await boardJS.contentEditor.init(isViewMode);
    }

    ,saveBoardForm: function () {

        const content = boardJS.contentEditor.getEditorData();
        const $form = $("#save-form")
        if (!$form[0].checkValidity() || content.length === 0 || ( type === 'faq' && $("#choose-clUid").val().length === 0) ) {

            $form.addClass("was-validated");
            boardJS.etcValidationCheck();
            return

        } else {

            boardJS.etcValidationCheck();
            thumbnailModal.chooseThumbNail().then(async () => {
                let obj = {
                    title: $("#title").val(),
                    content: content,
                    useAt: $('input[name="useAt"]:checked').val(),
                    bbsUid: bbsUid
                }
                if(type === 'faq') obj['clUid'] = $("#choose-clUid").val()
                obj['thumbnailSrc'] = thumbnailModal.thumbNailImg

                Http.post('/api/snipp/board', obj).then(() => {
                    Sweet.alert("게시글이 등록되었습니다.").then(() => {
                        window.location.href = `/snipp/board/${type}`
                    })
                });
            });

        }
    }

    ,etcValidationCheck: function () {
        const content = boardJS.contentEditor.getEditorData();
        boardJS.contentErrorSpan.text((content.length === 0) ? '필수 입력값입니다.' : '');
        if(type === 'faq') {
            const valueLength = $("#choose-clUid").val().length;
            if(valueLength === 0) $("#choose-clNm").addClass('is-invalid')
            else $("#choose-clNm").removeClass('is-invalid');
        }
    }
}