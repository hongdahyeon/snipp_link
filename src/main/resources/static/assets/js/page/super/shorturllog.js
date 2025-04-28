var shortUrlLogJS = {

    table: null,

    initPage: function () {
        shortUrlLogJS.table = new GridTable("short-url-log-hist-table")
            .get('/snipp/api/short-url/access-log/page')
            .headers("center")
            .setPaging(10, 1, 3, true)
            /*./!*add(new Column('logUid').width("5%").customName(`<span>유저<br>UID</span>`))*!/*/
            .useIndex('#')
            .add(new Column('shortUrl').customName('<span>short<br>URL</span>'))
            .add(new Column('originUrl', '원본URL'))
            .add(new Column('accessIp', '접근IP'))
            .add(new Column('accessUserAgent').customName('<span>접근 사용자<br>에이전트</span>').formatter((cell, row, column) => {
                const str = StringUtil.insertBrEveryNChars(cell, 50);
                return Grid.draw(str)
            }))
            .add(new Column('accessDate', '접근 일시').formatter((cell, row, column) => {
                return StringUtil.dateWithHHMM(cell);
            }))
            .add(new Column('accessTpStr').customName('<span>접근 성공<br>여부</span>').formatter((cell, row, column) => {
                const mainId = row._cells[0]['data']
                const rowData = shortUrlLogJS.table.getRowData(mainId)
                const color = (rowData['accessTpIsSuccess']) ? 'green' : 'red'
                return Grid.draw(
                    `<span class="click-class" style="color: ${color}; cursor: pointer;" data-test="${cell}">${cell}</span>`
                );
            }))
            .init();
    }
}