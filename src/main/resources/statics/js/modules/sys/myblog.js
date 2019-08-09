layui.use('table', function () {
    var table = layui.table;
    table.render({
        elem: '#myblogTable'
        , height: 'full-75'
        , toolbar: '#toolbarDemo'
        , url: baseURL + 'sys/myblog/list' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'id', checkbox: true}
            , {field: 'title', title: '文章标题', width: 150, align: 'center'}
            , {field: 'banner', title: 'banner图', width: 150, align: 'center'}
            , {field: 'content', title: '内容', width: 200, align: 'center'}
            , {field: 'labelName', title: '主标签', width: 100, align: 'center'}
            , {field: 'nickName', title: '作者', width: 120, align: 'center'}
            , {field: 'insTime', title: '新增时间', width: 180, align: 'center'}
            , {field: 'reader', title: '阅读', width: 80, align: 'center'}
            , {field: 'comments', title: '评论', width: 80, align: 'center'}
            , {field: 'compliments', title: '点赞', width: 80, align: 'center'}
            , {
                field: 'isTop', title: '标签', width: 80, align: 'center', templet: function (d) {
                    var status = ''
                    if (d.isTop == 1) {
                        status += '<i class="fa fa-hand-o-up" title="置顶" aria-hidden="true"></i>  ';
                    }
                    if (d.isSelected == 1) {
                        status += '<i class="fa fa-thumbs-o-up" title="精选" aria-hidden="true"></i>';
                    }
                    return status;
                }
            }
            , {field: 'menu', title: '操作', width: 80, align: 'center'}
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

    table.on('toolbar(myblogTable)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'add':
                layer.msg('添加');
                break;
            case 'delete':
                layer.msg('删除');
                break;
            case 'update':
                layer.msg('编辑');
                break;
        };
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        myBlog: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.myBlog = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function () {
                var url = vm.myBlog.id == null ? "sys/myblog/save" : "sys/myblog/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.myBlog),
                    success: function (r) {
                        if (r.code === 0) {
                            layer.msg("操作成功", {icon: 1});
                            vm.reload();
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        } else {
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
            if (ids == null) {
                return;
            }
            var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                if (!lock) {
                    lock = true;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "sys/myblog/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function (r) {
                            if (r.code == 0) {
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            } else {
                                layer.alert(r.msg);
                            }
                        }
                    });
                }
            }, function () {
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "sys/myblog/info/" + id, function (r) {
                vm.myBlog = r.myBlog;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
});