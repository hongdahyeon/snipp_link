var bbsJS = {

    table: null

    ,initTable: function () {
        bbsJS.table = new GridTable("bbs-table")
            .get('/snipp/api/bbs/page')
            .headers("left")
            .setPaging(10, 1, 3, true)
            .useIndex("번호")
            .add(new Column('bbsTpNm', '게시판 유형'))
            .add(new Column('bbsNm', '게시판명'))
            .add(new Column('useAt', '사용여부').formatter((cell, row, column) => {
                const color = (cell === 'Y') ? 'green' : 'red'
                const text = (cell === 'Y') ? '사용' : '미사용'
                return Grid.draw(
                    `<span style="color: ${color}">${text}</span>`
                )
            }))
            .add(new Column('regUid', '등록자').formatter((cell, row, column) => {
                const rowData = bbsJS.table.getRowDataFull(row)
                return Grid.draw(`<span>${rowData['regNm']} (${rowData['regId']})</span>`)
            }))
            .init()
            .rowClick((...args) => {
                const rowData = bbsJS.table.getRowDataFull(args[1])
                window.location.href =`/snipp/bbs/super/${rowData['bbsUid']}`
            });
    }

    ,saveBbsForm: async function () {

        const $form = $("#save-form")
        if (!$form[0].checkValidity()) {
            $form.addClass("was-validated");
            return
        } else {
            const obj = await FormDataToObj.getParameter("save-form")
            Http.post('/snipp/api/bbs', obj).then(() => {
                Sweet.alert('게시판이 등록되었습니다.').then(() => {
                    window.location.href = '/snipp/bbs/super'
                });
            });
        }

    }

    ,updateBbsForm: async function () {

        const $form = $("#save-form")
        if (!$form[0].checkValidity()) {
            $form.addClass("was-validated");
            return
        } else {
            const obj = await FormDataToObj.getParameter("save-form")
            Http.put(`/snipp/api/bbs/${uid}`, obj).then(() => {
                Sweet.alert('게시판이 수정되었습니다.').then(() => {
                    window.location.href = '/snipp/bbs/super'
                });
            });
        }

    }
    
    ,deleteBbs: async function () {

        const isOk = await Sweet.confirm('삭제하시겠습니까?')
        if (isOk) {
            Http.delete(`/snipp/api/bbs/${uid}`).then(() => {
                Sweet.alert('게시판이 삭제되었습니다.').then(() => {
                    window.location.href = '/snipp/bbs/super'
                });
            });
        }
    } 
}