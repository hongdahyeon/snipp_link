var shortUrlJS = {

    table: null,

    initPage: function () {
        shortUrlJS.table = new GridTable("short-url-mng-table")
            .get('/api/snipp/short-url/super/page')
            .headers("center")
            .setPaging(10, 1, 3, true)
            .useIndex('#')
            .add(new Column('shortUrl').customName('<span>short<br>URL</span>'))
            .add(new Column('originUrl', '원본URL'))
            .add(new Column('expiresAt', '생성 일시').formatter((cell, row, column) => {
                return StringUtil.dateWithHHMM(cell);
            }))
            .add(new Column('createdAt', '만료 일시').formatter((cell, row, column) => {
                return StringUtil.dateWithHHMM(cell);
            }))
            .add(new Column('isExpired', '만료 여부').formatter((cell, row, column) => {
                const color = (cell === 'N') ? 'green' : 'red'
                const text = (cell === 'N') ? '사용가능' : '만료됨'
                return Grid.draw(
                    `<span style="color: ${color}">${text}</span>`
                )
            }))
            .add(new Column('isPublic', '공개 여부').formatter((cell, row, column) => {
                const color = (cell === 'Y') ? 'green' : 'red'
                const text = (cell === 'Y') ? '공개' : '비공개'
                return Grid.draw(
                    `<span style="color: ${color}">${text}</span>`
                )
            }))
            .init()
            .rowClick((...args) => {
                const mainId = args[1]._cells[0].data;
                const rowData = shortUrlJS.table.getRowData(mainId)
                console.log(">> rowData: ", rowData)
                // todo : access-log 정보로 추가 작업 진행
            });
    }
}