class GridTable {

    /*
    * @출처 : https://gridjs.io/docs/examples/html-cells
    * */
    constructor(id) {
        this._id = id;
        this._table = null;

        // 1. 언어 세팅
        this._language = null

        // 2. 테이블 커스텀 세팅 + 페이징
        this._searchKeyword = null;
        this._useSearch = false;
        this._config = null;
        this.resizable = false;
        this.sort = false;
        this._limit = 10;
        this._page = 1;
        this._buttonsCount = 3;

        // 3. style 세팅
        this._style = null;

        // 4. server
        this._url = ''

        // 5. column list
        this._columns = []

        // 6. click-handler
        this._rowClickHandler = null;
    }

    /**
     * 데이터를 가져올 {url} 세팅
     * */
    get(url) {
        this._url = url
        return this;
    }

    /*
    * 검색어 기능을 이용할 경우 -> 검색어 {keyword} 값 세팅
    * */
    search(keyword) {
        this._useSearch = true;
        this._searchKeyword = keyword;
        return this;
    }

    /**
     * 헤더 정렬
     * => (center : 중앙) (left : 좌측) (right : 우측)
     * */
    headers(align) {
        $(`#${this._id} .gridjs-th-content`).css("display", "flex").css("align-items", align).css("justify-content", align);
        return this;
    }

    /*
    * 언어 설정
    * */
    _langs() {
        this._language = {
            'search': {
                placeholder: '🔍 검색어를 입력하세요..'
            },
            sort: {
                sortAsc: '🔼오름차순 정렬',
                sortDesc: '🔽내림차순 정렬',
            },
            /*pagination: {
                previous: '이전',
                next: '다음',
                navigate: (page, pages) => `Page ${page} of ${pages}`,
                page: (page) => `Page ${page}`,
                showing: '조회 결과',
                of: '건중',
                to: '-',
                results: '레코드',
            },*/
            pagination: {
                previous: '이전',
                next: '다음',
                of: '페이지',
                to: '부터',
                showing: '조회 결과',
                results: "건"
            },
            loading: '로딩중...',
            noRecordsFound: '😢 조회 결과가 없습니다.',
            error: '😢 데이터 조회 도중 오류가 발생했습니다.',
        }
    }

    /*
    * {config} 관련 설정
    * */
    _configSetting() {
        this._config = {
            search: this._search(),         // 검색 기능
            pagination: this._paging(),     // 페이징 기능
            resizable: this.resizable,      // 사이즈 조절 기능
            sort: this.sort                 // 정렬 기능
        }
    }

    /*
    * 검색 기능
    * */
    _search() {
        return {
            enabled: this._useSearch,
            server: {
                /***
                 * @prev : 기본 URL (_this._url)
                 * @keyword : 입력된 검색어
                 * */
                url: (prev, keyword) => {
                    const q = `${this._searchKeyword}=${encodeURIComponent(keyword)}`;
                    const joinChar = prev.includes('?') ? '&' : '?';
                    return `${prev}${joinChar}${q}`;
                }
            }
        }
    }

    /**
     *  페이징 기본 정보 변경
     * */
    setPaging(limit = 10, page = 1, buttonsCount = 3) {
        this._limit = limit;
        this._page = page;
        this._buttonsCount = buttonsCount;
        return this;
    }


    /*
    * 페이징 기능
    * */
    _paging() {
        return {
            enabled: true,                      // 페이징 기능 활성화
            limit: this._limit,                 // 1개의 페이지에 데이터 개수
            page: this._page,                   // 페이지 넘버
            summary: true,                      // 하단에 '조회 결과..' 텍스트 조회 가능 여부
            nextButton: true,                   // 하단에 '[다음] 버튼' 조회 가능 여부
            prevButton: true,                   // 하단에 '[이전] 버튼' 조회 가능 여부
            buttonsCount: this._buttonsCount,   // 하단에 '[이전]과 [다음] 버튼 사이에 보여질 페이징 버튼' 개수
            resetPageOnUpdate: true,            // [submit] 시점에 페이징 초기화 여부
            server: {
                /**
                 * @prev : 기본 URL (_this._url)
                 * @page : initial page (시작: 0)
                 * @limit : 1개의 페이지 데이터 개수
                 * */
                url: (prev, page, limit) => {
                    const joinChar = prev.includes('?') ? '&' : '?';
                    return `${prev}${joinChar}pageNumber=${page + 1}&size=${limit}`;
                }
            }
        }
    }

    /**
     * grid 테이블 스타일링
     * */
    _styleSetting() {
        this._style = {
            table: 'gridjs-custom-table',
            th: 'gridjs-custom-table-th',
            td: 'gridjs-custom-table-td',
            header: 'gridjs-custom-table-header',
            footer: 'gridjs-custom-table-footer'
        }
    }

    /**
     * {row} 클릭 이벤트
     * => 체크박스에 대해선 클릭 이벤트 무시
     * */
    rowClick(handler) {
        const wrappedHandler = (p, n) => {
            const $target = $(p.target);
            if ($target.hasClass('table-checkbox-td') || $target.hasClass('table-checkbox-all')) {
                return;
            }
            handler(p, n)
        }
        if (this._table) {
            this._table.on('rowClick', wrappedHandler);
        } else {
            this._rowClickHandler = wrappedHandler;
        }
        return this;
    }

    /**
     * 컬럼 추가
     * */
    add(column) {
        if(column instanceof Column) {
           this._columns.push(column)
        }
        return this
    }

    /*
    * 기본 테이블 구성용 메소드 전체 실행
    * */
    _initMethods() {
        this._langs();
        this._configSetting();
        this._styleSetting();
    }

