layui.config({
    base: '/statics/plugins/layui/',
})
layui.use(['treeTable', 'layer', 'code', 'form'], function () {
    var o = layui.$,
        form = layui.form,
        layer = layui.layer,
        treeTable = layui.treeTable;
    var re = treeTable.render({
        elem: '#tree-table',
        url: '/sys/mylabel/getAll',
        icon_key: 'labelName',
        is_checkbox: false,
        primary_key: 'id',
        parent_key: 'parentId',
        end: function (e) {
            form.render();
        },
        cols: [
            {
                key: 'labelName',
                title: '名称',
                width: '40%',
                template: function (item) {
                    if (item.tabGrade == 1) {
                        return '<span style="color:#000000;">' + item.labelName + '</span>';
                    } else if (item.tabGrade == 2) {
                        return '<span style="color:#929090;">' + item.labelName + '</span>';
                    }
                }
            },
            {
                key: 'parentName',
                title: '上级菜单',
                align: 'center',
                width: '15%',
                template: function (item) {
                    if (item.parentName) {
                        return item.parentName;
                    } else {
                        return '';
                    }
                }
            },
            {
                key: 'tabGrade',
                title: '类型',
                align: 'center',
                width: '30%',
                template: function (item) {
                    if (item.tabGrade == 1) {
                        return '<span class="layui-badge layui-bg-blue">标题栏</span>';
                    } else {
                        return '<span class="layui-badge layui-bg-green">文章分类</span>';
                    }
                }
            },
            {
                title: '操作',
                align: 'center',
                width: '15%',
                template: function (item) {
                    if (item.tabGrade == 1) {
                        return '<a lay-filter="add">添加</a> | <a lay-filter="update">编辑</a> | <a lay-filter="delete">删除</a>';
                    } else {
                        return '<a lay-filter="update">编辑</a> | <a lay-filter="delete">删除</a>';
                    }

                }
            }
        ]
    });
    /**
     * 监听自定义
     * 新增
     */
    treeTable.on('tree(add)', function (data) {
        var item = data.item;
        var tabGradeName = '文章分类';
        var tabGrade = 2;
        var url = 'label';
        if (item.id == 1) {
            $("#bannerDiv").hide();
            tabGradeName = '标题栏';
            tabGrade = 1;
            url = '';
        }
        form.val("myLabel", {
            id: "",
            labelName: "",
            parentName: item.labelName,
            parentId: item.id,
            tabGradeName: tabGradeName,
            tabGrade: tabGrade,
            url: url
        })
        form.render();
        $("#tree-table").hide();
        $("#detail").show();
    });
    /**
     * 监听自定义
     * 修改
     */
    treeTable.on('tree(update)', function (data) {
        var item = data.item;
        var tabGradeName = '文章分类';
        if (item.tabGrade == 1) {
            $("#bannerDiv").hide();
            tabGradeName = '标题栏';
        }
        form.val("myLabel", {
            id: item.id,
            labelName: item.labelName,
            parentName: item.parentName ? item.parentName : " ",
            tabGradeName: tabGradeName,
            tabGrade: item.tabGrade,
            url: item.url
        })
        form.render();
        $("#tree-table").hide();
        $("#detail").show();
    });
    /**
     * 监听自定义
     * 删除
     */
    treeTable.on('tree(delete)', function (data) {
        var ids = [];
        var msg = '';
        if(data.item.tabGrade == 1){
            msg = '标题栏不可删除！';
        }
        if(data.item.blogNum > 0){
            msg = '该分类下还有博客，暂不可删除！';
        }
        layer.confirm('确定要删除选中的记录？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            if(data.item.tabGrade != 1 && data.item.blogNum == 0){
                ids.push(data.item.id);
                $.ajax({
                    type: "POST",
                    url: "/sys/mylabel/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            layer.msg("操作成功", {icon: 1});
                            treeTable.render(re);
                        } else {
                            layer.alert(r.msg);
                        }
                    }
                });
            }else{
                layer.alert(msg);
            }

        }, function () {
        });
    });
    window.backDlog = function () {
        // 刷新重载树表（一般在异步处理数据后刷新显示）
        treeTable.render(re);
        $("#tree-table").show();
        $("#detail").hide();
        $("#bannerDiv").show();
    }

    form.on('submit(formSubmit)', function (data) {
        var myLabel = data.field;
        delete myLabel["parentName"];
        if(!myLabel.id){
            delete myLabel["id"];
        }
        var url = myLabel.id == null ? "/sys/mylabel/save" : "/sys/mylabel/update";
        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json",
            data: JSON.stringify(myLabel),
            success: function (r) {
                if (r.code === 0) {
                    layer.msg("操作成功！", {icon: 1}, function () {
                        backDlog();
                    });
                } else {
                    layer.alert(r.msg);
                }
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

    new AjaxUpload('#upload', {
        action: "/sys/oss/upload",
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png)$/.test(extension.toLowerCase()))) {
                layer.alert('只支持jpg、png、jpeg格式的图片！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r.code == 0) {
                $("#background").attr("src", r.url);
                $("#background").show();
                $("#backgroundInput").val(r.url);
            } else {
                layer.alert(r.msg);
            }
        }
    });
})


/*
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/mylabel/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '父Id(未0则为父标签)', name: 'parentId', index: 'parent_id', width: 80 }, 			
			{ label: '标签等级：1标题栏，2文章类型', name: 'tabGrade', index: 'tab_grade', width: 80 }, 			
			{ label: '标签名称', name: 'labelName', index: 'label_name', width: 80 }, 			
			{ label: '简介', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: '图片背景', name: 'background', index: 'background', width: 80 }, 			
			{ label: '新增人', name: 'insUser', index: 'ins_user', width: 80 }, 			
			{ label: '新增时间', name: 'insTime', index: 'ins_time', width: 80 }, 			
			{ label: '顺序', name: 'sort', index: 'sort', width: 80 }, 			
			{ label: '地址', name: 'url', index: 'url', width: 80 }			
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
		myLabel: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.myLabel = {};
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
                var url = vm.myLabel.id == null ? "sys/mylabel/save" : "sys/mylabel/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.myLabel),
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
                        url: baseURL + "sys/mylabel/delete",
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
			$.get(baseURL + "sys/mylabel/info/"+id, function(r){
                vm.myLabel = r.myLabel;
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
