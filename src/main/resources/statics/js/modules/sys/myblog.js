layui.use('table', function () {
    var table = layui.table;
    table.render({
        elem: '#myblogTable'
        , id: 'myblogTable'
        , height: 'full-60'
        , url: baseURL + 'sys/myblog/list' //数据接口
        , page: true //开启分页
        , limit: 15
        , cols: [[ //表头
            {field: 'id', checkbox: true}
            , {field: 'title', title: '文章标题', width: 150, align: 'center'}
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
                        status += '<span class="layui-badge layui-bg-blue"><i class="fa fa-hand-o-up" title="置顶" aria-hidden="true"></i></span>  ';
                    }
                    if (d.isSelected == 1) {
                        status += '<span class="layui-badge"><i class="fa fa-thumbs-o-up" title="精选" aria-hidden="true"></i></span>';
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

    var $ = layui.$, active = {
        reload: function () {

        },
        add: function () {
            var checkStatus = table.checkStatus('myblogTable')
                , data = checkStatus.data;
            console.log('选中了：' + data.length + ' 个');
            vm.add();
        },
        update: function () {
            var checkStatus = table.checkStatus('myblogTable')
                , data = checkStatus.data;
            if (data.length != 1) {
                if (data.length > 1) {
                    layer.msg('每次只能操作一条数据');
                    return false;
                } else {
                    return false;
                }
            }
            vm.title = "编辑";
            vm.getInfo(data[0].id);
        }
    };

    $('.demoTable .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    window.tableReload = function () {
        table.reload('myblogTable');
    };

});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        showMakeDown: true,
        title: null,
        myBlog: {},
        labelList: []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.myBlog = {};
            vm.openEdit("");
        },
        lastStep: function () {
            vm.showList = true;
            layer.closeAll();
        },
        nextStep: function () {
            vm.showMakeDown = false;
            layer.closeAll();
        },
        returnToEdit: function () {
            vm.showMakeDown = true;
            console.log(vm.myBlog.content)
            vm.openEdit(vm.myBlog.content);
        },
        openEdit:function(content){
            var weight = $("html").width() + "px";
            var height = $("html").height() + "px";
            layer.open({
                title: vm.title,
                type: 2,
                area: [weight, height], //宽高
                content: 'simple.html',
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    body.find('textarea').val(content);
                    vm.showList = false;
                },
                cancel: function () {
                    vm.lastStep();
                }
            });
        },
        getLabelList: function () {
            $.ajax({
                type: "POST",
                url: baseURL + 'sys/mylabel/getSonLabelList',
                contentType: "application/json",
                success: function (r) {
                    if (r.code === 0) {
                        vm.labelList = r.data;
                    }
                }
            });
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
            if (vm.labelList.length == 0) {
                vm.getLabelList();
            }
            $.get(baseURL + "sys/myblog/info/" + id, function (r) {
                vm.myBlog = r.myBlog;
                vm.openEdit(r.myBlog.content);
            });
        },
        reload: function (event) {
            vm.showList = true;
            tableReload();
        }
    }
});