layui.use(['table','form'], function () {
    var table = layui.table;
    var form = layui.form;
    table.render({
        elem: '#aboutTable'
        , id: 'aboutTable'
        , height: 'full-60'
        , url: '/sys/myabout/list' //数据接口
        , page: true //开启分页
        , limit: 15
        , cols: [[ //表头
            {field: 'id', checkbox: true}
            , {field: 'content', title: '内容',align: 'center'}
            , {field: 'insTime', title: '时间',align: 'center'}
        ]]
        , parseData: function (res) { //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": res.page.totalCount, //解析数据长度
                "data": res.page.list //解析数据列表
            };
        }
    });


    var $ = layui.$, active = {
        add: function () {
            openEdit();
        },
        delete: function () {
            var checkStatus = table.checkStatus('aboutTable')
                , data = checkStatus.data;
            if (data.length < 1) {
                return false;
            }
            var ids = [];
            for(var i in data){
                ids.push(data[i].id)
            }
            deleteIt(ids);
        }
    };

    $('.search-width .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    var openEdit = function (id) {
        $("#makerdownText").val("");
        getEditorMd();
        $("#dataTable").hide();
        $("#detail").show();
    }

    window.backTable = function (id) {
        $("#dataTable").show();
        $("#detail").hide();
    }

    window.saveDetail= function (content) {
        var myAbout={
            content:content
        }
        $.ajax({
            type: "POST",
            url: "/sys/myabout/save",
            contentType: "application/json",
            data: JSON.stringify(myAbout),
            success: function(r){
                if(r.code === 0){
                    layer.msg("操作成功", {icon: 1},function () {
                        table.reload("aboutTable");
                        backTable();
                    });
                }else{
                    layer.alert(r.msg);
                }
            }
        });
    }

    var deleteIt = function (ids) {
        layer.confirm('确定删除选中的内容？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                type: "POST",
                url: "/sys/myabout/delete",
                data: JSON.stringify(ids),
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        layer.msg("操作成功", {icon: 1},function () {
                            table.reload("aboutTable");
                        });
                    } else {
                        layer.alert(r.msg);
                    }
                }
            });
        }, function () {
        });
    };


});


/*
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/myabout/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '内容', name: 'content', index: 'content', width: 80 }, 			
			{ label: '时间', name: 'insTime', index: 'ins_time', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		myAbout: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.myAbout = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.myAbout.id == null ? "sys/myabout/save" : "sys/myabout/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.myAbout),
                    success: function(r){
                        if(r.code === 0){
                             layer.msg("操作成功", {icon: 1});
                             vm.reload();
                             $('#btnSaveOrUpdate').button('reset');
                             $('#btnSaveOrUpdate').dequeue();
                        }else{
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
               if(!lock) {
                    lock = true;
		            $.ajax({
                        type: "POST",
                        url: baseURL + "sys/myabout/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function(r){
                            if(r.code == 0){
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            }else{
                                layer.alert(r.msg);
                            }
                        }
				    });
			    }
             }, function(){
             });
		},
		getInfo: function(id){
			$.get(baseURL + "sys/myabout/info/"+id, function(r){
                vm.myAbout = r.myAbout;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});*/
