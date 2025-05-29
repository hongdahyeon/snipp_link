var freeJS = {

    table : null

    ,initTable: function () {
        freeJS.table = new GridTable("free-table")
            .search("userNm")
            .get('/snipp/api/user/page')
            .headers("left")
            .setPaging(10, 1, 3, true)
            .add(new Column("uid", "선택", true))
            .useIndex("번호")
            .add(new Column('uid', '유저UID').formatter((cell, row, column) => {
                return Grid.draw(
                    `<span class="click-class" style="color:red; cursor: pointer;" data-test="${cell}">${cell}</span>`
                );
            }))
            .add(new Column('userNm', '유저 이름').formatter((cell, row, column) => {
                return Grid.draw(
                    `<button class="btn btn-sm btn-outline-primary test-btn1" data-name="${cell}">버튼 텍스트1</button>`
                )
            }))
            .add(new Column('userEmail', '유저 이메일').formatter((cell, row, col) => {

                const mainId = row._cells[0]['data']
                const rowData = freeJS.table.getRowData(mainId)
                console.log(">>> ", rowData)

                return Grid.custom('button', {
                    className: "btn btn-sm btn-outline-primary",
                    'data-email': cell,
                    'data-name': rowData['userNm'],
                    onClick: (e) => {
                        e.stopPropagation();
                        Sweet.alert("버튼 클릭 : " + cell)
                    }
                }, "버튼 텍스트2")
            }))
            .init()
            .rowClick((...args) => {
                const cellData = args[1]._cells.map((cell, idx) => {
                    return `<span>[${idx}]: ${cell._id} <br> -> ${cell.data}</span><br><br>`;
                }).join("");
                Sweet.alert(cellData);
            });

    }
}