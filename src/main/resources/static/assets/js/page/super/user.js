var userJS = {

    table: null,

    initPage: function () {
        userJS.table = new GridTable("user-mng-table")
            .get('/snipp/api/user/super/page')
            .headers("center")
            .setPaging(10, 1, 3, true)
            .useIndex('#')
            .add(new Column('userId', '유저ID'))
            .add(new Column('userEmail', '유저이메일'))
            .add(new Column('userNm', '유저명'))
            .add(new Column('userRoleKr', '권한'))
            .add(new Column('socialUid', '소셜로그인').formatter((cell, row, column) => {
                const rowData = userJS.table.getRowDataFull(row);
                const displayHtml = rowData['socialUid']
                    ? `${rowData['socialUserId']} (${rowData['socialTpKor']})`
                    : '-';
                return Grid.draw(`<span>${displayHtml}</span>`);
            }))
            .init()
            .add(new Column('lastConnDt', '마지막 접근일시').formatter((cell, row, column) => {
                return StringUtil.dateWithHHMM(cell);
            }))
            .init();
    }
}