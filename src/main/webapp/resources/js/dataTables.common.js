/**
 * Created by Administrator on 2016/12/23.
 */
$.extend( $.fn.dataTable.defaults, {
    searching: false,
    paging: false,
    info: false,
    bSort: false,
    autoWidth: false,
    processing: true,
    deferRender: true,
    ordering: false,
    language: {
        sZeroRecords: '您暂未填报数据!'
    },
    /*column: {
        defaultContent: '',
        render: $.fn.dataTable.render.number(',')
    },*/
    // shenq
    createdRow: function(row, data, dataIndex){
        if(data.dataType==0){
            $(row).find('td').removeClass('details-control');
        }
    }
} );