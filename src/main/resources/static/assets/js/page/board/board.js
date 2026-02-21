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

    ,initFile: function () {
        // 파일 선택 이벤트
        $("#fileInput").on("change", fileJS.handleFileSelect);

        // 기존 파일 삭제 버튼 이벤트 (Delegation 사용으로 save/edit 공용)
        $(document).on("click", ".delete-existing-btn", function() {
            const fileId = $(this).data("fileId");
            fileJS.deleteExistingFile(fileId);
        });
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
                const mainId = args[1]._cells[0].data;
                const rowData = boardJS.table.getRowData(mainId)
                window.location.href =`/snipp/board/${type}/${rowData['boardUid']}`
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

    ,deleteBoard: async function (uid, type) {
        const isOk = await Sweet.confirm("게시글을 삭제하시겠습니까?")
        if(isOk) {
            Http.delete(`/api/snipp/board/${uid}`).then(() => {
                Sweet.alert("게시글이 삭제되었습니다.").then(() => {
                    window.location.href = `/snipp/board/${type}`
                })
            })
        }
    }

    ,initContent: async function (content = '', isViewMode = false) {
        boardJS.contentEditor = new Editor("content-ckeditor", content, true);
        boardJS.contentEditor = await boardJS.contentEditor.init(isViewMode);
    }

    ,saveBoardForm: function (isPost = true, boardUid = null, fileUid = null) {
        const content = boardJS.contentEditor.getEditorData();
        const $form = $("#save-form");

        if (!$form[0].checkValidity() || content.length === 0 || (type === 'faq' && $("#choose-clUid").val().length === 0)) {

            $form.addClass("was-validated");
            boardJS.etcValidationCheck();
            return;

        } else {

            boardJS.etcValidationCheck();
            thumbnailModal.chooseThumbNail().then(async () => {

                // 1. FormData 객체 생성
                const formData = new FormData();

                // 2. 게시글 메타데이터 객체 생성
                const boardData = {
                    title: $("#title").val(),
                    content: content,
                    useAt: $('input[name="useAt"]:checked').val(),
                    bbsUid: bbsUid,
                    thumbnailSrc: thumbnailModal.thumbNailImg
                };
                if (type === 'faq') boardData['clUid'] = $("#choose-clUid").val();
                if (!isPost) {
                    boardData['fileUid'] = fileUid;
                    boardData['deleteFiles'] = fileJS.deleteFileIds
                }

                // 3. {request}
                formData.append("request", new Blob([JSON.stringify(boardData)], {
                    type: "application/json"
                }));

                // 4. {files} 파일 추가
                if (fileJS.uploadFiles.length > 0) {
                    for (let i = 0; i < fileJS.uploadFiles.length; i++) {
                        formData.append("files", fileJS.uploadFiles[i]);
                    }
                }

                // 5. post, put
                if(isPost) {
                    Http.post('/api/snipp/board', formData).then(() => {
                        Sweet.alert("게시글이 등록되었습니다.").then(() => {
                            window.location.href = `/snipp/board/${type}`;
                        });
                    });
                } else {
                    Http.put(`/api/snipp/board/${boardUid}`, formData).then(() => {
                        Sweet.alert("게시글이 수정되었습니다.").then(() => {
                            window.location.href = `/snipp/board/${type}`;
                        });
                    });
                }
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

var fileJS = {

     uploadFiles: []   // 신규 업로드 파일 객체들
    ,deleteFileIds: [] // 삭제할 기존 파일 ID들

    ,handleFileSelect: function(e) {
        const files = Array.from(e.target.files);
        if(!files.length) return;

        files.forEach(file => {
            fileJS.uploadFiles.push(file);
        });

        fileJS.renderFileList();
        $(this).val(''); // 중복 선택 가능하도록 초기화
    }

    ,renderFileList: function() {
        $(".new-file-item").remove(); // 신규 항목만 지우고 다시 그림
        const $container = $("#file-list-box");

        fileJS.uploadFiles.forEach((file, index) => {
            const $item = $(`
                    <div class="file-row d-flex align-items-center justify-content-between mb-2 p-2 border rounded bg-white shadow-sm new-file-item">
                        <div class="d-flex align-items-center">
                            <i class="bi bi-file-earmark-plus me-2 text-success"></i>
                            <div class="d-flex flex-column">
                                <span class="small text-success fw-bold">${file.name}</span>
                                <span class="small text-muted" style="font-size: 0.75rem;">${fileJS.formatBytes(file.size)}</span>
                            </div>
                        </div>
                        <button type="button" class="btn-close" onclick="fileJS.removeNewFile(${index})"></button>
                    </div>
                `);
            $container.append($item);
        });
    }

    ,removeNewFile: function(index) {
        fileJS.uploadFiles.splice(index, 1);
        fileJS.renderFileList();
    }

    ,deleteExistingFile: function(fileId) {
        fileJS.deleteFileIds.push(fileId); // 삭제 대상 ID 담기
        $(`#file-row-${fileId}`).remove();   // 화면에서 제거
    }

    ,formatBytes: function(bytes, decimals = 2) {
        if (!+bytes) return '0 Bytes';
        const k = 1024;
        const dm = decimals < 0 ? 0 : decimals;
        const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        return `${parseFloat((bytes / Math.pow(k, i)).toFixed(dm))} ${sizes[i]}`;
    }
}