layui.use(['table','form'], function () {
    var table = layui.table;
    var form = layui.form;
    table.render({
        elem: '#myblogTable'
        , id: 'myblogTable'
        , height: 'full-60'
        , url: '/sys/myblog/list' //数据接口
        , page: true //开启分页
        , limit: 15
        , cols: [[ //表头
            {field: 'id', checkbox: true}
            , {field: 'title', title: '文章标题',width: 350,  align: 'center'}
            , {field: 'labelName', title: '主标签',width: 180, align: 'center'}
            , {field: 'nickName', title: '作者',width: 150, align: 'center'}
            , {field: 'insTime', title: '新增时间',width: 180, align: 'center'}
            , {field: 'reader', title: '阅读', align: 'center'}
            , {field: 'comments', title: '评论', align: 'center'}
            , {field: 'compliments', title: '点赞', align: 'center'}
            , {
                field: 'isTop', title: '标签', align: 'center', templet: function (d) {
                    var status = ''
                    if (d.isTop == 1) {
                        status += '<span class="layui-badge layui-bg-blue" title="置顶"><i class="fa fa-hand-o-up" aria-hidden="true"></i></span>  ';
                    }
                    if (d.isSelected == 1) {
                        status += '<span class="layui-badge" title="精选"><i class="fa fa-thumbs-o-up" aria-hidden="true"></i></span>';
                    }
                    return status;
                }
            }
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

    //监听查询按钮
    form.on("submit(queryBtn)", function (data) {
        var condition = data.field;
        console.log(condition);
        //重载表格
        table.reload("myblogTable", {
            where: condition
        });
        return false;
    });

    var $ = layui.$, active = {
        add: function () {
            var checkStatus = table.checkStatus('myblogTable')
                , data = checkStatus.data;
            openEdit();
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
            openEdit(data[0].id);
        },
        delete: function () {
            var checkStatus = table.checkStatus('myblogTable')
                , data = checkStatus.data;
            if (data.length < 1) {
                return false;
            }
            var ids = [];
            for(var i in data){
                ids.push(data[i].id)
            }
            deleteBlogs(ids);
        },
        setTop: function () {
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
            setMyBlogIsTop(data[0].id);
        },
        setSelected: function () {
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
            setMyBlogIsSelected(data[0].id);
        }
    };

    $('.content .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    var openEdit = function (id) {
        if(id){
            window.location.href = 'blogEdit.html?id='+id;
        }else{
            window.location.href = 'blogEdit.html';
        }
    }

    var setMyBlogIsTop = function (id) {
        layer.confirm('确定置顶选中的文章？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                type: "POST",
                url: "/sys/myblog/setTop/"+id,
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        layer.msg("操作成功", {icon: 1},function () {
                            $("#reload").click();
                        });
                    } else {
                        layer.alert(r.msg);
                    }
                }
            });
        }, function () {
        });
    };

    var deleteBlogs = function (ids) {
        layer.confirm('确定删除选中的文章？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                type: "POST",
                url: "/sys/myblog/delete",
                data: JSON.stringify(ids),
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        layer.msg("操作成功", {icon: 1},function () {
                            $("#reload").click();
                        });
                    } else {
                        layer.alert(r.msg);
                    }
                }
            });
        }, function () {
        });
    };

    var getLabelList = function (id) {
        $.ajax({
            type: "POST",
            url: '/sys/mylabel/getSonLabelList',
            contentType: "application/json",
            success: function (r) {
                if (r.code === 0) {
                    console.log( r.data)
                }
            }
        });
    };

    var setMyBlogIsSelected = function (id) {
        layer.confirm('确定将选中文章置为精选？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                type: "POST",
                url: "/sys/myblog/setSelected/"+id,
                contentType: "application/json",
                success: function (r) {
                    if (r.code == 0) {
                        layer.msg("操作成功", {icon: 1},function () {
                            $("#reload").click();
                        });
                    } else {
                        layer.alert(r.msg);
                    }
                }
            });
        }, function () {
        });
    };

    var getLabelList = function (id) {
        $.ajax({
            type: "POST",
            async: false,
            url: '/sys/mylabel/getSonLabelList',
            contentType: "application/json",
            success: function (r) {
                if (r.code === 0) {
                    labelList = r.data;
                    var op = '';
                    for (var i in labelList) {
                        op += '<option value="' + labelList[i].tabId + '">' + labelList[i].tabName + '</option>';
                    }
                    $("#" + id).append(op);
                    form.render();
                }
            }
        });
    };

    layer.ready(function () {
        getLabelList();
        getLabelList('labelList')
    });
});