    _server() {
        return {
            url: this._url,
            method: 'GET',
            handle: (res) => {
                if(res.status === 200) {
                    return res.json()
                } else {
                    console.error('table error')
                    return []
                }
            },
            then: data => data.list,
            total: (data) => data['totalElements']
        }
    }

    /**
     * grid 테이블 초기화
     * */
    init() {

        const _this = this;
        _this._initMethods();

        _this._table = new gridjs.Grid({
            language: _this._language,
            className : _this._style,
            columns: _this._columns.map(col => col.getColumn()),
            ..._this._config,
            server: _this._server()
        })
        .render(document.getElementById(`${_this._id}`));

        if(!_this._useSearch) $(`#${_this._id} .gridjs-search`).css('display', 'none');     // 검색 기능 사용하지 않을 경우 -> 검색창 숨기기
        this._initListener();                                                               // 체크박스 관련된 이벤트 리스너 초기화

        return this
    }

    /**
     * 이벤트 리스너 초기화
     * */
    _initListener() {
        /* 전체 체크박스 이벤트 리스너 */
        setTimeout(() => {
            const el = document.querySelector('.table-checkbox-all');
            if (el) {
                el.addEventListener('click', e => {
                    e.stopPropagation();
                    const isChecked = el.checked;
                    el.closest('table')
                        ?.querySelectorAll('.table-checkbox-td')
                        ?.forEach(cb => (cb.checked = isChecked));
                });
            }

            /* 각 체크박스 이벤트 리스너 */
            document.querySelectorAll('.table-checkbox-td').forEach(tdCb => {
                tdCb.addEventListener('change', e => {
                    const table = tdCb.closest('table');
                    if (!table) return;

                    const allCheckboxes = table.querySelectorAll('.table-checkbox-td');
                    const checkedCheckboxes = Array.from(allCheckboxes).filter(cb => cb.checked);
                    console.log("checkedCheckboxes : ", checkedCheckboxes)

                    const selectedDataCount = this.getSelectData().length;
                    console.log("selectedDataCount : ", selectedDataCount)
                    const allCheckbox = table.querySelector('.table-checkbox-all');

                    if (allCheckbox) {
                        allCheckbox.checked = checkedCheckboxes.length === selectedDataCount;
                    }
                });
            });

        }, 1000);
    }

    /**
     * 체크박스 체크된 row 목록 리턴
     * */
    getSelectData() {
        const selected = []
        $(`#${this._id} .table-checkbox-td:checked`).each(function () {
           const uid = $(this).data('uid');
           if(uid !== undefined) selected.push(uid)
        });
        return selected;
    }

    /**
     * 테이블 재조회 (submit)
     * */
    submitTable() {
        this._table.updateConfig({
            server: this._server()
        }).forceRender();
        this._initListener();
    }
}

class Column {

    /**
     * 컬럼 생성자
     * */
    constructor(id, name, isCheckbox = false) {
        this._id = id;           // {table}영역에서 {data}와 매칭될 {id}값
        this._name = name;       // 보여질 텍스트명
        this._sort = 0;          // sort column => true / 0
        this._hidden = 0;        // show or hide column => true / 0
        this._width = null;      // 30% or 200px
        this._formatter = null;
        this._attributes = null;

        // 체크박스를 이용할 경우 {name} 값을 {html} 기능을 통해 {checkbox}로 초기화
        this._isCheckbox = isCheckbox;
        if(isCheckbox) {
            this._name = gridjs.html(`<input type="checkbox" name="${id}" id="${id}" class="form-check-input me-2 table-checkbox-all gridjs-custom-checkbox" >`)
            this._width = "5%"
        }
    }

    /**
     * 컬럼 소팅 기능 활성화
     * */
    sorting() {
        this._sort = true;
        return this;
    }

    /*
    * 특정 컬럼 숨기기
    * */
    hide() {
        this._hidden = true;
        return this;
    }

    /**
     * 컬럼 너비 설정
     * */
    width(val = "30%") {
        this._width = val;
        return this;
    }

    /***
     * formatter : (cell, row, column)
     *  @ cell : 해당 cell 데이터 값
     *  @ row  : cell에 해당되는 row 정보
     *           => { _id , cells }
     *               * _id : 해당 cell id 값
     *               * cells : row에 있는 cell 리스트 -> { _id, data }
     *  @ column : 현재 컬럼 정보
     * */
    formatter(callback) {
        this._formatter = callback;
        return this;
    }

    /***
     * (when) cell is not null 로 시작하기 (ex) if ( cell ) { ~ 로직 구성 ~ }
     * attributes : (cell, row, column)
     *  @ cell : 해당 cell 데이터 값
     *  @ row  : cell에 해당되는 row 정보
     *           => { _id , cells }
     *               * _id : 해당 cell id 값
     *               * cells : row에 있는 cell 리스트 -> { _id, data }
     *  @ column : 현재 컬럼 정보
     * */
    attributes(callback) {
        this._attributes = callback;
        return this;
    }

    /**
     * 테이블 초기화 시점에 컬럼 정보 가져오기 위한 메소드
     * => 컬럼 세팅
     * */
    getColumn() {
        const col = {
            id: this._id,
            name: this._name,
            sort: this._sort,
            hidden: this._hidden
        };
        if (this._width) col.width = this._width
        if (this._formatter) col.formatter = this._formatter;
        if (this._attributes) col.attributes = this._attributes;

        /*
        * 만일 체크박스 {row}라면 기본적으로 체크박스로 렌더링 되도록 세팅
        * */
        if(this._isCheckbox) {
            col.formatter = (cell, row, column) => {
                return gridjs.html(`
                    <input type="checkbox" class="form-check-input me-2 table-checkbox-td gridjs-custom-checkbox" name="${this._id}" data-uid="${cell}" />
                `);
            };
        }
        return col;
    }
}