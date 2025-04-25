var shortUrlLogJS = {

    table: null,

    initPage: function () {
        shortUrlLogJS.table = new GridTable("short-url-log-hist-table")
            .get('/snipp/api/short-url/access-log/page')
            .headers("center")
            .setPaging(10, 1, 3, true)
            .add(new Column('logUid')
                .width("5%")
                .customName(`<span>유저<br>UID</span>`)
                .formatter((cell, row, column) => {
                /*console.log("[formatter] cell: ", cell)                         // 현재 cell data
                console.log("[formatter] row._id: ", row._id)                   // 현재 row._id
                console.log("[formatter] row._cells: ", row._cells)             // 현재 row cell 리스트 : { -id, data } 로 이루어짐
                row._cells.forEach(rw => {
                    console.log(">> [formatter] each row._id : ", rw._id, " => data : ", rw.data )
                });
                console.log("[formatter] column: ", column)                     // 현재 컬럼 정보*/
                return gridjs.html(
                    `<span class="click-class" style="color:red; cursor: pointer;" data-test="${cell}">${cell}</span>`
                );
            }))
            .add(new Column('shortUrl').customName('<span>short<br>URL</span>').width("7%"))
            .add(new Column('originUrl', '원본URL'))
            .add(new Column('accessIp', '접근IP'))
            .add(new Column('accessUserAgent').customName('<span>접근 사용자<br>에이전트</span>'))
            .add(new Column('accessDate', '접근 일시'))
            .add(new Column('accessTpStr', '접근 성공 여부').formatter((cell, row, column) => {
                return cell;
            }))
            .init();
    }
}