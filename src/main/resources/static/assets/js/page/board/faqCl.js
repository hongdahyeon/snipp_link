class FaqCl {

    constructor(id) {
        this._id = id;
        this._data = [];
    }

    data(data = []) {
        this._data = data;
        return this;
    }

    draw() {
        const dom = $(`#${this._id}`);
        dom.empty();

        const createTree = (nodes) => {
            let html = '<ul class="list-group list-group-flush category-list">';
            for (const node of nodes) {
                html += `
                <li class="list-group-item pb-3">
                    <strong class="parent" data-uid="${node['no']}">${node['text']}</strong>
                    ${node.children && node.children.length > 0 ? createChildren(node.children) : ''}
                </li>
            `;
            }
            html += '</ul>';
            return html;
        };

        // 자식 노드들 재귀 렌더링
        const createChildren = (children) => {
            let html = '<ul class="mt-2 ps-3">';
            for (const child of children) {
                html += `
                <li class="mb-2">
                    <a href="#" class="text-muted" data-uid="${child['no']}" data-upper="${child['upperNo']}">${child.text}</a>
                    ${child.children && child.children.length > 0 ? createChildren(child.children) : ''}
                </li>
            `;
            }
            html += '</ul>';
            return html;
        };
        const html = createTree(this._data);
        dom.html(html);
    }

}