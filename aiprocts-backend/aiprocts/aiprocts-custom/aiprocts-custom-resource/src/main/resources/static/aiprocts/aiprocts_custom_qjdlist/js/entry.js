(function() {
    var API_BASE = '/msService/aiprocts/qjdst';
    var MODEL_CODE = 'aiproctsQjdst';
    var MODULE_CODE = 'aiprocts';
    
    var pageInfo = {
        pageNo: 1,
        pageSize: 20,
        total: 0
    };

    function init() {
        console.log('[entry.js] init() 开始执行');
        loadTableData();
        bindEvents();
    }

    function bindEvents() {
        console.log('[entry.js] bindEvents() 开始执行');
        
        var queryBtn = document.querySelector('.quick_search_btnGroup .ant-btn-primary');
        console.log('[entry.js] 查询按钮元素:', queryBtn);
        if (queryBtn) {
            queryBtn.addEventListener('click', function(e) {
                console.log('[entry.js] 查询按钮被点击');
                e.preventDefault();
                pageInfo.pageNo = 1;
                loadTableData();
            });
        } else {
            console.warn('[entry.js] 未找到查询按钮，尝试其他选择器');
            var allPrimaryBtns = document.querySelectorAll('.ant-btn-primary');
            console.log('[entry.js] 页面上所有ant-btn-primary按钮:', allPrimaryBtns);
        }

        var clearBtn = document.querySelector('.quick_search_btnGroup .ant-btn-default');
        console.log('[entry.js] 清空按钮元素:', clearBtn);
        if (clearBtn) {
            clearBtn.addEventListener('click', function(e) {
                console.log('[entry.js] 清空按钮被点击');
                e.preventDefault();
                clearSearchForm();
                pageInfo.pageNo = 1;
                loadTableData();
            });
        }

        var addBtn = document.querySelector('button[code="button.0004"]');
        console.log('[entry.js] 发起请假按钮元素:', addBtn);
        if (addBtn) {
            addBtn.addEventListener('click', function(e) {
                e.preventDefault();
                handleAdd();
            });
        }

        bindPaginationEvents();
        bindTableButtonEvents();
    }

    function bindPaginationEvents() {
        var pagination = document.querySelector('.ant-pagination');
        if (!pagination) return;

        var prevBtn = pagination.querySelector('.ant-pagination-prev');
        var nextBtn = pagination.querySelector('.ant-pagination-next');
        var pageItems = pagination.querySelectorAll('.ant-pagination-item');

        if (prevBtn) {
            prevBtn.addEventListener('click', function() {
                if (!this.classList.contains('ant-pagination-disabled')) {
                    pageInfo.pageNo--;
                    loadTableData();
                }
            });
        }

        if (nextBtn) {
            nextBtn.addEventListener('click', function() {
                if (!this.classList.contains('ant-pagination-disabled')) {
                    pageInfo.pageNo++;
                    loadTableData();
                }
            });
        }

        pageItems.forEach(function(item) {
            item.addEventListener('click', function() {
                var page = parseInt(this.querySelector('a').textContent);
                pageInfo.pageNo = page;
                loadTableData();
            });
        });

        var pageSizeSelect = pagination.querySelector('.ant-select');
        if (pageSizeSelect) {
            pageSizeSelect.addEventListener('click', function() {
                var dropdown = document.querySelector('.ant-select-dropdown:not(.ant-select-dropdown-hidden)');
                if (dropdown) {
                    var options = dropdown.querySelectorAll('.ant-select-item');
                    options.forEach(function(opt) {
                        opt.addEventListener('click', function() {
                            var size = parseInt(this.textContent);
                            pageInfo.pageSize = size;
                            pageInfo.pageNo = 1;
                            loadTableData();
                        });
                    });
                }
            });
        }
    }

    function bindTableButtonEvents() {
        document.addEventListener('click', function(e) {
            var target = e.target;
            var btn = target.closest('button[code="button.0005"]');
            if (btn) {
                e.preventDefault();
                var rowData = getRowData(btn);
                handleEdit(rowData);
            }

            btn = target.closest('button[code="button.0006"]');
            if (btn) {
                e.preventDefault();
                var rowData = getRowData(btn);
                handleDelete(rowData);
            }
        });
    }

    function getRowData(btn) {
        var row = btn.closest('.ant-table-row');
        if (!row) return null;
        
        var cells = row.querySelectorAll('.ant-table-cell');
        return {
            id: row.getAttribute('data-row-key'),
            name: cells[1] ? cells[1].querySelector('.cds-table-cell-wrap span')?.textContent : '',
            num: cells[2] ? cells[2].querySelector('.cds-table-cell-wrap span')?.textContent : '',
            optime: cells[3] ? cells[3].querySelector('.cds-table-cell-wrap span')?.textContent : '',
            gsnum: cells[4] ? cells[4].querySelector('.cds-table-cell-wrap span')?.textContent : '',
            starttime: cells[5] ? cells[5].querySelector('.cds-table-cell-wrap span')?.textContent : '',
            endtime: cells[6] ? cells[6].querySelector('.cds-table-cell-wrap span')?.textContent : ''
        };
    }

    function buildQueryParams() {
        console.log('[entry.js] buildQueryParams() 开始构建查询参数');
        var entity = {};
        var searchBlocks = document.querySelectorAll('.cds-runtime-search-block');
        console.log('[entry.js] 找到搜索块数量:', searchBlocks.length);
        
        searchBlocks.forEach(function(block, idx) {
            var labelText = block.querySelector('.labelText');
            if (!labelText) {
                console.log('[entry.js] 搜索块', idx, '没有labelText');
                return;
            }
            
            var fieldName = labelText.textContent.trim();
            console.log('[entry.js] 搜索块', idx, '字段名:', fieldName);
            var keywordDiv = block.querySelector('.keyword');
            if (!keywordDiv) {
                console.log('[entry.js] 搜索块', idx, '没有keyword div');
                return;
            }

            if (fieldName === '姓名') {
                var input = keywordDiv.querySelector('input[type="text"]');
                console.log('[entry.js] 姓名输入框:', input, '值:', input?.value);
                if (input && input.value) {
                    entity.name = {
                        associatedPropertyShowCode: null,
                        fieldType: 'Character',
                        express: 'LIKE',
                        value: input.value,
                        ignoreCase: true
                    };
                }
            } else if (fieldName === '工号') {
                var input = keywordDiv.querySelector('input[type="text"]');
                console.log('[entry.js] 工号输入框:', input, '值:', input?.value);
                if (input && input.value) {
                    entity.num = {
                        associatedPropertyShowCode: null,
                        fieldType: 'Character',
                        express: 'LIKE',
                        value: input.value,
                        ignoreCase: true
                    };
                }
            } else if (fieldName === '入职时间') {
                var pickerInputs = keywordDiv.querySelectorAll('.ant-picker input');
                console.log('[entry.js] 入职时间选择器数量:', pickerInputs.length);
                var startVal = pickerInputs[0]?.value;
                var endVal = pickerInputs[1]?.value;
                console.log('[entry.js] 入职时间 - 开始:', startVal, '结束:', endVal);
                if (startVal) {
                    entity.optime = {
                        associatedPropertyShowCode: null,
                        fieldType: 'DateTime',
                        express: 'GE',
                        value: convertToTimestamp(startVal)
                    };
                }
                if (endVal) {
                    entity['_optime'] = {
                        associatedPropertyShowCode: null,
                        fieldType: 'DateTime',
                        express: 'LE',
                        value: convertToTimestamp(endVal)
                    };
                }
            } else if (fieldName === '可用假期') {
                var numInputs = keywordDiv.querySelectorAll('.ant-input-number-input');
                console.log('[entry.js] 可用假期输入框数量:', numInputs.length);
                var startVal = numInputs[0]?.value;
                var endVal = numInputs[1]?.value;
                console.log('[entry.js] 可用假期 - 开始:', startVal, '结束:', endVal);
                if (startVal) {
                    entity.gsnum = {
                        associatedPropertyShowCode: null,
                        fieldType: 'Decimal',
                        express: 'GE',
                        value: parseFloat(startVal)
                    };
                }
                if (endVal) {
                    entity['_gsnum'] = {
                        associatedPropertyShowCode: null,
                        fieldType: 'Decimal',
                        express: 'LE',
                        value: parseFloat(endVal)
                    };
                }
            } else if (fieldName === '请假开始时间') {
                var pickerInputs = keywordDiv.querySelectorAll('.ant-picker input');
                console.log('[entry.js] 请假开始时间选择器数量:', pickerInputs.length);
                var startVal = pickerInputs[0]?.value;
                var endVal = pickerInputs[1]?.value;
                console.log('[entry.js] 请假开始时间 - 开始:', startVal, '结束:', endVal);
                if (startVal) {
                    entity.starttime = {
                        associatedPropertyShowCode: null,
                        fieldType: 'DateTime',
                        express: 'GE',
                        value: convertToTimestamp(startVal)
                    };
                }
                if (endVal) {
                    entity['_starttime'] = {
                        associatedPropertyShowCode: null,
                        fieldType: 'DateTime',
                        express: 'LE',
                        value: convertToTimestamp(endVal)
                    };
                }
            } else if (fieldName === '请假结束时间') {
                var pickerInputs = keywordDiv.querySelectorAll('.ant-picker input');
                console.log('[entry.js] 请假结束时间选择器数量:', pickerInputs.length);
                var startVal = pickerInputs[0]?.value;
                var endVal = pickerInputs[1]?.value;
                console.log('[entry.js] 请假结束时间 - 开始:', startVal, '结束:', endVal);
                if (startVal) {
                    entity.endtime = {
                        associatedPropertyShowCode: null,
                        fieldType: 'DateTime',
                        express: 'GE',
                        value: convertToTimestamp(startVal)
                    };
                }
                if (endVal) {
                    entity['_endtime'] = {
                        associatedPropertyShowCode: null,
                        fieldType: 'DateTime',
                        express: 'LE',
                        value: convertToTimestamp(endVal)
                    };
                }
            }
        });

        var params = {
            entity: entity,
            isTotalSum: false,
            paging: true,
            pageNo: pageInfo.pageNo,
            pageSize: pageInfo.pageSize,
            ordering: false,
            queryDetails: false
        };
        console.log('[entry.js] 构建的查询参数:', JSON.stringify(params, null, 2));
        return params;
    }

    function convertToTimestamp(dateStr) {
        if (!dateStr) return null;
        var date = new Date(dateStr);
        if (isNaN(date.getTime())) {
            date = new Date(dateStr.replace(/-/g, '/'));
        }
        return date.getTime();
    }

    function getTicket() {
        var ticket = localStorage.getItem('ticket') || '';
        console.log('[entry.js] 从localStorage获取ticket:', ticket ? '已获取' : '未找到');
        return ticket;
    }

    function loadTableData() {
        console.log('[entry.js] loadTableData() 开始执行');
        var params = buildQueryParams();
        var ticket = getTicket();
        
        var url = API_BASE + '/page';
        console.log('[entry.js] 请求URL:', url);
        console.log('[entry.js] 请求参数:', params);
        
        var xhr = new XMLHttpRequest();
        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.setRequestHeader('x-nebule-model-code', MODEL_CODE);
        xhr.setRequestHeader('x-nebule-module-code', MODULE_CODE);
        if (ticket) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + ticket);
        }
        
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                console.log('[entry.js] 请求完成, 状态码:', xhr.status);
                console.log('[entry.js] 响应头:', xhr.getAllResponseHeaders());
                console.log('[entry.js] 响应内容:', xhr.responseText);
                if (xhr.status === 200) {
                    try {
                        var response = JSON.parse(xhr.responseText);
                        console.log('[entry.js] 解析后的响应:', response);
                        if (response.code === 100000000) {
                            console.log('[entry.js] 查询成功, 数据条数:', response.data?.records?.length);
                            renderTable(response.data);
                            updatePagination(response.data);
                        } else {
                            console.error('[entry.js] 查询失败, code:', response.code, 'message:', response.message);
                        }
                    } catch (e) {
                        console.error('[entry.js] 解析响应失败:', e);
                        console.error('[entry.js] 原始响应文本:', xhr.responseText);
                    }
                } else {
                    console.error('[entry.js] 请求失败, HTTP状态码:', xhr.status);
                    console.error('[entry.js] 响应文本:', xhr.responseText);
                }
            }
        };
        
        xhr.onerror = function() {
            console.error('[entry.js] 请求发生网络错误');
        };
        
        xhr.ontimeout = function() {
            console.error('[entry.js] 请求超时');
        };
        
        console.log('[entry.js] 发送请求...');
        xhr.send(JSON.stringify(params));
    }

    function renderTable(data) {
        console.log('[entry.js] renderTable() 开始渲染, 数据:', data);
        var records = data.records || [];
        var tbody = document.querySelector('.ant-table-tbody-virtual-holder-inner');
        console.log('[entry.js] 表格tbody元素:', tbody);
        if (!tbody) {
            console.error('[entry.js] 未找到表格tbody元素');
            return;
        }

        tbody.innerHTML = '';

        if (records.length === 0) {
            console.log('[entry.js] 没有数据，显示空状态');
            renderEmptyState(tbody);
            return;
        }

        console.log('[entry.js] 渲染', records.length, '条数据');
        records.forEach(function(record, index) {
            var row = document.createElement('div');
            row.className = 'ant-table-row';
            row.setAttribute('data-row-key', record.id);
            row.setAttribute('columns', '[object Object],[object Object],[object Object],[object Object],[object Object],[object Object],[object Object]');
            row.setAttribute('mergecolinfo', '[object Object]');
            row.setAttribute('rowindex', index);
            row.setAttribute('tablecellattrs', '[object Object]');
            row.setAttribute('operations', '[object Object],[object Object]');
            row.style.width = '838px';

            var rowNum = (pageInfo.pageNo - 1) * pageInfo.pageSize + index + 1;

            row.innerHTML = buildRowCells(record, rowNum);
            tbody.appendChild(row);
        });
        console.log('[entry.js] 表格渲染完成');
    }

    function buildRowCells(record, rowNum) {
        return `
            <div class="ant-table-cell serial_number_cell ant-table-cell-fix-left ant-table-cell-fix-left-last ant-table-cell-ellipsis"
                 data-index-key="index"
                 style="position: sticky; left: 0px; text-align: center; flex: 0 0 44px; width: 44px; margin-right: 0px; pointer-events: auto;">
                <span class="ant-table-cell-content">${rowNum}</span>
            </div>
            <div class="ant-table-cell ant-table-cell-ellipsis"
                 data-index-key="name"
                 data-component-name="Input"
                 style="text-align: left; flex: 0 0 100px; width: 100px; margin-right: 0px; pointer-events: auto;">
                <div class="cell-error"><span class="cell-error-text"></span></div>
                <div class="cell-error-box"></div>
                <div class="cds-table-cell-wrap"><span>${escapeHtml(record.name || '')}</span></div>
            </div>
            <div class="ant-table-cell ant-table-cell-ellipsis"
                 data-index-key="num"
                 data-component-name="Input"
                 style="text-align: left; flex: 0 0 100px; width: 100px; margin-right: 0px; pointer-events: auto;">
                <div class="cell-error"><span class="cell-error-text"></span></div>
                <div class="cell-error-box"></div>
                <div class="cds-table-cell-wrap"><span>${escapeHtml(record.num || '')}</span></div>
            </div>
            <div class="ant-table-cell ant-table-cell-ellipsis"
                 data-index-key="optime"
                 data-component-name="DateTimePicker"
                 style="text-align: left; flex: 0 0 120px; width: 120px; margin-right: 0px; pointer-events: auto;">
                <div class="cell-error"><span class="cell-error-text"></span></div>
                <div class="cell-error-box"></div>
                <div class="cds-table-cell-wrap"><span>${formatDateTime(record.optime)}</span></div>
            </div>
            <div class="ant-table-cell ant-table-cell-ellipsis"
                 data-index-key="gsnum"
                 data-component-name="InputNumber"
                 style="text-align: right; flex: 0 0 100px; width: 100px; margin-right: 0px; pointer-events: auto;">
                <div class="cell-error"><span class="cell-error-text"></span></div>
                <div class="cell-error-box"></div>
                <div class="cds-table-cell-wrap"><span>${formatNumber(record.gsnum)}</span></div>
            </div>
            <div class="ant-table-cell ant-table-cell-ellipsis"
                 data-index-key="starttime"
                 data-component-name="DateTimePicker"
                 style="text-align: left; flex: 0 0 120px; width: 120px; margin-right: 0px; pointer-events: auto;">
                <div class="cell-error"><span class="cell-error-text"></span></div>
                <div class="cell-error-box"></div>
                <div class="cds-table-cell-wrap"><span>${formatDateTime(record.starttime)}</span></div>
            </div>
            <div class="ant-table-cell ant-table-cell-ellipsis"
                 data-index-key="endtime"
                 data-component-name="DateTimePicker"
                 style="text-align: left; flex: 0 0 120px; width: 120px; margin-right: 0px; pointer-events: auto;">
                <div class="cell-error"><span class="cell-error-text"></span></div>
                <div class="cell-error-box"></div>
                <div class="cds-table-cell-wrap"><span>${formatDateTime(record.endtime)}</span></div>
            </div>
            <div class="ant-table-cell option_btns_cell ant-table-cell-fix-right-first ant-table-cell-fix-right ant-table-cell-fix-right-first"
                 style="position: sticky; right: 38px; flex: 0 0 96px; width: 96px; margin-right: 0px; pointer-events: auto;">
                <span class="col-group-buttons">
                    <button code="button.0005" limit="none" title="修改" customtype="link" type="button"
                            class="ant-btn css-ay9nb7 ant-btn-link undefined cds_button">
                        <span>修改</span>
                    </button>
                    <button code="button.0006" limit="none" title="删除" customtype="textDanger" type="button"
                            class="ant-btn css-ay9nb7 ant-btn-link ant-btn-dangerous undefined cds_button">
                        <span>删除</span>
                    </button>
                </span>
            </div>
            <div class="ant-table-cell setting-col ant-table-cell-fix-right ant-table-cell-ellipsis"
                 data-index-key="setting-btn"
                 style="position: sticky; right: 0px; flex: 0 0 38px; width: 38px; margin-right: 0px; pointer-events: auto;"></div>
        `;
    }

    function renderEmptyState(tbody) {
        var emptyDiv = document.createElement('div');
        emptyDiv.className = 'ant-empty ant-empty-normal';
        emptyDiv.innerHTML = `
            <div class="ant-empty-image">
                <svg width="64" height="41" viewBox="0 0 64 41" xmlns="http://www.w3.org/2000/svg">
                    <g fill="none" fill-rule="evenodd">
                        <ellipse fill="#F5F5F5" cx="32" cy="33" rx="32" ry="7"/>
                        <g fill-rule="nonzero" stroke="#D9D9D9">
                            <path d="M55 12.76L44.854 1.258C44.367.474 43.656 0 42.907 0H21.093c-.749 0-1.46.474-1.947 1.257L9 12.761V22h46v-9.24z"/>
                            <path d="M41.613 15.931c0-1.605.994-2.93 2.227-2.931H55v18.137C55 33.26 53.68 35 52.05 35H11.95C10.32 35 9 33.259 9 31.137V13h11.16c1.233 0 2.227 1.323 2.227 2.928v.022c0 1.605 1.005 2.901 2.237 2.901h14.752c1.232 0 2.237-1.308 2.237-2.913v-.007z" fill="#FAFAFA"/>
                        </g>
                    </g>
                </svg>
            </div>
            <p class="ant-empty-description">暂无数据</p>
        `;
        tbody.appendChild(emptyDiv);
    }

    function updatePagination(data) {
        pageInfo.total = data.total || 0;
        var totalPages = Math.ceil(pageInfo.total / pageInfo.pageSize) || 1;

        var pagination = document.querySelector('.ant-pagination');
        if (!pagination) return;

        var totalText = pagination.querySelector('.ant-pagination-total-text');
        if (totalText) {
            totalText.textContent = '共 ' + pageInfo.total + ' 条 第 ' + pageInfo.pageNo + '/' + totalPages + ' 页';
        }

        var prevBtn = pagination.querySelector('.ant-pagination-prev');
        var nextBtn = pagination.querySelector('.ant-pagination-next');

        if (prevBtn) {
            if (pageInfo.pageNo <= 1) {
                prevBtn.classList.add('ant-pagination-disabled');
                prevBtn.setAttribute('aria-disabled', 'true');
            } else {
                prevBtn.classList.remove('ant-pagination-disabled');
                prevBtn.setAttribute('aria-disabled', 'false');
            }
        }

        if (nextBtn) {
            if (pageInfo.pageNo >= totalPages) {
                nextBtn.classList.add('ant-pagination-disabled');
                nextBtn.setAttribute('aria-disabled', 'true');
            } else {
                nextBtn.classList.remove('ant-pagination-disabled');
                nextBtn.setAttribute('aria-disabled', 'false');
            }
        }

        var pageItems = pagination.querySelectorAll('.ant-pagination-item');
        pageItems.forEach(function(item, idx) {
            if (idx === 0) {
                item.querySelector('a').textContent = pageInfo.pageNo;
                if (pageInfo.pageNo === parseInt(item.querySelector('a').textContent)) {
                    item.classList.add('ant-pagination-item-active');
                } else {
                    item.classList.remove('ant-pagination-item-active');
                }
            }
        });
    }

    function clearSearchForm() {
        console.log('[entry.js] clearSearchForm() 清空搜索表单');
        var inputs = document.querySelectorAll('.cds-runtime-search input');
        inputs.forEach(function(input) {
            if (input.type !== 'search') {
                input.value = '';
            }
        });
    }

    function formatDateTime(timestamp) {
        if (!timestamp) return '';
        var date = new Date(timestamp);
        if (isNaN(date.getTime())) return '';
        
        var year = date.getFullYear();
        var month = String(date.getMonth() + 1).padStart(2, '0');
        var day = String(date.getDate()).padStart(2, '0');
        var hours = String(date.getHours()).padStart(2, '0');
        var minutes = String(date.getMinutes()).padStart(2, '0');
        var seconds = String(date.getSeconds()).padStart(2, '0');
        
        return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
    }

    function formatNumber(num) {
        if (num === null || num === undefined) return '';
        return parseFloat(num).toFixed(2);
    }

    function escapeHtml(str) {
        if (!str) return '';
        var div = document.createElement('div');
        div.textContent = str;
        return div.innerHTML;
    }

    function handleAdd() {
        var editUrl = '../aiprocts_custom_qjdadd/qjdadd.html';
        window.location.href = editUrl;
    }

    function handleEdit(rowData) {
        if (!rowData || !rowData.id) return;
        var editUrl = '../aiprocts_custom_qjdedit/qjdedit.html?id=' + rowData.id;
        window.location.href = editUrl;
    }

    function handleDelete(rowData) {
        if (!rowData || !rowData.id) return;
        if (!confirm('确定要删除该记录吗？')) return;

        var ticket = getTicket();
        console.log('[entry.js] 删除记录, id:', rowData.id);
        var xhr = new XMLHttpRequest();
        xhr.open('GET', API_BASE + '/delete?id=' + rowData.id, true);
        xhr.setRequestHeader('x-nebule-model-code', MODEL_CODE);
        xhr.setRequestHeader('x-nebule-module-code', MODULE_CODE);
        if (ticket) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + ticket);
        }
        
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                console.log('[entry.js] 删除响应, 状态码:', xhr.status, '响应:', xhr.responseText);
                if (xhr.status === 200) {
                    try {
                        var response = JSON.parse(xhr.responseText);
                        if (response.code === 100000000) {
                            alert('删除成功');
                            loadTableData();
                        } else {
                            alert('删除失败: ' + response.message);
                        }
                    } catch (e) {
                        alert('删除失败');
                    }
                } else {
                    alert('删除失败');
                }
            }
        };
        
        xhr.send();
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
})();
