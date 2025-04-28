var loginhistJS = {

    table: null,

    initPage: function () {
        loginhistJS.table = new GridTable("short-url-log-hist-table")
            .get('/snipp/api/login-hist/page')
            .headers("center")
            .setPaging(10, 1, 3, true)
            .useIndex('#')
            .add(new Column('userEmail', '접근 사용자'))
            .add(new Column('accessIp', '접근IP'))
            .add(new Column('accessUserAgent').customName('<span>접근 사용자<br>에이전트</span>').formatter((cell, row, column) => {
                const str = StringUtil.insertBrEveryNChars(cell, 50);
                return Grid.draw(str)
            }))
            .add(new Column('loginDt', '로그인 일시').formatter((cell, row, column) => {
                return StringUtil.dateWithHHMM(cell);
            }))
            .add(new Column('loginAccessTpStr').customName('<span>접근 성공<br>여부</span>').formatter((cell, row, column) => {
                const mainId = row._cells[0]['data']
                const rowData = loginhistJS.table.getRowData(mainId)
                const color = (rowData['loginAccessTpIsSuccess']) ? 'green' : 'red'
                return Grid.draw(
                    `<span class="click-class" style="color: ${color}; cursor: pointer;" data-test="${cell}">${cell}</span>`
                );
            }))
            .add(new Column('loginAccessDescription', '비고').formatter((cell, row, column) => {
                const mainId = row._cells[0]['data']
                const rowData = loginhistJS.table.getRowData(mainId)
                const str = (rowData['loginAccessTpIsSuccess']) ? '-'
                        : `<span class="click-class" data-test="${cell}">${cell}</span>`
                return Grid.draw(str);
            }))
            .init();
    }
}