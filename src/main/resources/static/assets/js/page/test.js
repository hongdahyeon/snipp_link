var testJs = {
    ckeditor1: null,
    ckeditor2: null,
    table: null,

    initial: async function () {
        const content = '<p>hello world</p>';

        testJs.ckeditor1 = new Editor("ckeditor1", content, true);
        testJs.ckeditor1 = await testJs.ckeditor1.init();

        testJs.ckeditor2 = new Editor("ckeditor2", '', false);
        testJs.ckeditor2 = await testJs.ckeditor2.init();
        testJs.ckeditor2.setEditorData("<b>hi?</b>")

        testJs.table = new GridTable("table")
            .search("userNm")
            .get('/snipp/api/user/page')
            .headers("center")
            .setPaging(10, 1, 3, true)
            .add(new Column("uid", "선택", true))
            .add(new Column('uid', '유저UID').formatter((cell, row, column) => {
                /*console.log("[formatter] cell: ", cell)                         // 현재 cell data
                console.log("[formatter] row._id: ", row._id)                   // 현재 row._id
                console.log("[formatter] row._cells: ", row._cells)             // 현재 row cell 리스트 : { -id, data } 로 이루어짐
                row._cells.forEach(rw => {
                    console.log(">> [formatter] each row._id : ", rw._id, " => data : ", rw.data )
                });
                console.log("[formatter] column: ", column)                     // 현재 컬럼 정보*/
                return Grid.draw(
                    `<span class="click-class" style="color:red; cursor: pointer;" data-test="${cell}">${cell}</span>`
                );
            }))
            .add(new Column('userNm', '유저 이름').formatter((cell, row, column) => {
                return Grid.draw(
                    `<span class="click-class" style="cursor: pointer; color: green;" data-test="${cell}">${cell}</span>`
                )
            }))
            .add(new Column('userEmail', '유저 이메일').formatter((cell, row, col) => {

                const mainId = row._cells[0]['data']
                const rowData = testJs.table.getRowData(mainId)
                console.log(">>> ", rowData)

                return Grid.custom('button', {
                    className: "btn btn-sm btn-outline-primary",
                    'data-email': cell,
                    onClick: (e) => {
                        e.stopPropagation();
                        Sweet.alert("버튼 클릭 : " + cell)
                    }
                }, "버튼 텍스트")
            }))
            .init()
            .rowClick((...args) => {
                const cellData = args[1]._cells.map((cell, idx) => {
                    return `<span>[${idx}]: ${cell._id} <br> -> ${cell.data}</span><br><br>`;
                }).join("");
                Sweet.alert(cellData);
            });
    }

    ,getCkeditorData: function () {
        console.log(">> content1 : ", testJs.ckeditor1.getEditorData())
        console.log(">> content2 : ", testJs.ckeditor2.getEditorData())
        console.log(">> clicked row : ", testJs.table.getSelectData())
    }
}