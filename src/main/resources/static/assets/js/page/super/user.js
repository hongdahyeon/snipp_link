var userJS = {

    table: null,

    initPage: function () {
        userJS.table = new GridTable("user-mng-table")
            .get('/api/snipp/user/super/page')
            .headers("center")
            .setPaging(10, 1, 3, true)
            .useIndex('#')
            .add(new Column('userId', '유저ID(이메일)').formatter((cell, row, column) => {
                const rowData = userJS.table.getRowDataFull(row);
                const displayHtml = `${rowData['userId']} (${rowData['userEmail']})`
                return Grid.draw(`<span>${displayHtml}</span>`);
            }))
            .add(new Column('userNm', '유저명'))
            .add(new Column('userRoleKr', '권한'))
            .add(new Column('socialUid', '소셜로그인').formatter((cell, row, column) => {
                const rowData = userJS.table.getRowDataFull(row);
                const displayHtml = rowData['socialUid']
                    ? `${rowData['socialUserId']} (${rowData['socialTpKor']})`
                    : '-';
                return Grid.draw(`<span>${displayHtml}</span>`);
            }))
            .add(new Column('lastConnDt', '마지막 접근일시').formatter((cell, row, column) => {
                return StringUtil.dateWithHHMM(cell);
            }))
            .add(new Column('isEnable', '활성화여부').formatter((cell, row, column) => {
                const rowData = userJS.table.getRowDataFull(row);
                const isActive = (cell === 'Y');

                // UI 설정
                const btnClass = isActive ? 'btn-outline-success' : 'btn-outline-danger';
                const btnText = isActive ? '활성화(사용중)' : '비활성화(중지)';
                // 동작 설정 (현재가 활성이면 -> disable 요청, 현재가 비활성이면 -> enable 요청)
                const nextActionCode = isActive ? 'disable' : 'enable';
                const confirmMsg = isActive ? '비활성화' : '활성화';

                return Grid.custom('button', {
                    className: `btn btn-sm ${btnClass}`,
                    'data-uid': rowData['uid'],
                    onClick: async (e) => {
                        e.stopPropagation();
                        const isOk = await Sweet.confirm(`[${btnText}] 상태입니다.\n${confirmMsg} 처리 하시겠습니까?`)
                        if (isOk) userJS.userCodeChange(nextActionCode, rowData['uid']);
                    }
                }, btnText)
            }))
            .add(new Column('isLocked', '잠금여부').formatter((cell, row, column) => {
                const rowData = userJS.table.getRowDataFull(row);
                const isLocked = (cell === 'Y');

                // UI 설정 (잠김=Red, 정상=Green)
                const btnClass = isLocked ? 'btn-outline-danger' : 'btn-outline-success';
                const btnText = isLocked ? '잠김' : '정상';
                // 동작 설정 (현재가 잠김이면 -> unlock 요청, 현재가 정상이면 -> lock 요청)
                const nextActionCode = isLocked ? 'unlock' : 'lock';
                const confirmMsg = isLocked ? '잠금 해제' : '계정 잠금';

                return Grid.custom('button', {
                    className: `btn btn-sm ${btnClass}`,
                    'data-uid': rowData['uid'],
                    onClick: async (e) => {
                        e.stopPropagation();
                        const isOk = await Sweet.confirm(`[${btnText}] 상태입니다.\n${confirmMsg} 처리 하시겠습니까?`)
                        if (isOk) userJS.userCodeChange(nextActionCode, rowData['uid']);
                    }
                }, btnText)
            }))
            .init();
    }

    ,userCodeChange: function (code, uid) {
        let successMsg = "";
        switch(code) {
            case 'enable':  successMsg = "유저가 활성화되었습니다."; break;
            case 'disable': successMsg = "유저가 비활성화되었습니다."; break;
            case 'unlock':  successMsg = "유저 잠금이 풀렸습니다."; break;
            case 'lock':    successMsg = "유저가 잠겼습니다."; break;
            default:        successMsg = "처리되었습니다.";
        }
        Http.put(`/api/snipp/user/super/${code}/${uid}`).then(() => {
            Sweet.alert(successMsg).then(() => {
                userJS.table.submitTable();
            });
        }).catch(err => {
            console.error(err);
        });
    }
}