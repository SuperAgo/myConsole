$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/myuser/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '真实姓名', name: 'userName', index: 'user_name', width: 80 }, 			
			{ label: '昵称', name: 'nickName', index: 'nick_name', width: 80 }, 			
			{ label: '手机号', name: 'telephone', index: 'telephone', width: 80 }, 			
			{ label: '邮箱', name: 'email', index: 'email', width: 80 }, 			
			{ label: '密码', name: 'password', index: 'password', width: 80 }, 			
			{ label: '生日', name: 'birthday', index: 'birthday', width: 80 }, 			
			{ label: '网络头像', name: 'networkAvatar', index: 'network_avatar', width: 80 }, 			
			{ label: '1男 2女', name: 'sex', index: 'sex', width: 80 },
			{ label: '个人签名', name: 'signature', index: 'signature', width: 80 }, 			
			{ label: '简介', name: 'introduction', index: 'introduction', width: 80 }, 			
			{ label: '爱好', name: 'hobby', index: 'hobby', width: 80 }, 			
			{ label: '地区', name: 'region', index: 'region', width: 80 }, 			
			{ label: '地区代码', name: 'regionCode', index: 'region_code', width: 80 }, 			
			{ label: '手机号', name: 'background', index: 'background', width: 80 }, 			
			{ label: '小图1', name: 'picOne', index: 'pic_one', width: 80 }, 			
			{ label: '小图2', name: 'picTwo', index: 'pic_two', width: 80 }, 			
			{ label: '小图3', name: 'picThree', index: 'pic_three', width: 80 }, 			
			{ label: '小图4', name: 'picFour', index: 'pic_four', width: 80 }, 			
			{ label: '留言板背景图', name: 'messageBoardPictures', index: 'message_board_pictures', width: 80 }, 			
			{ label: '留言板寄语', name: 'messageBoards', index: 'message_boards', width: 80 }, 			
			{ label: '状态 1使用中 0已删除', name: 'state', index: 'State', width: 80 },
			{ label: '0未实名 1已实名', name: 'struts', index: 'struts', width: 80 },
			{ label: '新增时间', name: 'insTime', index: 'ins_time', width: 80 }, 			
			{ label: '盐', name: 'salt', index: 'salt', width: 80 }, 			
			{ label: '1.博主 2.其他', name: 'type', index: 'type', width: 80 },
			{ label: '订阅状态:0未订阅，1已订阅', name: 'subscribe', index: 'subscribe', width: 80 }			
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
		myUser: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.myUser = {};
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
                var url = vm.myUser.id == null ? "sys/myuser/save" : "sys/myuser/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.myUser),
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
                        url: baseURL + "sys/myuser/delete",
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
			$.get(baseURL + "sys/myuser/info/"+id, function(r){
                vm.myUser = r.myUser;
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
});