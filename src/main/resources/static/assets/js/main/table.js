class GridTable {

    /*
    * @출처 : https://gridjs.io/docs/examples/html-cells
    * */
    constructor(id) {
        this._id = id;
        this._table = null;
        this._index = 0

        // 1. 언어 세팅
        this._language = null
        this._langs();

        // 2. 테이블 커스텀 세팅 + 페이징
        this._searchKeyword = null;
        this._useSearch = false;
        this._config = null;
        this.resizable = true;
        this.sort = true;
        this._configSetting();

        // 3. style 세팅
        this._style = null;
        this._styleSetting();

        // 4. server
        this._url = ''

        this._columns = []
    }

    get(url) {
        this._url = url
        return this;
    }

    search(keyword) {
        this._useSearch = true;
        this._searchKeyword = keyword;
        return this;
    }

    _langs() {
        this._language = {
            'search': {
                placeholder: '🔍 검색어를 입력하세요..'
            },
            sort: {
                sortAsc: '🔼오름차순 정렬',
                sortDesc: '🔽내림차순 정렬',
            },
            pagination: {
                previous: '이전',
                next: '다음',
                navigate: (page, pages) => `Page ${page} of ${pages}`,
                page: (page) => `Page ${page}`,
                showing: '조회 결과',
                of: '건중',
                to: '-',
                results: '레코드',
            },
            loading: '로딩중...',
            noRecordsFound: '😢 조회 결과가 없습니다.',
            error: '😢 데이터 조회 도중 오류가 발생했습니다.',
        }
    }

    _configSetting() {

        this._config = {
            search: this._search(),
            pagination: this._paging(),
            resizable: this.resizable,
            sort: this.sort
        }
    }

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

    _paging() {
        return {
            enabled: true,
            limit: 10,                  // rows per page
            page: 1,                    // initial page
            summary: true,              // show/hide the pagination summary
            nextButton: true,           // show/hide the next button
            prevButton: true,           // show/hide the prev button
            buttonsCount: 5,            // number of buttons to display
            resetPageOnUpdate: true,    // reset the pagination when table is updated
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

    _styleSetting() {
        this._style = {
            table: {
                'border-collapse': 'collapse',
                'width': '100%',
                'font-size': '14px',
                'border': '1px solid #ddd',
                'border-radius': '8px',
                'overflow': 'hidden',
                'box-shadow': '0 2px 10px rgba(0,0,0,0.05)',
            },
            th: {
                'background-color': '#f5f7fa',
                'color': '#333',
                'padding': '12px 10px',
                'border-bottom': '1px solid #e0e0e0',
                'text-align': 'center',
                'font-weight': '600',
            },
            td: {
                'padding': '10px',
                'text-align': 'center',
                'border-bottom': '1px solid #f0f0f0',
                'color': '#555',
            },
            header: {
                'background-color': '#f5f7fa',
                'border-bottom': '2px solid #ccc',
            },
            footer: {
                'background-color': '#fafafa',
                'color': '#666',
                'font-size': '13px',
                'padding': '10px',
                'border-top': '1px solid #eee',
            }
        }
    }

    add(column) {
        if(column instanceof Column) {
           this._columns.push(column)
        }
        return this
    }

    init() {

        const _this = this;

        _this._table = new gridjs.Grid({
            language: _this._language,
            style: _this._style,
            columns: [
                {
                    name: 'uid',
                    id: 'uid'
                },
                {
                    name: 'userNm',
                    id: 'userNm'
                },
                {
                    name: 'userEmail',
                    id: 'userEmail',
                    formatter: (cell, row, col, _rowData) => {
                        console.log("cell: ", cell)
                        console.log("row: ", row)
                        console.log("col: ", col)
                        console.log("_rowData: ", _rowData)
                        return gridjs.html(`<a href="#">${cell}</a>`);
                    }
                }
            ],
            ..._this._config,
            server: {
                url: _this._url,
                method: 'GET',
                then: data => data.list,
                total: (data) => data['totalElements']
            }
        }).render(document.getElementById(`${_this._id}`));

        if(!_this._useSearch) $(`#${_this._id} .gridjs-search`).css('display', 'none');

        return this
    }


}

class Column {

    constructor(id, name) {
        this.id = id;           // table영역에서 data와 매칭될 id값
        this.name = name;       // 보여질 텍스트명
        this._formatter = null;
    }

    getColumn() {
        const obj = {
            id: this.id,
            name: this.name,
            formatter: this._formatter
        }
        return this;
    }

    formatter(callback) {
        this._formatter = callback;
        return this
    }
}